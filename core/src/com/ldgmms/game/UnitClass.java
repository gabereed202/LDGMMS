package com.ldgmms.game;

// Entity class is replaces Unit class from example code on discord
public class UnitClass{

    float hp;
    float mp;

    float status;
    float poisonResistance;

    // I don't know what all damage types will be so I'm not naming them yet
    // they will all work the same as poison
    float type1Resistance;
    float type2Resistance;
    float type3Resistance;
    float type4Resistance;

    public UnitClass(){

    }

    public void setStatus( float status ){
        this.status = status;
        return;
    }

    public float getPoisonResistance(){
        return this.poisonResistance;
    }

}
