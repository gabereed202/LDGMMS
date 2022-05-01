package com.ldgmms.game;

import java.util.Random;

public class Goblin extends Enemy{
    private Random myRandom;
    public boolean isBaby;
    public boolean isGeriatric;

    /**
     * Goblin: a creature from the depths, renowned for its lust for gold. Goblins are known to hunt in packs. Be wary!
     */
    public Goblin() {
        EnemyType = "goblin";
        MeleeDamage = 10;
        Armor = 20;
        Health = 100;
        myRandom = new Random();
        Age = myRandom.nextInt(45); // goblins live to max age of 45
        if (this.Age > 40) isGeriatric = true;
        if (this.Age < 5) isBaby = true;
        assert !(isGeriatric && isBaby);
        if (isBaby) {
            MeleeDamage = 1;
            Armor = 0;
            Health = 50;
        }
        if (isGeriatric) {
            MeleeDamage = 5;
            Armor = 25;
            Health = 75;
        }
    }

    @Override
    public void fight(int attack, int defend) {
        return;
    }
}
