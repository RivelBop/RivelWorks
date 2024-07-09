package com.rivelbop.rivelworks.g2d.physics.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.rivelbop.rivelworks.g2d.physics.body.PhysicsBody2D;

/**
 * Weld joint, essentially glues two bodies together. A weld joint may distort somewhat because the island constraint solver is approximate.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class JointWeld extends JointBase {
    /**
     * Creates a weld joint.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param anchor           The world anchor point.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointWeld(World world, Body bodyA, Body bodyB, Vector2 anchor, boolean collideConnected) {
        WeldJointDef definition = new WeldJointDef();
        definition.initialize(bodyA, bodyB, anchor);

        definition.collideConnected = collideConnected;

        this.definition = definition;
        this.joint = world.createJoint(definition);
    }

    /**
     * Creates a weld joint. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param anchor           The world anchor point.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointWeld(World world, PhysicsBody2D bodyA, PhysicsBody2D bodyB, Vector2 anchor, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), anchor, collideConnected);
    }

    /**
     * @return The weld joint definition.
     */
    public WeldJointDef getDefinition() {
        return (WeldJointDef) definition;
    }

    /**
     * @return The weld joint from the physics world.
     */
    public WeldJoint getJoint() {
        return (WeldJoint) joint;
    }
}