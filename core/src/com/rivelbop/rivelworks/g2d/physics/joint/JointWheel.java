package com.rivelbop.rivelworks.g2d.physics.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WheelJoint;
import com.badlogic.gdx.physics.box2d.joints.WheelJointDef;
import com.rivelbop.rivelworks.g2d.physics.body.PhysicsBody2D;

/**
 * Wheel joint, provides two degrees of freedom: translation along an axis fixed in bodyA and rotation in the plane. You can use a joint limit to restrict the range of motion and a joint motor to drive the rotation or to model rotational friction. This joint is designed for vehicle suspensions.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class JointWheel extends JointBase {
    /**
     * Creates a wheel joint.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param anchor           The world anchor point.
     * @param axis             The local translation axis in body1.
     * @param maxMotorTorque   The maximum motor torque, usually in N-m.
     * @param motorSpeed       The desired motor speed in radians per second.
     * @param dampingRatio     Suspension damping ratio, one indicates critical damping.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointWheel(World world, Body bodyA, Body bodyB, Vector2 anchor, Vector2 axis, float maxMotorTorque, float motorSpeed, float dampingRatio, boolean collideConnected) {
        WheelJointDef definition = new WheelJointDef();
        definition.initialize(bodyA, bodyB, anchor, axis);

        definition.maxMotorTorque = maxMotorTorque;
        definition.motorSpeed = motorSpeed;
        definition.dampingRatio = dampingRatio;

        definition.collideConnected = collideConnected;

        this.definition = definition;
        this.joint = world.createJoint(definition);
    }

    /**
     * Creates a wheel joint. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param anchor           The world anchor point.
     * @param axis             The local translation axis in body1.
     * @param maxMotorTorque   The maximum motor torque, usually in N-m.
     * @param motorSpeed       The desired motor speed in radians per second.
     * @param dampingRatio     Suspension damping ratio, one indicates critical damping.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointWheel(World world, PhysicsBody2D bodyA, PhysicsBody2D bodyB, Vector2 anchor, Vector2 axis, float maxMotorTorque, float motorSpeed, float dampingRatio, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), anchor, axis, maxMotorTorque, motorSpeed, dampingRatio, collideConnected);
    }

    /**
     * @return The wheel join definition.
     */
    public WheelJointDef getDefinition() {
        return (WheelJointDef) definition;
    }

    /**
     * @return The wheel joint from the physics world.
     */
    public WheelJoint getJoint() {
        return (WheelJoint) joint;
    }
}