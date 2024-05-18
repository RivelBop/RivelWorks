package com.rivelbop.rivelworks;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.bullet.Bullet;

/**
 * Used as the "Main" class of the game.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class RivelWorks extends Game {
    /**
     * Used to initialize both Box2D and JBullet.
     *
     * @param box2D  Should Box2D be initialized?
     * @param bullet Should JBullet be initialized?
     */
    public static void init(boolean box2D, boolean bullet) {
        if (box2D) {
            Box2D.init();
            System.out.println("Box2D has been initialized!");
        }

        if (bullet) {
            Bullet.init();
            System.out.println("Bullet has been initialized!");
        }
    }

    @Override
    public void create() {

    }

    @Override
    public void render() {

    }

    @Override
    public void dispose() {

    }
}