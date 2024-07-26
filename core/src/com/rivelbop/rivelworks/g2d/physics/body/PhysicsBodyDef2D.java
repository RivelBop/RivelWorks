package com.rivelbop.rivelworks.g2d.physics.body;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Disposable;

public class PhysicsBodyDef2D implements Disposable {
    public BodyDef bodyDef;
    public FixtureDef fixtureDef;

    public PhysicsBodyDef2D() {
        this.bodyDef = new BodyDef();
        this.fixtureDef = new FixtureDef();
    }

    public PhysicsBodyDef2D(BodyDef bodyDef, FixtureDef fixtureDef) {
        this.bodyDef = bodyDef;
        this.fixtureDef = fixtureDef;
    }

    @Override
    public void dispose() {
        fixtureDef.shape.dispose();
    }
}