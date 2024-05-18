package com.rivelbop.rivelworks.physics3d.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Disposable;
import com.rivelbop.rivelworks.physics3d.collision.CollisionBody3D;

import java.util.HashSet;

/**
 * Stores and handles physics bodies.
 *
 * @author David Jerzak (RivelBop)
 */
public class PhysicsWorld3D implements Disposable {
    /**
     * Set of added physics bodies.
     */
    private final HashSet<PhysicsBody3D> PHYSICS_BODIES;

    /**
     * Set of added collision bodies.
     */
    private final HashSet<CollisionBody3D> COLLISION_BODIES;

    /**
     * Collision configuration set as default.
     */
    private final btDefaultCollisionConfiguration CONFIG;

    private final btCollisionDispatcher DISPATCHER;
    private final btDbvtBroadphase BROADPHASE;
    private final btSequentialImpulseConstraintSolver SOLVER;

    /**
     * Handles all added physics bodies.
     */
    private final btDynamicsWorld WORLD;

    /**
     * Creates a physics world with the provided gravity vector.
     *
     * @param gravity The gravity to apply to the world.
     */
    public PhysicsWorld3D(Vector3 gravity) {
        this.PHYSICS_BODIES = new HashSet<>();
        this.COLLISION_BODIES = new HashSet<>();

        this.CONFIG = new btDefaultCollisionConfiguration();
        this.DISPATCHER = new btCollisionDispatcher(CONFIG);
        this.BROADPHASE = new btDbvtBroadphase();
        this.SOLVER = new btSequentialImpulseConstraintSolver();
        this.WORLD = new btDiscreteDynamicsWorld(DISPATCHER, BROADPHASE, SOLVER, CONFIG);
        WORLD.setGravity(gravity);
    }

    /**
     * Creates a physics world with the provided gravity dimensions.
     *
     * @param x The amount of gravity to apply in the x-plane.
     * @param y The amount of gravity to apply in the y-plane.
     * @param z The amount of gravity to apply in the z-plane.
     */
    public PhysicsWorld3D(float x, float y, float z) {
        this(new Vector3(x, y, z));
    }

    /**
     * Simulates the physics world.
     */
    public void step() {
        float delta = Math.min(1f / 30f, Gdx.graphics.getDeltaTime());
        WORLD.stepSimulation(delta, 5, 1f / 60f);

        for (CollisionBody3D b : COLLISION_BODIES) {
            b.update();
        }
    }

    public void addBody(btRigidBody body) {
        WORLD.addRigidBody(body);
    }

    public void addBody(btCollisionObject body) {
        WORLD.addCollisionObject(body);
    }

    public void addBody(PhysicsBody3D body) {
        addBody(body.getBody());
        PHYSICS_BODIES.add(body);
    }

    public void addBody(CollisionBody3D body) {
        addBody(body.getBody());
        COLLISION_BODIES.add(body);
    }

    public void removeBody(btRigidBody body) {
        WORLD.removeRigidBody(body);
    }

    public void removeBody(btCollisionObject body) {
        WORLD.removeCollisionObject(body);
    }

    public void removeBody(PhysicsBody3D body) {
        removeBody(body.getBody());
        PHYSICS_BODIES.remove(body);
    }

    public void removeBody(CollisionBody3D body) {
        removeBody(body.getBody());
        COLLISION_BODIES.remove(body);
    }

    /**
     * Renders all bodies in the world.
     *
     * @param batch The ModelBatch to render to.
     */
    public void render(ModelBatch batch) {
        for (CollisionBody3D b : COLLISION_BODIES) {
            batch.render(b.getShape());
        }

        for (PhysicsBody3D b : PHYSICS_BODIES) {
            batch.render(b.getShape());
        }
    }

    /**
     * Renders all the bodies in the world, applying the environment to each body.
     *
     * @param batch       The ModelBatch to render to.
     * @param environment The environment applied to each body.
     */
    public void render(ModelBatch batch, Environment environment) {
        for (CollisionBody3D b : COLLISION_BODIES) {
            batch.render(b.getShape(), environment);
        }

        for (PhysicsBody3D b : PHYSICS_BODIES) {
            batch.render(b.getShape(), environment);
        }
    }

    /**
     * @return Array of all collision objects within the world.
     */
    public btCollisionObjectArray getCollisionObjects() {
        return WORLD.getCollisionObjectArray();
    }

    /**
     * @return Set of all physics bodies within the world.
     */
    public HashSet<PhysicsBody3D> getPhysicsBodies() {
        return PHYSICS_BODIES;
    }

    /**
     * @return Set of all collision bodies within the world.
     */
    public HashSet<CollisionBody3D> getCollisionBodies() {
        return COLLISION_BODIES;
    }

    /**
     * @return The physics world itself.
     */
    public btDynamicsWorld getWorld() {
        return WORLD;
    }

    /**
     * Disposes of all bodies and the world.
     */
    @Override
    public void dispose() {
        for (PhysicsBody3D b : PHYSICS_BODIES) {
            b.dispose();
        }
        for (CollisionBody3D b : COLLISION_BODIES) {
            b.dispose();
        }
        WORLD.dispose();
        CONFIG.dispose();
        DISPATCHER.dispose();
        BROADPHASE.dispose();
        SOLVER.dispose();
    }
}