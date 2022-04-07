package com.ldgmms.game;

import org.junit.Test;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Tests {
    @Test
    public void testCharacterHasNoJob(){
        // acceptance test
        Hero hero = new Hero();

        assertEquals(hero.job, -1);
        assertFalse(hero.job == 2);

    }

    @Test
    public void testCharacterJobAssignment(){
        // acceptance test
        Hero hero = new Hero();
        hero.equipmentStorage.addEquipment(1, "test", 2, 2);

        hero.changeEquipment(0, hero.equipmentStorage.map.get(1));

        assertEquals(hero.job, 2);

    }

    @Test
    public void testCharacterJobAssignmentIncorrect(){
        // acceptance test
        Hero hero = new Hero();
        hero.equipmentStorage.addEquipment(1, "test", 2, 2);

        hero.changeEquipment(0, hero.equipmentStorage.map.get(1));

        assertFalse(hero.job == 0);
        assertFalse(hero.job == 3);
    }

    @Test
    public void testNewMoveset(){
        // acceptance test
        Hero hero = new Hero();
        hero.equipmentStorage.addEquipment(1, "test", 1, 2);

        hero.changeEquipment(0, hero.equipmentStorage.map.get(1));
        hero.gainExp(500.0f);

        assertEquals(hero.job, 1);
        assertEquals(hero.scholar.skills.map.get(hero.scholar.currentSkill-1).getSkillName(), "Fire Rune");
    }
    @Test
    public void testLevelUp(){
        // acceptance test
        Hero hero = new Hero();
        hero.equipmentStorage.addEquipment(1, "test", 1, 2);

        hero.changeEquipment(0, hero.equipmentStorage.map.get(1));
        hero.gainExp(500.0f);

        assertEquals(hero.jobLevels[1], 5);
        assertEquals(hero.level, 3);
    }

    @Test
    public void testPickUpEquipment(){
        // acceptance test
        Hero hero = new Hero();
        EquipmentData e = new EquipmentData(0, "test", 1, 1);
        hero.pickUpEquipment(e);

        assertEquals(hero.equipmentStorage.map.get(0).getEquipmentName(), "test");
    }

    @Test
    public void testSpecialCombo(){
        // acceptance test
        Hero hero = new Hero();
        hero.equipmentStorage.addEquipment(0, "test1", 3, 3);
        hero.equipmentStorage.addEquipment(1, "test2", 4, 4);
        hero.changeEquipment(0, hero.equipmentStorage.map.get(0));
        hero.changeEquipment(1, hero.equipmentStorage.map.get(1));

        assertEquals(hero.specialCombo.map.get(1).getSkillName(), "Magical Thrash");
    }

    @Test
    public void testNoLevel(){
        // acceptance test
        Hero hero = new Hero();

        assertEquals(hero.level, 1);
    }

    @Test
    public void testAttributeGain(){
        //acceptance test
        Hero hero = new Hero();
        hero.gainExp(500.0f);

        assertEquals(hero.health, 120.0f, 0.0f);
        assertEquals(hero.stamina, 120.0f, 0.0f);
        assertEquals(hero.mana, 120.0f, 0.0f);
    }

    @Test
    public void testNewSkillEntry(){
        // integration test, bottom up approach
        Hero hero = new Hero();
        SkillData newSkill = new SkillData(11, "test", 1, 1, 1);
        hero.baseSkills.map.put(newSkill.getKey(), newSkill);

        assertEquals(hero.baseSkills.map.get(11).getSkillName(), "test");
    }

    @Test
    public void testBranchTrue(){
        // along with testBranchFalse this achieves full branch coverage
        Hero hero = new Hero();

        hero.level = 10;

        assertEquals(hero.checkMaxLevel(), true);

    }

    @Test
    public void testBranchFalse(){
        // along with testBranchTrue this achieves full branch coverage
        Hero hero = new Hero();

        hero.level = 0;

        assertEquals(hero.checkMaxLevel(), false);

    }

}
