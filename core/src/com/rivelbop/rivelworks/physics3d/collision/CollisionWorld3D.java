package com.rivelbop.rivelworks.physics3d.collision;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashSet;

/**
 * Stores and handles collision bodies.
 *
 * @author David Jerzak (RivelBop)
 */
public class CollisionWorld3D implements Disposable {
    /**
     * Stores any provided collision bodies.
     */
    private final HashSet<CollisionBody3D> BODIES;

    /**
     * Default collision configuration used to initialize the dispatcher.
     */
    private final btDefaultCollisionConfiguration CONFIG;

    private final btCollisionDispatcher DISPATCHER;
    private final btDbvtBroadphase BROADPHASE;

    /**
     * Handles collision simulation and stores all bodies.
     */
    private final btCollisionWorld WORLD;

    /**
     * Initializes a collision world with default properties.
     */
    public CollisionWorld3D() {
        this.BODIES = new HashSet<>();
        this.CONFIG = new btDefaultCollisionConfiguration();
        this.DISPATCHER = new btCollisionDispatcher(CONFIG);
        this.BROADPHASE = new btDbvtBroadphase();
        this.WORLD = new btCollisionWorld(DISPATCHER, BROADPHASE, CONFIG);
    }

    /**
     * Performs collision detection and updates each body according to its shape model.
     */
    public void step() {
        WORLD.performDiscreteCollisionDetection();
        for (CollisionBody3D b : BODIES) {
            b.update();
        }
    }

    /**
     * @param collisionObject Default collision object to add to world.
     */
    public void addBody(btCollisionObject collisionObject) {
        WORLD.addCollisionObject(collisionObject);
    }

    /**
     * @param collisionBody Collision body to add to the world and update.
     */
    public void addBody(CollisionBody3D collisionBody) {
        addBody(collisionBody.getBody());
        BODIES.add(collisionBody);
    }

    /**
     * @param collisionObject Default collision object to remove from the world.
     */
    public void removeBody(btCollisionObject collisionObject) {
        WORLD.removeCollisionObject(collisionObject);
    }

    /**
     * @param collisionBody Collision body to remove from the world.
     */
    public void removeBody(CollisionBody3D collisionBody) {
        removeBody(collisionBody.getBody());
        BODIES.remove(collisionBody);
    }

    /**
     * Renders all contained {@link CollisionBody3D} objects stored in the world.
     *
     * @param batch The ModelBatch to render the world to.
     */
    public void render(ModelBatch batch) {
        for (CollisionBody3D b : BODIES) {
            batch.render(b.getShape());
        }
    }

    /**
     * Renders all contained {@link CollisionBody3D} objects stored in the world, effected by the provided environment.
     *
     * @param batch       The ModelBatch to render the world to.
     * @param environment The environment to apply to the rendered world.
     */
    public void render(ModelBatch batch, Environment environment) {
        for (CollisionBody3D b : BODIES) {
            batch.render(b.getShape(), environment);
        }
    }

    /**
     * @return All {@link btCollisionObject} objects in the world.
     */
    public btCollisionObjectArray getCollisionObjects() {
        return WORLD.getCollisionObjectArray();
    }

    /**
     * @return All {@link CollisionBody3D} objects in the world.
     */
    public HashSet<CollisionBody3D> getCollisionBodies() {
        return BODIES;
    }

    /**
     * @return The collision world itself.
     */
    public btCollisionWorld getWorld() {
        return WORLD;
    }

    /**
     * Disposes of all bodies and the world.
     */
    @Override
    public void dispose() {
        for (CollisionBody3D b : BODIES) {
            b.dispose();
        }
        WORLD.dispose();
        BROADPHASE.dispose();
        CONFIG.dispose();
        DISPATCHER.dispose();
    }
}