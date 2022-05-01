package com.ldgmms.game;

import java.util.Random;

public class MapParams {
    public int goldChance;
    public int ironChance;
    public int goblinChance;
    public int trollChance;
    MapSize mapSize;
    public int GrassPercent;
    public int ForestPercent;
    public int HillsPercent;
    public int WaterPercent;
    public int DesertPercent;
    int ResourceDifficulty;
    int EnemyDifficulty;
    Random myRandom;
    String worldSeed;

    /**
     * MapParams: This class stores the user's preferences set in the MapGenerator UI.
     * @param mapSize: Enum, LARGE/MEDIUM/SMALL
     * @param grassPercent: int, 0-99. The terrain percents add up to 100.
     * @param forestPercent: int, 0-99. The terrain percents add up to 100.
     * @param hillsPercent: int, 0-99. The terrain percents add up to 100.
     * @param waterPercent: int, 0-99. The terrain percents add up to 100.
     * @param desertPercent: int, 0-99. The terrain percents add up to 100.
     * @param resourceDifficulty: int, 0-99. Used in generation of gold and iron.
     * @param enemyDifficulty: int, 0-99. Used in generation of trolls and goblins.
     */
    public MapParams(MapSize mapSize, int grassPercent, int forestPercent, int hillsPercent, int waterPercent, int desertPercent, int resourceDifficulty, int enemyDifficulty) {
        this.mapSize = mapSize;
        this.GrassPercent = grassPercent;
        this.ForestPercent = forestPercent;
        this.HillsPercent = hillsPercent;
        this.WaterPercent = waterPercent;
        this.DesertPercent = desertPercent;
        this.ResourceDifficulty = resourceDifficulty;
        this.EnemyDifficulty = enemyDifficulty;
        this.myRandom = new Random();
        assignDifficultyPercents();
        char sizeChar = ' ';
        switch (this.mapSize) {
            case SMALL:
                sizeChar = '#'; //char(23)
                break;
            case MEDIUM:
                sizeChar = '$'; //char(24)
                break;
            case LARGE:
                sizeChar = '%'; //char(25)
                break;
        }
        worldSeed = String.format("%c|%c%c%c%c%c|%c%c", sizeChar,
                grassPercent+23, forestPercent+23, hillsPercent+23, waterPercent+23, desertPercent+23,
                resourceDifficulty+23, enemyDifficulty+23 ); // use printable ascii range from 23-122 for 0-99 percent
    }

    /**
     * baseRate: Returns the base likelihood percentage for the string key, to be multiplied with the random chance.
     * @param item: String, represents the item the percentage is a frequency for.
     * @return: int 1-100, the base rate percentage.
     */
    private int baseRate(String item) { // int 0-99
        int rate;
        switch (item) {
            case "gold":
                rate = 10;
                break;
            case "iron":
                rate = 20;
                break;
            case "goblin":
                rate = 40;
                break;
            case "troll":
                rate = 10;
                break;
            default:
                rate = 100; // we are multiplying by the base rate; thus, 100% is the "do nothing"
                break;
        }
        return rate;
    }

    /**
     * assignDifficultyPercents: Sets the map param level difficulty percents based on the base rate and the pseudorandom generator.
     */
    private void assignDifficultyPercents() {
        int rand = myRandom.nextInt(100);
        goldChance = rand;
        goldChance *= ResourceDifficulty;
        goldChance /= 100;
        goldChance *= baseRate("gold");
        goldChance /= 100;
        rand = myRandom.nextInt(100);
        ironChance = rand;
        ironChance *= ResourceDifficulty;
        ironChance /= 100;
        ironChance *= baseRate("iron");
        ironChance /= 100;

        rand = myRandom.nextInt(100);
        goblinChance = rand;
        goblinChance *= EnemyDifficulty;
        goblinChance /= 100;
        goblinChance *= baseRate("goblin");
        goblinChance /= 100;
        rand = myRandom.nextInt(100);
        trollChance = rand;
        trollChance *= EnemyDifficulty;
        trollChance /= 100;
        trollChance *= baseRate("troll");
        trollChance /= 100;
    }
}
