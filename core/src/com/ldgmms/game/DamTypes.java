
package com.ldgmms.game;

//written by Daniel Fuchs
public class DamTypes { //casting from float to int is lossless iff the magnitude of a float is less than 2^24
    interface DamageEffect{ //can result in a status effect occurring for a number of turns
        void applyDamage(GenericUnit u, float magnitude, int duration); //duration not necessarily the damage itself but the duration of status effects, u is target, int vs float for magnitude?
    }
    interface Damage{ //no status effects involved
        void applyDamage(GenericUnit u, float magnitude);
    }

    class CutDamage implements DamageEffect{
        public void applyDamage(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getCutRes();
            if(resistance < Roll.dice((int)magnitude, 3)){ //roll chance is magnitude d3
                StatusEffect.Bleed BleedEffect = new StatusEffect.Bleed((int)magnitude, duration); //update applyEffect method, passing magnitude and duration no longer necessary
                BleedEffect.applyEffect(u.getEffectList(), (int)magnitude, duration);
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int)(magnitude - resistance));
            }
            else u.damageHp(5);

        }
    }
    class PierceDamage implements Damage{
        public void applyDamage(GenericUnit u, float magnitude){
            int res = u.getPierceRes();
            if (magnitude - res > 5) {//ensures at least 5 damage taken
                u.damageHp(((int)magnitude - res));
            } else {
                u.damageHp(5);
            }
        }
    }
    class PoisonDamage implements DamageEffect{
        public void applyDamage(GenericUnit u, float magnitude, int duration){
            int res = u.getPoisonRes();
            if(res < Roll.dice((int)magnitude, 6)){
                StatusEffect.Poison PoisonEffect = new StatusEffect.Poison((int)magnitude, duration);
                PoisonEffect.applyEffect( u.getEffectList(), (int)Math.ceil(magnitude/2), duration);
            }
            if((magnitude - res) > 5){
                u.damageHp(((int)magnitude - res));
            }
            else u.damageHp(5);
        }

    }
    class FireDamage implements DamageEffect{
        public void applyDamage(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getFireRes();
            if(resistance < Roll.dice((int)magnitude, 8)){
                StatusEffect.Burn BurnEffect = new StatusEffect.Burn((int)magnitude, duration);
                BurnEffect.applyEffect( u.getEffectList(), (int)Math.ceil(magnitude/2), duration);
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int)(magnitude - resistance));
            }
            else u.damageHp(5);
        }
    }

    class IceDamage implements DamageEffect{
        public void applyDamage(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getIceRes();
            if(resistance < Roll.dice((int)magnitude, 6)){
                StatusEffect.Frozen FrozenEffect = new StatusEffect.Frozen((int)magnitude, duration);
                FrozenEffect.applyEffect( u.getEffectList(), (int)Math.ceil(magnitude/4), duration);
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int) (magnitude - resistance));
            }
            else u.damageHp(5);
        }
    }
    class Haste implements DamageEffect{
        public void applyDamage(GenericUnit u, float magnitude, int duration){
            StatusEffect.Haste HasteEffect = new StatusEffect.Haste((int)magnitude, duration);
            HasteEffect.applyEffect(u.getEffectList(), (int)magnitude, duration);
        }
    }
    class Slow implements DamageEffect{
        public void applyDamage(GenericUnit u, float magnitude, int duration) {
            int resistance = u.getSlowRes();
            if(resistance < Roll.dice((int)magnitude, 6)){
                StatusEffect.Slow SlowEffect = new StatusEffect.Slow((int)magnitude, duration);
                SlowEffect.applyEffect( u.getEffectList(), (int)magnitude, duration); //remove int casting, multiply apMax by magnitude (apMax * 0.75)
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int) (magnitude - resistance));
            }
            else u.damageHp(5);
        }
    }
}



