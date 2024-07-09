package com.rivelbop.rivelworks.g3d.graphics.particle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.particles.batches.ModelInstanceParticleBatch;

/**
 * Used to create and render model particles.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class ModelParticleEmitter extends ParticleEmitter3D {
    /**
     * Initializes a Model Emitter by simply specifying the asset and loop.
     *
     * @param assets   The asset manager to grab the particle file from.
     * @param particle The name of the particle file in the asset manager.
     * @param loop     Determines if the particle will loop endlessly or not.
     */
    public ModelParticleEmitter(AssetManager assets, String particle, boolean loop) {
        super(new ModelInstanceParticleBatch(), assets, particle, null, loop);
    }
}