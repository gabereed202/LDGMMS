
package com.ldgmms.game;

//written by Daniel Fuchs
public class DamTypes {
    interface DamageEffect{ //can result in a status effect occuring for a number of turns
        void applyDamage(HeroClass.GenericUnit u, int magnitude, int duration); //duration not necessarily the damage itself but the duration of status effects, u is target, int vs float for magnitude?
    }
    interface Damage{ //no status effects involved
        void applyDamage(HeroClass.GenericUnit u, int magnitude);
    }

    class CutDamage implements DamageEffect{
        public void applyDamage(HeroClass.GenericUnit u, int magnitude, int duration) {
            int resistance = u.getCutRes();
            if(resistance < Roll.dice(magnitude, 3)){ //roll chance is magnitude d3
                //u.setStatus(StatusEffect.Bleed.setEffect(Math.ceil(magnitude/3), duration)); //sets status effect damage to be (1/3)magnitude rounded up
            }
            if((magnitude - resistance) > 5){
                u.damageHp(magnitude - resistance);
            }
            else u.damageHp(5);

        }
    }
    class PierceDamage implements Damage{
        public void applyDamage(HeroClass.GenericUnit u, int magnitude){
            if (magnitude - u.getPierceRes() > 5) {//ensures at least 5 damage taken
                u.damageHp(magnitude - u.getPierceRes());
            } else {
                u.damageHp(5);
            }
        }

    }
    class PoisonDamage implements DamageEffect{
        public void applyDamage(HeroClass.GenericUnit u, int magnitude, int duration){
            int resistance = u.getPoisonRes();
            if(resistance < Roll.dice(magnitude, 6)){
                //u.setStatus(StatusEffect.Poison.setEffect((int)Math.ceil(magnitude/2), duration));
            }
            if((magnitude - resistance) > 5){
                u.damageHp(magnitude - resistance);
            }
            else u.damageHp(5);
        }

    }
    class FireDamage implements DamageEffect{
        public void applyDamage(HeroClass.GenericUnit u, int magnitude, int duration) {
            int resistance = u.getFireRes();
            if(resistance < Roll.dice(magnitude, 8)){
                //u.setStatus(StatusEffect.Burn.setEffect((int)Math.ceil(magnitude/2), duration));
            }
            if((magnitude - resistance) > 5){
                u.damageHp(magnitude - resistance);
            }
            else u.damageHp(5);
        }
    }

    class IceDamage implements DamageEffect{
        public void applyDamage(HeroClass.GenericUnit u, int magnitude, int duration) {
            int resistance = u.getIceRes();
            if(resistance < Roll.dice(magnitude, 6)){
                //u.setStatus(StatusEffect.Frozen.setEffect((int)Math.ceil(magnitude/4), duration));
            }
            if((magnitude - resistance) > 5){
                u.damageHp(magnitude - resistance);
            }
            else u.damageHp(5);
        }
    }
}



