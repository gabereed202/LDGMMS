package com.ldgmms.game;

import org.junit.Test;
import static org.junit.Assert.*;

public class DanTests {
    @Test
    public void TestHasteCaseSingle(){ //checks if the apBonus is properly removed after two turns
        GenericUnit u = new GenericUnit(10, 10, 10, 10, 10, 10, 10, 10, 10);
        DamTypes.ResistFire newEffect = new DamTypes.ResistFire();
        newEffect.applyDamageEffect(u, 10, 2);
        u.update();
        assertEquals(20, u.getFireRes()); //did apBonus get added?
        u.update();
        u.removeFinishedEffects();
        u.update();
        u.removeFinishedEffects();
        assertEquals(10, u.getFireRes());
    }
    @Test
    public void TestHasteCaseMultiple(){ //checks if apBonus is correct after overwriting weaker effect
        GenericUnit u = new GenericUnit(10, 10, 10, 10, 10, 10, 10, 10, 10);
        DamTypes.ResistFire newEffect = new DamTypes.ResistFire();
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        DamTypes.ResistFire secondEffect = new DamTypes.ResistFire();
        secondEffect.applyDamageEffect(u, 20, 5); //should overwrite newEffect (greater magnitude) NOTE, not working because bonus isn't reapplied
        u.update();
        u.removeFinishedEffects();
        assertEquals(30, u.getFireRes()); //30 because 10 from base stats + 20 core stats
    }
    @Test
    public void TestCutDamageNoProcMinDam(){ //case where no status effect occurs and minimum damage is applied
        GenericUnit u = new GenericUnit(100, 100, 100, 100, 100, 100, 100, 100, 100); //high to ensure no status effect will be created as a result of damage
        DamTypes.CutDamage newEffect = new DamTypes.CutDamage();
        newEffect.applyDamageEffect(u, 1, 5); //magnitude of 1 so minimum damage will be 5
        u.update();
        assertEquals(u.hp, 95); //minimum damage of 5 once, so unit HP should be 95 since 100 maxHP set
    }
    @Test
    public void TestCutDamageProcRegDam(){ //case where status effect occurs and regular damage occurs
        GenericUnit u = new GenericUnit(25, 100, 100, 100, 100, 100, 100, 100, 100); //cutres set to 25
        DamTypes.CutDamage newEffect = new DamTypes.CutDamage();
        newEffect.applyDamageEffect(u, 25, 5); // check against res is currently 25d3 so guarantee of status effect occuring
        u.update();
        //StatusEffect.Bleed returnedEffect = u.effectList.get(0);
        assertTrue(u.effectList.get(0) instanceof StatusEffect.Bleed);
    }

}
