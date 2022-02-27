package com.ldgmms.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GenericUnit { //GenericUnit written by Daniel Fuchs //replace resistances and health with a hash map, return values in getters //use hash set to test if status effect is available in constant time
    //HashMap<String, Integer> statsMap;
    int cutRes;
    int pierceRes;
    int poisonRes;
    int iceRes;
    int fireRes;
    int slowRes;
    int hp;         //player life
    int hpMax;
    int mp;         //mana points
    int mpMax;
    int ap;         //action points (movement points)
    int apMax;
    ArrayList<StatusEffect.Effect> effectList;

    void damageHp(int magnitude) {
        int currHp = getHp();
        currHp = currHp - magnitude;
        if(currHp > 0) { //if unit isn't dead after the attack
            setHp(currHp);
        } else setHp(0); //else unit is dead (we won't be doing any negative HP shenanigans)
    }

    public GenericUnit(int cut, int pierce, int poison, int ice, int fire, int slow, int life, int mana, int action) { //constructor for our generic unit, sets up the initial hash map for base stats as well as the hash set for status effects
        cutRes = cut;
        pierceRes = pierce;
        poisonRes = poison;
        iceRes = ice;
        fireRes = fire;
        slowRes = slow;
        hp = life;
        hpMax = life;
        mp = mana;
        mpMax = mana;
        ap = action;
        apMax = action;
        effectList = new ArrayList<>(); //generates hash set that will store our status effects
    }


    void update() { //still need to implement, can do without iterator
        for (StatusEffect.Effect e : effectList) {
            e.apply(this); //should apply every damage effect in the list
        }

    }

    void removeFinishedEffects() { //still need to implement
        //if effect returns that no turns are remaining, remove it from the array list
        effectList.removeIf(e->e.finished());

    }

    public ArrayList<StatusEffect.Effect> getEffectList() {
        return effectList;
    }

    int getCutRes() {
        return cutRes;
    }

    int getPierceRes() {return pierceRes;
    }
    int getPoisonRes() {return poisonRes;}
    int getIceRes() {return iceRes;}
    int getFireRes() {return fireRes;}
    int getSlowRes(){return slowRes;}
    int getHp() {return hp;}
    int getHpMax() {return hpMax;}
    int getMp() {return mp;}
    int getMpMax() {return mpMax;}
    int getAp(){return ap;}
    int getApMax(){return apMax;}

    void setHp(int life) { //method will be used for both damage and healing so will need to check against maxHp
        if (life > this.hpMax) {
            hp = hpMax;
        } else this.hp = life;
    }
    void setMp(int mana){
        if (mana > this.mpMax){
            mp = mpMax;
        } else this.mp = mana;
    }
    void setAp(int movement){
        if (movement > this.apMax){
            ap = apMax;
        } else this.ap = movement; //maybe set up the potential to store extra AP when passing turns
    }

}
