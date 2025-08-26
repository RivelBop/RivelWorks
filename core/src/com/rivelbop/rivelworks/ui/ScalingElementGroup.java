package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Keys;

public class ScalingElementGroup extends ScalingElement {
    protected final ScalingElement ANCHOR;
    protected final ObjectMap<ScalingElement, Vector2> ELEMENTS;
    private boolean hasBegun;

    public ScalingElementGroup(ScalingElement anchor) {
        super(
                anchor.nativeX, anchor.nativeY,
                anchor.nativeWidth, anchor.nativeHeight,
                anchor.alignment,
                anchor.nativeScreenWidth, anchor.nativeScreenHeight
        );
        this.ANCHOR = anchor;
        this.ELEMENTS = new ObjectMap<>();
    }

    public ScalingElementGroup(float x, float y, int align, int screenWidth, int screenHeight) {
        this(new ScalingAnchor(x, y, align, screenWidth, screenHeight));
    }

    public ScalingElementGroup(ScalingScene scene, float x, float y, int align) {
        this(x, y, align, scene.getNativeWidth(), scene.getNativeHeight());
    }

    public void begin(ScalingScene scene, boolean clearAll) {
        if (this.scene != null) {
            this.scene.remove(this);
        }
        this.scene = scene;
        if (ANCHOR.scene != scene) {
            if (ANCHOR.scene != null) {
                ANCHOR.scene.remove(ANCHOR);
            }
            scene.add(ANCHOR);
        }

        if (clearAll) {
            if (ELEMENTS.notEmpty()) {
                for (ScalingElement e : ELEMENTS.keys()) {
                    if (e.scene != null) {
                        e.scene.remove(e);
                    }
                }
                ELEMENTS.clear();
            }
        }

        hasBegun = true;
    }

    public void begin(ScalingScene scene) {
        begin(scene, false);
    }

    public void add(ScalingElement element, float offsetX, float offsetY) {
        if (!hasBegun) {
            return;
        }

        element.setNativeX(offsetX, false);
        element.setNativeY(offsetY, false);

        ELEMENTS.put(element, new Vector2(offsetX, offsetY));
        if (element.scene != scene) {
            if (element.scene != null) {
                element.scene.remove(element);
            }
            scene.add(element);
        }
    }

    public void add(ScalingElement element) {
        add(element, element.getNativeX(), element.getNativeY());
    }

    public void end() {
        if (!hasBegun) {
            return;
        }
        scene.add(this);
        hasBegun = false;
    }

    @Override
    public final void addListener(ElementListener listener) {
    }

    @Override
    protected final void render(SpriteBatch batch) {
    }

    protected void updateBounds(float newX, float newY, float newWidth, float newHeight) {
        if (ANCHOR != null) {
            super.updateBounds(ANCHOR.currX, ANCHOR.currY, ANCHOR.currWidth, ANCHOR.currHeight);
            return;
        }
        super.updateBounds(newX, newY, newWidth, newHeight);
    }

    public void updateElementBounds(ScalingElement element) {
        Rectangle bounds = this.getBounds();
        float x = bounds.x;
        float y = bounds.y;
        float width = bounds.width;
        float height = bounds.height;

        Vector2 offset = ELEMENTS.get(element);
        offset.set(element.getNativeX(), element.getNativeY());
        element.updateBounds(x + width / 2f + offset.x * scene.getScale(), y + height / 2f + offset.y * scene.getScale(), element.currWidth, element.currHeight);

        Rectangle b = element.bounds;
        element.updateFromBounds(b.x, b.y, b.width, b.height);

        switch (element.alignment) {
            case Align.center:
                element.currX = b.x + b.width / 2f;
                element.currY = b.y + b.height / 2f;
                break;
            case Align.right:
                element.currX = b.x + b.width;
                element.currY = b.y + b.height / 2f;
                break;
            case Align.topRight:
                element.currX = b.x + b.width;
                element.currY = b.y + b.height;
                break;
            case Align.top:
                element.currX = b.x + b.width / 2f;
                element.currY = b.y + b.height;
                break;
            case Align.topLeft:
                element.currX = b.x;
                element.currY = b.y + b.height;
                break;
            case Align.left:
                element.currX = b.x;
                element.currY = b.y + b.height / 2f;
                break;
            case Align.bottomLeft:
                element.currX = b.x;
                element.currY = b.y;
                break;
            case Align.bottom:
                element.currX = b.x + b.width / 2f;
                element.currY = b.y;
                break;
            case Align.bottomRight:
                element.currX = b.x + b.width;
                element.currY = b.y;
                break;
        }
    }

    @Override
    protected void updateFromBounds(float x, float y, float width, float height) {
        for (ScalingElement element : ELEMENTS.keys()) {
            updateElementBounds(element);
        }
    }

    public void setOffsetX(ScalingElement e, float x) {
        e.setNativeX(x, false);
        updateElementBounds(e);
    }

    public void setOffsetY(ScalingElement e, float y) {
        e.setNativeY(y, false);
        updateElementBounds(e);
    }

    public void setOffset(ScalingElement e, float x, float y) {
        e.setNativeX(x, false);
        e.setNativeY(y, false);
        updateElementBounds(e);
    }

    public ScalingElement getAnchor() {
        return ANCHOR;
    }

    public ObjectMap<ScalingElement, Vector2> getElementMap() {
        return ELEMENTS;
    }

    public Keys<ScalingElement> getElements() {
        return ELEMENTS.keys();
    }
}
