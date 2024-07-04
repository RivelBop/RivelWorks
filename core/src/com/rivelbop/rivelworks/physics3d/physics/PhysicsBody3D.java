package com.rivelbop.rivelworks.physics3d.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;
import com.rivelbop.rivelworks.graphics3d.shape.Shape3D;
import com.rivelbop.rivelworks.physics3d.Physics3D;
import com.rivelbop.rivelworks.physics3d.collision.CollisionBody3D;

/**
 * Used as the base of all 3D bodies influenced by physics.
 *
 * @author David Jerzak (RivelBop)
 */
public class PhysicsBody3D implements Disposable {
    /**
     * Used for inertia calculations when creating a new physics body.
     */
    private static final Vector3 LOCAL_INERTIA = new Vector3();

    /**
     * The shape model of the body.
     */
    private final Shape3D SHAPE;

    /**
     * The shape of the collision body.
     */
    private final btCollisionShape COLLISION_SHAPE;

    /**
     * Information used to construct the rigid body.
     */
    private final btRigidBody.btRigidBodyConstructionInfo INFO;

    /**
     * The physics body.
     */
    private final btRigidBody BODY;

    /**
     * Used to calculate the transformation between the {@link #SHAPE} and {@link #BODY}.
     */
    private final MotionState STATE;

    /**
     * The collision configuration of the dispatcher, set to default.
     */
    private final btDefaultCollisionConfiguration CONFIG;

    /**
     * The collision dispatcher, takes in the configuration.
     */
    private final btCollisionDispatcher DISPATCHER;

    /**
     * Handles transformation data between the shape model and body.
     */
    public static class MotionState extends btMotionState {
        public Matrix4 transform;

        @Override
        public void getWorldTransform(Matrix4 worldTrans) {
            worldTrans.set(transform);
        }

        @Override
        public void setWorldTransform(Matrix4 worldTrans) {
            transform.set(worldTrans);
        }
    }

    /**
     * @param shape         The shape model for the body shape and rendering.
     * @param mass          The mass of the body.
     * @param motionState   Used for transformation data between shape model and body.
     * @param collisionFlag Handles flagging the data for collision handling.
     */
    public PhysicsBody3D(Shape3D shape, float mass, MotionState motionState, int collisionFlag) {
        this.SHAPE = shape;

        this.COLLISION_SHAPE = Physics3D.collisionShape(SHAPE);
        if (mass > 0f) {
            COLLISION_SHAPE.calculateLocalInertia(mass, LOCAL_INERTIA);
        } else {
            LOCAL_INERTIA.setZero();
        }

        this.STATE = motionState;
        if (STATE != null) {
            STATE.transform = SHAPE.transform;
        }

        this.INFO = new btRigidBody.btRigidBodyConstructionInfo(mass, STATE, COLLISION_SHAPE, LOCAL_INERTIA);
        this.BODY = new btRigidBody(INFO);
        BODY.setCollisionFlags(BODY.getCollisionFlags() | collisionFlag);

        this.CONFIG = new btDefaultCollisionConfiguration();
        this.DISPATCHER = new btCollisionDispatcher(CONFIG);
    }

    /**
     * Checks to see if the provided collision body collides with the {@link #BODY}.
     *
     * @param otherBody The collision body to check with.
     * @return True if the number of contacts between the bodies is greater than 0.
     */
    public boolean isColliding(CollisionBody3D otherBody) {
        return isColliding(otherBody.getBody());
    }

    /**
     * Checks to see if the provided physics body collides with the {@link #BODY}.
     *
     * @param otherBody The physics body to check with.
     * @return True if the number of contacts between the bodies is greater than 0.
     */
    public boolean isColliding(PhysicsBody3D otherBody) {
        return isColliding(otherBody.BODY);
    }

