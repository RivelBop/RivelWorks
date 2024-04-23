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
    }
}