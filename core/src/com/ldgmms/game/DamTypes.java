/*
package com.ldgmms.game;

public class DamTypes {
    interface DamageType{
        void applyDamage(GenericUnit u, int magnitude, int duration); //duration not necessarily the damage itself but the duration of status effects, u is target, int vs float for magnitude?
    }
    interface DamageTypeOnce{
        void applyDamage(GenericUnit u, int magnitude);
    }

    class CutDamage implements DamageType{
        public void applyDamage(GenericUnit u, int magnitude, int duration) {
            int resistance = u.getCutRes();
            if(resistance < Roll.dice(magnitude, 3)){ //roll chance is magnitude d3
                u.setStatus(StatusEffect.Bleed.setBleed(Math.ceil(magnitude/3), duration)); //sets status effect damage to be (1/3)magnitude rounded up
            }
            if((magnitude - resistance) > 5){
                u.subLife(magnitude - resistance);
            }
            else u.subLife(5);

        }
    }
    class PierceDamage implements DamageTypeOnce{
        public void applyDamage(GenericUnit u, int magnitude){
            return (magnitude - u.getPierceRes() > 5)? u.subLife(magnitude - u.getPierceRes()) : u.subLife(5); //ensures at least 5 damage taken
        }

    }
    class PoisonDamage implements DamageType{
        public void applyDamage(GenericUnit u, int magnitude, int duration){
            int resistance = u.getPoisonRes();
            if(resistance < Roll.dice(magnitude, 6)){
                u.setStatus(StatusEffect.Poison.setPoison((int)Math.ceil(magnitude/2), duration));
            }
            if((magnitude - resistance) > 5){
                u.subLife(magnitude - resistance);
            }
            else u.subLife(5);
        }

    }
    class FireDamage implements DamageType{
        public void applyDamage(GenericUnit u, int magnitude, int duration) {
            int resistance = u.getFireRes();
            if(resistance < Roll.dice(magnitude, 8)){
                u.setStatus(StatusEffect.Burn((int)Math.ceil(magnitude/2), duration));
            }
            if((magnitude - resistance) > 5){
                u.subLife(magnitude - resistance);
            }
            else u.subLife(5);
        }
    }

    class IceDamage implements DamageType{
        public void applyDamage(GenericUnit u, int magnitude, int duration) {
            int resistance = u.getIceRes();
            if(resistance < Roll.dice(magnitude, 6)){
                u.setStatus(StatusEffect.Burn((int)Math.ceil(magnitude/4), duration));
            }
            if((magnitude - resistance) > 5){
                u.subLife(magnitude - resistance);
            }
            else u.subLife(5);
        }
    }
}
*/
