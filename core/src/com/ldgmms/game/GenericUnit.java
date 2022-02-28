package com.ldgmms.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GenericUnit { //GenericUnit written by Daniel Fuchs
    //HashMap<String, Integer> statsMap;
    int cutRes, pierceRes, poisonRes, iceRes, fireRes, slowRes; //resistances
    int cutResBonus, pierceResBonus, poisonResBonus, iceResBonus, fireResBonus, slowResBonus; //bonuses could be negative, make sure resistances never less than zero?
    int hp, hpBonus, hpMax;          //player life
    int mp, mpBonus, mpMax;          //mana points
    int ap, apBonus, apMax;          //action points (movement points)

    ArrayList<StatusEffect.Effect> effectList;

    void damageHp(int magnitude) {
        int currHp = getHp();
        currHp = currHp - magnitude;
        if(currHp > 0) { //if unit isn't dead after the attack
            setHp(currHp);
        } else setHp(0); //else unit is dead (we won't be doing any negative HP shenanigans)
    }

    public GenericUnit(int cut, int pierce, int poison, int ice, int fire, int slow, int life, int mana, int action) { //constructor for our generic unit
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
        hpBonus = mpBonus = apBonus = cutResBonus = pierceResBonus = poisonResBonus = iceResBonus = fireResBonus = slowResBonus = 0; //set all these bonuses to zero
        effectList = new ArrayList<>(); //generates array list that will store our status effects
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
        return cutRes + cutResBonus;
    }

    int getPierceRes() {return pierceRes + pierceResBonus;}
    int getPoisonRes() {return poisonRes + poisonResBonus;}
    int getIceRes() {return iceRes + iceResBonus;}
    int getFireRes() {return fireRes + fireResBonus;}
    int getSlowRes(){return slowRes + slowResBonus;}
    int getHp() {return hp + hpBonus;}
    int getHpMax() {return hpMax;}
    int getMp() {return mp + mpBonus;}
    int getMpMax() {return mpMax;}
    int getAp(){return ap + apBonus;}
    int getApMax(){return apMax;}

    void setHp(int life) { //method will be used for both damage and healing so will need to check against maxHp
        if (life > this.hpMax + this.hpBonus) {
            this.hp = this.hpMax + this.hpBonus; //is "this." necessary?
        } else this.hp = life;
    }
    void setMp(int mana){
        if (mana > this.mpMax + this.mpBonus){
            this.mp = this.mpMax + this.mpBonus;
        } else this.mp = mana;
    }
    void setAp(int movement){
        if (movement > this.apMax + this.apBonus){
            this.ap = this.apMax + this.apBonus;
        } else this.ap = movement; //maybe set up the potential to store extra AP when passing turns
    }

}
