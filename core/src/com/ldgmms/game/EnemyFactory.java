package com.ldgmms.game;

/**
 * Class EnemyFactory: This class uses the Factory pattern to create the requested type of enemy and return it.
 */
public class EnemyFactory {

    /**
     * getEnemy: gets an enemy of the chosen type.
     * @param enemyType: String, type of enemy. Currently support "goblin" and "troll".
     * @return: Enemy object of the specified type.
     */
    public static Enemy getEnemy(String enemyType) {
        if (enemyType == null) return null;
        if (enemyType == "goblin") {
            return new Goblin();
        }
        if (enemyType == "troll") {
            return new Troll();
        }
        return null;
    }
}
