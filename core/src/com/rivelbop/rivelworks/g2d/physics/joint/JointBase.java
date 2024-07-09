package com.rivelbop.rivelworks.g2d.physics.joint;

import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef;

/**
 * The skeleton of all Joints.
 *
 * @author David Jerzak (RivelBop)
 */
public abstract class JointBase {
    /**
     * The definition used to create the joint.
     */
    protected JointDef definition;

    /**
     * The created joint stored from the world.
     */
    protected Joint joint;

    /**
     * Gets the specific definition of the created joint (depending on the joint type).
     *
     * @return The definition of the joint.
     */
    public abstract JointDef getDefinition();

    /**
     * Gets the specific joint created from the world (depending on the joint type).
     *
     * @return The joint from the physics world.
     */
    public abstract Joint getJoint();
}