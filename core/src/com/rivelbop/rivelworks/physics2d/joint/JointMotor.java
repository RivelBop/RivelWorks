package com.rivelbop.rivelworks.physics2d.joint;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MotorJoint;
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef;
import com.rivelbop.rivelworks.physics2d.body.PhysicsBody;

/**
 * Motor Joint, used to control the relative motion between two bodies. A typical usage is to control the movement of a dynamic body with respect to the ground.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class JointMotor extends JointBase {
    /**
     * Creates a motor joint, you must additionally specify the correction factor.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param correctionFactor Position correction factor in the range [0,1] (0.3f default).
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointMotor(World world, Body bodyA, Body bodyB, float correctionFactor, float maxForce, float maxTorque, boolean collideConnected) {
        MotorJointDef definition = new MotorJointDef();
        definition.initialize(bodyA, bodyB);

        definition.correctionFactor = correctionFactor;
        definition.maxForce = maxForce;
        definition.maxTorque = maxTorque;

        definition.collideConnected = collideConnected;

        super.definition = definition;
        joint = world.createJoint(definition);
    }

    /**
     * Creates a motor joint without specifying the correction factor.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointMotor(World world, Body bodyA, Body bodyB, float maxForce, float maxTorque, boolean collideConnected) {
        MotorJointDef definition = new MotorJointDef();
        definition.initialize(bodyA, bodyB);
        definition.maxForce = maxForce;
        definition.maxTorque = maxTorque;
        definition.collideConnected = collideConnected;

        super.definition = definition;
        joint = world.createJoint(definition);
    }

    /**
     * Creates a motor joint, you must additionally specify the correction factor. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param correctionFactor Position correction factor in the range [0,1] (0.3f default).
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointMotor(World world, PhysicsBody bodyA, PhysicsBody bodyB, float correctionFactor, float maxForce, float maxTorque, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), correctionFactor, maxForce, maxTorque, collideConnected);
    }

    /**
     * Creates a motor joint without specifying the correction factor. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param maxForce         The maximum friction force in Newtons.
     * @param maxTorque        The maximum torque friction in N-m.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointMotor(World world, PhysicsBody bodyA, PhysicsBody bodyB, float maxForce, float maxTorque, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), maxForce, maxTorque, collideConnected);
    }

    /**
     * @return The motor joint definition.
     */
    @Override
    public MotorJointDef getDefinition() {
        return (MotorJointDef) definition;
    }

    /**
     * @return The motor joint from the physics world.
     */
    @Override
    public MotorJoint getJoint() {
        return (MotorJoint) joint;
    }
}