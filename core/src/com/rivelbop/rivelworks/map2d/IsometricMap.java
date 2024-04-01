package com.rivelbop.rivelworks.map2d;

import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;

/**
 * Handles loading and rendering isometric maps.
 *
 * @author Philip Jerzak (RivelBop)
 */
public class IsometricMap extends TileMap {
    /**
     * Load a map and create an isometric renderer off of the provided file name.
     *
     * @param fileName The name of the 'tmx' file to load.
     */
    public IsometricMap(String fileName) {
        super(fileName);
        renderer = new IsometricTiledMapRenderer(map);
    }
}