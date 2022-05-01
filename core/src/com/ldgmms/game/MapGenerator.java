package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

import java.util.Random;

import static java.lang.Math.abs;
import static java.lang.Math.floorDiv;

/**
 * Class MapGenerator: In charge of the map generator interface model/state, as well as generating the map when mapGenerate() is called.
 */
public class MapGenerator implements Screen {
    private final TBDGame game;
    private OrthographicCamera camera;
    private final Player player;

    /* Player inputs */
    public MapSize MapSizePicker;
    public int GrassPercentSlider;
    public int ForestPercentSlider;
    public int HillsPercentSlider;
    public int WaterPercentSlider;
    public int DesertPercentSlider;
    public int UnifiedDifficultySlider;
    public boolean OverrideDifficulty;
    public int ResourceDifficulty;
    public int EnemyDifficulty;
    public Map map;

    private MapParams mapParams;

    public MapGenerator(TBDGame game, Player player) {
        this.game = game;
        this.player = player;

        this.MapSizePicker = MapSize.SMALL;
        this.GrassPercentSlider = 20; this.ForestPercentSlider = 20; this.HillsPercentSlider = 20; this.WaterPercentSlider = 20; this.DesertPercentSlider = 20;
        this.UnifiedDifficultySlider = 50; this.OverrideDifficulty = false; this.ResourceDifficulty = 0; this.EnemyDifficulty = 0;
    }

    /**
     * mapGenerate: Generate map using the Map() constructor
     */
    public void mapGenerate() {
        if (!OverrideDifficulty) {
            ResourceDifficulty = UnifiedDifficultySlider;
            EnemyDifficulty = UnifiedDifficultySlider;
        }
        mapParams = new MapParams(MapSizePicker, GrassPercentSlider, ForestPercentSlider, HillsPercentSlider, WaterPercentSlider, DesertPercentSlider, ResourceDifficulty, EnemyDifficulty);
        // Uses the Map(MapParams) constructor's random feature
        map = new Map(mapParams);
    }

    /**
     * mapGenerate: Generate map using the Map() constructor
     */
    public void mapGenerateDeterministic(int seed) {
        if (!OverrideDifficulty) {
            ResourceDifficulty = UnifiedDifficultySlider;
            EnemyDifficulty = UnifiedDifficultySlider;
        }
        mapParams = new MapParams(MapSizePicker, GrassPercentSlider, ForestPercentSlider, HillsPercentSlider, WaterPercentSlider, DesertPercentSlider, ResourceDifficulty, EnemyDifficulty);

        mapGenWalker generator = new mapGenWalker(seed);
        generator.walk();
        map = new Map(generator.mapTileArray);
    }


    /**
     * Class mapGenWalker: provides a way to iterate over the tiles in the map in a certain pattern, with repeatable random seed.
     */
    private class mapGenWalker {
        private Random genRand;
        private int mapDim;
        private int currentTileX = -1; // -1 is never a valid map index, so will throw ArrayOutOfBoundsException if accidentally use before setting
        private int currentTileY = -1;
        private CompassDirection directionFacing = CompassDirection.SOUTH;
        private MapTile[][] mapTileArray;

        /**
         * mapGenWalker constructor: Depends on the outer class's member fields for map parameters.
         * @param randomSeed: you must give an int to use, passing the same int (with same map params as well) results in exactly the same map being generated.
         */
        private mapGenWalker(int randomSeed) {
            genRand = new Random(randomSeed);
            switch (MapSizePicker) {
                case SMALL:
                    mapDim = 48;
                    break;
                case MEDIUM:
                    mapDim = 96;
                    break;
                case LARGE:
                    mapDim = 192;
                    break;
                default:
                    throw new IllegalStateException("Map size not set to a valid option!");
            }
            int mapCenterX = (int)Math.floor(mapDim / 2);
            int mapCenterY = (int)Math.floor(mapDim / 2);
            this.currentTileX = mapCenterX;
            this.currentTileY = mapCenterY;
            this.walk();
        }

        /**
         * walk: steps through the tiles in the map, generating new terrain for them
         */
        private void walk() {
            int inProgress = 0;
            while (inProgress != 1) {
                inProgress = nextTile();
            }
        }

