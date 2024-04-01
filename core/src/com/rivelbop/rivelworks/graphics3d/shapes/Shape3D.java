package com.rivelbop.rivelworks.graphics3d.shapes;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Disposable;

/**
 * Used as a skeleton for all 3D shapes.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public abstract class Shape3D extends ModelInstance implements Disposable {
    /**
     * Commonly shared constructor of each shape, accepts an instance {@link Model}.
     *
     * @param model The 3D model to use as the shape.
     */
    protected Shape3D(Model model) {
        super(model);
    }

    /**
     * Remove the {@link #model} from memory.
     * Should be called if the shape is no longer in use.
     */
    @Override
    public void dispose() {
        model.dispose();
    }
}