package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Align;

public abstract class ScalingButton extends ScalingSprite implements ScalingElement.ElementListener {
    private final float hoverScale;

    public ScalingButton(Sprite sprite, float x, float y, int align, int screenWidth, int screenHeight, float hoverScale) {
        super(sprite, x, y, align, screenWidth, screenHeight);
        this.hoverScale = hoverScale;
        this.addListener(this);
    }

    public ScalingButton(ScalingScene scene, Sprite sprite, float x, float y, int align, float hoverScale) {
        this(sprite, x, y, align, scene.getNativeWidth(), scene.getNativeHeight(), hoverScale);
        scene.add(this);
    }

    @Override
    public void onHover() {
        float newX = getX();
        float newY = getY();
        float newWidth = getWidth() * hoverScale;
        float newHeight = getHeight() * hoverScale;

        SPRITE.setSize(newWidth, newHeight);
        switch (alignment) {
            case Align.center:
                SPRITE.setPosition(newX - newWidth / 2f, newY - newHeight / 2f);
                break;
            case Align.right:
                SPRITE.setPosition(newX - newWidth, newY - newHeight / 2f);
                break;
            case Align.topRight:
                SPRITE.setPosition(newX - newWidth, newY - newHeight);
                break;
            case Align.top:
                SPRITE.setPosition(newX - newWidth / 2f, newY - newHeight);
                break;
            case Align.topLeft:
                SPRITE.setPosition(newX, newY - newHeight);
                break;
            case Align.left:
                SPRITE.setPosition(newX, newY - newHeight / 2f);
                break;
            case Align.bottomLeft:
                SPRITE.setPosition(newX, newY);
                break;
            case Align.bottom:
                SPRITE.setPosition(newX - newWidth / 2f, newY);
                break;
            case Align.bottomRight:
                SPRITE.setPosition(newX - newWidth, newY);
                break;
        }
    }

    @Override
    public void onNotHover() {
        SPRITE.setBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }
}
