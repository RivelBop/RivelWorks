package com.rivelbop.rivelworks.g3d.physics.dynamic;

import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.rivelbop.rivelworks.g3d.graphics.shape.Shape3D;
import com.rivelbop.rivelworks.g3d.physics.Physics3D;

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

    @Override
    public void transform(Physics3D.TransformConfig config) {
        setState(Collision.ACTIVE_TAG);
        super.transform(config);
    }
}