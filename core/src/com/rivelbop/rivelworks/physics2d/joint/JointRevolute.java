package com.rivelbop.rivelworks.physics2d.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.rivelbop.rivelworks.physics2d.body.PhysicsBody;

/**
 * Revolute Joint, forces two bodies to share a common anchor point, often called a hinge point. The revolute joint has a single degree of freedom: the relative rotation of the two bodies. This is called the joint angle.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class JointRevolute extends JointBase {
    /**
     * Creates a revolute joint.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param anchor           World anchor point.
     * @param lowerAngle       The lower angle for the joint limit (radians).
     * @param upperAngle       The upper angle for the joint limit (radians).
     * @param maxMotorTorque   The maximum motor torque used to achieve the desired motor speed. Usually in N-m.
     * @param motorSpeed       The desired motor speed. Usually in radians per second.
     * @param enableLimit      A flag to enable joint limits.
     * @param enableMotor      A flag to enable the joint motor.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointRevolute(World world, Body bodyA, Body bodyB, Vector2 anchor, float lowerAngle, float upperAngle, float maxMotorTorque, float motorSpeed, boolean enableLimit, boolean enableMotor, boolean collideConnected) {
        RevoluteJointDef definition = new RevoluteJointDef();
        definition.initialize(bodyA, bodyB, anchor);

        definition.lowerAngle = lowerAngle * (float) Math.PI;
        definition.upperAngle = upperAngle * (float) Math.PI;

        definition.maxMotorTorque = maxMotorTorque;
        definition.motorSpeed = motorSpeed;

        definition.enableLimit = enableLimit;
        definition.enableMotor = enableMotor;
        definition.collideConnected = collideConnected;

        super.definition = definition;
        joint = world.createJoint(definition);
    }

    /**
     * Creates a revolute joint. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param anchor           World anchor point.
     * @param lowerAngle       The lower angle for the joint limit (radians).
     * @param upperAngle       The upper angle for the joint limit (radians).
     * @param maxMotorTorque   The maximum motor torque used to achieve the desired motor speed. Usually in N-m.
     * @param motorSpeed       The desired motor speed. Usually in radians per second.
     * @param enableLimit      A flag to enable joint limits.
     * @param enableMotor      A flag to enable the joint motor.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointRevolute(World world, PhysicsBody bodyA, PhysicsBody bodyB, Vector2 anchor, float lowerAngle, float upperAngle, float maxMotorTorque, float motorSpeed, boolean enableLimit, boolean enableMotor, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), anchor, lowerAngle, upperAngle, maxMotorTorque, motorSpeed, enableLimit, enableMotor, collideConnected);
    }

    /**
     * @return The revolute joint definition.
     */
    public RevoluteJointDef getDefinition() {
        return (RevoluteJointDef) definition;
    }

    /**
     * @return The revolute joint from the physics world.
     */
    public RevoluteJoint getJoint() {
        return (RevoluteJoint) joint;
    }
}