    /**
     * Checks to see if the provided body collides with the {@link #BODY}.
     *
     * @param otherBody The body to check with.
     * @return True if the number of contacts between the bodies is greater than 0.
     */
    public boolean isColliding(btCollisionObject otherBody) {
        CollisionObjectWrapper col1 = new CollisionObjectWrapper(BODY);
        CollisionObjectWrapper col2 = new CollisionObjectWrapper(otherBody);

        btCollisionAlgorithm algorithm = DISPATCHER.findAlgorithm(col1.wrapper, col2.wrapper, null, 0);

        btDispatcherInfo dispatcherInfo = new btDispatcherInfo();
        btManifoldResult result = new btManifoldResult(col1.wrapper, col2.wrapper);

        algorithm.processCollision(col1.wrapper, col2.wrapper, dispatcherInfo, result);

        boolean r = result.getPersistentManifold().getNumContacts() > 0;

        DISPATCHER.freeCollisionAlgorithm(algorithm.getCPointer());
        col1.dispose();
        col2.dispose();
        algorithm.dispose();
        dispatcherInfo.dispose();
        result.dispose();
        return r;
    }

    /**
     * Transforms the shape then the body according to that shape based on the provided transformation configuration.
     *
     * @param config Alter the body's transformation.
     */
    public void transform(Physics3D.TransformConfig config) {
        config.transform(SHAPE.transform);
        BODY.proceedToTransform(SHAPE.transform);
    }

    /**
     * Translates the physics body's position.
     *
     * @param x Translate the x-position.
     * @param y Translate the y-position.
     * @param z Translate the z-position.
     */
    public void translate(float x, float y, float z) {
        translate(new Vector3(x, y, z));
    }

    /**
     * Translates the physics body's position.
     *
     * @param translation The x, y, and z translation amounts.
     */
    public void translate(Vector3 translation) {
        BODY.translate(translation);
    }

    /**
     * Sets the physics body's linear velocity on x and z only.
     *
     * @param x Velocity on the x-axis.
     * @param z Velocity on the z-axis.
     */
    public void setVelocity(float x, float z) {
        setVelocity(x, getVelocity().y, z);
    }

    /**
     * Sets the physics body's linear velocity.
     *
     * @param x Velocity on the x-axis.
     * @param y Velocity on the y-axis.
     * @param z Velocity on the z-axis.
     */
    public void setVelocity(float x, float y, float z) {
        setVelocity(new Vector3(x, y, z));
    }

    /**
     * Sets the physics body's linear velocity.
     *
     * @param velocity The x, y, and z velocity values.
     */
    public void setVelocity(Vector3 velocity) {
        BODY.setLinearVelocity(velocity);
    }

    /**
     * @return The {@link Vector3} position of the physics body.
     */
    public Vector3 getPosition() {
        Vector3 position = Vector3.Zero;
        BODY.getWorldTransform().getTranslation(position);
        return position;
    }

    /**
     * @return The linear velocity of the physics body.
     */
    public Vector3 getVelocity() {
        return BODY.getLinearVelocity();
    }

    /**
     * @return The rotation of the physics body.
     */
    public Quaternion getRotation() {
        return BODY.getOrientation();
    }

    /**
     * @param flag Used as a contact 'id' for other bodies to filter out.
     */
    public void setContactFlag(int flag) {
        BODY.setContactCallbackFlag(flag);
    }

    /**
     * @param filter Used to filter contacts with the provided flags.
     */
    public void setContactFilter(int filter) {
        BODY.setContactCallbackFilter(filter);
    }

    /**
     * @return The shape model.
     */
    public Shape3D getShape() {
        return SHAPE;
    }

    /**
     * @return The rigid body itself.
     */
    public btRigidBody getBody() {
        return BODY;
    }

    /**
     * Dispose of the shape model and all JBullet bindings.
     */
    @Override
    public void dispose() {
        SHAPE.dispose();
        BODY.dispose();
        INFO.dispose();
        CONFIG.dispose();
        DISPATCHER.dispose();
        COLLISION_SHAPE.dispose();
        if (STATE != null) {
            STATE.dispose();
        }
    }
}