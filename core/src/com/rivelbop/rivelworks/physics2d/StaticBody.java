package com.rivelbop.rivelworks.physics2d;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * An extension of {@link PhysicsBody}, used to create static bodies.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class StaticBody extends PhysicsBody {
    /**
     * Create a static body by providing the world to add the body to and its shape.
     *
     * @param world  The world to add the body to.
     * @param aShape The shape of the body.
     */
    public StaticBody(World world, Shape aShape) {
        super(world, BodyDef.BodyType.StaticBody);

        this.fixture = body.createFixture(aShape, 0f);
        aShape.dispose();
    }
}