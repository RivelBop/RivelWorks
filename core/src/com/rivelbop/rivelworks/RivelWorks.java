package com.rivelbop.rivelworks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rivelbop.rivelworks.map2d.OrthogonalMap;
import com.rivelbop.rivelworks.physics2d.body.DynamicBody;
import com.rivelbop.rivelworks.physics2d.body.StaticBody;
import com.rivelbop.rivelworks.physics2d.joint.JointMouse;
import com.rivelbop.rivelworks.utils.Utils;

/**
 * Will be used for more functionality in the future, currently a mess due to testing.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class RivelWorks extends ApplicationAdapter {
    private final float PPM = 100f; // SHOULD BE SET TO A COMMON SIZE FOR YOUR IN-GAME OBJECTS

    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera cam;
    DynamicBody dynamicBody;
    DynamicBody dynamicBody1;
    StaticBody staticBody;
    StretchViewport viewport;

    SpriteBatch batch;
    Sprite sprite;
    Sprite wall;
    OrthogonalMap map;

    JointMouse joint3;

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

        world = new World(new Vector2(0, 0f), true);
        debugRenderer = new Box2DDebugRenderer();
        debugRenderer.SHAPE_STATIC.set(Color.LIME);

        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        batch = new SpriteBatch();
        sprite = new Sprite(new Texture("badlogic.jpg"));
        sprite.setSize(48f, 48f);

        wall = new Sprite(new Texture("badlogic.jpg"));
        wall.setSize(48f, 48f);

        dynamicBody = new DynamicBody(world, new PolygonShape() {{
            setAsBox(sprite.getWidth() / 2f / PPM, sprite.getHeight() / 2f / PPM);
        }}, 0f, 0f, 0f);
        dynamicBody.getBody().setFixedRotation(true);
        dynamicBody.getBody().setUserData(sprite);

        dynamicBody1 = new DynamicBody(world, new PolygonShape() {{
            setAsBox(sprite.getWidth() / 2f / PPM, sprite.getHeight() / 2f / PPM);
        }}, 0f, 0f, 0f);
        dynamicBody.getBody().setTransform(1f, 0f, 0f);

        staticBody = new StaticBody(world, new PolygonShape() {{
            setAsBox(wall.getWidth() / 2f / PPM, wall.getHeight() / 2f / PPM);
        }});
        staticBody.getBody().setFixedRotation(true);
        staticBody.getBody().setUserData(wall);

        map = new OrthogonalMap("map.tmx");
        map.boundingShapesToPhysicsWorld(1, world, PPM);
        System.out.println(map.getBoundingShapes(Polygon.class, "Obj").size);
    }

    @Override
    public void render() {
        Utils.clearScreen2D();
        float speed = 125f / PPM;
        dynamicBody.getBody().setLinearVelocity(0f, 0f);
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dynamicBody.getBody().setLinearVelocity(dynamicBody.getBody().getLinearVelocity().x, speed);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dynamicBody.getBody().setLinearVelocity(-speed, dynamicBody.getBody().getLinearVelocity().y);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            dynamicBody.getBody().setLinearVelocity(dynamicBody.getBody().getLinearVelocity().x, -speed);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dynamicBody.getBody().setLinearVelocity(speed, dynamicBody.getBody().getLinearVelocity().y);
        }

        world.step(Gdx.graphics.getDeltaTime(), 4, 6);
        sprite.setPosition(
                (dynamicBody.getBody().getPosition().x * PPM) - sprite.getWidth() / 2f,
                (dynamicBody.getBody().getPosition().y * PPM) - sprite.getHeight() / 2f
        );
        wall.setPosition(
                (staticBody.getBody().getPosition().x * PPM) - wall.getWidth() / 2f,
                (staticBody.getBody().getPosition().y * PPM) - wall.getHeight() / 2f
        );

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            cam.zoom -= 0.5f * Gdx.graphics.getDeltaTime();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            cam.zoom += 0.5f * Gdx.graphics.getDeltaTime();
        }

        cam.update();

        map.render(cam);
        debugRenderer.render(world, cam.combined);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        batch.end();
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
    }
}