package com.rivelbop.rivelworks.graphics3d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.Disposable;

/**
 * Creates a {@link ModelInstance} without having to create a {@link ObjLoader} or {@link Model}.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class Model3D extends ModelInstance implements Disposable {
    /**
     * Loads a 3D model from the provided OBJ file.
     *
     * @param modelFile OBJ 3D Model File
     */
    public Model3D(FileHandle modelFile) {
        super(new ObjLoader().loadModel(modelFile));
    }

    /**
     * Loads a 3D model from the provided {@link com.badlogic.gdx.graphics.g3d.Model}.
     *
     * @param model 3D Model used to created {@link ModelInstance}.
     */
    public Model3D(Model model) {
        super(model);
    }

    /**
     * Removes {@link Model} provided to the object from memory.
     * Should be called if the object is no longer in use and not a part of an {@link AssetManager}.
     */
    @Override
    public void dispose() {
        model.dispose();
    }
}