package com.ldgmms.game;

import java.util.ArrayList;

public class Turn { //we will assume player is always 0, maybe define playerteam variable?
    public static void deployPhase(ArrayList<GenericUnit> allUnits){
        for (GenericUnit u : allUnits){
            if (u.team == 0){
                //u.deployUnit(); rather than do this, think of a way to apply model view controller
            }else{
                while(!u.deployUnit((int) Math.random(), (int) Math.random())); //keep trying to deploy unit in a random position until valid, not sure how
                                                                    //it will work with the current hex system but it can be updated later
            }
        }

    }
    public static void actionPhase(ArrayList<GenericUnit> allUnits){ //should input be handled here? or should there be a more global method of handling input with a logic loop in SquareScreen

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
