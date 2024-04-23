package com.rivelbop.rivelworks.graphics3d.particle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;

/**
 * Used to create and render Point particles.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class PointParticleEmitter extends ParticleEmitter3D {
    /**
     * Initializes a Point Emitter by simply specifying the asset, camera, and loop.
     *
     * @param assets   The asset manager to grab the particle file from.
     * @param particle The name of the particle file in the asset manager.
     * @param camera   The camera that the particle will be rendered to.
     * @param loop     Determines if the particle will loop endlessly or not.
     */
    public PointParticleEmitter(AssetManager assets, String particle, Camera camera, boolean loop) {
        super(new PointSpriteParticleBatch(), assets, particle, camera, loop);
    }
}