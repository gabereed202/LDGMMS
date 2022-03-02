package com.ldgmms.game;

import java.util.ArrayList;
import java.util.List;

class SkillData { //object to store a skill's data
    private String skill_name;
    private double damage;
    private double mana;

    public SkillData(String skill_name, double damage, double mana) { //constructor
        this.skill_name = skill_name;
        this.damage = damage;
        this.mana = mana;
    }

    public String getSkillName() {
        return skill_name;
    }

    public double getDamage() {
        return damage;
    }

    public double getMana() {
        return mana;
    }
    
    private boolean changeDamage(double new_damage) {
        if(new_damage < 0)
            return false;
        damage = new_damage;
        return true; //success
    }

    private boolean changeMana(double new_mana) {
        if(new_mana < 0)
            return false;
        mana = new_mana;
        return true;
    }

    public boolean modifyValue(String data, double new_value) {
        switch (data) {
            case "damage":
                if(!changeDamage(new_value))
                    return false;
                break;
            case "mana":
                if(!changeMana(new_value))
                    return false;
                break;
        }
        return true; //success
    }

    public List getValues() {
        List<String> valueStringList = new ArrayList<String>();
        valueStringList.add("damage");
        valueStringList.add("mana");
        return valueStringList;
    }

    @Override
    public String toString() {
        return "SkillData [skill_name = " + skill_name + ", damage = " + damage
                + ", mana = " + mana + "]";
    }
}
