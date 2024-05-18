package com.rivelbop.rivelworks.physics3d.physics;

import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.rivelbop.rivelworks.graphics3d.shape.Shape3D;

/**
 * Used to create a JBullet static body.
 *
 * @author David Jerzak (RivelBop)
 */
public class StaticBody3D extends PhysicsBody3D {
    /**
     * Creates a static body with the provided shape.
     *
     * @param shape The shape of the body.
     */
    public StaticBody3D(Shape3D shape) {
        super(shape, 0f, new MotionState(), btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
    }
}