package com.rivelbop.rivelworks.preset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A basic preset for vehicle movement.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class VehicleMovementPreset {
    /**
     * Sprite effected by vehicle movement.
     */
    private Sprite sprite;

    /**
     * Movement values that will remain mostly unchanged.
     */
    private float acceleration, deceleration, maxSpeed, rotationSpeed;

    /**
     * Stores the velocity of the sprite, applied to the translation.
     */
    private float velocity;

    /**
     * Creates a vehicle from the provided sprite and movement data.
     *
     * @param sprite        The sprite effected by the vehicle movement.
     * @param acceleration  The acceleration of the sprite.
     * @param deceleration  The deceleration of the sprite.
     * @param maxSpeed      The maximum speed of the sprite.
     * @param rotationSpeed The rotation speed of the sprite.
     */
    public VehicleMovementPreset(Sprite sprite, float acceleration, float deceleration, float maxSpeed, float rotationSpeed) {
        this.sprite = sprite;
        this.acceleration = acceleration;
        this.deceleration = deceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }

    /**
     * Updates the sprite position and rotation according to the movement.
     */
    public void update() {
        boolean isMoving = false;
        float delta = Gdx.graphics.getDeltaTime();

        // Rotation bases off PI/2 instead of 0
        float xAngle = (float) Math.cos(Math.toRadians(sprite.getRotation() + 90f));
        float yAngle = (float) Math.sin(Math.toRadians(sprite.getRotation() + 90f));

        // Accelerates vehicle in the forward direction
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            velocity += acceleration * delta;
            isMoving = true;
        }

        // Accelerates vehicle in the backward direction
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
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

        // Cap the velocity of the vehicle and translate
        velocity = (velocity < 0f ? -1f : 1f) * Math.min(Math.abs(velocity), maxSpeed);
        sprite.translate(xAngle * velocity * delta, yAngle * velocity * delta);

        sprite.setOriginCenter();

        // Rotate left
        if (velocity != 0f && Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.rotate(rotationSpeed * delta);
        }

        // Rotate right
        if (velocity != 0f && Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.rotate(-rotationSpeed * delta);
        }
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
        this.acceleration = Math.abs(acceleration);
    }

    /**
     * Sets the vehicle's deceleration.
     *
     * @param deceleration The deceleration to set.
     */
    public void setDeceleration(float deceleration) {
        this.deceleration = Math.abs(deceleration);
    }

    /**
     * Sets the vehicle's maximum speed.
     *
     * @param maxSpeed The speed to set.
     */
    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = Math.abs(maxSpeed);
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
        return acceleration;
    }

    /**
     * @return The vehicle's deceleration.
     */
    public float getDeceleration() {
        return deceleration;
    }

    /**
     * @return The vehicle's maximum speed.
     */
    public float getMaxSpeed() {
        return maxSpeed;
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
        return velocity;
    }
}