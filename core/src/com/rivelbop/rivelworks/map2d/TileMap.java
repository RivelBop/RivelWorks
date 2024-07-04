package com.rivelbop.rivelworks.map2d;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.BatchTiledMapRenderer;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.minlog.Log;
import com.rivelbop.rivelworks.physics2d.body.StaticBody2D;

/**
 * Used as a skeleton for all tile maps.
 *
 * @author David/Philip Jerzak (RivelBop)
 */
public abstract class TileMap implements Disposable {
    private static final String LOG_TAG = TileMap.class.getSimpleName();

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
        this.map = new TmxMapLoader().load(fileName);
        Log.info(LOG_TAG, "LOADED: " + fileName);
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
     * Renders the tile map with the provided layers to the provided camera.
     *
     * @param camera The camera to render the tile map to.
     * @param layers The layers from the tile mape to render.
     */
    public void render(OrthographicCamera camera, int... layers) {
        renderer.setView(camera);
        renderer.render(layers);
    }

    /**
     * Returns a list of bounding shapes found with the same type and tiled map layer index.
     *
     * @param tClass Used to 'identify' the shape's class type.
     * @param index  The layer ID to get the shapes from.
     * @param <T>    Specifies only Shape2D types can be provided.
     * @return Bounding shape list.
     */
    public <T extends Shape2D> Array<T> getBoundingShapes(Class<T> tClass, int index) {
        return getBoundingShapes(tClass, map.getLayers().get(index));
    }

    /**
     * Returns a list of bounding shapes found with the same type and tiled map layer name.
     *
     * @param tClass Used to 'identify' the shape's class type.
     * @param name   The layer name to get the shapes from.
     * @param <T>    Specifies only Shape2D types can be provided.
     * @return Bounding shape list.
     */
    public <T extends Shape2D> Array<T> getBoundingShapes(Class<T> tClass, String name) {
        return getBoundingShapes(tClass, map.getLayers().get(name));
    }

    /**
     * Returns a list of bounding shapes found with the same type and tiled map layer.
     *
     * @param tClass   Used to 'identify' the shape's class type.
     * @param mapLayer The map layer to gather bounding shapes from.
     * @param <T>      Specifies only Shape2D types can be provided.
     * @return Bounding shape list.
     */
    @SuppressWarnings("unchecked")
    private <T extends Shape2D> Array<T> getBoundingShapes(Class<T> tClass, MapLayer mapLayer) {
        if (tClass == Rectangle.class) {
            Array<RectangleMapObject> rectangleMapObjects = mapLayer.getObjects().getByType(RectangleMapObject.class);
            Array<Rectangle> rectangles = new Array<>();

            for (RectangleMapObject r : rectangleMapObjects) {
                rectangles.add(r.getRectangle());
            }

            return (Array<T>) rectangles;
        } else if (tClass == Polygon.class) {
            Array<PolygonMapObject> polygonMapObjects = mapLayer.getObjects().getByType(PolygonMapObject.class);
            Array<Polygon> polygons = new Array<>();

            for (PolygonMapObject p : polygonMapObjects) {
                polygons.add(p.getPolygon());
            }

            return (Array<T>) polygons;
        } else if (tClass == Ellipse.class) {
            Array<EllipseMapObject> ellipseMapObjects = mapLayer.getObjects().getByType(EllipseMapObject.class);
            Array<Ellipse> ellipses = new Array<>();

            for (EllipseMapObject e : ellipseMapObjects) {
                ellipses.add(e.getEllipse());
            }

            return (Array<T>) ellipses;
        } else if (tClass == Shape2D.class) {
            Array<Rectangle> rectangles = getBoundingShapes(Rectangle.class, mapLayer);
            Array<Polygon> polygons = getBoundingShapes(Polygon.class, mapLayer);
            Array<Ellipse> ellipses = getBoundingShapes(Ellipse.class, mapLayer);

            Array<Shape2D> shapes = new Array<>();
            shapes.addAll(rectangles);
            shapes.addAll(polygons);
            shapes.addAll(ellipses);

            return (Array<T>) shapes;
        }

        return null;
    }

    /**
     * Adds all applicable {@link Shape2D} objects, {@link Rectangle} and {@link Polygon}, from the indexed layer into the provided physics world.
     *
     * @param index The layer ID to get the shapes from.
     * @param world The physics world to add the shapes to.
     * @param PPM   Pixel to meter conversion.
     */
    public void boundingShapesToPhysicsWorld(int index, World world, float PPM) {
        boundingShapesToPhysicsWorld(map.getLayers().get(index), world, PPM);
    }

    /**
     * Adds all applicable {@link Shape2D} objects, {@link Rectangle} and {@link Polygon}, from the named layer into the provided physics world.
     *
     * @param name  The layer name to get the shapes from.
     * @param world The physics world to add the shapes to.
     * @param PPM   Pixel to meter conversion.
     */
    public void boundingShapesToPhysicsWorld(String name, World world, float PPM) {
        boundingShapesToPhysicsWorld(map.getLayers().get(name), world, PPM);
    }

    /**
     * Adds all applicable {@link Shape2D} objects, {@link Rectangle} and {@link Polygon}, from the layer into the provided physics world.
     *
     * @param mapLayer The layer to get the shapes from.
     * @param world    The physics world to add the shapes to.
     * @param PPM      Pixel to meter conversion.
     */
    private void boundingShapesToPhysicsWorld(MapLayer mapLayer, World world, float PPM) {
        Array<Rectangle> rectangles = getBoundingShapes(Rectangle.class, mapLayer);
        Array<Polygon> polygons = getBoundingShapes(Polygon.class, mapLayer);

        if (rectangles != null) {
            for (Rectangle r : rectangles) {
                StaticBody2D staticBody = new StaticBody2D(world, new PolygonShape() {{
                    setAsBox(r.width / 2f / PPM, r.height / 2f / PPM);
                }});
                staticBody.getBody().setTransform((r.x + r.width / 2f) / PPM, (r.y + r.height / 2f) / PPM, 0f);
            }
        }

        if (polygons != null) {
            for (Polygon p : polygons) {
                float[] vertices = p.getVertices();
                for (int i = 0; i < vertices.length; i++) {
                    vertices[i] /= PPM;
                }

                StaticBody2D staticBody = new StaticBody2D(world, new PolygonShape() {{
                    set(vertices);
                }});
                staticBody.getBody().setTransform(p.getX() / PPM, p.getY() / PPM, 0f);
            }
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