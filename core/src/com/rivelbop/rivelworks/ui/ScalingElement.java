package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Align;

public abstract class ScalingElement extends ScalingObject {
    public interface ElementListener {
        void onHover();

        void onNotHover();

        void onPress();
    }

    public static final ElementListener LISTENER = new ElementListener() {
        @Override
        public void onHover() {
        }

        @Override
        public void onNotHover() {
        }

        @Override
        public void onPress() {
        }
    };

    protected ScalingScene scene;
    protected ElementListener listener;
    protected Rectangle bounds;
    protected int alignment;

    public ScalingElement(float x, float y, float width, float height, int alignment, int screenWidth, int screenHeight, ElementListener listener) {
        super(x, y, width, height, screenWidth, screenHeight);
        this.alignment = alignment;
        this.bounds = new Rectangle();
        updateBounds(x, y, width, height);
        addListener(listener);
    }

    public ScalingElement(ScalingScene scene, float x, float y, float width, float height, int alignment, ElementListener listener) {
        this(x, y, width, height, alignment, scene.getNativeWidth(), scene.getNativeHeight(), listener);
        scene.add(this);
    }

    public ScalingElement(float x, float y, float width, float height, int alignment, int screenWidth, int screenHeight) {
        this(x, y, width, height, alignment, screenWidth, screenHeight, LISTENER);
    }

    public ScalingElement(ScalingScene scene, float x, float y, float width, float height, int alignment) {
        this(scene, x, y, width, height, alignment, LISTENER);
    }

    public void addListener(ElementListener listener) {
        this.listener = listener;
    }

    protected abstract void render(SpriteBatch batch);

    @Override
    protected void resize(float newX, float newY, float newWidth, float newHeight, float optionalScale) {
        totalBoundsUpdate(newX, newY, newWidth, newHeight);
    }

    public void setAlignment(int alignment) {
        this.alignment = alignment;
        totalBoundsUpdate(currX, currY, currWidth, currHeight);
    }

    protected void updateBounds(float newX, float newY, float newWidth, float newHeight) {
        bounds.setSize(newWidth, newHeight);
        switch (alignment) {
            case Align.center:
                bounds.setPosition(newX - newWidth / 2f, newY - newHeight / 2f);
                break;
            case Align.right:
                bounds.setPosition(newX - newWidth, newY - newHeight / 2f);
                break;
            case Align.topRight:
                bounds.setPosition(newX - newWidth, newY - newHeight);
                break;
            case Align.top:
                bounds.setPosition(newX - newWidth / 2f, newY - newHeight);
                break;
            case Align.topLeft:
                bounds.setPosition(newX, newY - newHeight);
                break;
            case Align.left:
                bounds.setPosition(newX, newY - newHeight / 2f);
                break;
            case Align.bottomLeft:
                bounds.setPosition(newX, newY);
                break;
            case Align.bottom:
                bounds.setPosition(newX - newWidth / 2f, newY);
                break;
            case Align.bottomRight:
                bounds.setPosition(newX - newWidth, newY);
                break;
        }
    }

    protected abstract void updateFromBounds(float x, float y, float width, float height);

    @Override
    public void setNativeX(float x, boolean updateCurrX) {
        super.setNativeX(x, updateCurrX);
        if (updateCurrX) {
            totalBoundsUpdate(currX, currY, currWidth, currHeight);
        }
    }

    @Override
    public void setNativeY(float y, boolean updateCurrY) {
        super.setNativeY(y, updateCurrY);
        if (updateCurrY) {
            totalBoundsUpdate(currX, currY, currWidth, currHeight);
        }
    }

    @Override
    public void setNativeWidth(float width, boolean updateCurrWidth) {
        super.setNativeWidth(width, updateCurrWidth);
        if (updateCurrWidth) {
            totalBoundsUpdate(currX, currY, currWidth, currHeight);
        }
    }

    @Override
    public void setNativeHeight(float height, boolean updateCurrHeight) {
        super.setNativeHeight(height, updateCurrHeight);
        if (updateCurrHeight) {
            totalBoundsUpdate(currX, currY, currWidth, currHeight);
        }
    }

    private void totalBoundsUpdate(float x, float y, float width, float height) {
        updateBounds(x, y, width, height);
        updateFromBounds(bounds.x, bounds.y, bounds.width, bounds.height);
    }

    public ScalingScene getScene() {
        return scene;
    }

    public ElementListener getListener() {
        return listener;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public int getAlignment() {
        return alignment;
    }
}
