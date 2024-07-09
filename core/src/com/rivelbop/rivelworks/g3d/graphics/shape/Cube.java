package com.rivelbop.rivelworks.g3d.graphics.shape;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
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
     * The dimensions of the Cube.
     */
    private final float WIDTH, HEIGHT, DEPTH;

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

        this.WIDTH = width;
        this.HEIGHT = height;
        this.DEPTH = depth;
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

        this.WIDTH = width;
        this.HEIGHT = height;
        this.DEPTH = depth;
    }

    /**
     * Generates a cube by providing its model instance and dimensions.
     *
     * @param modelInstance The model instance to get the model from.
     * @param width         The width of the cube.
     * @param height        The height of the cube.
     * @param depth         The depth/girth of the cube.
     */
    public Cube(ModelInstance modelInstance, float width, float height, float depth) {
        this(modelInstance.model, width, height, depth);
    }

    /**
     * Generates a cube by providing its model and dimensions.
     *
     * @param model  The model to use.
     * @param width  The width of the cube.
     * @param height The height of the cube.
     * @param depth  The depth/girth of the cube.
     */
    public Cube(Model model, float width, float height, float depth) {
        super(model);

        this.WIDTH = width;
        this.HEIGHT = height;
        this.DEPTH = depth;
    }

    /**
     * @return The width provided in the constructor.
     */
    public float getWidth() {
        return WIDTH;
    }

    /**
     * @return The height provided in the constructor.
     */
    public float getHeight() {
        return HEIGHT;
    }

    /**
     * @return The depth/length provided in the constructor.
     */
    public float getDepth() {
        return DEPTH;
    }
}