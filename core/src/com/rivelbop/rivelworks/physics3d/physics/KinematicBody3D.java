package com.rivelbop.rivelworks.physics3d.physics;

import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.rivelbop.rivelworks.graphics3d.shape.Shape3D;
import com.rivelbop.rivelworks.physics3d.Physics3D;

/**
 * Used to create a JBullet kinematic body.
 *
 * @author David Jerzak (RivelBop)
 */
public class KinematicBody3D extends PhysicsBody3D {
    /**
     * Creates a kinematic body with the provided shape.
     *
     * @param shape The shape of the body.
     */
    public KinematicBody3D(Shape3D shape) {
        super(shape, 0f, null, btCollisionObject.CollisionFlags.CF_KINEMATIC_OBJECT);
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