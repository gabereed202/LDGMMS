package com.ldgmms.game;

import java.util.ArrayList;
import java.util.HashMap;

public class GenericUnit { //GenericUnit written by Daniel Fuchs //replace resistances and health with a hash map, return values in getters //use hash set to test if status effect is available in constant time
    HashMap<String, Integer> statsMap;
    ArrayList<StatusEffect.Effect> effectList;

    void damageHp(int magnitude) {
        int currHp = getHp();
        currHp = currHp - magnitude;
        if (currHp > 0) { //if unit isn't dead after the attack
            setHp(currHp);
        } else setHp(0); //else unit is dead (we won't be doing any negative HP shenanigans)
    }

    public GenericUnit(int cutRes, int pierceRes, int poisonRes, int iceRes, int fireRes, int hpMax, int mpMax) { //constructor for our generic unit, sets up the initial hash map for base stats as well as the hash set for status effects
        statsMap = new HashMap<>();
        statsMap.put("cutRes", cutRes);
        statsMap.put("pierceRes", pierceRes);
        statsMap.put("poisonRes", poisonRes);
        statsMap.put("iceRes", iceRes);
        statsMap.put("fireRes", fireRes);
        statsMap.put("hp", hpMax);
        statsMap.put("hpMax", hpMax);
        statsMap.put("mp", mpMax);
        statsMap.put("mpMax", mpMax);
        effectList = new ArrayList<>(); //generates hash set that will store our status effects
    }

    void applyEffect(StatusEffect.Effect.EType effectType, int magnitude, int duration) { //have bleed,etc, effect created then inserted here
        boolean effectExists = false;
        for (StatusEffect.Effect e : effectList) {
            if (e.effectName.equals(effectType)) {
                effectExists = true; //flag that ensures we don't repeat status effects
                if (e.magnitude * e.turnsRemaining < magnitude * duration) { //if existing status effect is less total damage than new, replace effects
                    e.magnitude = magnitude;
                    e.turnsRemaining = duration;
                }
                break;
            }
        }
        if (effectExists == false) {
            //need to think of a way to construct the correct status effect here, switch case?
            switch (effectType) {
                case Bleed:
                    StatusEffect.Bleed bleedEffect = new StatusEffect.Bleed(magnitude, duration);
                    effectList.add(bleedEffect);
                    break;
                case Poison:
                    StatusEffect.Poison poisonEffect = new StatusEffect.Poison(magnitude, duration);
                    effectList.add(poisonEffect);
                    break;
                case Burn:
                    StatusEffect.Burn burnEffect = new StatusEffect.Burn(magnitude, duration);
                    effectList.add(burnEffect);
                    break;
                case Frozen:
                    StatusEffect.Frozen frozenEffect = new StatusEffect.Frozen(magnitude, duration);
                    effectList.add(frozenEffect);
                    break;
            }
        }
    }

    void update() { //still need to implement, can do without iterator
        for (StatusEffect.Effect e : effectList) {
            e.apply(this); //should apply every damage effect in the list
        }

    }

    void removeFinishedEffects() { //still need to implement
        for (StatusEffect.Effect e : effectList) {
            if (e.finished()) {
                effectList.remove(e); //if effect returns that no turns are remaining, remove it from the array list
            }
        }

    }

    int getCutRes() {
        return statsMap.get("cutRes");
    }

    int getPierceRes() {
        return statsMap.get("pierceRes");
    }

    int getPoisonRes() {
        return statsMap.get("poisonRes");
    }

    int getIceRes() {
        return statsMap.get("iceRes");
    }

    int getFireRes() {
        return statsMap.get("fireRes");
    }

    int getHp() {
        return statsMap.get("hp");
    }

    int getHpMax() {
        return statsMap.get("hpMax");
    }

    int getMp() {
        return statsMap.get("mp");
    }

    int getMpMax() {
        return statsMap.get("mpMax");
    }

    void setHp(int hp) { //method will be used for both damage and healing so will need to check against maxHp
        int hpMax = statsMap.get("hpMax");
        if (hp > hpMax) {
            statsMap.put("hp", hpMax);
        } else statsMap.put("hp", hp);
    }

}
