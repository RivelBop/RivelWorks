package com.rivelbop.rivelworks.g3d.physics.dynamic;

import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.rivelbop.rivelworks.g3d.graphics.shape.Shape3D;
import com.rivelbop.rivelworks.g3d.physics.Physics3D;

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

    @Override
    public void transform(Physics3D.TransformConfig config) {
        super.setState(Collision.ACTIVE_TAG);
        super.transform(config);
    }
}