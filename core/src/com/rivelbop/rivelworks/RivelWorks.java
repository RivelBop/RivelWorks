package com.rivelbop.rivelworks;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.rivelbop.rivelworks.graphics2d.ShapeBatch;
import com.rivelbop.rivelworks.graphics3d.Model;
import com.rivelbop.rivelworks.graphics3d.shapes.Cone;
import com.rivelbop.rivelworks.graphics3d.shapes.Cube;
import com.rivelbop.rivelworks.map2d.IsometricMap;
import com.rivelbop.rivelworks.map2d.OrthogonalMap;
import com.rivelbop.rivelworks.physics2d.DynamicBody;
import com.rivelbop.rivelworks.ui.Font;

/**
 * Will be used for more functionality in the future, currently a mess due to testing.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public class RivelWorks extends ApplicationAdapter {
    World world;
    Box2DDebugRenderer debugRenderer;
    //OrthographicCamera cam;
    PerspectiveCamera cam;
    DynamicBody dynamicBody;
    SpriteBatch batch;
    Texture texture;
    StretchViewport viewport;
    Sprite sprite;

    OrthogonalMap map1;
    IsometricMap map2;
    ShapeBatch shapeBatch;
    Model model;

    FirstPersonCameraController cameraController;
    Font font;
    ModelBatch modelBatch;
    Cone cube;

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

        cam = new PerspectiveCamera(90f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), cam);

        cameraController = new FirstPersonCameraController(cam);
        Gdx.input.setInputProcessor(cameraController);

        dynamicBody = new DynamicBody(world, new CircleShape() {{
            setRadius(25f);
        }}, 0.1f, 1f, 1f);
        dynamicBody.getBody().setTransform(80f, 100f, 0f);
        batch = new SpriteBatch();
        texture = new Texture("badlogic.jpg");
        sprite = new Sprite(texture);
        dynamicBody.getBody().setUserData(sprite);

        map1 = new OrthogonalMap("level1.tmx");
        map2 = new IsometricMap("mqeqw.tmx");

        map1.staticBodyToPhysicsWorld("Ground", world);
        shapeBatch = new ShapeBatch();

        font = new Font(Gdx.files.internal("Catfiles.ttf"), 64, Color.RED);
        model = new Model(Gdx.files.internal("Creeper.obj"));

        modelBatch = new ModelBatch();
        cube = new Cone(30f, 30f, 30, Color.LIME);
    }

    @Override
    public void render() {
        Utils.clearScreen3D();

        float speed = 100f * Gdx.graphics.getDeltaTime();
        /*if (Gdx.input.isKeyPressed(Input.Keys.W)) {
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
        }*/

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            dynamicBody.getBody().setLinearVelocity(50f, dynamicBody.getBody().getLinearVelocity().y);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dynamicBody.getBody().setLinearVelocity(-50f, dynamicBody.getBody().getLinearVelocity().y);
        }

        world.step(1 / 60f, 6, 2);
        sprite.setPosition(dynamicBody.getBody().getPosition().x, dynamicBody.getBody().getPosition().y);

        cameraController.update();
        debugRenderer.render(world, cam.combined);
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        sprite.draw(batch);
        font.draw(batch, "HELLO WORLD", 0f, 300f);
        batch.end();

        //map1.render(cam);

        shapeBatch.setProjectionMatrix(cam.combined);
        shapeBatch.begin(ShapeRenderer.ShapeType.Filled);
        shapeBatch.setColor(Color.LIME);
        for(Rectangle r : map1.getBoundingBoxes("Bricks")) {
            shapeBatch.rect(r.x, r.y, r.width, r.height);
        }
        shapeBatch.end();

        modelBatch.begin(cam);
        modelBatch.render(model);
        modelBatch.render(cube);
        modelBatch.end();
        //map2.render(cam);
    }

    @Override
    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
        batch.dispose();
        texture.dispose();
        map1.dispose();
        map2.dispose();
    }
}