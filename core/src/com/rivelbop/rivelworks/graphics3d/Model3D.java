package com.rivelbop.rivelworks.graphics3d;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.UBJsonReader;

/**
 * Creates a {@link ModelInstance} without having to create a {@link ObjLoader}, {@link G3dModelLoader}, or {@link Model}.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class Model3D extends ModelInstance implements Disposable {
    /**
     * Loads a 3D model from the provided OBJ/G3DJ file.
     *
     * @param modelFile OBJ 3D Model File
     */
    public Model3D(FileHandle modelFile) {
        // Load OBJ Model
        this(modelFile.extension().equals("obj") ?
                new ObjLoader().loadModel(modelFile) :

                // Load G3DB Model
                modelFile.extension().equals("g3db") ?
                        new G3dModelLoader(new UBJsonReader()).loadModel(modelFile) :

                        // Load G3DJ Model
                        modelFile.extension().equals("g3dj") ?
                                new G3dModelLoader(new JsonReader()).loadModel(modelFile) :

                                // UNRECOGNIZED
                                new Model());
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
     * Renders the model to the batch.
     *
     * @param batch The batch to render the model to.
     */
    public void render(ModelBatch batch) {
        batch.render(this);
    }

    /**
     * Renders the current model instance to the batch and applied the environment to that model.
     *
     * @param batch       The batch to render the model to.
     * @param environment The environment that will be applied to the model.
     */
    public void render(ModelBatch batch, Environment environment) {
        batch.render(this, environment);
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