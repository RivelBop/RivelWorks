package com.rivelbop.rivelworks.physics2d.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.rivelbop.rivelworks.physics2d.body.PhysicsBody;

/**
 * A distance joint, makes length between bodies constant.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class JointDistance extends JointBase {
    /**
     * A more customized variant of a distance joint.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param x1               The x used for length and positioning on bodyA.
     * @param y1               The y used for length and positioning on a bodyA.
     * @param x2               The x used for length and positioning on a bodyB.
     * @param y2               The y used for length and positioning on a bodyB.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointDistance(World world, Body bodyA, Body bodyB, float x1, float y1, float x2, float y2, boolean collideConnected) {
        DistanceJointDef definition = new DistanceJointDef();
        definition.initialize(bodyA, bodyB, new Vector2(x1, y1), new Vector2(x2, y2));
        definition.collideConnected = collideConnected;

        super.definition = definition;
        joint = world.createJoint(definition);
    }

    /**
     * Creates a distance joint by providing just the length instead of the specific coordinates.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param length           The length of the joint.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointDistance(World world, Body bodyA, Body bodyB, float length, boolean collideConnected) {
        DistanceJointDef definition = new DistanceJointDef();
        definition.bodyA = bodyA;
        definition.bodyB = bodyB;
        definition.length = length;
        definition.collideConnected = collideConnected;

        super.definition = definition;
        joint = world.createJoint(definition);
    }

    /**
     * A more customized variant of a distance joint, takes in the wrapped physics bodies instead of the LibGDX defaults.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param x1               The x used for length and positioning on bodyA.
     * @param y1               The y used for length and positioning on a bodyA.
     * @param x2               The x used for length and positioning on a bodyB.
     * @param y2               The y used for length and positioning on a bodyB.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointDistance(World world, PhysicsBody bodyA, PhysicsBody bodyB, float x1, float y1, float x2, float y2, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), x1, y1, x2, y2, collideConnected);
    }

    /**
     * Creates a distance joint by providing just the length instead of the specific coordinates, takes in the wrapped physics bodies instead of the LibGDX defaults.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param length           The length of the joint.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointDistance(World world, PhysicsBody bodyA, PhysicsBody bodyB, float length, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), length, collideConnected);
    }

    /**
     * @return The distance joint definition.
     */
    @Override
    public DistanceJointDef getDefinition() {
        return (DistanceJointDef) definition;
    }

    /**
     * @return The distance joint from the physics world.
     */
    @Override
    public DistanceJoint getJoint() {
        return (DistanceJoint) joint;
    }
}