package com.rivelbop.rivelworks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rivelbop.rivelworks.physics2d.DynamicBody;

/**
 * Will be used for more functionality in the future, currently a mess due to testing.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class RivelWorks extends ApplicationAdapter {
    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    DynamicBody dynamicBody;
    SpriteBatch batch;
    Texture texture;
    StretchViewport viewport;
    Sprite sprite;

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
        init(true, false);

        world = new World(new Vector2(0, -100f), true);
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.SHAPE_STATIC.set(Color.LIME);

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);
        dynamicBody = new DynamicBody(world, new CircleShape() {{
            setRadius(25f);
        }}, 0.1f, 1f, 1f);
        dynamicBody.getBody().setTransform(80f, 100f, 0f);
        batch = new SpriteBatch();
        texture = new Texture("badlogic.jpg");
        sprite = new Sprite(texture);
        dynamicBody.getBody().setUserData(sprite);
    }

    @Override
    public void render() {
        Utils.clearScreen2D();

        float speed = 100f * Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0f, speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-speed, 0f);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0f, -speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cam.translate(speed, 0f);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dynamicBody.getBody().setLinearVelocity(50f, dynamicBody.getBody().getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dynamicBody.getBody().setLinearVelocity(-50f, dynamicBody.getBody().getLinearVelocity().y);
        }

        world.step(1 / 60f, 6, 2);
        sprite.setPosition(dynamicBody.getBody().getPosition().x, dynamicBody.getBody().getPosition().y);

        cam.update();
        debugRenderer.render(world, cam.combined);
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        texture.dispose();
    }
}