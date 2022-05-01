package com.ldgmms.game;

import org.junit.*;

/*
CS 320 - Software Engineering
Testing Suite
Linden Sarkinen
 */

public class MapGeneratorTest {

    // Create a MapGenerator instance to use in tests
    MapGenerator myMapGen = new MapGenerator(null, null);

    // Acceptance Test
    @Test
    public void test01MapSize() {
        // Test that constructor correctly set the MapSize
        assert myMapGen.MapSizePicker == MapSize.SMALL;
    }

    // Acceptance Test
    @Test
    public void test02UpdateGrassSlider() {
        // User moves the grass slider up to 75%
        myMapGen.updateGrassSlider(75);
        // This leaves 25 percentage points evenly divided among the other sliders, with the odd one out going to Desert.
        // Thus, we expect 75% Grass, 6% Forest, 6% Hills, 6% Water, 7% Desert
        assert myMapGen.GrassPercentSlider == 75;
        assert myMapGen.ForestPercentSlider == 6;
        assert myMapGen.HillsPercentSlider == 6;
        assert myMapGen.WaterPercentSlider == 6;
        assert myMapGen.DesertPercentSlider == 7;
    }

    // Acceptance Test
    @Test
    public void test03UpdateDesertSlider() {
        // From previous test (test02UpdateGrassSlider), values of G,F,H,W,D are 75,6,6,6,7
        // Now, let's bump up the desert slider to 50%
        myMapGen.updateDesertSlider(50);
        // This delta (43 percentage points) should be spread across the other percents proportionally
        // Expect 41,3,3,3,50
        assert myMapGen.GrassPercentSlider == 41;
        assert myMapGen.ForestPercentSlider == 3;
        assert myMapGen.HillsPercentSlider == 3;
        assert myMapGen.WaterPercentSlider == 3;
        assert myMapGen.DesertPercentSlider == 50;
    }

    // Create new MapParams instance to test on
    // Parameters: mapSize, grassPercent, forestPercent, hillsPercent, waterPercent, desertPercent
    //      , resourceDifficulty, enemyDifficulty
    MapParams myMapParams = new MapParams(MapSize.MEDIUM, 40, 10, 15, 10, 20
            , 25, 80);

    // Acceptance Test
    @Test
    public void test04MapParams() {
        // Test that constructor correctly built up the right World Seed
        assert myMapParams.worldSeed != null;
        //System.out.println(myMapParams.worldSeed);
        assert myMapParams.worldSeed.compareTo("%|?!&!+|0g") == 0;
    }

    // Acceptance Test
    @Test
    public void test05MapParamsEnemyChance() {
        // Troll base rate is 10%, the chosen Enemy Difficulty is 80%.
        // Thus, no matter what random value it is multiplied by, the trollChance can be no greater than 8%.
        assert myMapParams.trollChance <= 8;
        // Goblin base rate is 40%, the chosen Enemy Difficulty is 80%.
        // Thus, no matter what random value it is multiplied by, the goblinChance can be no greater than 32%.
        assert myMapParams.goblinChance <= 32;
    }

    // Acceptance Test
    @Test
    public void test06MapParamsResourceChance() {
        // Gold base rate: 10%
        // Iron base rate: 20%
        // resource difficulty multiplier: 25
        assert myMapParams.goldChance <= 3;
        assert myMapParams.ironChance <= 5;
    }

    // Acceptance Test
    @Test
    public void test07MapTileResourcesGenerate() {
        // Create new instance of map tile
        MapTile myMapTile = new MapTile(myMapParams,5,5);
        // Just as in test07MapParamsResourceChance, the actual gold amounts cannot be greater than the gold probability, likewise with iron.
        assert myMapTile.goldAmount <= 3;
        assert myMapTile.ironAmount <= 5;
    }

    // Acceptance Test
    @Test
    public void test08MapTileAddEnemy() {
        // Create new map tile
        MapTile myMapTile = new MapTile(myMapParams,5,5);
        // Test adding a new enemy of type troll, make sure it did create a troll
        myMapTile.addEnemy("troll");
        Enemy myTroll = myMapTile.Enemies.get(0);
        assert myTroll instanceof Troll;
    }

    // Acceptance Test
    @Test
    public void test09MapTileAddGoblin() {
        // Create new map tile
        MapTile myMapTile = new MapTile(myMapParams,5,5);
        // Test adding a new enemy of type goblin, make sure it did create a goblin
        myMapTile.addEnemy("goblin");
        Enemy myGoblin = myMapTile.Enemies.get(0);
        assert myGoblin instanceof Goblin;
    }

    // Acceptance Test
    @Test
    public void test10EnemyCreateTrollAge() {
        // Create new troll, test age-based logic
        Troll myTroll = new Troll();
        if (myTroll.Age > 300) assert myTroll.isGeriatric;
        if (myTroll.Age < 150) assert myTroll.isChild;
        if (myTroll.Age < 50) assert myTroll.isBaby;
        if (myTroll.Age > 150 && myTroll.Age < 300) assert (!myTroll.isGeriatric && !myTroll.isChild && !myTroll.isBaby);
        if (myTroll.MeleeDamage == 20) assert myTroll.isGeriatric;
        if (myTroll.MeleeDamage == 40) assert (!myTroll.isGeriatric && !myTroll.isChild && !myTroll.isBaby);
        if (myTroll.MeleeDamage == 35) assert myTroll.isChild;
        if (myTroll.MeleeDamage == 10) assert myTroll.isBaby;
    }

    // White Box Test of Map() with full statement coverage
    // Copy of the method's code:
    /*
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
        }
        mapArray = new MapTile[sizeX][sizeY];
        myRandom = new Random();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                mapArray[i][j] = new MapTile(this.mapParams, i, j);
            }
        }

    }
     */
    @Test
    public void test11WhiteBoxMap() {
        MapParams mapParamsSmall = new MapParams(MapSize.SMALL,20,20,20,20,20,50,50);
        MapParams mapParamsMedium = new MapParams(MapSize.MEDIUM,20,20,20,20,20,50,50);
        MapParams mapParamsLarge = new MapParams(MapSize.LARGE,20,20,20,20,20,50,50);

        Map mapSmall = new Map(mapParamsSmall);
        Map mapMedium = new Map(mapParamsMedium);
        Map mapLarge = new Map(mapParamsLarge);

        assert mapSmall.mapArray != null;
        assert mapMedium.mapArray != null;
        assert mapLarge.mapArray != null;

        for (int x = 0; x < 48; x++) {
            for (int y = 0; y < 48; y++) {
                assert mapSmall.mapArray[x][y] != null;
            }
        }
        for (int x = 0; x < 96; x++) {
            for (int y = 0; y < 96; y++) {
                assert mapMedium.mapArray[x][y] != null;
            }
        }
        for (int x = 0; x < 192; x++) {
            for (int y = 0; y < 192; y++) {
                assert mapLarge.mapArray[x][y] != null;
            }
        }

    }

    // Integration Test
    @Test
    public void test12Integration() {
        // Tests 3 classes - MapGenerator, MapParams, and Map
        // Test that construction of a Map succeeds with the current state of myMapGen (MapGenerator)
        myMapGen.mapGenerate();
        assert myMapGen.map != null;
    }
}
