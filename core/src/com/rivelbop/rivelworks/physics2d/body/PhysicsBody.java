package com.rivelbop.rivelworks.physics2d.body;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.World;

/**
 * The skeleton for all 2D physics bodies.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public abstract class PhysicsBody {
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
     *
     * @param world    The Box2D Physics World that the body will be created off of.
     * @param bodyType Determines how the physics will interact with the {@link #body}.
     */
    public PhysicsBody(World world, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        this.body = world.createBody(bodyDef);
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