package com.ldgmms.game;
import com.badlogic.gdx.math.MathUtils;


public class Roll {
    public static int dice(int numDice, int sides){ //will let you define any sided die (do 100 sides for percentile)
        int sum = 0;
        for (int i = 0; i < numDice; i++){
            sum += MathUtils.random(1, sides);
        }
        return sum;

    }

}
