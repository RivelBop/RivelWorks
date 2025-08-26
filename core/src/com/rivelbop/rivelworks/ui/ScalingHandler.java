package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.utils.Array;

public class ScalingHandler {
    private final int NATIVE_WIDTH, NATIVE_HEIGHT;
    private final float NATIVE_ASPECT_RATIO;
    private final boolean HORIZONTAL;

    private int currWidth, currHeight;
    private float currAspectRatio, scale;

    protected final Array<ScalingObject> SCALABLE_OBJECTS = new Array<>();

    public ScalingHandler(int width, int height) {
        this.NATIVE_WIDTH = width;
        this.NATIVE_HEIGHT = height;
        this.NATIVE_ASPECT_RATIO = (float) width / height;
        this.HORIZONTAL = NATIVE_ASPECT_RATIO > 1f;
    }

    public void resize(int width, int height) {
        if (width == 0 || height == 0) {
            return;
        }

        currWidth = width;
        currHeight = height;
        currAspectRatio = (float) currWidth / currHeight;

        if (HORIZONTAL) { // 16:9, etc.
            if (currAspectRatio > NATIVE_ASPECT_RATIO) {
                scale = (float) height / NATIVE_HEIGHT; // Scale by the smaller dimension
            } else {
                scale = (float) width / NATIVE_WIDTH;
            }
        } else { // 9:16, etc.
            if (currAspectRatio > NATIVE_ASPECT_RATIO) {
                scale = (float) width / NATIVE_WIDTH;
            } else {
                scale = (float) height / NATIVE_HEIGHT;
            }
        }

        for (ScalingObject obj : SCALABLE_OBJECTS) {
            obj.resize(currWidth, currHeight, scale);
        }
    }

    public void add(ScalingObject obj) {
        SCALABLE_OBJECTS.add(obj);
    }

    public void remove(ScalingObject obj) {
        SCALABLE_OBJECTS.removeValue(obj, true);
    }

    public int getNativeWidth() {
        return NATIVE_WIDTH;
    }

    public int getNativeHeight() {
        return NATIVE_HEIGHT;
    }

    public float getNativeAspectRatio() {
        return NATIVE_ASPECT_RATIO;
    }

    public int getWidth() {
        return currWidth;
    }

    public int getHeight() {
        return currHeight;
    }

    public float getAspectRatio() {
        return currAspectRatio;
    }

    public float getScale() {
        return scale;
    }

    public boolean isHorizontalRatio() {
        return HORIZONTAL;
    }

    public Array<ScalingObject> getObjects() {
        return SCALABLE_OBJECTS;
    }
}
