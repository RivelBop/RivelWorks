package com.rivelbop.rivelworks.g3d.physics.dynamic;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;
import com.rivelbop.rivelworks.g3d.graphics.shape.Shape3D;
import com.rivelbop.rivelworks.g3d.physics.Physics3D;
import com.rivelbop.rivelworks.g3d.physics.collision.CollisionBody3D;

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
     * @param translation The x, y, and z translation amounts.
     */
    public void translate(Vector3 translation) {
        transform(transform -> transform.translate(translation));
    }

    /**
     * Translates the physics body's position.
     *
     * @param x Translate the x-position.
     * @param y Translate the y-position.
     * @param z Translate the z-position.
     */
    public void translate(float x, float y, float z) {
        transform(transform -> transform.translate(x, y, z));
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
     * Rotates both the shape model instance and the body.
     *
     * @param axis The vector axis to rotate around.
     * @param deg  The angle in degrees.
     */
    public void rotate(Vector3 axis, float deg) {
        transform(transform -> transform.rotate(axis, deg));
    }

    /**
     * Rotates both the shape model instance and the body.
     *
     * @param x   The x-axis component of the vector to rotate around.
     * @param y   The y-axis component of the vector to rotate around.
     * @param z   The z-axis component of the vector to rotate around.
     * @param deg The angle in degrees.
     */
    public void rotate(float x, float y, float z, float deg) {
        transform(transform -> transform.rotate(x, y, z, deg));
    }

    /**
     * Rotates both the shape model instance and the body.
     *
     * @param quaternion The rotation information to rotate by.
     */
    public void rotate(Quaternion quaternion) {
        transform(transform -> transform.rotate(quaternion));
    }

    /**
     * Rotates both the shape model instance and the body.
     *
     * @param v1 The first rotation vector.
     * @param v2 The second rotation vector.
     */
    public void rotate(Vector3 v1, Vector3 v2) {
        transform(transform -> transform.rotate(v1, v2));
    }

    /**
     * Fixes the rotation on each axis.
     *
     * @param x Fix on x-axis.
     * @param y Fix on y-axis.
     * @param z Fix on z-axis.
     */
    public void fixRotation(boolean x, boolean y, boolean z) {
        BODY.setAngularFactor(new Vector3(
                x ? 0f : 1f,
                y ? 0f : 1f,
                z ? 0f : 1f
        ));
    }

    /**
     * Sets the body's activation state.
     *
     * @param activationState The activation state of the body, using tags from {@link Collision}.
     */
    public void setState(int activationState) {
        getBody().setActivationState(activationState);
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
     * @return The shape model instance.
     */
    public Shape3D getShape() {
        return SHAPE;
    }

    /**
     * @return The collision shape.
     */
    public btCollisionShape getCollisionShape() {
        return COLLISION_SHAPE;
    }

    /**
     * @return The rigid body construction info.
     */
    public btRigidBody.btRigidBodyConstructionInfo getInfo() {
        return INFO;
    }

    /**
     * @return The rigid body.
     */
    public btRigidBody getBody() {
        return BODY;
    }

    /**
     * @return The motion state.
     */
    public MotionState getState() {
        return STATE;
    }

    /**
     * @return The default collision configuration.
     */
    public btDefaultCollisionConfiguration getConfig() {
        return CONFIG;
    }

    /**
     * @return The collision dispatcher.
     */
    public btCollisionDispatcher getDispatcher() {
        return DISPATCHER;
    }

    /**
     * Dispose of the shape model and all JBullet bindings.
     */
    @Override
    public void dispose() {
        dispose(true);
    }

    /**
     * Dispose of all JBullet bindings and optionally dispose of the shape model.
     *
     * @param shapeInstance Whether the shape instance should be disposed.
     */
    public void dispose(boolean shapeInstance) {
        COLLISION_SHAPE.dispose();
        BODY.dispose();
        if (STATE != null) {
            STATE.dispose();
        }
        CONFIG.dispose();
        DISPATCHER.dispose();
        INFO.dispose();

        if (shapeInstance) {
            SHAPE.dispose();
        }
    }
}