package com.rivelbop.rivelworks.graphics3d.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

/**
 * An extension of the {@link Shape3D} class that creates a cone.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class Cone extends Shape3D {
    /**
     * Creates a cone by providing its dimensions and color.
     *
     * @param radius    The radius of the cone.
     * @param height    The height of the cone.
     * @param divisions The amount of divisions the cone will have.
     * @param color     The color of the cone.
     */
    public Cone(float radius, float height, int divisions, Color color) {
        super(new ModelBuilder().createCone(radius * 2f, height, radius * 2f, divisions,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

    /**
     * Creates a cone by providing its dimensions and wrapping texture.
     *
     * @param radius    The radius of the cone.
     * @param height    The height of the cone.
     * @param divisions The amount of divisions the cone will have.
     * @param texture   The wrapping texture applied to the cone.
     */
    public Cone(float radius, float height, int divisions, Texture texture) {
        super(new ModelBuilder().createCone(radius * 2f, height, radius * 2f, divisions,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
    }
}