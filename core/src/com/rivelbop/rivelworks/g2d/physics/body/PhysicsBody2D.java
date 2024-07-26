package com.rivelbop.rivelworks.g2d.physics.body;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The skeleton for all 2D physics bodies.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class PhysicsBody2D {
    /**
     * The body of the physics object from the physics world.
     */
    protected Body body;

    /**
     * The fixture of the {@link #body}.
     */
    protected Fixture fixture;

    /**
     * Common constructor amongst physics bodies, used to create the {@link #body} of the physics object.
     * SHOULD NOT BE CONSTRUCTED DIRECTLY
     *
     * @param world    The Box2D Physics World that the body will be created and added to.
     * @param bodyType Determines how the physics will interact with the {@link #body}.
     */
    protected PhysicsBody2D(World world, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        this.body = world.createBody(bodyDef);
    }

    /**
     * Creates and adds a physics body to a world using a physics body definition.
     *
     * @param world   The Box2D Physics World that the body will be created and added to.
     * @param bodyDef The body definition used to construct the body.
     */
    public PhysicsBody2D(World world, PhysicsBodyDef2D bodyDef) {
        this.body = world.createBody(bodyDef.bodyDef);
        this.fixture = body.createFixture(bodyDef.fixtureDef);
    }

    /**
     * Returns the body of the physics object to be used with more advanced Box2D features.
     *
     * @return The default LibGDX {@link Body} stored within the physics body.
     */
    public Body getBody() {
        return body;
    }

    /**
     * Returns the fixture of the physics object to be used with more advanced Box2D features.
     *
     * @return The default LibGDX {@link Fixture} stored within the physics body.
     */
    public Fixture getFixture() {
        return fixture;
    }
}