package com.rivelbop.rivelworks;

import com.badlogic.gdx.Game;
import com.esotericsoftware.minlog.Log;

/**
 * A basic example game class to use for RivelWorks.
 *
 * @author David Jerzak (RivelBop)
 */
public class RivelWorksGame extends Game {
    @Override
    public void create() {
        // Adjust to your desires
        RivelWorks.init(Log.LEVEL_DEBUG, "RIVELWORKS_LOG.txt", true, true);
        // YOUR CODE BELOW
    }

    @Override
    public void render() {
        super.render(); // Updates and renders the current screen
    }

    @Override
    public void dispose() {
        // Normally hides the screen but Game is an ApplicationListener so it will likely need to dispose the screen upon disposal
        if (screen != null) {
            screen.dispose();
        }
        RivelWorks.dispose(); // Should be disposed at the end
    }
}