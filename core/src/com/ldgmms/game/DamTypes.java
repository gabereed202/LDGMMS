
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
                //u.setStatus(StatusEffect.Bleed.setEffect(Math.ceil(magnitude/3), duration)); //sets status effect damage to be (1/3)magnitude rounded up
                u.applyEffect( StatusEffect.Effect.EType.Bleed, (int)Math.ceil(magnitude/3), duration); //need to implement here
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
//void update(); not sure why this is here, typo?
    }
    class PoisonDamage implements DamageEffect{
        public void applyDamage(GenericUnit u, float magnitude, int duration){
            int res = u.getPoisonRes();
            if(res < Roll.dice((int)magnitude, 6)){
                //u.setSu.applyEffect( StatusEffect.Effect.EType.Bleed, (int)Math.ceil(magnitude/3), duration); tatus(StatusEffect.Poison.setEffect((int)Math.ceil(magnitude/2), duration));
                u.applyEffect( StatusEffect.Effect.EType.Poison, (int)Math.ceil(magnitude/2), duration);
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
                //u.setStatus(StatusEffect.Burn.setEffect((int)Math.ceil(magnitude/2), duration));
                u.applyEffect( StatusEffect.Effect.EType.Burn, (int)Math.ceil(magnitude/2), duration);
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
                //u.setStatus(StatusEffect.Frozen.setEffect((int)Math.ceil(magnitude/4), duration));
                u.applyEffect( StatusEffect.Effect.EType.Frozen, (int)Math.ceil(magnitude/4), duration);
            }
            if((magnitude - resistance) > 5){
                u.damageHp((int) (magnitude - resistance));
            }
            else u.damageHp(5);
        }
    }
}