        /**
         * nextTile: steps to next tile and generates based on the class's pseudorandom seed
         * Returns int which means: if successful move, 0; if reached end, 1
         */
        private int nextTile() {
            int generated = genRand.nextInt();
            int NumBits = 32;
            int[] bits = new int[NumBits];
            for (int i=0; i<NumBits; i++) {
                bits[i] = generated & (1 << i); // get the ith bit by masking with bit-shifted 1
            }
            assert(mapDim % 2 == 0); // spiral algorithm only tested to work with odd numbered sizes of sides of grid.
            switch (directionFacing) {
                case SOUTH: // fall through
                case SOUTHEAST:
                    currentTileY -= 1; // move South
                    if (currentTileY < 0 || currentTileY >= mapDim) return 1;
                    if (isNullCellToInside()) {
                        eighthTurn();
                    }
                    break;
                case EAST: // fall through
                case NORTHEAST:
                    currentTileX += 1; // move East
                    if (currentTileX < 0 || currentTileX >= mapDim) return 1;
                    if (isNullCellToInside()) {
                        eighthTurn();
                    }
                    break;
                case NORTH: // fall through
                case NORTHWEST:
                    currentTileY += 1; // move North
                    if (currentTileY < 0 || currentTileY >= mapDim) return 1;
                    if (isNullCellToInside()) {
                        eighthTurn();
                    }
                    break;
                case WEST: // fall through
                case SOUTHWEST:
                    currentTileX -= 1; // move West
                    if (currentTileX < 0 || currentTileX >= mapDim) return 1;
                    if (isNullCellToInside()) {
                        eighthTurn();
                    }
                    break;
            }
            // generate next tile
            return 0;
        }

        /**
         * isNullCellToInside: Check if the cell on the inside (left-hand side since this is a counterclockwise spiral) is empty.
         * @return boolean True if null, False if not null
         */
        private boolean isNullCellToInside() {
            int cellToInsideX = -1;
            int cellToInsideY = -1;
            switch (directionFacing) {
                case SOUTH: // fall through
                case SOUTHEAST:
                    cellToInsideX = currentTileX + 1;
                    cellToInsideY = currentTileY;
                    break;
                case EAST: // fall through
                case NORTHEAST:
                    cellToInsideX = currentTileX;
                    cellToInsideY = currentTileY + 1;
                    break;
                case NORTH: // fall through
                case NORTHWEST:
                    cellToInsideX = currentTileX - 1;
                    cellToInsideY = currentTileY;
                    break;
                case WEST: // fall through
                case SOUTHWEST:
                    cellToInsideX = currentTileX;
                    cellToInsideY = currentTileY - 1;
                    break;
            }
            return (null == this.mapTileArray[cellToInsideX][cellToInsideY]);
        }

        /**
         * eighthTurn: Execute a 1/8 turn counterclockwise.
         * Side effect: changes self.directionFacing.
         */
        private void eighthTurn() {
            switch (directionFacing) {
                case NORTH:
                    directionFacing = CompassDirection.NORTHWEST;
                    break;
                case NORTHWEST:
                    directionFacing = CompassDirection.WEST;
                    break;
                case WEST:
                    directionFacing = CompassDirection.SOUTHWEST;
                    break;
                case SOUTHWEST:
                    directionFacing = CompassDirection.SOUTH;
                    break;
                case SOUTH:
                    directionFacing = CompassDirection.SOUTHEAST;
                    break;
                case SOUTHEAST:
                    directionFacing = CompassDirection.EAST;
                    break;
                case EAST:
                    directionFacing = CompassDirection.NORTHEAST;
                    break;
                case NORTHEAST:
                    directionFacing =  CompassDirection.NORTH;
                    break;
            }
        }
    }

    /**
     * updateGrassSlider: Set grass % slider to a new value, adjusting the other sliders to add up to 100%.
     * Side effect: alters all the terrain sliders.
     * @param newVal : new grass percentage.
     */
    public void updateGrassSlider(int newVal) {
        int diff = this.GrassPercentSlider - newVal;
        if (diff == 0) return;
        GrassPercentSlider = newVal;
        int sign = (diff > 0 ? 1 : -1);
        int quotient = diff / 4;
        int remainder = abs(diff % 4);
        ForestPercentSlider += quotient;
        HillsPercentSlider += quotient;
        WaterPercentSlider += quotient;
        DesertPercentSlider += quotient;
        while (true) {
            if (remainder ==0) break;
            ForestPercentSlider += sign;
            if (remainder == 1) break;
            HillsPercentSlider += sign;
            if (remainder == 2) break;
            WaterPercentSlider += sign;
            break;
        }
        if (GrassPercentSlider + ForestPercentSlider + HillsPercentSlider + WaterPercentSlider + DesertPercentSlider != 100)
            throw new IllegalStateException("Slider percentages do not add up to 100");
    }

