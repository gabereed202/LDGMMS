package com.ldgmms.game;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.internal.TextListener;
import org.junit.runner.JUnitCore;

import java.util.ArrayList;
import java.util.List;

public class SkillsTest {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        junit.addListener(new TextListener(System.out));
        junit.run(SkillsTest.class);
    }

    /*  Acceptance Test:
        Tests if Skills.hasSkill() can check if a skill exists, or doesn't */
    @Test
    public void testHasSkill() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball now exists in skill
        Assert.assertEquals(true, skill.hasSkill("fire ball"));
        Assert.assertEquals(false, skill.hasSkill("not fire ball"));
    }

    /*  Acceptance Test:
        Tests if Skills.addSkill() successfully adds skill, and fails to add skill correctly */
    @Test
    public void testAddSkill() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball now exists in skill
        Assert.assertEquals(false, skill.addSkill("fire ball", 1, 1));
        Assert.assertEquals(true, skill.addSkill("water ball", 1, 1));
        Assert.assertEquals(true, skill.skillMap.containsKey("water ball"));
    }

    /*  Acceptance Test:
        Tests if Skills.removeSkill() successfully removes skill, and fails to removes correctly */
    @Test
    public void testRemoveSkill() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball now exists in skill
        Assert.assertEquals(false, skill.removeSkill("not fire ball"));
        Assert.assertEquals(true, skill.removeSkill("fire ball"));
        Assert.assertEquals(false, skill.skillMap.containsKey("fire ball"));
    }

    /*  Tests flat decrease, flat increase, percent decrease, and percent increase.
        modifySkillValues(): returns 1 if fails due to incorrect operation name
                             returns 2 if fails due to incorrect value name
                             returns 0 if modify is successful
        Note: The next five tests gives full Acceptance coverage for modifySkillValues()
        Note: The number of tests will increase by 2 for every new operation added to the method */

    //White Box Test Portion:
    @Test
    public void testModifySkillValuesReturns() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball with 1 damage now exists in skill

        //Tests if modifySkillValues() fails due to incorrect operation name correctly, and passes
        Assert.assertEquals(1, skill.modifySkillValues("fire ball", "damage", "incorrect operation name", 1));
        Assert.assertEquals(0, skill.modifySkillValues("fire ball", "damage", "flat increase", 1));
        skill.skillMap.replace("fire ball", data); //resets "fire ball" skill data

        //Tests if modifySkillValues() fails due to incorrect value name correctly, and passes
        Assert.assertEquals(2, skill.modifySkillValues("fire ball", "incorrect value name", "incorrect operation name", 1));
        Assert.assertEquals(0, skill.modifySkillValues("fire ball", "damage", "flat increase", 1));
        skill.skillMap.replace("fire ball", data); //resets "fire ball" skill data
    }

    //Black Box Test Portion:
    @Test
    public void testModifyFlatDecrease() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball with 1 damage now exists in skill

        //Test if flat decrease returns success (0) when it should
        Assert.assertEquals(0, skill.modifySkillValues("fire ball", "damage", "flat decrease", 1));
        //"fire ball" should have damage = 0 at this point

        //Test if flat decrease modifies the values correctly
        Assert.assertEquals(0, skill.skillMap.get("fire ball").getDamage());
    }

    //Black Box Test Portion:
    @Test
    public void testModifyFlatIncrease() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball with 1 damage now exists in skill

        //Test if flat increase returns success (0) when it should
        Assert.assertEquals(0, skill.modifySkillValues("fire ball", "damage", "flat increase", 1));
        //"fire ball" should have damage = 2 at this point

        //Test if flat increase modifies the values correctly
        Assert.assertEquals(2, skill.skillMap.get("fire ball").getDamage());
    }

    @Test
    public void testModifyPercentDecrease() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball with 1 damage now exists in skill

        //Test if flat decrease returns success (0) when it should
        Assert.assertEquals(0, skill.modifySkillValues("fire ball", "damage", "percent decrease", 100));
        //"fire ball" should have damage = 0 at this point

        //Test if flat decrease modifies the values correctly
        Assert.assertEquals(0, skill.skillMap.get("fire ball").getDamage());
    }

    //Black Box Test Portion:
    @Test
    public void testModifyPercentIncrease() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball with 1 damage now exists in skill

        //Test if flat increase returns success (0) when it should
        Assert.assertEquals(0, skill.modifySkillValues("fire ball", "damage", "percent increase", 100));
        //"fire ball" should have damage = 2 at this point

        //Test if flat increase modifies the values correctly
        Assert.assertEquals(2, skill.skillMap.get("fire ball").getDamage());
    }

    //White Box Test:
    //SkillDataToString
    @Test
    public void testSkillDataToString() {
        Skills skill = new Skills();
        SkillData data = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data);
        //A skill (key) called fire ball with 1 damage now exists in skill

        //Since the return value is a string and must be exact, one assert is sufficient
        Assert.assertEquals("fire ball-1.0-1.0", skill.skillDataToString("fire ball"));
    }

    //Acceptance test:
    //Find skills with specific values
    @Test
    public void testFindSkillsWithValue() {
        Skills skill = new Skills();
        SkillData data1 = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data1);
        SkillData data2 = new SkillData("kick", 2, 2);
        skill.skillMap.put("kick", data2);
        //A skill (key) called fire ball with 1 damage now exists in skill

        Assert.assertEquals("fire ball", skill.findSkillsWithValue("damage", 1).get(0));
        Assert.assertEquals("kick", skill.findSkillsWithValue("damage", 2).get(0));
    }

    //Acceptance test:
    //Remove skills with specific values
    @Test
    public void testRemoveSkillsWithValue() {
        Skills skill = new Skills();
        SkillData data1 = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data1);
        //A skill (key) called fire ball with 1 damage now exists in skill

        Assert.assertEquals(true, skill.skillMap.containsKey("fire ball"));

        Assert.assertEquals(false, skill.removeSkillsWithValue("damage", 2));
        Assert.assertEquals(true, skill.removeSkillsWithValue("damage", 1));

        Assert.assertEquals(false, skill.skillMap.containsKey("fire ball"));
    }

    @Test
    public void testDoesValueExist() {
        Skills skill = new Skills();
        SkillData data1 = new SkillData("fire ball", 1, 1);
        skill.skillMap.put("fire ball", data1);
        //A skill (key) called fire ball with 1 damage now exists in skill

        Assert.assertEquals(true, skill.doesValueExist("damage"));
        Assert.assertEquals(true, skill.doesValueExist("mana"));
        Assert.assertEquals(false, skill.doesValueExist("not a value"));
    }
}

