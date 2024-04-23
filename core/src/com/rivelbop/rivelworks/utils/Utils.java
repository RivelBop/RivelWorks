package com.rivelbop.rivelworks.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

/**
 * Stores a variety of common useful utilities.
 *
 * @author David Jerzak (RivelBop)
 */
public class Utils {
    /**
     * Clears the screen for 2D games (only clears the color buffer).
     */
    public static void clearScreen2D() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Clears the screen for 3D games (clears both the color and depth buffers).
     */
    public static void clearScreen3D() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
    }
}