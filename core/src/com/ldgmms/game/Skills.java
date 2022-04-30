package com.ldgmms.game;

import java.util.HashMap;
import java.util.Set;
import java.util.Map;

// Modified version of Minh's code
public class Skills {
    public Map<Integer,SkillData> map;
    public Skills(){
        this.map = new HashMap <Integer, SkillData>();
    }
    public void addSkill(int key, String name, int damage, int mana, int stamina, int range){
        SkillData newSkill = new SkillData(key, name, damage, mana, stamina, range);
        this.map.put(newSkill.getKey(), newSkill);
        return;
    }

    public void printMap(){
        System.out.println("Hashmap entries**************");
        Set<Integer> keySet= map.keySet();
        for(int i:keySet){
            System.out.println(map.get(i));
        }

        //testing hashmap search
        //System.out.println("Test Searches****************");
        //System.out.println(map.containsKey(data1.getKey()));
        //System.out.println(map.containsKey(data2.getKey()));
        //System.out.println(map.containsKey(data3.getKey()));
        //System.out.println(map.containsValue(data1));
    }
}

//object to store a skill's data
class SkillData {
    private int key;
    private String skill_name;
    private int damage;
    private int mana;
    private int stamina;
    private int range;

    //constructor
    public SkillData(int key, String skill_name, int damage, int mana, int stamina, int range) {
        this.key = key;
        this.skill_name = skill_name;
        this.damage = damage;
        this.mana = mana;
        this.stamina = stamina;
        this.range = range;
    }

    public int getKey() {
        return key;
    }

    public String getSkillName() {
        return skill_name;
    }

    public int getDamage() {
        return damage;
    }

    public int getMana() {
        return mana;
    }

    public int getStamina() { return stamina; }

    public int getRange() {return range; }

    @Override
    public String toString() {
        return "SkillData [skill_name = " + skill_name + ", damage = " + damage
                + ", mana = " + mana + "]";
    }
}
