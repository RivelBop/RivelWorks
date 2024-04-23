package com.rivelbop.rivelworks.physics2d.joint;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.rivelbop.rivelworks.physics2d.body.PhysicsBody;

/**
 * Rope joint, enforces a maximum distance between two points on two bodies. It has no other effect. Warning: if you attempt to change the maximum length during the simulation you will get some non-physical behavior.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class JointRope extends JointBase {
    /**
     * Creates a rope joint.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointRope(World world, Body bodyA, Body bodyB, boolean collideConnected) {
        RopeJointDef definition = new RopeJointDef();
        definition.bodyA = bodyA;
        definition.bodyB = bodyB;

        definition.collideConnected = collideConnected;

        super.definition = definition;
        joint = world.createJoint(definition);
    }

    /**
     * Creates a rope joint. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointRope(World world, PhysicsBody bodyA, PhysicsBody bodyB, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), collideConnected);
    }

    /**
     * @return The rope joint definition.
     */
    public RopeJointDef getDefinition() {
        return (RopeJointDef) definition;
    }

    /**
     * @return The rope joint from the physics world.
     */
    public RopeJoint getJoint() {
        return (RopeJoint) joint;
    }
}