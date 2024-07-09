package com.rivelbop.rivelworks.g3d.graphics.particle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BufferedParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.emitters.Emitter;
import com.badlogic.gdx.graphics.g3d.particles.emitters.RegularEmitter;
import com.badlogic.gdx.utils.Disposable;

/**
 * A simplified method for creating a {@link ParticleSystem}.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
@SuppressWarnings("rawtypes")
public class ParticleEmitter3D implements Disposable {
    /**
     * The particle effect that will be loaded from an assets file and added to {@link #system}.
     */
    protected ParticleEffect effect;

    /**
     * The particle system that will control the {@link #effect} and {@link #batch} added to it.
     */
    protected ParticleSystem system;

    /**
     * The batch renderer that will handle rendering the {@link #effect} added to it and added to {@link #system}.
     */
    protected ParticleBatch batch;

    /**
     * Initializes a 3D Particle Emitter by simply specifying the batch, asset, camera (if necessary), and loop.
     *
     * @param aBatch   The batch the particle will be rendered to.
     * @param assets   The asset manager to grab the particle file from.
     * @param particle The name of the particle file in the asset manager.
     * @param camera   The camera that the particle will be rendered to (not necessary for {@link ModelParticleEmitter}).
     * @param loop     Determines if the particle will loop endlessly or not.
     */
    public ParticleEmitter3D(ParticleBatch aBatch, AssetManager assets, String particle, Camera camera, boolean loop) {
        // Initialize independent particle objects
        this.system = new ParticleSystem();
        this.batch = aBatch;

        // Set up the camera (if necessary) and the ParticleBatch to the ParticleSystem
        if (batch instanceof BufferedParticleBatch) {
            ((BufferedParticleBatch) batch).setCamera(camera);
        }
        system.add(batch);

        // Load the ParticleEffect from an AssetManager, copy and initialize it, and add it to the ParticleSystem
        if (!assets.contains(particle, ParticleEffect.class)) {
            assets.load(particle, ParticleEffect.class, new ParticleEffectLoader.ParticleEffectLoadParameter(system.getBatches()));
            assets.finishLoading();
        }
        this.effect = assets.get(particle, ParticleEffect.class).copy();
        effect.init();
        system.add(effect);

        // Loop the particle if specified by 'loop'
        Emitter emitter = effect.getControllers().first().emitter;
        if (loop) {
            if (emitter instanceof RegularEmitter) {
                RegularEmitter reg = (RegularEmitter) emitter;
                reg.setEmissionMode(RegularEmitter.EmissionMode.EnabledUntilCycleEnd);
            }
        }
    }

    /**
     * Updates and draws the {@link #system} to the provided {@link ModelBatch}.
     *
     * @param modelBatch The batch that the particle will be drawn to.
     */
    public void render(ModelBatch modelBatch) {
        // Update and prepare the particle system
        system.update();
        system.begin();
        system.draw();
        system.end();

        // Render the particle system to the model batch
        modelBatch.render(system);
    }

    /**
     * @return The particle effect itself.
     */
    public ParticleEffect getEffect() {
        return effect;
    }

    /**
     * @return The particle system associated with the particle effect.
     */
    public ParticleSystem getSystem() {
        return system;
    }

    /**
     * @return The particle batch associated with the particle effect.
     */
    public ParticleBatch getBatch() {
        return batch;
    }

    /**
     * Removes the stored {@link ParticleEffect} from memory.
     * Should be called if the object is no longer in use.
     */
    @Override
    public void dispose() {
        effect.dispose();
    }
}