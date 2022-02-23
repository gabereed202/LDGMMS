package com.ldgmms.game;

import java.util.ArrayList;

public class HeroClass {
    abstract class GenericUnit{ //GenericUnit written by Daniel Fuchs
        int cutRes;
        int pierceRes;
        int poisonRes;
        int iceRes;
        int fireRes;
        int hp;
        int mp;
        int getCutRes(){
            return cutRes;
        }
        int getPierceRes(){
            return pierceRes;
        }
        int getPoisonRes(){
            return poisonRes;
        }
        int getIceRes(){
            return iceRes;
        }
        int getFireRes(){
            return fireRes;
        }
        abstract void setStatus();
        abstract void damageHp(int magnitude);
        ArrayList <StatusEffect.Effect> effects = new ArrayList<StatusEffect.Effect>();
        abstract void applyEffects();
        abstract void removeFinishedEffects();
        abstract void update();

    }
}
