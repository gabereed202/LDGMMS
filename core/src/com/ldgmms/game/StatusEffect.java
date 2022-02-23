package com.ldgmms.game;
//written by Daniel Fuchs
public class StatusEffect { //Still need to implement, bare minimum set up for code to compile
    interface Effect{
        void apply(HeroClass.GenericUnit u);
        boolean finished();

    }
    public class Bleed implements Effect{
        int magnitude;
        int turnsRemaining;
        public Bleed(int magnitude, int duration){

        }
        @Override public boolean finished(){
            return turnsRemaining > 0;
        }
        @Override public void apply(HeroClass.GenericUnit u){
            u.damageHp(magnitude);
        }
        Bleed setEffect(int magnitude, int duration){
            return new Bleed(magnitude, duration);

        }
    }
    class Poison{
        void setEffect(int magnitude, int duration){

        }

    }
    class Burn{
        void setEffect(int magnitude, int duration){

        }

    }
    class Frozen{
        void setEffect(int magnitude, int duration){

        }

    }
}
