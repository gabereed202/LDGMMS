package com.ldgmms.game;

public class Enemy extends GenericUnit{
    String spritePath;
    String name;

    public Enemy(String spritePath, String name, int health, int poisonResistance, int cutResistance,
                 int pierceResistance, int fireResistance, int iceResistance){

        this.spritePath = spritePath;
        this.name = name;
        this.health = health;
        this.poisonResistance = poisonResistance;
        this.cutResistance = cutResistance;
        this.pierceResistance = pierceResistance;
        this.fireResistance = fireResistance;
        this.iceResistance = iceResistance;
    }
}
