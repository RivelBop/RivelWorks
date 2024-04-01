package com.rivelbop.rivelworks.graphics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * An extension of the {@link ShapeRenderer} that allows shapes to render with alpha values.
 *
 * @author David Jerzak (RivelBop)
 */
public class ShapeBatch extends ShapeRenderer {
    /**
     * Begins rendering shapes with an alpha blend.
     *
     * @param shapeType The type of shape that should be rendered.
     */
    @Override
    public void begin(ShapeType shapeType) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        super.begin(shapeType);
    }

    /**
     * Stops rendering shapes and disables alpha blending.
     */
    @Override
    public void end() {
        super.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}