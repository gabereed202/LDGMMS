package com.ldgmms.game;

public class Enemy extends GenericUnit{
    String spritePath;
    String name;
    Strategy strategy;

    Skills baseSkills;


    public Enemy(String spritePath, String name, int ID, int x, int y, int difficulty, int job){

        this.spritePath = spritePath;
        this.name = name;
        this.health = 100;
        this.poisonResistance = 5;
        this.cutResistance = 5;
        this.pierceResistance = 5;
        this.fireResistance = 5;
        this.iceResistance = 5;
        this.ID = ID;
        this.xPosition = x;
        this.yPosition = y;
        this.baseSkills = new Skills();

        // job assigns the skill they have
        // 1 is melee, 2 is ranged, 3 is magic
        if (job == 1){
            baseSkills.addSkill(1, "Melee Attack", 7, 0, 10, 1);
        }
        else if (job == 2) {
            baseSkills.addSkill(1, "Ranged Attack", 7, 0, 10, 2);
        }
        else if (job == 3) {
            baseSkills.addSkill(1, "Magic Attack", 7, 10, 0, 1);
        }

        if (difficulty == 0){
            this.strategy = new EasyMelee();
        }
        else if (difficulty >= 1){
            this.strategy = new HardMelee();
        }
    }
}
