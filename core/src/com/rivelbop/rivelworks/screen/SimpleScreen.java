package com.rivelbop.rivelworks.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.esotericsoftware.minlog.Log;

/**
 * A simplified screen class that removes the less used Screen methods.
 *
 * @author David Jerzak (RivelBop)
 */
public abstract class SimpleScreen implements Screen {
    private static final String LOG_TAG = SimpleScreen.class.getSimpleName();

    /**
     * Stores a reference to the {@link Game} that is handling the {@link Screen}.
     */
    public final Game GAME = (Game) Gdx.app.getApplicationListener();

    /**
     * Called when window is minimized, not commonly used.
     */
    @Override
    public void pause() {
    }

    /**
     * Called when window is maximized, not commonly used.
     */
    @Override
    public void resume() {
    }

    /**
     * When the Screen is hidden, dispose of the Screen.
     */
    @Override
    public void hide() {
        dispose();
        Log.debug(LOG_TAG, "{" + getClass().getSimpleName() + "} was automatically disposed.");
    }
}