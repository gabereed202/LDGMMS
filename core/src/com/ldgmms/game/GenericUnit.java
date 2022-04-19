package com.ldgmms.game;

import java.util.ArrayList;

public class GenericUnit { //GenericUnit written by Daniel Fuchs
    //HashMap<String, Integer> statsMap;
    int cutRes, pierceRes, poisonRes, iceRes, fireRes, slowRes; //resistances
    int cutResBonus, pierceResBonus, poisonResBonus, iceResBonus, fireResBonus, slowResBonus; //bonuses could be negative, make sure resistances never less than zero?
    float hp, hpBonus, hpMax;          //player life
    float mp, mpBonus, mpMax;          //mana points
    int ap, apBonus, apMax;          //action points (movement points)
    int team;                       //which team the unit is on (team 0, team 1, team 2, etc)
    String spritePath;
    String name;

    ArrayList<StatusEffect.Effect> effectList;


    public GenericUnit(String spritePath, String name, int cutRes, int pierceRes, int poisonRes, int iceRes, int fireRes, int slowRes, int hpMax, int mpMax, int apMax, int team) { //constructor for our generic unit
        this.spritePath = spritePath;
        this.name = name;
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
        this.team = team;
        hpBonus = mpBonus = apBonus = cutResBonus = pierceResBonus = poisonResBonus = iceResBonus = fireResBonus = slowResBonus = 0; //set all these bonuses to zero
        effectList = new ArrayList<>(); //generates array list that will store our status effects
    }



    void update() {
        for (StatusEffect.Effect e : effectList) {
            e.apply(this); //should apply every damage effect in the list
        }
        effectList.removeIf(e->e.finished(this));

    }
    boolean deployUnit(int x, int y){ //decide whether or not to implement here or in a gamescreen and how
        return true;

    }

    /*void removeFinishedEffects() { //merge into update
        //if effect returns that no turns are remaining, remove it from the array list
        effectList.removeIf(e->e.finished(this)); //need to add reference to this unit for classes where we mess with stat bonuses

    }*/

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
    float getHp() {return hp + hpBonus;}
    float getHpMax() {return hpMax;}
    float getMp() {return mp + mpBonus;}
    float getMpMax() {return mpMax;}
    int getAp(){return ap + apBonus;}
    int getApMax(){return apMax;}

    void setHp(float hp) { //method will be used for both damage and healing so will need to check against maxHp
        this.hp = Math.min(hp, hpMax + hpBonus);
    }
    void setMp(float mp){
        this.mp = Math.min(mp, mpMax + mpBonus);
    }
    void setAp(int movement){
        //maybe set up the potential to store extra AP when passing turns
        this.ap = Math.min(movement, this.apMax + this.apBonus);
    }
    void setApBonus(int bonus){
        this.apBonus = bonus;
    }//have set as well as add because method is sometimes called for additive bonuses while other times it is to remove a bonus, using additive for all instances causes problems when new effect is added and needs to replace old effect
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
    void addFireResBonus(int bonus){ this.fireResBonus += bonus; }
    void addIceResBonus(int bonus){ this.iceResBonus += bonus;}
    void addCutResBonus(int bonus){this.cutResBonus += bonus;}
    void addPierceResBonus(int bonus){this.pierceResBonus += bonus;}
    void addPoisonResBonus(int bonus){this.poisonResBonus += bonus;}
    void addSlowResBonus(int bonus){this.slowResBonus += bonus;}
    void damageHp(int magnitude) {
        float currHp = getHp();
        currHp = currHp - magnitude;
        //if unit isn't dead after the attack set to leftover mp
        //else unit is dead (we won't be doing any negative HP shenanigans)
        setHp(Math.max(currHp, 0));
    }

}
