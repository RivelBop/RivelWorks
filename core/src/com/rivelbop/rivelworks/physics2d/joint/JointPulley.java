package com.rivelbop.rivelworks.physics2d.joint;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.rivelbop.rivelworks.physics2d.body.PhysicsBody2D;

/**
 * Pulley joint, used to create an idealized pulley. The pulley connects two bodies to ground and to each other. As one body goes up, the other goes down. The total length of the pulley rope is conserved according to the initial configuration.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class JointPulley extends JointBase {
    /**
     * Creates a pulley joint.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param groundAnchorA    The first ground anchor in world coordinates. This point never moves.
     * @param groundAnchorB    The second ground anchor in world coordinates. This point never moves.
     * @param anchorA          The local anchor point relative to bodyA's origin.
     * @param anchorB          The local anchor point relative to bodyB's origin.
     * @param ratio            The pulley ratio, used to simulate a block-and-tackle.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointPulley(World world, Body bodyA, Body bodyB, Vector2 groundAnchorA, Vector2 groundAnchorB, Vector2 anchorA, Vector2 anchorB, float ratio, boolean collideConnected) {
        PulleyJointDef definition = new PulleyJointDef();
        definition.initialize(bodyA, bodyB, groundAnchorA, groundAnchorB, anchorA, anchorB, ratio);

        definition.collideConnected = collideConnected;

        this.definition = definition;
        this.joint = world.createJoint(definition);
    }

    /**
     * Creates a pulley joint. Uses custom physics wrappers.
     *
     * @param world            The world to add the joint to.
     * @param bodyA            The first body to connect.
     * @param bodyB            The second body to connect.
     * @param groundAnchorA    The first ground anchor in world coordinates. This point never moves.
     * @param groundAnchorB    The second ground anchor in world coordinates. This point never moves.
     * @param anchorA          The local anchor point relative to bodyA's origin.
     * @param anchorB          The local anchor point relative to bodyB's origin.
     * @param ratio            The pulley ratio, used to simulate a block-and-tackle.
     * @param collideConnected Enable/disable collisions between the two bodies.
     */
    public JointPulley(World world, PhysicsBody2D bodyA, PhysicsBody2D bodyB, Vector2 groundAnchorA, Vector2 groundAnchorB, Vector2 anchorA, Vector2 anchorB, float ratio, boolean collideConnected) {
        this(world, bodyA.getBody(), bodyB.getBody(), groundAnchorA, groundAnchorB, anchorA, anchorB, ratio, collideConnected);
    }

    /**
     * @return The pulley joint definition.
     */
    public PulleyJointDef getDefinition() {
        return (PulleyJointDef) definition;
    }

    /**
     * @return The pulley joint from the physics world.
     */
    public PulleyJoint getJoint() {
        return (PulleyJoint) joint;
    }
}