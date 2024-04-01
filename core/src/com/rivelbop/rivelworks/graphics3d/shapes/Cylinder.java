package com.rivelbop.rivelworks.graphics3d.shapes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

/**
 * An extension of the {@link Shape3D} class that creates a cylinder.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class Cylinder extends Shape3D {
    /**
     * Creates a cylinder by providing its dimensions and color.
     *
     * @param width     The width of the cylinder.
     * @param height    The height of the cylinder.
     * @param depth     The depth of the cylinder.
     * @param divisions The amount of divisions that the cylinder will have.
     * @param color     The color of the cylinder.
     */
    public Cylinder(float width, float height, float depth, int divisions, Color color) {
        super(new ModelBuilder().createCylinder(width, height, depth, divisions,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

    /**
     * Creates a cylinder by providing its dimensions and wrapping texture.
     *
     * @param width     The width of the cylinder.
     * @param height    The height of the cylinder.
     * @param depth     The depth of the cylinder.
     * @param divisions The amount of divisions that the cylinder will have.
     * @param texture   The wrapping texture applied to the cylinder.
     */
    public Cylinder(float width, float height, float depth, int divisions, Texture texture) {
        super(new ModelBuilder().createCylinder(width, height, depth, divisions,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
    }
}