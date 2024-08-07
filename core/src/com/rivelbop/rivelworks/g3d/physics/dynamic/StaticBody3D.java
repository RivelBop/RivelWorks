package com.rivelbop.rivelworks.g3d.physics.dynamic;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.esotericsoftware.minlog.Log;
import com.rivelbop.rivelworks.g3d.graphics.shape.Shape3D;

/**
 * Used to create a JBullet static body.
 *
 * @author David Jerzak (RivelBop)
 */
public class StaticBody3D extends PhysicsBody3D {
    private static final String LOG_TAG = StaticBody3D.class.getSimpleName();

    /**
     * Creates a static body with the provided shape.
     *
     * @param shape The shape of the body.
     */
    public StaticBody3D(Shape3D shape) {
        super(shape, 0f, new MotionState(), btCollisionObject.CollisionFlags.CF_CUSTOM_MATERIAL_CALLBACK);
    }

    @Override
    public void setState(int activationState) {
        Log.warn(LOG_TAG, "It is not recommended to set the activation state of a static body!");
        super.setState(activationState);
    }

    @Override
    public void setVelocity(float x, float z) {
        Log.error(LOG_TAG, "Velocity cannot be set for static body!");
    }

    @Override
    public void setVelocity(Vector3 velocity) {
        Log.error(LOG_TAG, "Velocity cannot be set for static body!");
    }

    @Override
    public void setVelocity(float x, float y, float z) {
        Log.error(LOG_TAG, "Velocity cannot be set for static body!");
    }
}