package com.rivelbop.rivelworks.map2d;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

/**
 * Handles loading and rendering orthogonal maps.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class OrthogonalMap extends TileMap {
    /**
     * Load a map and create an orthogonal renderer off of the provided file name.
     *
     * @param fileName The name of the 'tmx' file to load.
     */
    public OrthogonalMap(String fileName) {
        super(fileName);
        renderer = new OrthogonalTiledMapRenderer(map);
    }
}