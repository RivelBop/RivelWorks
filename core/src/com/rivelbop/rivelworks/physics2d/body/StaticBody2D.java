package com.rivelbop.rivelworks.physics2d.body;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * An extension of {@link PhysicsBody2D}, used to create static bodies.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class StaticBody2D extends PhysicsBody2D {
    /**
     * Create a static body by providing the world to add the body to and its shape.
     *
     * @param world  The world to add the body to.
     * @param aShape The shape of the body.
     */
    public StaticBody2D(World world, Shape aShape) {
        super(world, BodyDef.BodyType.StaticBody);

        this.fixture = body.createFixture(aShape, 0f);
        aShape.dispose();
    }
}