package com.rivelbop.rivelworks.physics2d;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * An extension of {@link PhysicsBody}, used to create dynamic bodies.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class DynamicBody extends PhysicsBody {
    /**
     * Create a dynamic body by providing the world to add the body to, the body shape, and properties of the body.
     *
     * @param world      The world to add the body to.
     * @param aShape     The shape of the body.
     * @param density    The density of the body.
     * @param friction   The friction of the body.
     * @param bounciness The bounciness of the body.
     */
    public DynamicBody(World world, Shape aShape, float density, float friction, float bounciness) {
        super(world, BodyDef.BodyType.DynamicBody);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = aShape;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = bounciness;

        this.fixture = body.createFixture(fixtureDef);
        aShape.dispose();
    }
}