package com.rivelbop.rivelworks.preset;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * A preset for non-physics basic top down, 8-directional movement.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class TopDownMovementPreset {
    /**
     * The sprite that the movement will be applied to.
     */
    private Sprite sprite;

    /**
     * The speed at which the object will travel.
     */
    private float speed;

    /**
     * Creates a top-down movement object from the provided sprite object and its initial speed.
     *
     * @param sprite The sprite to apply the movements to.
     * @param speed  The speed at which the object will travel.
     */
    public TopDownMovementPreset(Sprite sprite, float speed) {
        this.sprite = sprite;
        this.speed = speed;
    }

    /**
     * Updates the objects movement according to its input (8 total directions).
     */
    public void update() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            sprite.translateY(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            sprite.translateX(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            sprite.translateY(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            sprite.translateX(speed * delta);
        }
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
        this.speed = Math.abs(speed);
    }

    /**
     * @return The presets' sprite.
     */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * @return The speed of the sprite.
     */
    public float getSpeed() {
        return speed;
    }
}