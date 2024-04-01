package com.rivelbop.rivelworks.math;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;

/**
 * An easy to use {@link Interpolation} handler.
 *
 * @author David Jerzak (RivelBop)
 */
public class Interpolator {
    /**
     * Handles the calculations necessary for interpolations.
     */
    private final Interpolation INTERPOLATION;

    /**
     * Stores the lifetime of the interpolation.
     */
    private final float LIFE_TIME;

    /**
     * Stores the current time that the interpolation has been running for.
     */
    private float currentTime;

    /**
     * Stores the completion status of the interpolation.
     */
    private boolean isComplete;

    /**
     * Creates an Interpolator based on the provided interpolation type and lifetime.
     *
     * @param interpolation The type of interpolation.
     * @param lifeTime      The lifetime of the interpolation.
     */
    public Interpolator(Interpolation interpolation, float lifeTime) {
        this.INTERPOLATION = interpolation;
        this.LIFE_TIME = lifeTime;
    }

    /**
     * Updates the {@link #currentTime} and applies it to the interpolation, returning the value produced.
     *
     * @return The interpolation value based in the amount of time that has passed.
     */
    public float update() {
        currentTime += Gdx.graphics.getDeltaTime();

        float interVal = INTERPOLATION.apply(Math.min(1f, currentTime / LIFE_TIME));
        isComplete = (interVal == 1f);
        return interVal;
    }

    /**
     * Check to see if the interpolation is complete.
     *
     * @return {@link #isComplete}
     */
    public boolean isComplete() {
        return isComplete;
    }
}