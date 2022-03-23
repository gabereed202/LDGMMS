package com.ldgmms.game;

import java.util.ArrayList;

public class GenericUnit { //GenericUnit written by Daniel Fuchs
    //HashMap<String, Integer> statsMap;
    int cutRes, pierceRes, poisonRes, iceRes, fireRes, slowRes; //resistances
    int cutResBonus, pierceResBonus, poisonResBonus, iceResBonus, fireResBonus, slowResBonus; //bonuses could be negative, make sure resistances never less than zero?
    int hp, hpBonus, hpMax;          //player life
    int mp, mpBonus, mpMax;          //mana points
    int ap, apBonus, apMax;          //action points (movement points)

    ArrayList<StatusEffect.Effect> effectList;


    public GenericUnit(int cutRes, int pierceRes, int poisonRes, int iceRes, int fireRes, int slowRes, int hpMax, int mpMax, int apMax) { //constructor for our generic unit
        this.cutRes = cutRes;
        this.pierceRes = pierceRes;
        this.poisonRes = poisonRes;
        this.iceRes = iceRes;
        this.fireRes = fireRes;
        this.slowRes = slowRes;
        this.hp = hpMax;
        this.hpMax = hpMax;
        this.mp = mpMax;
        this.mpMax = mpMax;
        this.ap = apMax;
        this.apMax = apMax;
        hpBonus = mpBonus = apBonus = cutResBonus = pierceResBonus = poisonResBonus = iceResBonus = fireResBonus = slowResBonus = 0; //set all these bonuses to zero
        effectList = new ArrayList<>(); //generates array list that will store our status effects
    }


    void update() {
        for (StatusEffect.Effect e : effectList) {
            e.apply(this); //should apply every damage effect in the list
        }

    }

    void removeFinishedEffects() { //still need to implement
        //if effect returns that no turns are remaining, remove it from the array list
        effectList.removeIf(e->e.finished(this)); //need to add reference to this unit for classes where we mess with stat bonuses

    }

    public ArrayList<StatusEffect.Effect> getEffectList() {
        return effectList;
    }

    int getCutRes() {
        return cutRes + cutResBonus;
    }
    int getCutResBonus(){return cutResBonus;}
    int getPierceRes() {return pierceRes + pierceResBonus;}
    int getPierceResBonus(){return pierceResBonus;}
    int getPoisonRes() {return poisonRes + poisonResBonus;}
    int getPoisonResBonus(){return poisonResBonus;}
    int getIceRes() {return iceRes + iceResBonus;}
    int getIceResBonus(){return iceResBonus;}
    int getFireRes() {return fireRes + fireResBonus;}
    int getFireResBonus(){return fireResBonus;}
    int getSlowRes(){return slowRes + slowResBonus;}
    int getSlowResBonus(){return slowResBonus;}
    int getHp() {return hp + hpBonus;}
    int getHpMax() {return hpMax;}
    int getMp() {return mp + mpBonus;}
    int getMpMax() {return mpMax;}
    int getAp(){return ap + apBonus;}
    int getApMax(){return apMax;}

    void setHp(int hp) { //method will be used for both damage and healing so will need to check against maxHp
        this.hp = Math.min(hp, hpMax + hpBonus);
    }
    void setMp(int mp){
        this.mp = Math.min(mp, mpMax + mpBonus);
    }
    void setAp(int movement){
        //maybe set up the potential to store extra AP when passing turns
        this.ap = Math.min(movement, this.apMax + this.apBonus);
    }
    void setApBonus(int bonus){
        this.apBonus = bonus;
    }//doing += so that there can be additive bonuses from different things (spells, items, skills, etc) EDIT: changed because it broke removing finished effects, item bonuses will have to be implemented differently
    void setCutResBonus(int bonus){
        this.cutResBonus = bonus;
    }
    void setPierceResBonus(int bonus){
        this.pierceResBonus = bonus;
    }
    void setPoisonResBonus(int bonus){
        this.poisonResBonus = bonus;
    }
    void setIceResBonus(int bonus){
        this.iceResBonus = bonus;
    }
    void setFireResBonus(int bonus){ this.fireResBonus = bonus; }
    void setSlowResBonus(int bonus){
        this.slowResBonus = bonus;
    }
    void damageHp(int magnitude) {
        int currHp = getHp();
        currHp = currHp - magnitude;
        //if unit isn't dead after the attack set to leftover mp
        //else unit is dead (we won't be doing any negative HP shenanigans)
        setHp(Math.max(currHp, 0));
    }

}
