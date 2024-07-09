package com.rivelbop.rivelworks.g2d.physics.joint;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.rivelbop.rivelworks.g2d.physics.body.PhysicsBody2D;

/**
 * Mouse joint, used in the testbed to manipulate bodies with the mouse. It attempts to drive a point on a body towards the current position of the cursor. There is no restriction on rotation.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class JointMouse extends JointBase {
    /**
     * Creates a mouse joint.
     *
     * @param world The world to add the joint to.
     * @param bodyA The first body to connect.
     * @param bodyB The second body to connect.
     */
    public JointMouse(World world, Body bodyA, Body bodyB) {
        MouseJointDef definition = new MouseJointDef();
        definition.target.set(Gdx.input.getX(), Gdx.input.getY());

        definition.bodyA = bodyA;
        definition.bodyB = bodyB;

        this.definition = definition;
        this.joint = world.createJoint(definition);
    }

    /**
     * Creates a mouse joint. Uses custom physics wrappers.
     *
     * @param world The world to add the joint to.
     * @param bodyA The first body to connect.
     * @param bodyB The second body to connect.
     */
    public JointMouse(World world, PhysicsBody2D bodyA, PhysicsBody2D bodyB) {
        this(world, bodyA.getBody(), bodyB.getBody());
    }

    /**
     * Updates the mouse joints target to the cursors position.
     *
     * @param camera The camera to project mouse coordinates to.
     */
    public void update(Camera camera) {
        Vector3 mouseCoords = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0f));
        ((MouseJoint) joint).setTarget(new Vector2(mouseCoords.x, mouseCoords.y));
    }

    /**
     * @return The mouse joint definition.
     */
    @Override
    public MouseJointDef getDefinition() {
        return (MouseJointDef) definition;
    }

    /**
     * @return The mouse joint from the physics world.
     */
    @Override
    public MouseJoint getJoint() {
        return (MouseJoint) joint;
    }
}