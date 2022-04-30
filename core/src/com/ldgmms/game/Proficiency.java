package com.ldgmms.game;
import java.util.HashMap;

//import javax.lang.model.util.ElementScanner14;

public class Proficiency {
    private int points = 0; //capped at 100 points
    private int pointsUsed = 0;
    private int totalPointsUsed = 0;
    HashMap<String, Integer> proficiencyLevels = new HashMap<String, Integer>();
    
    public Proficiency() {
        proficiencyLevels.put("offensive", 0); //increase damage
        proficiencyLevels.put("defensive", 0); //damage cut
        proficiencyLevels.put("utility", 0); //moving around
        proficiencyLevels.put("support", 0); //healing/buffing
        proficiencyLevels.put("life", 0); //quality of life
    }

    public void resetProficiency() {
        proficiencyLevels.clear();
        points = totalPointsUsed;
        totalPointsUsed = 0;
    }

    //calculates points based off of score
    public boolean calculatePoints(int score) {
        int pointsToAdd;
        if(score >= 0) {
            pointsToAdd = points + score/100;
            if(pointsToAdd > 100) { //cap of 100 points
                return false;
            }else{
                points = pointsToAdd;
            }
        }
        return false;
    }

    //should be private after tests
    public void setPoints(int pointsToSet) {
        points = pointsToSet;
    }

    public int getTotalPointsUsed() {
        return totalPointsUsed;
    }

    public boolean searchType(String type) {
        if(proficiencyLevels.get(type) == 1) {
            return true;
        }
        return false;
    }

    //apply points to proficiency types
    public boolean applyPoints(String type, int pointsToUse) {
        if(points < pointsToUse) {
            return false;
        }
        if(searchType(type)) {
            return false;
        }
        if(isCappedLevel(type, pointsToUse)) {
            return false;
        }
        switch (type) {
            case "offensive":
                points -= pointsToUse;
                pointsUsed = pointsToUse;
                totalPointsUsed += pointsToUse;
                proficiencyLevels.put(type, (proficiencyLevels.get("offensive") + points));
                break;
            case "defensive":
                points -= pointsToUse;
                pointsUsed = pointsToUse;
                totalPointsUsed += pointsToUse;
                proficiencyLevels.put(type, (proficiencyLevels.get("defensive") + points));
                break;
            case "utility":
                points -= pointsToUse;
                pointsUsed = pointsToUse;
                totalPointsUsed += pointsToUse;
                proficiencyLevels.put(type, (proficiencyLevels.get("utility") + points));
                break;
            case "support":
                points -= pointsToUse;
                pointsUsed = pointsToUse;
                totalPointsUsed += pointsToUse;
                proficiencyLevels.put(type, (proficiencyLevels.get("support") + points));
                break;
            case "life":
                points -= pointsToUse;
                pointsUsed = pointsToUse;
                totalPointsUsed += pointsToUse;
                proficiencyLevels.put(type, (proficiencyLevels.get("life") + points));
                break;
            default:
                System.out.println("Incorrect type");
        }
        return false;
    }

    public int getLevel(String type) {
        if(points < 0) {
            return -1; //something went wrong
        }
        return(proficiencyLevels.get(type));
    }

    public int getPoints() {
        return points;
    }

    public int getPointsUsed() {
        return pointsUsed;
    }

    private boolean editLevel(String type, int level) {
        if(searchType(type)) {
            proficiencyLevels.put(type, level);
            return true;
        }
        return false;
    }

    //Skills class will call this method to increase skill damage/mana
    public float improveSkill(float skillValue, String skillParam, float flatValue) {
        float returnValue = -1;
        if(flatValue < 0 || skillValue < 0)
            return returnValue;
        switch (skillParam) {
            case "damage":
                returnValue = skillValue + flatValue;
                break;
            case "mana":
                returnValue = skillValue + flatValue;
                break;
            default:
                return returnValue;
        }
        return returnValue;
    }

    public void gameWonPointsIncrease(boolean gameWon) {
        if(gameWon) {
            points += 5;
        }else{
            points += 1;
        }
    }

    public void setLevel(String type, int level) {
        switch (type) {
            case "offensive":
                proficiencyLevels.replace("offensive", level);
                break;
            case "defensive":
                proficiencyLevels.replace("defensive", level);
                break;
            case "utility":
                proficiencyLevels.replace("support", level);
                break;
            case "support":
                proficiencyLevels.replace("utility", level);
                break;
            case "life":
                proficiencyLevels.replace("life", level);
                break;
            default:
                System.out.println("Incorrect type");
        }
    }

    public boolean isCappedLevel(String type, int pointsToUse) {
        if ((getLevel(type) + pointsToUse / 100) > 100) {
            return true;
        } else {
            return false;
        }
    }

    public boolean achievementTypePointsIncrease(String achievementType) {
        if(achievementType == "minor") {
            points += 10;
            return true;
        }else if(achievementType == "major") {
            points += 35;
            return true;
        }
        return false;
    }

    public boolean survivalTimePointsIncrease(double time) {
        if(time >= 10 && time < 30) {
            points += points + (time * 0.1);
            return true;
        }else if(time >= 30) {
            points += 8;
            return true;
        }else if(time >= 60) {
            points += 16;
            return true;
        }
        return false;
    }
}
