package com.rivelbop.rivelworks.g3d.graphics.shape;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.rivelbop.rivelworks.g3d.graphics.Model3D;

/**
 * Used as a skeleton for all 3D shapes.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class Shape3D extends Model3D {
    /**
     * Commonly shared constructor of each shape, accepts a {@link Model}.
     *
     * @param model The 3D model to use as the shape.
     */
    public Shape3D(Model model) {
        super(model);
    }

    /**
     * Commonly shared constructor of each shape, accepts a {@link ModelInstance}.
     *
     * @param modelInstance The model instance to gather model data from.
     */
    public Shape3D(ModelInstance modelInstance) {
        super(modelInstance);
    }
}