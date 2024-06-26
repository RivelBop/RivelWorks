package com.rivelbop.rivelworks.preset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rivelbop.rivelworks.physics2d.body.DynamicBody2D;

/**
 * A preset for physics basic top down, 8-directional movement.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class TopDownMovementPhysicsPreset {
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
        Body body = this.BODY.getBody();
        body.setLinearVelocity(Vector2.Zero);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            body.setLinearVelocity(body.getLinearVelocity().x, speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            body.setLinearVelocity(-speed, body.getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            body.setLinearVelocity(body.getLinearVelocity().x, -speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            body.setLinearVelocity(speed, body.getLinearVelocity().y);
        }

        sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2f, body.getPosition().y * PPM - sprite.getHeight() / 2f);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void setSpeed(float speed) {
        this.speed = Math.abs(speed) / PPM;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public DynamicBody2D getBody() {
        return BODY;
    }

    public float getSpeed() {
        return speed * PPM;
    }

    public float getPPM() {
        return PPM;
    }
}