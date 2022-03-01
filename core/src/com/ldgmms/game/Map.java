package com.ldgmms.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;

/**
 * Game map, consisting of tiles.
 * @author Matthew Rease
 */
public class Map extends TiledMap {
    TiledMapTileSet tiles;

    /**
     * Create a new map, with a given tile set.
     * @param data The map tileset.
     * @see TiledMap
     * @see TiledMapTileSet
     */
    public Map(TiledMapTileSet data) {
        // Set our copy
        tiles = data;
        // And set parent class' things, so it can be used properly.
        super.getTileSets().addTileSet(tiles);
    }
}
