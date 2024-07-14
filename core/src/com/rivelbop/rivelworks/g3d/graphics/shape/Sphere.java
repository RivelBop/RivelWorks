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
 * An extension of the {@link Shape3D} class that creates a sphere.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class Sphere extends Shape3D {
    /**
     * The radius of the sphere, same from all angles.
     */
    private final float RADIUS;

    /**
     * Amount of divisions the model has, more = slower + better looking.
     */
    private final int DIVISIONS;

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

        this.RADIUS = radius;
        this.DIVISIONS = divisions;
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

        this.RADIUS = radius;
        this.DIVISIONS = divisions;
    }

    /**
     * Creates a sphere by providing its model instance and dimensions.
     *
     * @param modelInstance The model instance to get the model from.
     * @param radius        The radius of the sphere.
     * @param divisions     The amount of divisions the sphere will have.
     */
    public Sphere(ModelInstance modelInstance, float radius, int divisions) {
        super(modelInstance);

        this.RADIUS = radius;
        this.DIVISIONS = divisions;
    }

    /**
     * Creates a sphere by providing its model and dimensions.
     *
     * @param model     The model to use.
     * @param radius    The radius of the sphere.
     * @param divisions The amount of divisions the sphere will have.
     */
    public Sphere(Model model, float radius, int divisions) {
        super(model);

        this.RADIUS = radius;
        this.DIVISIONS = divisions;
    }

    /**
     * @return The radius provided in the constructor.
     */
    public float getRadius() {
        return RADIUS;
    }

    /**
     * @return The divisions provided in the constructor.
     */
    public int getDivisions() {
        return DIVISIONS;
    }
}