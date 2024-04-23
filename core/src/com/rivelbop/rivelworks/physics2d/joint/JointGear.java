package com.rivelbop.rivelworks.physics2d.joint;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.GearJoint;
import com.badlogic.gdx.physics.box2d.joints.GearJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.rivelbop.rivelworks.physics2d.body.PhysicsBody;

/**
 * Gear Joint, is used to connect two joints together. Either joint can be a revolute or prismatic joint.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class JointGear extends JointBase {
    /**
     * Creates a gear joint, you specify a gear ratio to bind the motions together: coordinate1 + ratio * coordinate2 = constant The ratio can be negative or positive. If one joint is a revolute joint and the other joint is a prismatic joint, then the ratio will have units of length or units of 1/length.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param joint1           A revolute joint to connect.
     * @param joint2           A prismatic joint to connect.
     * @param ratio            A gear ratio to bind the motions together.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointGear(World world, Body bodyA, Body bodyB, RevoluteJoint joint1, PrismaticJoint joint2, float ratio, boolean collideConnected) {
        GearJointDef definition = new GearJointDef();
        definition.bodyA = bodyA;
        definition.bodyB = bodyB;

        definition.joint1 = joint1;
        definition.joint2 = joint2;

        definition.ratio = ratio;
        definition.collideConnected = collideConnected;

        super.definition = definition;
        joint = world.createJoint(definition);
    }

    /**
     * Creates a gear joint, you specify a gear ratio to bind the motions together: coordinate1 + ratio * coordinate2 = constant The ratio can be negative or positive. If one joint is a revolute joint and the other joint is a prismatic joint, then the ratio will have units of length or units of 1/length. Uses custom wrapped physics objects.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param joint1           A revolute joint to connect.
     * @param joint2           A prismatic joint to connect.
     * @param ratio            A gear ratio to bind the motions together.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointGear(World world, PhysicsBody bodyA, PhysicsBody bodyB, JointRevolute joint1, JointPrismatic joint2, float ratio, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), joint1.getJoint(), joint2.getJoint(), ratio, collideConnected);
    }

    /**
     * @return The gear joint definition.
     */
    @Override
    public GearJointDef getDefinition() {
        return (GearJointDef) definition;
    }

    /**
     * @return The gear joint from the physics world.
     */
    @Override
    public GearJoint getJoint() {
        return (GearJoint) joint;
    }
}