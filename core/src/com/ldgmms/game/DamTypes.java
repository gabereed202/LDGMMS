
package com.ldgmms.game;

import com.badlogic.gdx.math.MathUtils;


//written by Daniel Fuchs
public class DamTypes { //casting from float to int is lossless iff the magnitude of a float is less than 2^24
    interface DamageEffect{ //can result in a status effect occurring for a number of turns
        void applyDamageEffect(GenericUnit u, float magnitude, int duration); //duration not necessarily the damage itself but the duration of status effects, u is the target
    }
    interface DamageOnce{ //no status effect, just flat damage
        void applyDamage(GenericUnit u, float magnitude);
    }

    static class CutDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getCutRes();
            if(resistance < Roll.dice((int)magnitude, 3)){ //roll chance is magnitude d3
                StatusEffect.Bleed BleedEffect = new StatusEffect.Bleed(magnitude, duration);
                BleedEffect.applyEffect(u.getEffectList());
            }
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5)); //checks if damage formula is greater than or less than 5, if less than, do 5 damage

        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) {
            int resistance = u.getCutRes();
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));

        }
    }
    static class PierceDamage implements DamageOnce{
        @Override
        public void applyDamage(GenericUnit u, float magnitude){
            int resistance = u.getPierceRes();
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));
        }
    }
    static class PoisonDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            int resistance = u.getPoisonRes();
            if(resistance < Roll.dice((int)magnitude, 6)){
                StatusEffect.Poison PoisonEffect = new StatusEffect.Poison(magnitude * 0.75f, duration); //poisoned effect will give 3/4 damage, maybe invert instead?
                PoisonEffect.applyEffect(u.getEffectList());
            }
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));
        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) {
            int resistance = u.getPoisonRes();
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));
        }
    }
    static class FireDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getFireRes();
            if(resistance < Roll.dice((int)magnitude, 8)){
                StatusEffect.Burn BurnEffect = new StatusEffect.Burn(magnitude * 0.5f, duration);
                BurnEffect.applyEffect(u.getEffectList());
            }
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));
        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) {
            int resistance = u.getFireRes();
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));
        }
    }

    static class IceDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getIceRes();
            if(resistance < Roll.dice((int)magnitude, 6)){
                StatusEffect.Frozen FrozenEffect = new StatusEffect.Frozen(magnitude/4, duration);
                FrozenEffect.applyEffect(u.getEffectList());
            } else if(resistance < Roll.dice((int)magnitude, 8)){
                StatusEffect.Chilled ChillEffect = new StatusEffect.Chilled(magnitude/8, duration);
                ChillEffect.applyEffect(u.getEffectList());
            }
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));
        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) {
            int resistance = u.getIceRes();
            u.damageHp(Math.max(MathUtils.ceil(magnitude * (1 - ((float) resistance / 100))), 5));
        }
    }
    static class Haste implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.Haste HasteEffect = new StatusEffect.Haste(magnitude, duration);
            HasteEffect.applyEffect(u.getEffectList());
        }
    }
    static class Slow implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getSlowRes();
            if(resistance < Roll.dice((int)magnitude, 6)){
                StatusEffect.Slow SlowEffect = new StatusEffect.Slow(magnitude, duration);
                SlowEffect.applyEffect(u.getEffectList()); //remove int casting, multiply apMax by magnitude (apMax * 0.75)
            }
        }
    }
    static class Heal implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            StatusEffect.Heal HealEffect = new StatusEffect.Heal(magnitude, duration);
            HealEffect.applyEffect(u.getEffectList());
        }
    }
    static class Dispel implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.Dispel DispelEffect = new StatusEffect.Dispel(magnitude, duration);
            DispelEffect.applyEffect(u.getEffectList());
        }
    }
    static class ResistFire implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResFire ResEffect = new StatusEffect.ResFire(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    static class ResistPhys implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResPhys ResEffect = new StatusEffect.ResPhys(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    static class ResistIce implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResIce ResEffect = new StatusEffect.ResIce(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    static class ResistPoison implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResPoison ResEffect = new StatusEffect.ResPoison(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    static class ResistSlow implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResSlow ResEffect = new StatusEffect.ResSlow(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
}



