package com.rivelbop.rivelworks.preset;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rivelbop.rivelworks.g2d.physics.body.DynamicBody2D;

/**
 * A preset for physics basic top down, 8-directional movement.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class TopDownMovementPhysicsPreset extends InputAdapter {
    /**
     * Stores the direction being actively held.
     */
    protected boolean forward, left, back, right;

    /**
     * Pixels per meter conversion constant.
     */
    private final float PPM;

    /**
     * The sprite used to display the body's position.
     */
    private Sprite sprite;

    /**
     * The body to apply the movement to.
     */
    private final DynamicBody2D BODY;

    /**
     * The speed at which the body will travel by.
     */
    private float speed;

    /**
     * Creates a top-down movement preset by providing the sprite to render, the body to move, the speed to move by, and the pixels per meter conversion.
     *
     * @param sprite The visual representation of the body.
     * @param body   The body to be moved.
     * @param speed  The speed at which the body will move initially.
     * @param PPM    The pixels per meter conversion constant.
     */
    public TopDownMovementPhysicsPreset(Sprite sprite, DynamicBody2D body, float speed, float PPM) {
        this.sprite = sprite;
        this.BODY = body;
        this.speed = speed / PPM;
        this.PPM = PPM;
    }

    /**
     * Updates the bodies velocity according to the speed and updates its sprite.
     */
    public void update() {
        Body body = BODY.getBody();
        body.setLinearVelocity(Vector2.Zero);

        if (forward) {
            body.setLinearVelocity(body.getLinearVelocity().x, speed);
        }
        if (left) {
            body.setLinearVelocity(-speed, body.getLinearVelocity().y);
        }
        if (back) {
            body.setLinearVelocity(body.getLinearVelocity().x, -speed);
        }
        if (right) {
            body.setLinearVelocity(speed, body.getLinearVelocity().y);
        }

        sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2f, body.getPosition().y * PPM - sprite.getHeight() / 2f);
    }

    /**
     * Sets this presets' sprite to move.
     *
     * @param sprite The sprite to move.
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Sets this presets' movement speed.
     *
     * @param speed The speed to set.
     */
    public void setSpeed(float speed) {
        this.speed = Math.abs(speed) / PPM;
    }

    /**
     * @return The presets' sprite.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @return The presets' physics body.
     */
    public DynamicBody2D getBody() {
        return BODY;
    }

    /**
     * @return The speed of the physics body.
     */
    public float getSpeed() {
        return speed * PPM;
    }

    /**
     * @return The pixel per meter conversion constant.
     */
    public float getPPM() {
        return PPM;
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