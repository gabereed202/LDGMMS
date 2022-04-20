package com.ldgmms.game;

import java.util.ArrayList;
import java.util.Iterator;

public class Turn { //we will assume player is always 0, maybe define playerteam variable? Always assumed to be player vs enemy, not ai vs ai
    public static void deployPhase(Hero h, Hero e){ //pass gamescreen as well?
        Iterator<GenericUnit> pIter; //player list of units
        Iterator<GenericUnit> eIter; //ai list of units
        GenericUnit pElem;           //unit from player list
        GenericUnit eElem;           //unit from enemy list
        if(h.team == 0){
            pIter = h.unitList.iterator();
            eIter = e.unitList.iterator();
        }else{
            pIter = e.unitList.iterator();
            eIter = h.unitList.iterator();
        }

        while (pIter.hasNext() && eIter.hasNext()){
            pElem = pIter.next();
            eElem = eIter.next();
            //connect this to deploying unit
        }

    }
    public static void actionPhase(ArrayList<GenericUnit> allUnits){

    }
    public static void resolvePhase(ArrayList<GenericUnit> allUnits){
        for(GenericUnit u : allUnits){
            u.update();
            if(u.getHp() == 0){
                allUnits.remove(u);
            }
        }

    }
}
