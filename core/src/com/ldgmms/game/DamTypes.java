
package com.ldgmms.game;

//written by Daniel Fuchs
public class DamTypes { //casting from float to int is lossless iff the magnitude of a float is less than 2^24
    interface DamageEffect{ //can result in a status effect occurring for a number of turns
        void applyDamageEffect(GenericUnit u, float magnitude, int duration); //duration not necessarily the damage itself but the duration of status effects, u is the target
    }
    interface DamageOnce{
        void applyDamage(GenericUnit u, float magnitude);
    }

    class CutDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getCutRes();
            if(resistance < Roll.dice((int)magnitude, 3)){ //roll chance is magnitude d3
                StatusEffect.Bleed BleedEffect = new StatusEffect.Bleed(magnitude, duration); //update applyEffect method, passing magnitude and duration no longer necessary
                BleedEffect.applyEffect(u.getEffectList());
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int)(magnitude - resistance));
            } else u.damageHp(5);

        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) { //temporary method, will write in such a way that damage won't incur status effects
            int resistance = u.getCutRes();
            if(magnitude - resistance > 5){
                u.damageHp((int)(magnitude - resistance));
            } else u.damageHp(5);

        }
    }
    class PierceDamage implements DamageOnce{
        @Override
        public void applyDamage(GenericUnit u, float magnitude){
            int res = u.getPierceRes();
            if (magnitude - res > 5) {//ensures at least 5 damage taken
                u.damageHp(((int)magnitude - res));
            } else {
                u.damageHp(5);
            }
        }
    }
    class PoisonDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            int res = u.getPoisonRes();
            if(res < Roll.dice((int)magnitude, 6)){
                StatusEffect.Poison PoisonEffect = new StatusEffect.Poison(magnitude * 0.75f, duration); //poisoned effect will give 3/4 damage, maybe invert instead?
                PoisonEffect.applyEffect(u.getEffectList());
            }
            if((magnitude - res) > 5){
                u.damageHp(((int)magnitude - res));
            }
            else u.damageHp(5);
        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) {
            int res = u.getPoisonRes();
            if(magnitude - res > 5){
                u.damageHp((int)magnitude-res);
            } else u.damageHp(5);

        }
    }
    class FireDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getFireRes();
            if(resistance < Roll.dice((int)magnitude, 8)){
                StatusEffect.Burn BurnEffect = new StatusEffect.Burn(magnitude * 0.5f, duration);
                BurnEffect.applyEffect(u.getEffectList());
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int)(magnitude - resistance));
            } else u.damageHp(5);
        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) {
            int res = u.getFireRes();
            if(magnitude - res > 5){
                u.damageHp((int)(magnitude - res));
            } else u.damageHp(5);
        }
    }

    class IceDamage implements DamageEffect, DamageOnce{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getIceRes();
            if(resistance < Roll.dice((int)magnitude, 6)){
                StatusEffect.Frozen FrozenEffect = new StatusEffect.Frozen(magnitude/4, duration);
                FrozenEffect.applyEffect(u.getEffectList());
            } else if(resistance < Roll.dice((int)magnitude, 8)){
                StatusEffect.Chilled ChillEffect = new StatusEffect.Chilled(magnitude/8, duration);
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int) (magnitude - resistance));
            }
            else u.damageHp(5);
        }

        @Override
        public void applyDamage(GenericUnit u, float magnitude) {
            int res = u.getIceRes();
            if(magnitude - res > 5){
                u.damageHp((int)(magnitude - res));
            } else u.damageHp(5);
        }
    }
    class Haste implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.Haste HasteEffect = new StatusEffect.Haste(magnitude, duration);
            HasteEffect.applyEffect(u.getEffectList());
        }
    }
    class Slow implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getSlowRes();
            if(resistance < Roll.dice((int)magnitude, 6)){
                StatusEffect.Slow SlowEffect = new StatusEffect.Slow(magnitude, duration);
                SlowEffect.applyEffect(u.getEffectList()); //remove int casting, multiply apMax by magnitude (apMax * 0.75)
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int) (magnitude - resistance));
            }
            else u.damageHp(5);
        }
    }
    class Heal implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration) {
            StatusEffect.Heal HealEffect = new StatusEffect.Heal(magnitude, duration);
            HealEffect.applyEffect(u.getEffectList());
        }
    }
    class Dispel implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.Dispel DispelEffect = new StatusEffect.Dispel(magnitude, duration);
            DispelEffect.applyEffect(u.getEffectList());
        }
    }
    class ResistFire implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResFire ResEffect = new StatusEffect.ResFire(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    class ResistPhys implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResPhys ResEffect = new StatusEffect.ResPhys(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    class ResistIce implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResIce ResEffect = new StatusEffect.ResIce(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    class ResistPoison implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResPoison ResEffect = new StatusEffect.ResPoison(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
    class ResistSlow implements DamageEffect{
        @Override
        public void applyDamageEffect(GenericUnit u, float magnitude, int duration){
            StatusEffect.ResSlow ResEffect = new StatusEffect.ResSlow(magnitude, duration);
            ResEffect.applyEffect(u.getEffectList());
        }
    }
}



