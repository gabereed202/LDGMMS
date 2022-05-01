package com.ldgmms.game;

public abstract class Enemy {
    public String EnemyType;
    public int MeleeDamage;
    public int Armor;
    public int Health;
    public int Age;

    public abstract void fight(int attack, int defend);
}
