package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScalingAnchor extends ScalingElement {
    public ScalingAnchor(float x, float y, int alignment, int screenWidth, int screenHeight) {
        super(x, y, 1f, 1f, alignment, screenWidth, screenHeight);
    }

    @Override
    protected final void render(SpriteBatch batch) {
    }

    @Override
    protected final void updateFromBounds(float x, float y, float width, float height) {
    }
}
