package com.rivelbop.rivelworks.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public abstract class ScalingScene extends ScalingHandler {
    protected final Array<ScalingElement> ELEMENTS = new Array<>();
    protected final Array<ScalingElement.ElementListener> INTERACTABLES = new Array<>();
    protected ScreenViewport viewport;

    public ScalingScene(int screenWidth, int screenHeight) {
        super(screenWidth, screenHeight);
    }

    public ScalingScene(ScreenViewport viewport, int screenWidth, int screenHeight) {
        this(screenWidth, screenHeight);
        this.viewport = viewport;
    }

    public abstract void update();

    public void render(SpriteBatch batch) {
        for (ScalingElement e : ELEMENTS) {
            e.render(batch);
        }
    }

    public void add(ScalingElement element) {
        super.add(element);
        ELEMENTS.add(element);
        if (element instanceof ScalingElement.ElementListener) {
            INTERACTABLES.add((ScalingElement.ElementListener) element);
        }
        element.scene = this;
    }

    public void remove(ScalingElement element) {
        super.remove(element);
        ELEMENTS.removeValue(element, true);
        if (element instanceof ScalingElement.ElementListener) {
            INTERACTABLES.removeValue((ScalingElement.ElementListener) element, true);
        }
        element.scene = null;
    }

    public void setViewport(ScreenViewport viewport) {
        this.viewport = viewport;
    }

    public Array<ScalingElement> getElements() {
        return ELEMENTS;
    }

    public Array<ScalingElement.ElementListener> getInteractables() {
        return INTERACTABLES;
    }
}
