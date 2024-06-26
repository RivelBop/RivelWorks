package com.rivelbop.rivelworks.physics3d.collision;

import com.badlogic.gdx.physics.bullet.collision.*;
import com.badlogic.gdx.utils.Disposable;
import com.rivelbop.rivelworks.graphics3d.shape.Shape3D;
import com.rivelbop.rivelworks.physics3d.Physics3D;

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
        CollisionObjectWrapper col1 = new CollisionObjectWrapper(BODY);
        CollisionObjectWrapper col2 = new CollisionObjectWrapper(otherBody.BODY);

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
     * @return Shape Model
     */
    public Shape3D getShape() {
        return SHAPE;
    }

    /**
     * @return Collision Object Body
     */
    public btCollisionObject getBody() {
        return BODY;
    }

    /**
     * Disposes all C-bound Bullet objects, as well as the {@link #SHAPE}.
     */
    @Override
    public void dispose() {
        COLLISION_SHAPE.dispose();
        BODY.dispose();
        CONFIG.dispose();
        DISPATCHER.dispose();
        SHAPE.dispose();
    }
}