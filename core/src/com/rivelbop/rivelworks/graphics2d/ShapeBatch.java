package com.rivelbop.rivelworks.graphics2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

/**
 * An extension of the {@link ShapeRenderer} with additional functionality.
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
     * Renders the provided rectangle to the batch.
     *
     * @param rectangle The rectangle to render.
     */
    public void rect(Rectangle rectangle) {
        super.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    /**
     * Renders the provided polygon to the batch.
     *
     * @param polygon The polygon to render.
     */
    public void polygon(Polygon polygon) {
        super.polygon(polygon.getVertices());
    }

    /**
     * Renders the provided circle to the batch.
     *
     * @param circle The circle to render.
     */
    public void circle(Circle circle) {
        super.circle(circle.x, circle.y, circle.radius);
    }

    /**
     * Renders the provided ellipse to the batch.
     *
     * @param ellipse The ellipse to render.
     */
    public void ellipse(Ellipse ellipse) {
        super.ellipse(ellipse.x, ellipse.y, ellipse.width, ellipse.height);
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