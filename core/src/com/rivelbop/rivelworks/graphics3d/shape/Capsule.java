package com.rivelbop.rivelworks.graphics3d.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

/**
 * An extension of the {@link Shape3D} class that creates a capsule.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class Capsule extends Shape3D {
    /**
     * The dimensions of the Capsule.
     */
    private final float RADIUS, HEIGHT;

    /**
     * Amount of divisions the model has, more = slower + better looking (more 'circular').
     */
    private final int DIVISIONS;

    /**
     * Creates a capsule by providing its dimensions and color.
     *
     * @param radius    The radius of the capsule.
     * @param height    The height of the capsule.
     * @param divisions The amount of divisions the capsule will have.
     * @param color     The color of the capsule.
     */
    public Capsule(float radius, float height, int divisions, Color color) {
        super(new ModelBuilder().createCapsule(radius, height, divisions,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));

        this.RADIUS = radius;
        this.HEIGHT = height;
        this.DIVISIONS = divisions;
    }

    /**
     * Creates a capsule by providing its dimensions and wrapping texture.
     *
     * @param radius    The radius of the capsule.
     * @param height    The height of the capsule.
     * @param divisions The amount of divisions the capsule will have.
     * @param texture   The wrapping texture applied to the capsule.
     */
    public Capsule(float radius, float height, int divisions, Texture texture) {
        super(new ModelBuilder().createCapsule(radius, height, divisions,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));

        this.RADIUS = radius;
        this.HEIGHT = height;
        this.DIVISIONS = divisions;
    }

    /**
     * @return The radius of the capsule model.
     */
    public float getRadius() {
        return RADIUS;
    }

    /**
     * @return The height of the capsule model.
     */
    public float getHeight() {
        return HEIGHT;
    }

    /**
     * @return The divisions of the capsule model.
     */
    public int getDivisions() {
        return DIVISIONS;
    }
}