package com.ldgmms.game;

public class HeroClass {
    abstract class GenericUnit{
        abstract int getCutRes(); //don't make getters abstract
        abstract int getPierceRes();
        abstract int getPoisonRes();
        abstract int getIceRes();
        abstract int getFireRes();
        abstract void setStatus();
        abstract void subLife(int magnitude);
    }
}
