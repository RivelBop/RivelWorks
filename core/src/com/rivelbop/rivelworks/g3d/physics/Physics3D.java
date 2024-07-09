package com.rivelbop.rivelworks.g3d.physics;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.*;
import com.rivelbop.rivelworks.g3d.graphics.shape.*;
import com.rivelbop.rivelworks.g3d.physics.dynamic.PhysicsBody3D;

/**
 * A static class containing methods and interfaces useful for 3D physics.
 *
 * @author David Jerzak (RivelBop)
 */
public final class Physics3D {
    private Physics3D() {
    }

    /**
     * Converts a {@link Shape3D} into a {@link btCollisionShape}, useful when creating {@link btCollisionObject}s.
     *
     * @param shape The shape model used to create the collision shape.
     * @return The collision shape with the provided shape's model.
     */
    public static btCollisionShape collisionShape(Shape3D shape) {
        btCollisionShape collisionShape;
        if (shape instanceof Cube) {
            Cube s = (Cube) shape;
            collisionShape = new btBoxShape(new Vector3(
                    s.getWidth() / 2f,
                    s.getHeight() / 2f,
                    s.getDepth() / 2f
            ));
        } else if (shape instanceof Sphere) {
            Sphere s = (Sphere) shape;
            collisionShape = new btSphereShape(s.getRadius());
        } else if (shape instanceof Cone) {
            Cone s = (Cone) shape;
            collisionShape = new btConeShape(s.getRadius(), s.getHeight());
        } else if (shape instanceof Capsule) {
            Capsule s = (Capsule) shape;
            collisionShape = new btCapsuleShape(s.getRadius(), s.getHeight() - s.getRadius() * 2f);
        } else if (shape instanceof Cylinder) {
            Cylinder s = (Cylinder) shape;
            collisionShape = new btCylinderShape(new Vector3(
                    s.getWidth() / 2f,
                    s.getHeight() / 2f,
                    s.getDepth() / 2f
            ));
        } else {
            collisionShape = Bullet.obtainStaticNodeShape(shape.model.nodes);
        }
        return collisionShape;
    }

    /**
     * Used to transform {@link PhysicsBody3D} objects.
     */
    @FunctionalInterface
    public interface TransformConfig {
        void transform(Matrix4 transform);
    }
}