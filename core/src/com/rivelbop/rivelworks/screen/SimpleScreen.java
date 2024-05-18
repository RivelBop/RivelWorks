package com.rivelbop.rivelworks.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * A simplified screen class that removes the less used Screen methods.
 *
 * @author David Jerzak (RivelBop)
 */
public abstract class SimpleScreen implements Screen {
    /**
     * Stores a reference to the {@link Game} that is handling the {@link Screen}.
     */
    public final Game game;

    /**
     * Creates a new screen with the provided {@link Game}.
     *
     * @param game A reference to the game that handles this Screen.
     */
    public SimpleScreen(Game game) {
        this.game = game;
    }

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
    }
}