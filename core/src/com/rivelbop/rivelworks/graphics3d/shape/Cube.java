package com.rivelbop.rivelworks.graphics3d.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

/**
 * An extension of the {@link Shape3D} class that creates a cube.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class Cube extends Shape3D {
    /**
     * Creates a cube by providing its dimensions and color.
     *
     * @param width  The width of the cube.
     * @param height The height of the cube.
     * @param depth  The depth/girth of the cube.
     * @param color  The color of the cube.
     */
    public Cube(float width, float height, float depth, Color color) {
        super(new ModelBuilder().createBox(width, height, depth,
                new Material(ColorAttribute.createDiffuse(color)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal));
    }

    /**
     * Generates a cube by providing its dimensions and the wrapping texture.
     *
     * @param width   The width of the cube.
     * @param height  The height of the cube.
     * @param depth   The depth/girth of the cube.
     * @param texture The wrapping texture applied to the cube.
     */
    public Cube(float width, float height, float depth, Texture texture) {
        super(new ModelBuilder().createBox(width, height, depth,
                new Material(TextureAttribute.createDiffuse(texture)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
    }
}