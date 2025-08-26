package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.Gdx;

public abstract class ScalingObject {
    protected float
            nativeX, nativeY, // Original Position
            nativeWidth, nativeHeight, // Original Dimensions
            percentX, percentY; // Position Percentage According to Screen Width and Height
    protected int nativeScreenWidth, nativeScreenHeight;
    protected float currX, currY, currWidth, currHeight, scale = 1f;

    public ScalingObject(float x, float y, float width, float height, int screenWidth, int screenHeight) {
        this.nativeX = x;
        this.nativeY = y;

        this.nativeWidth = width;
        this.nativeHeight = height;

        this.nativeScreenWidth = screenWidth;
        this.nativeScreenHeight = screenHeight;

        // Get percentage of screen covered
        this.percentX = x / screenWidth;
        this.percentY = y / screenHeight;
    }

    public final void resize(int screenWidth, int screenHeight, float scale) {
        this.scale = scale;
        currX = percentX * screenWidth;
        currY = percentY * screenHeight;
        currWidth = nativeWidth * scale;
        currHeight = nativeHeight * scale;
        resize(currX, currY, currWidth, currHeight, scale);
    }

    /**
     * Provides custom application for resizing.
     *
     * @param newX          The new x-pos to set to.
     * @param newY          The new y-pos to set to.
     * @param newWidth      The new width to set to (can use scale if preferred).
     * @param newHeight     The new height to set to (can use scale if preferred).
     * @param optionalScale The new scale to set to (can use new dimensions directly if preferred).
     */
    protected abstract void resize(float newX, float newY, float newWidth, float newHeight, float optionalScale);

    public void setNativeX(float x, boolean updateCurrX) {
        nativeX = x;
        percentX = x / nativeScreenWidth;
        if (updateCurrX) {
            currX = percentX * Gdx.graphics.getWidth();
        }
    }

    public void setNativeY(float y, boolean updateCurrY) {
        nativeY = y;
        percentY = y / nativeScreenHeight;
        if (updateCurrY) {
            currY = percentY * Gdx.graphics.getHeight();
        }
    }

    public void setNativeWidth(float width, boolean updateCurrWidth) {
        nativeWidth = width;
        if (updateCurrWidth) {
            currWidth = nativeWidth * scale;
        }
    }

    public void setNativeHeight(float height, boolean updateCurrHeight) {
        nativeHeight = height;
        if (updateCurrHeight) {
            currHeight = nativeHeight * scale;
        }
    }

    public final float getNativeX() {
        return nativeX;
    }

    public final float getNativeY() {
        return nativeY;
    }

    public final float getNativeWidth() {
        return nativeWidth;
    }

    public final float getNativeHeight() {
        return nativeHeight;
    }

    public final int getNativeScreenWidth() {
        return nativeScreenWidth;
    }

    public final int getNativeScreenHeight() {
        return nativeScreenHeight;
    }

    public final float getX() {
        return currX;
    }

    public final float getY() {
        return currY;
    }

    public final float getWidth() {
        return currWidth;
    }

    public final float getHeight() {
        return currHeight;
    }
}