    /**
     * updateForestSlider: Set forest % slider to a new value, adjusting the other sliders to add up to 100%.
     * Side effect: alters all the terrain sliders.
     * @param newVal : new forest percentage.
     */
    public void updateForestSlider(int newVal) {
        int diff = this.ForestPercentSlider - newVal;
        if (diff == 0) return;
        ForestPercentSlider = newVal;
        int sign = (diff > 0 ? 1 : -1);
        int quotient = diff / 4;
        int remainder = abs(diff % 4);
        GrassPercentSlider += quotient;
        HillsPercentSlider += quotient;
        WaterPercentSlider += quotient;
        DesertPercentSlider += quotient;
        while (remainder != 0) {
            GrassPercentSlider += sign;
            if (remainder == 1) break;
            HillsPercentSlider += sign;
            if (remainder == 2) break;
            WaterPercentSlider += sign;
            break;
        }
        if (GrassPercentSlider + ForestPercentSlider + HillsPercentSlider + WaterPercentSlider + DesertPercentSlider != 100)
            throw new IllegalStateException("Slider percentages do not add up to 100");
    }

    /**
     * updateHillsSlider: Set hills % slider to a new value, adjusting the other sliders to add up to 100%.
     * Side effect: alters all the terrain sliders.
     * @param newVal : new hills percentage.
     */
    public void updateHillsSlider(int newVal) {
        int diff = this.HillsPercentSlider - newVal;
        if (diff == 0) return;
        HillsPercentSlider = newVal;
        int sign = (diff > 0 ? 1 : -1);
        int quotient = diff / 4;
        int remainder = abs(diff % 4);
        GrassPercentSlider += quotient;
        ForestPercentSlider += quotient;
        WaterPercentSlider += quotient;
        DesertPercentSlider += quotient;
        while (remainder != 0) {
            GrassPercentSlider += sign;
            if (remainder == 1) break;
            ForestPercentSlider += sign;
            if (remainder == 2) break;
            WaterPercentSlider += sign;
            break;
        }
        if (GrassPercentSlider + ForestPercentSlider + HillsPercentSlider + WaterPercentSlider + DesertPercentSlider != 100)
            throw new IllegalStateException("Slider percentages do not add up to 100");
    }

    /**
     * updateWaterSlider: Set water % slider to a new value, adjusting the other sliders to add up to 100%.
     * Side effect: alters all the terrain sliders.
     * @param newVal : new water percentage.
     */
    public void updateWaterSlider(int newVal) {
        int diff = this.WaterPercentSlider - newVal;
        if (diff == 0) return;
        WaterPercentSlider = newVal;
        int sign = (diff > 0 ? 1 : -1);
        int quotient = diff / 4;
        int remainder = abs(diff % 4);
        GrassPercentSlider += quotient;
        ForestPercentSlider += quotient;
        HillsPercentSlider += quotient;
        DesertPercentSlider += quotient;
        while (remainder != 0) {
            GrassPercentSlider += sign;
            if (remainder == 1) break;
            ForestPercentSlider += sign;
            if (remainder == 2) break;
            HillsPercentSlider += sign;
            break;
        }
        if (GrassPercentSlider + ForestPercentSlider + HillsPercentSlider + WaterPercentSlider + DesertPercentSlider != 100)
            throw new IllegalStateException("Slider percentages do not add up to 100");
    }

    /**
     * updateDesertSlider: Set desert % slider to a new value, adjusting the other sliders to add up to 100%.
     * Side effect: alters all the terrain sliders.
     * @param newVal : new desert percentage.
     */
    public void updateDesertSlider(int newVal) {
        int diff = this.DesertPercentSlider - newVal;
        if (diff == 0) return;
        DesertPercentSlider = newVal;
        int sign = (diff > 0 ? 1 : -1);
        int quotient = diff / 4;
        int remainder = abs(diff % 4);
        GrassPercentSlider += quotient;
        ForestPercentSlider += quotient;
        HillsPercentSlider += quotient;
        WaterPercentSlider += quotient;
        while (remainder != 0) {
            GrassPercentSlider += sign;
            if (remainder == 1) break;
            ForestPercentSlider += sign;
            if (remainder == 2) break;
            HillsPercentSlider += sign;
            break;
        }
        if (GrassPercentSlider + ForestPercentSlider + HillsPercentSlider + WaterPercentSlider + DesertPercentSlider != 100)
            throw new IllegalStateException("Slider percentages do not add up to 100");
    }



    // Todo
    @Override
    public void show() {
        return;
    }

    @Override
    public void render(float delta) {
        return;
    }

    @Override
    public void resize(int width, int height) {
        return;
    }

    @Override
    public void pause() {
        return;
    }

    @Override
    public void resume() {
        return;
    }

    @Override
    public void hide() {
        return;
    }

    @Override
    public void dispose() {
        return;
    }

}
