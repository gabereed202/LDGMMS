package com.ldgmms.game;

import java.util.Random;

public class Troll extends Enemy{
    private Random myRandom;
    public boolean isBaby = false;
    public boolean isChild = false;
    public boolean isGeriatric = false;

    /**
     * Troll: A mountain-dwelling beast, trolls are very tough and resistant to piercing damage. Their skin forms a leathery armor.
     * Though trolls are territorial and are rarely seen more than one at time, one troll may be enough to ruin your whole day.
     */
    public Troll() {
        EnemyType = "troll";
        MeleeDamage = 40;
        Armor = 60;
        Health = 800;
        myRandom = new Random();
        Age = myRandom.nextInt(350); // trolls live to max age of 350
        if (this.Age > 300) isGeriatric = true;
        if (this.Age < 50) isBaby = true;
        if (this.Age < 150) isChild = true;
        assert !(isGeriatric && isBaby);
        assert !(isGeriatric && isChild);
        if (isBaby) {
            MeleeDamage = 10;
            Armor = 10;
            Health = 100;
        }
        if (isChild) {
            MeleeDamage = 35;
            Armor = 30;
            Health = 400;
        }
        if (isGeriatric) {
            MeleeDamage = 20;
            Armor = 80;
            Health = 900;
        }
    }

    @Override
    public void fight(int attack, int defend) {
        return;
    }
}