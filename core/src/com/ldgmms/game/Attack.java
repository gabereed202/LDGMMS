package com.ldgmms.game;

public class Attack extends Move{
    SkillData skill;
    GenericUnit target;
    int score;

    public Attack(SkillData skill, GenericUnit target){
        this.skill = skill;
        this.target = target;
    }

    public int enemyAttack(Boolean real){
        int potentialDamage = this.skill.getDamage();
        int targetHealth = (int) this.target.health;
        if (real){
            this.target.health -= (potentialDamage - this.target.defense);
        }
        targetHealth -= (potentialDamage - this.target.defense);
        return targetHealth;
    }
}
