package com.rivelbop.rivelworks.map2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.rivelbop.rivelworks.physics2d.StaticBody;

/**
 * Used as a skeleton for all tile maps.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public abstract class TileMap implements Disposable {
    /**
     * Stores the data of the tile map.
     */
    protected TiledMap map;

    /**
     * Used to render the tile map.
     */
    protected BatchTiledMapRenderer renderer;

    /**
     * Creates a tile map from the provided file name.
     *
     * @param fileName The name of the 'tmx' file to load.
     */
    public TileMap(String fileName) {
        map = new TmxMapLoader().load(fileName);
    }

    /**
     * Renders the tile map to the provided camera.
     *
     * @param camera The camera to render the tile map to.
     */
    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    /**
     * Returns an array of {@link Rectangle} obtained from the layer with the provided index.
     *
     * @param index The layer ID to get rectangles from.
     * @return {@link Rectangle} array based of {@link RectangleMapObject} in the layer.
     */
    public Array<Rectangle> getBoundingBoxes(int index) {
        // Get all the rectangles from the tile map
        Array<RectangleMapObject> rectangleMapObjects = map.getLayers().get(index).getObjects().getByType(RectangleMapObject.class);

        // Store all the rectangles into an array and return it
        Array<Rectangle> rectangles = new Array<>();
        for (RectangleMapObject r : rectangleMapObjects) {
            rectangles.add(r.getRectangle());
        }
        return rectangles;
    }

    /**
     * Returns an array of {@link Rectangle} obtained from the layer with the provided name.
     *
     * @param name The layer name to get rectangles from.
     * @return {@link Rectangle} array based of {@link RectangleMapObject} in the layer.
     */
    public Array<Rectangle> getBoundingBoxes(String name) {
        // Get all the rectangles from the tile map
        Array<RectangleMapObject> rectangleMapObjects = map.getLayers().get(name).getObjects().getByType(RectangleMapObject.class);

        // Store all the rectangles into an array and return it
        Array<Rectangle> rectangles = new Array<>();
        for (RectangleMapObject r : rectangleMapObjects) {
            rectangles.add(r.getRectangle());
        }
        return rectangles;
    }

    /**
     * Adds all {@link Rectangle} objects from the indexed layer into the provided physics world.
     *
     * @param index The layer ID to get rectangles from.
     * @param world The physics world to add the rectangles to.
     */
    public void staticBodyToPhysicsWorld(int index, World world) {
        // Get all the rectangles from the tile map
        Array<Rectangle> rectangles = getBoundingBoxes(index);

        // Add the rectangles to the physics world
        for (Rectangle r : rectangles) {
            StaticBody staticBody = new StaticBody(world, new PolygonShape() {{
                setAsBox(r.width / 2f, r.height / 2f);
            }});
            staticBody.getBody().setTransform(r.x + r.width / 2f, r.y + r.height / 2f, 0f);
        }
    }

    /**
     * Adds all {@link Rectangle} objects from the named layer into the provided physics world.
     *
     * @param name  The layer name to get rectangles from.
     * @param world The physics world to add the rectangles to.
     */
    public void staticBodyToPhysicsWorld(String name, World world) {
        // Get all the rectangles from the tile map
        Array<Rectangle> rectangles = getBoundingBoxes(name);

        // Add the rectangles to the physics world
        for (Rectangle r : rectangles) {
            StaticBody staticBody = new StaticBody(world, new PolygonShape() {{
                setAsBox(r.width / 2f, r.height / 2f);
            }});
            staticBody.getBody().setTransform(r.x + r.width / 2f, r.y + r.height / 2f, 0f);
        }
    }

    /**
     * Returns the stored default LibGDX tiled map for more control.
     *
     * @return The tiled map.
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Returns the renderer associated with this tiled map.
     *
     * @return Tiled map renderer.
     */
    public BatchTiledMapRenderer getRenderer() {
        return renderer;
    }

    /**
     * Removes {@link #map} and {@link #renderer} in the object from memory.
     * Should be called if the map is no longer in use.
     */
    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}