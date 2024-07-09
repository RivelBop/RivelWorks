package com.rivelbop.rivelworks.g3d.physics.collision;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Disposable;
import com.rivelbop.rivelworks.g3d.graphics.shape.Shape3D;
import com.rivelbop.rivelworks.g3d.physics.Physics3D;
import com.rivelbop.rivelworks.g3d.physics.dynamic.PhysicsBody3D;

/**
 * A basic JBullet 3D collision object.
 *
 * @author David Jerzak (RivelBop)
 */
public class CollisionBody3D implements Disposable {
    /**
     * The shape model used for collision dimensions, transformations, and rendering.
     */
    private final Shape3D SHAPE;

    /**
     * The collision shape dimensions/information.
     */
    private final btCollisionShape COLLISION_SHAPE;

    /**
     * The body of the collision object, interacts with other collision objects.
     */
    private final btCollisionObject BODY;

    /**
     * The collision configuration of the dispatcher, set to default.
     */
    private final btDefaultCollisionConfiguration CONFIG;

    /**
     * The collision dispatcher, takes in the configuration.
     */
    private final btCollisionDispatcher DISPATCHER;

    /**
     * Creates a new collision body from the provided shape model.
     *
     * @param shape The shape model to use for dimensions, geometry, transformation, and rendering.
     */
    public CollisionBody3D(Shape3D shape) {
        this.SHAPE = shape;
        this.COLLISION_SHAPE = Physics3D.collisionShape(SHAPE);

        this.BODY = new btCollisionObject();
        BODY.setCollisionShape(COLLISION_SHAPE);
        BODY.setCollisionFlags(BODY.getCollisionFlags() | btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
        BODY.setWorldTransform(SHAPE.transform);

        this.CONFIG = new btDefaultCollisionConfiguration();
        this.DISPATCHER = new btCollisionDispatcher(CONFIG);
    }

    /**
     * Updates the shape's transformation according to the {@link #BODY}.
     */
    public void update() {
        BODY.setWorldTransform(SHAPE.transform);
    }

    /**
     * Checks to see if the provided collision body collides with the {@link #BODY}.
     *
     * @param otherBody The collision body to check with.
     * @return True if the number of contacts between the bodies is greater than 0.
     */
    public boolean isColliding(CollisionBody3D otherBody) {
        return isColliding(otherBody.BODY);
    }

    /**
     * Checks to see if the provided physics body collides with the {@link #BODY}.
     *
     * @param otherBody The physics body to check with.
     * @return True if the number of contacts between the bodies is greater than 0.
     */
    public boolean isColliding(PhysicsBody3D otherBody) {
        return isColliding(otherBody.getBody());
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
     * Alters the {@link #SHAPE}'s transformation, then updates the {@link #BODY}.
     *
     * @param config The transform configuration to apply to the transformation.
     */
    public void transform(Physics3D.TransformConfig config) {
        config.transform(SHAPE.transform);
        update();
    }

    /**
     * Translates both the shape model instance and the body.
     *
     * @param translation Translation as a Vector3.
     */
    public void translate(Vector3 translation) {
        transform(transform -> transform.translate(translation));
    }

    /**
     * Translates both the shape model instance and the body.
     *
     * @param x Translation on the x-axis.
     * @param y Translation on the y-axis.
     * @param z Translation on the z-axis.
     */
    public void translate(float x, float y, float z) {
        transform(transform -> transform.translate(x, y, z));
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
     * Sets the contact flag of this object, serves as ID for filtering in other bodies.
     *
     * @param flag Integer representation of a flag.
     */
    public void setContactFlag(int flag) {
        BODY.setContactCallbackFlag(flag);
    }

    /**
     * Sets the contact filter of this object, used to match with incoming contact events.
     *
     * @param filter Integer representation of a filter flag.
     */
    public void setContactFilter(int filter) {
        BODY.setContactCallbackFilter(filter);
    }

    /**
     * @return The shape model instance
     */
    public Shape3D getShape() {
        return SHAPE;
    }

    /**
     * @return The collision shape
     */
    public btCollisionShape getCollisionShape() {
        return COLLISION_SHAPE;
    }

    /**
     * @return The collision object
     */
    public btCollisionObject getBody() {
        return BODY;
    }

    /**
     * @return The collision configuration
     */
    public btDefaultCollisionConfiguration getConfig() {
        return CONFIG;
    }

    /**
     * @return The collision dispatcher
     */
    public btCollisionDispatcher getDispatcher() {
        return DISPATCHER;
    }

    /**
     * @return The body's position.
     */
    public Vector3 getPosition() {
        Vector3 position = Vector3.Zero;
        BODY.getWorldTransform().getTranslation(position);
        return position;
    }

    /**
     * @return The body's rotation.
     */
    public Quaternion getRotation() {
        Quaternion rotation = new Quaternion();
        BODY.getWorldTransform().getRotation(rotation);
        return rotation;
    }

    /**
     * Disposes all C-bound Bullet objects, as well as the {@link #SHAPE}.
     */
    @Override
    public void dispose() {
        dispose(true);
    }

    /**
     * Disposes all C-bound Bullet objects, and optionally the model within the shape instance.
     *
     * @param shapeInstance Whether the shape instance should be disposed.
     */
    public void dispose(boolean shapeInstance) {
        COLLISION_SHAPE.dispose();
        BODY.dispose();
        CONFIG.dispose();
        DISPATCHER.dispose();

        if (shapeInstance) {
            SHAPE.dispose();
        }
    }
}