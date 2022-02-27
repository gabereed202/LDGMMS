package com.ldgmms.game;

import java.util.ArrayList;

//written by Daniel Fuchs
public class StatusEffect { //Still need to implement, bare minimum set up for code to compile
    abstract static class Effect{
        int magnitude;
        int turnsRemaining;
        abstract void apply(GenericUnit u);
        abstract void applyEffect(ArrayList<Effect> effectList, int magnitude, int duration); //should these be public?
        boolean finished() { //implement boolean method here
            return !(turnsRemaining > 0);
        }

    }
    public static class Bleed extends Effect{

        public Bleed(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        /*public boolean finished(){
            return !(turnsRemaining > 0);
        }*/
        public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
        }
        public void applyEffect(ArrayList<Effect> effectList, int magnitude, int duration){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Bleed){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < magnitude * duration) { //if existing status effect is less total damage than new, replace effects
                        e.magnitude = magnitude;
                        e.turnsRemaining = duration;
                    }
                    break;
                }

            }
            if (!effectExists){
                //Bleed newEffect = new Bleed(magnitude, duration);
                effectList.add(this); //will this add current class to the list? check with prof, will garbage collection take care of redundant effects?
            }
        }

    }
    public static class Poison extends Effect{
        public Poison(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        /*public boolean finished(){
            return !(turnsRemaining > 0);
        }*/
        public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
        }
        public void applyEffect(ArrayList<Effect> effectList, int magnitude, int duration){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Poison){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < magnitude * duration) { //if existing status effect is less total damage than new, replace effects
                        e.magnitude = magnitude;
                        e.turnsRemaining = duration;
                    }
                    break;
                }

            }
            if (!effectExists){
                //Poison newEffect = new Poison(magnitude, duration);
                effectList.add(this); //was originally newEffect
            }
        }

    }
    public static class Burn extends Effect{
        public Burn(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        /*@Override public boolean finished(){
            return !(turnsRemaining > 0);
        }*/
        @Override public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
        }
        public void applyEffect(ArrayList<Effect> effectList, int magnitude, int duration){
            boolean effectExists = false;
            effectList.removeIf(e->(e instanceof Frozen)); //burning negates freezing
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Burn){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < magnitude * duration) { //if existing status effect is less total damage than new, replace effects
                        e.magnitude = magnitude;
                        e.turnsRemaining = duration;
                    }
                    break;
                }

            }
            if (!effectExists){
                //Burn newEffect = new Burn(magnitude, duration);
                effectList.add(this);
            }
        }

    }
    public static class Frozen extends Effect{
        public Frozen(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        /*@Override public boolean finished(){
            return !(turnsRemaining > 0);
        }*/
        @Override public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
            u.setAp(0); //unit is frozen, can't do any actions
        }
        public void applyEffect(ArrayList<Effect> effectList, int magnitude, int duration){
            boolean effectExists = false;
            effectList.removeIf(e->(e instanceof Burn));
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Frozen){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < magnitude * duration) { //if existing status effect is less total damage than new, replace effects
                        e.magnitude = magnitude;
                        e.turnsRemaining = duration;
                    }
                    break;
                }

            }
            if (!effectExists){
                //Frozen newEffect = new Frozen(magnitude, duration);
                effectList.add(this);
            }
        }

    }
    public static class Haste extends Effect{
        public Haste(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }

        @Override
        void apply(GenericUnit u) {
            u.setAp(u.ap * magnitude);
            turnsRemaining--;
        }

        public void applyEffect(ArrayList<Effect> effectList, int magnitude, int duration){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Haste){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude < this.magnitude) { //will always replace if magnitude is greater
                        e.magnitude = this.magnitude;
                        e.turnsRemaining = this.turnsRemaining;
                    }
                    break;
                }

            }
            if (!effectExists){
                effectList.add(this);
            }
        }
    }
    public static class Slow extends Effect{
        public Slow(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            u.setAp((int)Math.ceil((float)u.apMax/(float)magnitude)); //unit is slowed
        }
        public void applyEffect(ArrayList<Effect> effectList, int magnitude, int duration){
            boolean effectExists = false;
            effectList.removeIf(e->(e instanceof Burn));
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Frozen){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < magnitude * duration) { //if existing status effect is less total damage than new, replace effects
                        e.magnitude = magnitude;
                        e.turnsRemaining = duration;
                    }
                    break;
                }

            }
            if (!effectExists){
                //Frozen newEffect = new Frozen(magnitude, duration);
                effectList.add(this);
            }
        }

    }
}
