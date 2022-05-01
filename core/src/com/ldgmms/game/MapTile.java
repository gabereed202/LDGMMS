package com.ldgmms.game;

import java.util.Random;
import java.util.ArrayList;

/**
 * Class MapTile: Each tile in the map stores its terrain, resource, and enemy presence attributes here.
 */
public class MapTile {
    Terrain terrainType;
    int goldAmount; // Range 0 to 99
    int ironAmount;
    ArrayList<Enemy> Enemies = new ArrayList<>();
    int xCoord;
    int yCoord;
    Random myRandom = new Random();

    /**
     * MapTile: Randomly generates tile based on the given chances in the params object. Used in the Map random constructor.
     * @param mapParams: MapParams object, gives chance percents for the resources and terrains.
     * @param xCoord: int, index X location in the map array.
     * @param yCoord: int, index Y location in the map array.
     */
    public MapTile(MapParams mapParams, int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        int random = myRandom.nextInt(100);
        int accumulator = mapParams.GrassPercent;
        if (random < accumulator) {
            terrainType = Terrain.GRASS;
        } else if (random < (accumulator += mapParams.ForestPercent)) {
            terrainType = Terrain.FOREST;
        } else if (random < (accumulator += mapParams.HillsPercent)) {
            terrainType = Terrain.HILLS;
        } else if (random < (accumulator += mapParams.WaterPercent)) {
            terrainType = Terrain.WATER;
        } else {
            terrainType = Terrain.DESERT;
        }
        int randomGold = myRandom.nextInt(100);
        if (mapParams.goldChance > randomGold) {
            goldAmount = mapParams.goldChance - randomGold;
        } else {
            goldAmount = 0;
        }
        int randomIron = myRandom.nextInt(100);
        if (mapParams.ironChance > randomIron) {
            ironAmount = mapParams.ironChance - randomIron;
        } else {
            ironAmount = 0;
        }
        int randomGoblin = myRandom.nextInt(100);
        if (mapParams.goblinChance > randomGoblin) {
            addEnemy("goblin");
        }
        int randomTroll = myRandom.nextInt(100);
        if (mapParams.trollChance > randomTroll) {
            addEnemy("troll");
        }
    }

    /**
     * Alternate constructor for DETERMINISTIC map generation, see MapGenerator.mapGenWalker.
     * @param terrainType : what terrain to use
     * @param xCoord : x coordinate of tile on map
     * @param yCoord : y coordinate of tile on map
     */
    public MapTile(Terrain terrainType, int xCoord, int yCoord) {
        this.terrainType = terrainType;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * addEnemy: Adds an enemy of the specified type to this map tile.
     * @param enemyType: String, key representing type of enemy. Currently supports "goblin" and "troll".
     */
    public void addEnemy(String enemyType) {
        Enemy enemy = EnemyFactory.getEnemy(enemyType);
        Enemies.add(enemy);
    }

}
