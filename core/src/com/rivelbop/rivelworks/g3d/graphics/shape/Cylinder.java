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
 * An extension of the {@link Shape3D} class that creates a cylinder.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class Cylinder extends Shape3D {
    /**
     * The dimensions of the Cylinder.
     */
    private final float WIDTH, HEIGHT, DEPTH;

    /**
     * Amount of divisions the model has, more = slower + better looking.
     */
    private final int DIVISIONS;

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

        this.WIDTH = width;
        this.HEIGHT = height;
        this.DEPTH = depth;
        this.DIVISIONS = divisions;
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

        this.WIDTH = width;
        this.HEIGHT = height;
        this.DEPTH = depth;
        this.DIVISIONS = divisions;
    }

    /**
     * Creates a cylinder by providing its model instance and dimensions.
     *
     * @param modelInstance The model instance to get the model from.
     * @param width         The width of the cylinder.
     * @param height        The height of the cylinder.
     * @param depth         The depth of the cylinder.
     * @param divisions     The amount of divisions that the cylinder will have.
     */
    public Cylinder(ModelInstance modelInstance, float width, float height, float depth, int divisions) {
        this(modelInstance.model, width, height, depth, divisions);
    }

    /**
     * Creates a cylinder by providing its model and dimensions.
     *
     * @param model     The model to use.
     * @param width     The width of the cylinder.
     * @param height    The height of the cylinder.
     * @param depth     The depth of the cylinder.
     * @param divisions The amount of divisions that the cylinder will have.
     */
    public Cylinder(Model model, float width, float height, float depth, int divisions) {
        super(model);

        this.WIDTH = width;
        this.HEIGHT = height;
        this.DEPTH = depth;
        this.DIVISIONS = divisions;
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

    /**
     * @return The divisions provided in the constructor.
     */
    public int getDivisions() {
        return DIVISIONS;
    }
}