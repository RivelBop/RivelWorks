package com.rivelbop.rivelworks.physics3d.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.utils.Disposable;
import com.rivelbop.rivelworks.graphics3d.shape.Shape3D;
import com.rivelbop.rivelworks.physics3d.Physics3D;

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
        COLLISION_SHAPE.dispose();
        if (STATE != null) {
            STATE.dispose();
        }
    }
}