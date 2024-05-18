package com.rivelbop.rivelworks.physics2d.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.rivelbop.rivelworks.physics2d.body.PhysicsBody2D;

/**
 * Friction Joint, is used for top-down friction. It provides 2D translational friction and angular friction.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class JointFriction extends JointBase {
    /**
     * Creates a friction joint with the specified anchor point.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param x                The x-coordinate of the anchor point.
     * @param y                The y-coordinate of the anchor point.
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointFriction(World world, Body bodyA, Body bodyB, float x, float y, float maxForce, float maxTorque, boolean collideConnected) {
        FrictionJointDef definition = new FrictionJointDef();
        definition.initialize(bodyA, bodyB, new Vector2(x, y));
        definition.maxForce = maxForce;
        definition.maxTorque = maxTorque;
        definition.collideConnected = collideConnected;

        this.definition = definition;
        this.joint = world.createJoint(definition);
    }

    /**
     * Creates a friction joint without an anchor point.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointFriction(World world, Body bodyA, Body bodyB, float maxForce, float maxTorque, boolean collideConnected) {
        FrictionJointDef definition = new FrictionJointDef();
        definition.bodyA = bodyA;
        definition.bodyB = bodyB;
        definition.maxForce = maxForce;
        definition.maxTorque = maxTorque;
        definition.collideConnected = collideConnected;

        this.definition = definition;
        this.joint = world.createJoint(definition);
    }

    /**
     * Creates a friction joint with the specified anchor point, takes in the wrapped physics bodies instead of the LibGDX defaults.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param x                The x-coordinate of the anchor point.
     * @param y                The y-coordinate of the anchor point.
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointFriction(World world, PhysicsBody2D bodyA, PhysicsBody2D bodyB, float x, float y, float maxForce, float maxTorque, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), x, y, maxForce, maxTorque, collideConnected);
    }

    /**
     * Creates a friction joint without an anchor point, takes in the wrapped physics bodies instead of the LibGDX defaults.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointFriction(World world, PhysicsBody2D bodyA, PhysicsBody2D bodyB, float maxForce, float maxTorque, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), maxForce, maxTorque, collideConnected);
    }

    /**
     * @return The friction joint definition.
     */
    @Override
    public FrictionJointDef getDefinition() {
        return (FrictionJointDef) definition;
    }

    /**
     * @return The friction joint from the physics world.
     */
    @Override
    public FrictionJoint getJoint() {
        return (FrictionJoint) joint;
    }
}