package com.rivelbop.rivelworks.graphics3d.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

/**
 * An extension of the {@link Shape3D} class that creates a sphere.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class Sphere extends Shape3D {
    /**
     * Creates a sphere by providing its dimensions and color.
     *
     * @param radius    The radius of the sphere.
     * @param divisions The amount of divisions the sphere will have.
     * @param color     The color of the sphere.
     */
    public Sphere(float radius, int divisions, Color color) {
        super(new ModelBuilder().createSphere(radius * 2f, radius * 2f, radius * 2f, divisions, divisions,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

    /**
     * Creates a sphere by providing its dimensions and wrapping texture.
     *
     * @param radius    The radius of the sphere.
     * @param divisions The amount of divisions the sphere will have.
     * @param texture   The wrapping texture applied to the sphere.
     */
    public Sphere(float radius, int divisions, Texture texture) {
        super(new ModelBuilder().createSphere(radius * 2f, radius * 2f, radius * 2f, divisions, divisions,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
    }
}