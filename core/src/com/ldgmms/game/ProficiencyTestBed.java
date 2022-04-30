package com.ldgmms.game;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

//Test bed used while coding Proficiency class to make sure things at least somewhat work the way intended
public class ProficiencyTestBed {
    public static void main(String[] args){
        Proficiency proficiencyLevel = new Proficiency();
        System.out.println(proficiencyLevel.getLevel("offensive"));

        System.out.println(proficiencyLevel.getPoints());
        System.out.println(proficiencyLevel.getPointsUsed());

        if(proficiencyLevel.calculatePoints(100))
            System.out.println("calculatePoints 100 good");
        if(proficiencyLevel.calculatePoints(-100))
            System.out.println("calculatePoints -100 no good");

        if(proficiencyLevel.searchType("offensive") && proficiencyLevel.searchType("offensive") &&
        proficiencyLevel.searchType("defensive") && proficiencyLevel.searchType("support") &&
        proficiencyLevel.searchType("utility") && proficiencyLevel.searchType("life")){
            System.out.println("types work");
        }else{
            System.out.println("types doesnt work");
        }

        proficiencyLevel.setPoints(300); //points now 10
        proficiencyLevel.applyPoints("offensive", 100);
        proficiencyLevel.applyPoints("defensive", 100);
        System.out.println(proficiencyLevel.getPoints()); //should be 1
        System.out.println(proficiencyLevel.getLevel("offensive"));
        System.out.println(proficiencyLevel.getLevel("defensive"));

        proficiencyLevel.resetProficiency(); //needs to all be zeroes
        System.out.println(proficiencyLevel.getLevel("offensive"));
        System.out.println(proficiencyLevel.getLevel("defensive"));
        System.out.println(proficiencyLevel.getLevel("support"));
        System.out.println(proficiencyLevel.getLevel("utility"));
        System.out.println(proficiencyLevel.getLevel("life"));

        proficiencyLevel.setLevel("life", 100);
        System.out.println(proficiencyLevel.getLevel("life")); //should be 100
        if(proficiencyLevel.isCappedLevel("life", 1)) {
            System.out.println("life is capped");
        }else{
            System.out.println("life is not capped");
        }

        if(proficiencyLevel.survivalTimePointsIncrease(20)) {
            System.out.println("20: points awarded");
        }else{
            System.out.println("20: no points awarded");
        }
        if(proficiencyLevel.survivalTimePointsIncrease(100)) {
            System.out.println("100: points awarded");
        }else{
            System.out.println("100: no points awarded");
        }

        if(proficiencyLevel.achievementTypePointsIncrease("major")) {
            System.out.println("major: awarded");
        }else{
            System.out.println("major: not awarded");
        }
    }
}
