package com.rivelbop.rivelworks.g2d.physics.joint;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.rivelbop.rivelworks.g2d.physics.body.PhysicsBody2D;

/**
 * Prismatic joint, allows for relative translation of two bodies along a specified axis. A prismatic joint prevents relative rotation. Therefore, a prismatic joint has a single degree of freedom.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class JointPrismatic extends JointBase {
    /**
     * Creates a prismatic joint.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param lowerTranslation The lower translation limit, usually in meters.
     * @param upperTranslation The upper translation limit, usually in meters.
     * @param maxMotorForce    The maximum motor torque, usually in N-m.
     * @param motorSpeed       The desired motor speed in radians per second.
     * @param enableLimit      Enable/disable the joint limit.
     * @param enableMotor      Enable/disable the joint motor.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointPrismatic(World world, Body bodyA, Body bodyB, float lowerTranslation, float upperTranslation, float maxMotorForce, float motorSpeed, boolean enableLimit, boolean enableMotor, boolean collideConnected) {
        PrismaticJointDef definition = new PrismaticJointDef();

        definition.bodyA = bodyA;
        definition.bodyB = bodyB;

        definition.lowerTranslation = lowerTranslation;
        definition.upperTranslation = upperTranslation;

        definition.maxMotorForce = maxMotorForce;
        definition.motorSpeed = motorSpeed;

        definition.enableLimit = enableLimit;
        definition.enableMotor = enableMotor;
        definition.collideConnected = collideConnected;

        this.definition = definition;
        this.joint = world.createJoint(definition);
    }

    /**
     * Creates a prismatic joint. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param lowerTranslation The lower translation limit, usually in meters.
     * @param upperTranslation The upper translation limit, usually in meters.
     * @param maxMotorForce    The maximum motor torque, usually in N-m.
     * @param motorSpeed       The desired motor speed in radians per second.
     * @param enableLimit      Enable/disable the joint limit.
     * @param enableMotor      Enable/disable the joint motor.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointPrismatic(World world, PhysicsBody2D bodyA, PhysicsBody2D bodyB, float lowerTranslation, float upperTranslation, float maxMotorForce, float motorSpeed, boolean enableLimit, boolean enableMotor, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), lowerTranslation, upperTranslation, maxMotorForce, motorSpeed, enableLimit, enableMotor, collideConnected);
    }

    /**
     * @return The prismatic joint definition.
     */
    public PrismaticJointDef getDefinition() {
        return (PrismaticJointDef) definition;
    }

    /**
     * @return The prismatic joint from the physics world.
     */
    public PrismaticJoint getJoint() {
        return (PrismaticJoint) joint;
    }
}