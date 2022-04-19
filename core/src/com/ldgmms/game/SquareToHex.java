package com.ldgmms.game;

import java.util.ArrayList;

public class SquareToHex {
    public static ArrayList<GenericUnit> getUnits(Hero h, Hero e){
        ArrayList<GenericUnit> allUnits = new ArrayList<>();
        allUnits.add(h);
        allUnits.add(e);
        allUnits.addAll(h.unitList);
        allUnits.addAll(e.unitList);
        return allUnits; //pass this to the turn logic?
    }
}
