package com.rivelbop.rivelworks.physics3d.physics;

import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.rivelbop.rivelworks.graphics3d.shape.Shape3D;

/**
 * Used to create a JBullet dynamic body.
 *
 * @author David Jerzak (RivelBop)
 */
public class DynamicBody3D extends PhysicsBody3D {
    /**
     * Creates a dynamic body with the provided shape and mass.
     *
     * @param shape The shape of the body.
     * @param mass  The mass of the body.
     */
    public DynamicBody3D(Shape3D shape, float mass) {
        super(shape, mass, new MotionState(), btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
    }

    /**
     * @param activationState The activation state of the body, using tags from {@link com.badlogic.gdx.physics.bullet.collision.Collision}.
     */
    public void setState(int activationState) {
        getBody().setActivationState(activationState);
    }
}