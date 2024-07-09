package com.rivelbop.rivelworks.preset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rivelbop.rivelworks.g2d.physics.body.DynamicBody2D;

/**
 * A physics preset for vehicle movement.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class VehicleMovementPhysicsPreset extends InputAdapter {
    /**
     * Stores the direction being actively held.
     */
    protected boolean forward, left, back, right;

    /**
     * Pixels per meter conversion constant.
     */
    private final float PPM;

    /**
     * Provides the visual of the vehicle.
     */
    private Sprite sprite;

    /**
     * The moving physics body.
     */
    private final DynamicBody2D BODY;

    /**
     * Movement values that will remain mostly unchanged.
     */
    private float acceleration, deceleration, maxSpeed, rotationSpeed;

    /**
     * Stores the velocity of the body, applied as the linear velocity.
     */
    private float velocity;

    /**
     * Creates a vehicle from the provided sprite, physics body, movement constants, and pixels per meter conversion.
     *
     * @param sprite        The visual for the body.
     * @param body          The physics body that is moved.
     * @param acceleration  The acceleration of the body.
     * @param deceleration  The deceleration of the body.
     * @param maxSpeed      The maximum speed of the body.
     * @param rotationSpeed The speed at which the body rotates.
     * @param PPM           Pixels per meter conversion.
     */
    public VehicleMovementPhysicsPreset(Sprite sprite, DynamicBody2D body, float acceleration, float deceleration, float maxSpeed, float rotationSpeed, float PPM) {
        this.sprite = sprite;
        this.BODY = body;
        this.acceleration = acceleration / PPM;
        this.deceleration = deceleration / PPM;
        this.maxSpeed = maxSpeed / PPM;
        this.rotationSpeed = rotationSpeed;
        this.PPM = PPM;
    }

    /**
     * Updates the body position and rotation, along with its sprite.
     */
    public void update() {
        boolean isMoving = false;
        float delta = Gdx.graphics.getDeltaTime();

        Body physicsBody = BODY.getBody();
        Vector2 position = physicsBody.getTransform().getPosition();

        // Rotation bases off PI/2 instead of 0
        float rotation = physicsBody.getAngle() + 90f * MathUtils.radiansToDegrees;
        float xAngle = MathUtils.cos(rotation);
        float yAngle = MathUtils.sin(rotation);

        // Accelerates vehicle in the forward direction
        if (forward) {
            velocity += acceleration * delta;
            isMoving = true;
        }

        // Accelerates vehicle in the backward direction
        if (back) {
            velocity -= acceleration * delta;
            isMoving = true;
        }

        // Decelerates vehicle if no input
        if (!isMoving && velocity != 0f) {
            velocity += (velocity < 0f ? 1f : -1f) * deceleration * delta;
            velocity = (velocity < 0f ? -1f : 1f) * Math.max(Math.abs(velocity), 0f);

            if (Math.abs(velocity) < 0.01f) {
                velocity = 0f;
            }
        }

        // Cap the velocity of the vehicle and set linear velocity of body
        velocity = (velocity < 0f ? -1f : 1f) * Math.min(Math.abs(velocity), maxSpeed);
        physicsBody.setLinearVelocity(xAngle * velocity, yAngle * velocity);

        // Rotate left
        if (velocity != 0f && left) {
            physicsBody.setTransform(position.x, position.y, physicsBody.getAngle() + rotationSpeed * MathUtils.degreesToRadians * delta);
        }

        // Rotate right
        if (velocity != 0f && right) {
            physicsBody.setTransform(position.x, position.y, physicsBody.getAngle() - rotationSpeed * MathUtils.degreesToRadians * delta);
        }

        // Update the sprite's position and rotation according to the body's
        sprite.setPosition(position.x * PPM - sprite.getWidth() / 2f, position.y * PPM - sprite.getHeight() / 2f);
        sprite.setOriginCenter();
        sprite.setRotation(physicsBody.getAngle() * MathUtils.radiansToDegrees);
    }

    /**
     * Sets the vehicle's sprite.
     *
     * @param sprite The sprite to set.
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Sets the vehicle's acceleration.
     *
     * @param acceleration The acceleration to set.
     */
    public void setAcceleration(float acceleration) {
        this.acceleration = Math.abs(acceleration) / PPM;
    }

    /**
     * Sets the vehicle's deceleration.
     *
     * @param deceleration The deceleration to set.
     */
    public void setDeceleration(float deceleration) {
        this.deceleration = Math.abs(deceleration) / PPM;
    }

    /**
     * Sets the vehicle's maximum speed.
     *
     * @param maxSpeed The speed to set.
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = Math.abs(maxSpeed) / PPM;
    }

    /**
     * Sets the vehicle's rotation speed.
     *
     * @param rotationSpeed The speed to set.
     */
    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = Math.abs(rotationSpeed);
    }

    /**
     * @return The vehicle's sprite.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @return The vehicle's acceleration.
     */
    public float getAcceleration() {
        return acceleration * PPM;
    }

    /**
     * @return The vehicle's deceleration.
     */
    public float getDeceleration() {
        return deceleration * PPM;
    }

    /**
     * @return The vehicle's maximum speed.
     */
    public float getMaxSpeed() {
        return maxSpeed * PPM;
    }

    /**
     * @return The vehicle's rotation speed.
     */
    public float getRotationSpeed() {
        return rotationSpeed;
    }

    /**
     * @return The vehicle's velocity.
     */
    public float getVelocity() {
        return velocity * PPM;
    }

    /**
     * @return The pixels per meter conversion constant.
     */
    public float getPPM() {
        return PPM;
    }

    /**
     * @return The vehicle's physics body.
     */
    public DynamicBody2D getBody() {
        return BODY;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                forward = false;
                break;
            case Input.Keys.A:
                left = false;
                break;
            case Input.Keys.S:
                back = false;
                break;
            case Input.Keys.D:
                right = false;
                break;
        }
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                forward = true;
                break;
            case Input.Keys.A:
                left = true;
                break;
            case Input.Keys.S:
                back = true;
                break;
            case Input.Keys.D:
                right = true;
                break;
        }
        return true;
    }
}