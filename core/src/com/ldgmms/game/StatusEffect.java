package com.ldgmms.game;

import java.util.ArrayList;

//written by Daniel Fuchs
public class StatusEffect {
    abstract static class Effect{
        float magnitude;
        int turnsRemaining;
        abstract void apply(GenericUnit u);
        abstract void applyEffect(ArrayList<Effect> effectList); //should these be public?
        boolean finished(GenericUnit u) { //implement boolean method here
            return !(turnsRemaining >= 0);
        }

    }
    public static class Bleed extends Effect{

        public Bleed(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        public void apply(GenericUnit u){
            u.damageHp((int)magnitude);
            turnsRemaining--;
        }
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Bleed){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < this.magnitude * this.turnsRemaining) { //if existing status effect is less total damage than new, replace effects
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
    public static class Poison extends Effect{
        public Poison(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        public void apply(GenericUnit u){
            u.damageHp((int)magnitude);
            turnsRemaining--;
        }
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Poison){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < magnitude * turnsRemaining) { //if existing status effect is less total damage than new, replace effects
                        e.magnitude = magnitude;
                        e.turnsRemaining = turnsRemaining;
                    }
                    break;
                }

            }
            if (!effectExists){
                effectList.add(this); //was originally newEffect
            }
        }

    }
    public static class Burn extends Effect{
        public Burn(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        @Override public void apply(GenericUnit u){
            u.damageHp((int)magnitude);
            turnsRemaining--;
        }
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            var relevantEffects = effectList.stream().filter(e->(e instanceof Frozen) || (e instanceof Chilled));
            relevantEffects.forEach(e->e.turnsRemaining = 0); //readies frozen and chilled status effects for cleanup

            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Burn){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < this.magnitude * this.turnsRemaining) { //if existing status effect is less total damage than new, replace effects
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
    public static class Frozen extends Effect{
        public Frozen(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        @Override public boolean finished(GenericUnit u){ //reimplement to compensate for apBonus
            return !(turnsRemaining > 0); //adjusting the apBonus not necessary because this method will set ap to zero, regardless of bonus
        }
        @Override public void apply(GenericUnit u){
            u.damageHp((int)magnitude);
            turnsRemaining--;
            u.setAp(0); //unit is frozen, can't do any actions
        }
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            var relevantEffects = effectList.stream().filter(e->(e instanceof Burn) || (e instanceof Chilled));
            relevantEffects.forEach(e->e.turnsRemaining = 0); //readies burn and chilled status effects for cleanup
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Frozen){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < this.magnitude * this.turnsRemaining) { //if existing status effect is less total damage than new, replace effects
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
    public static class Haste extends Effect{
        public Haste(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }

        @Override
        void apply(GenericUnit u) {
            u.setAp((int)Math.ceil(u.ap * magnitude));
            turnsRemaining--;
        }

        @Override
        public void applyEffect(ArrayList<Effect> effectList){
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
        public Slow(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            u.setApBonus(-(int)Math.ceil((float)u.apMax*(magnitude))); //unit is slowed, negative ap bonus
        }
        @Override public boolean finished(GenericUnit u){ //reimplement to compensate for apBonus
            if (turnsRemaining > 0) { //rather than just returning if 0, clean up apBonus if 0
                return false;
            }
            else{
                u.setApBonus((int)Math.ceil((float)u.apMax*(magnitude)));
                return true;
            }
        }
        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Slow){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < this.magnitude * this.turnsRemaining) { //if existing status effect is less total damage than new, replace effects
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
    public static class Heal extends Effect{
        public Heal(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            u.setHp(u.hp + (int)magnitude); //unit is healed, should correct itself to maxHP in the setHp method
        }
        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Heal){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < this.magnitude * this.turnsRemaining) { //if existing status effect is less total damage than new, replace effects, consider making redundant effects additive
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
    public static class Dispel extends Effect{
        public Dispel(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            ArrayList<StatusEffect.Effect> list = u.getEffectList(); //remove all status effects
            var relevantEffects = list.stream().filter(e->!(e instanceof Dispel));
            relevantEffects.forEach(e->e.turnsRemaining = 0);
        }
        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (!(e instanceof Dispel)){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    if (e.magnitude * e.turnsRemaining < this.magnitude * this.turnsRemaining) { //if existing status effect is less total damage than new, replace effects, consider making redundant effects additive
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
    public static class Chilled extends Effect{
        public Chilled(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            u.setAp((int)(u.getAp() * magnitude)); //magnitude should be a float here
        }
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for (StatusEffect.Effect e : effectList) {
                if (e instanceof Chilled){
                    effectExists = true; //flag that ensures we don't repeat status effects
                    Frozen newEffect = new Frozen(magnitude, turnsRemaining);
                    newEffect.applyEffect(effectList); //2 chilled instances turns into a frozen status effect
                    break;
                }
            }
            if (!effectExists){
                effectList.add(this);
            }
        }
    }
    public static class ResFire extends Effect{
        boolean wasApplied;
        public ResFire(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            wasApplied = false;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            if(!wasApplied){
                u.setFireResBonus((int)magnitude);
                wasApplied = true;
            }
        }
        @Override public boolean finished(GenericUnit u){
            if(turnsRemaining >= 0) return false;
            else{
                u.setFireResBonus(u.getFireResBonus() - (int)magnitude);
                return true;
            }
        }

        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectGreater = false; //if the new effect is smaller than the old one, we won't apply it
            for(StatusEffect.Effect e : effectList){
                if(e instanceof ResFire){
                    if (e.magnitude > this.magnitude) {
                        effectGreater = true;
                    }
                    else e.turnsRemaining = 0; //old effect is smaller, must be removed with next update
                    break;
                }
            }
            if (!effectGreater){
                effectList.add(this);
            }
        }
    }
    public static class ResIce extends Effect{
        boolean wasApplied;
        public ResIce(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            wasApplied = false;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            if(!wasApplied){
                u.setIceResBonus((int)magnitude);
                wasApplied = true;
            }
        }
        @Override public boolean finished(GenericUnit u){
            if(turnsRemaining >= 0) return false;
            else{
                u.setIceResBonus(u.getIceResBonus()-(int)magnitude);
                return true;
            }
        }

        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for(StatusEffect.Effect e : effectList){
                if(e instanceof ResIce){
                    effectExists = true;
                    if (e.magnitude < this.magnitude) {
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
    public static class ResPhys extends Effect{
        boolean wasApplied;
        public ResPhys(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            wasApplied = false;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            if(!wasApplied){
                u.setCutResBonus((int)magnitude);
                u.setPierceResBonus((int)magnitude);
                wasApplied = true;
            }
        }
        @Override public boolean finished(GenericUnit u){
            if(turnsRemaining >= 0) return false;
            else{
                u.setCutResBonus(u.getCutResBonus() -(int)magnitude);
                u.setPierceResBonus(u.getPierceResBonus() -(int)magnitude);
                return true;
            }
        }

        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for(StatusEffect.Effect e : effectList){
                if(e instanceof ResPhys){
                    effectExists = true;
                    if (e.magnitude < this.magnitude){
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
    public static class ResPoison extends Effect{
        boolean wasApplied;
        public ResPoison(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            wasApplied = false;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            if(!wasApplied){
                u.setPoisonResBonus((int)magnitude);
                wasApplied = true;
            }
        }
        @Override public boolean finished(GenericUnit u){
            if(turnsRemaining >= 0) return false;
            else{
                u.setPoisonResBonus(u.getPoisonResBonus() -(int)magnitude);
                return true;
            }
        }

        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for(StatusEffect.Effect e : effectList){
                if(e instanceof ResPoison){
                    effectExists = true;
                    if (e.magnitude < this.magnitude) {
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
    public static class ResSlow extends Effect{
        boolean wasApplied;
        public ResSlow(float m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            wasApplied = false;
        }
        @Override public void apply(GenericUnit u){
            turnsRemaining--;
            if(!wasApplied){
                u.setSlowResBonus((int)magnitude);
                wasApplied = true;
            }
        }
        @Override public boolean finished(GenericUnit u){
            if(turnsRemaining >= 0) return false;
            else{
                u.setSlowResBonus(u.getSlowResBonus() -(int)magnitude);
                return true;
            }
        }

        @Override
        public void applyEffect(ArrayList<Effect> effectList){
            boolean effectExists = false;
            for(StatusEffect.Effect e : effectList){
                if(e instanceof ResSlow){
                    effectExists = true;
                    if (e.magnitude < this.magnitude) {
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
}
