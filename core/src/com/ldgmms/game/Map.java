package com.ldgmms.game;

import java.util.Random;

public class Map {
    MapTile[][] mapArray;
    Random myRandom;
    MapParams mapParams;

    /**
     * Default Map constructor that uses the given params object to build a map.
     * @param params: MapParams object giving the percentage chances for the different terrains and resources.
     */
    public Map(MapParams params) {
        mapParams = params;
        int sizeX, sizeY;
        switch (mapParams.mapSize) {
            case SMALL:
                sizeX = 48;
                sizeY = 48;
                break;
            case MEDIUM:
                sizeX = 96;
                sizeY = 96;
                break;
            case LARGE:
                sizeX = 192;
                sizeY = 192;
                break;
            default:
                sizeX = 48;
                sizeY = 48;
                break;
        }
        mapArray = new MapTile[sizeX][sizeY];
        myRandom = new Random();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                mapArray[i][j] = new MapTile(this.mapParams, i, j);
            }
        }

    }

    /**
     * Alternate map constructor for maps made by the DETERMINISTIC MapGenerator.mapGenWalker.
     * @param RawMap : n x n array of map tiles
     */
    public Map(MapTile[][] RawMap) {
        this.mapArray = RawMap;
    }
}
