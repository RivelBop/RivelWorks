package com.rivelbop.rivelworks.graphics3d.particles;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;

/**
 * Used to create and render Billboard particles.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class BillboardParticleEmitter extends ParticleEmitter3D {
    /**
     * Initializes a Billboard Emitter by simply specifying the asset, camera, and loop.
     *
     * @param assets   The asset manager to grab the particle file from.
     * @param particle The name of the particle file in the asset manager.
     * @param camera   The camera that the particle will be rendered to.
     * @param loop     Determines if the particle will loop endlessly or not.
     */
    public BillboardParticleEmitter(AssetManager assets, String particle, Camera camera, boolean loop) {
        super(new BillboardParticleBatch(), assets, particle, camera, loop);
    }
}