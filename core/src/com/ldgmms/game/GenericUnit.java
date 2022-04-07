package com.ldgmms.game;

public abstract class GenericUnit {

    int poisonResistance;
    int cutResistance;
    int pierceResistance;
    int fireResistance;
    int iceResistance;

    float health;
    float mana;
    float stamina;

    public int getPoisonResistance(){
        return this.poisonResistance;
    }
    public int getCutResistance(){
        return this.cutResistance;
    }
    public int getPierceResistance(){
        return this.pierceResistance;
    }
    public int getFireResistance() {
        return fireResistance;
    }
    public int getIceResistance() {
        return iceResistance;
    }
    public float getHealth() {
        return health;
    }
    public float getMana() {
        return mana;
    }
    public float getStamina() {
        return stamina;
    }
}
