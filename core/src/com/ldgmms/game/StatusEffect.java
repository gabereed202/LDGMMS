package com.ldgmms.game;
//written by Daniel Fuchs
public class StatusEffect { //Still need to implement, bare minimum set up for code to compile
    abstract static class Effect{
        int magnitude;
        int turnsRemaining;
        enum EType{
            Bleed, Poison, Burn, Frozen
        }
        EType effectName;
        abstract void apply(GenericUnit u);
        boolean finished() { //implement boolean method here
            return !(turnsRemaining > 0);
        }

    }
    public static class Bleed extends Effect{

        public Bleed(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            effectName = EType.Bleed;
        }
        public boolean finished(){
            return !(turnsRemaining > 0);
        }
        public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
        }

    }
    public static class Poison extends Effect{
        public Poison(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            effectName = EType.Poison;
        }
        public boolean finished(){
            return !(turnsRemaining > 0);
        }
        public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
        }

    }
    public static class Burn extends Effect{ //think of how burn or frozen may or may not affect eachother
        public Burn(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            effectName = EType.Burn;
        }
        @Override public boolean finished(){
            return !(turnsRemaining > 0);
        }
        @Override public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
        }

    }
    public static class Frozen extends Effect{
        int magnitude;
        int turnsRemaining;
        public Frozen(int m, int turns){
            magnitude = m;
            turnsRemaining = turns;
            effectName = EType.Frozen;
        }
        @Override public boolean finished(){
            return !(turnsRemaining > 0);
        }
        @Override public void apply(GenericUnit u){
            u.damageHp(magnitude);
            turnsRemaining--;
        }

    }
}
