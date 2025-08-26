package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ScalingSprite extends ScalingElement {
    protected final Sprite SPRITE;

    public ScalingSprite(Sprite sprite, float x, float y, int align, int screenWidth, int screenHeight) {
        super(x, y, sprite.getWidth(), sprite.getHeight(), align, screenWidth, screenHeight);
        this.SPRITE = sprite;
        SPRITE.setPosition(bounds.x, bounds.y);
    }

    public ScalingSprite(ScalingScene scene, Sprite sprite, float x, float y, int align) {
        this(sprite, x, y, align, scene.getNativeWidth(), scene.getNativeHeight());
        scene.add(this);
    }

    @Override
    protected void render(SpriteBatch batch) {
        SPRITE.draw(batch);
    }

    @Override
    protected void updateFromBounds(float x, float y, float width, float height) {
        SPRITE.setBounds(x, y, width, height);
    }

    public Sprite getSprite() {
        return SPRITE;
    }
}
