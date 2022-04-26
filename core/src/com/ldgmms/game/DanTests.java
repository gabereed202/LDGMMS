package com.ldgmms.game;

import com.badlogic.gdx.math.MathUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class DanTests {
    @Test
    public void TestFireResCaseSingle(){ //black box test, functional coverage
        GenericUnit u = new GenericUnit(null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistFire newEffect = new DamTypes.ResistFire();
        newEffect.applyDamageEffect(u, 10, 2);
        u.update();
        assertEquals(20, u.getFireRes()); //did bonus get added?
        u.update();
        u.update();
        assertEquals(10, u.getFireRes()); //did it get removed?
    }
    @Test
    public void TestFireResCaseMultiple(){ //white box test, achieves branch coverage as all potential branches in the Effect class FireRes are tested
        GenericUnit u = new GenericUnit(null, "test", 10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistFire newEffect = new DamTypes.ResistFire();
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        DamTypes.ResistFire secondEffect = new DamTypes.ResistFire();
        secondEffect.applyDamageEffect(u, 20, 5); //should overwrite newEffect (greater magnitude)
        u.update();
        assertEquals(30, u.getFireRes()); //30 because 10 from base stats + 20 core stats
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        assertEquals(30, u.getFireRes()); // effect applied was weaker than current effect on unit, will not be applied
    }

    @Test
    public void TestIceResCaseSingle(){ //black box test, functional coverage
        GenericUnit u = new GenericUnit(null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistIce newEffect = new DamTypes.ResistIce();
        newEffect.applyDamageEffect(u, 10, 2);
        u.update();
        assertEquals(20, u.getIceRes()); //did bonus get added?
        u.update();
        u.update();
        assertEquals(10, u.getIceRes()); //did it get removed?
    }
    @Test
    public void TestIceResCaseMultiple(){//white box test, achieves branch coverage as all potential branches in the Effect class FireRes are tested
        GenericUnit u = new GenericUnit(null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistIce newEffect = new DamTypes.ResistIce();
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        DamTypes.ResistIce secondEffect = new DamTypes.ResistIce();
        secondEffect.applyDamageEffect(u, 20, 5); //should overwrite newEffect (greater magnitude) NOTE, not working because bonus isn't reapplied
        u.update();
        //u.removeFinishedEffects();
        assertEquals(30, u.getIceRes()); //30 because 10 from base stats + 20 core stats
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        assertEquals(30, u.getIceRes()); // effect applied was weaker than current effect on unit, will not be applied
    }
    @Test
    public void TestPhysResCaseSingle(){ //black box test, statement coverage
        GenericUnit u = new GenericUnit( null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistPhys newEffect = new DamTypes.ResistPhys();
        newEffect.applyDamageEffect(u, 10, 2);
        u.update();
        assertEquals(20, u.getCutRes()); //did bonus get added?
        assertEquals(20, u.getPierceRes());
        u.update();
        u.update();
        assertEquals(10, u.getCutRes()); //did it get removed?
        assertEquals(10, u.getPierceRes());
    }
    @Test
    public void TestPhysResCaseMultiple(){ //white box test, achieves branch coverage as all potential branches in the Effect class PhysRes are tested
        GenericUnit u = new GenericUnit(null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistPhys newEffect = new DamTypes.ResistPhys();
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        DamTypes.ResistPhys secondEffect = new DamTypes.ResistPhys();
        secondEffect.applyDamageEffect(u, 20, 5); //should overwrite newEffect (greater magnitude) NOTE, not working because bonus isn't reapplied
        u.update();
        assertEquals(30, u.getCutRes()); //30 because 10 from base stats + 20 core stats
        assertEquals(30, u.getPierceRes());
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        assertEquals(30, u.getCutRes()); //30 because 10 from base stats + 20 core stats
        assertEquals(30, u.getPierceRes());
    }
    @Test
    public void TestPoisonResCaseSingle(){ //black box test, functional coverage
        GenericUnit u = new GenericUnit(null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistPoison newEffect = new DamTypes.ResistPoison();
        newEffect.applyDamageEffect(u, 10, 2);
        u.update();
        assertEquals(20, u.getPoisonRes()); //did bonus get added?
        u.update();
        u.update();
        assertEquals(10, u.getPoisonRes()); //did it get removed?
    }
    @Test
    public void TestPoisonResCaseMultiple(){ //white box test, achieves branch coverage as all potential branches in the Effect class PoisonRes are tested
        GenericUnit u = new GenericUnit(null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistPoison newEffect = new DamTypes.ResistPoison();
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        DamTypes.ResistPoison secondEffect = new DamTypes.ResistPoison();
        secondEffect.applyDamageEffect(u, 20, 5); //should overwrite newEffect (greater magnitude) NOTE, not working because bonus isn't reapplied
        u.update();
        assertEquals(30, u.getPoisonRes()); //30 because 10 from base stats + 20 core stats
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        assertEquals(30, u.getPoisonRes());
    }
    @Test
    public void TestCutDamageNoProcMinDam(){ //white box test, function coverage with TestCutDamageProcRegDam (not statement because applyDamage not tested)
        GenericUnit u = new GenericUnit(null, "test", 100, 100, 100, 100, 100, 100, 100, 100, 100, 0); //high to ensure no status effect will be created as a result of damage
        DamTypes.CutDamage newEffect = new DamTypes.CutDamage();
        newEffect.applyDamageEffect(u, 1, 5); //magnitude of 1 so minimum damage will be 5
        u.update();
        assertEquals(95, u.hp, 0); //minimum damage of 5 once, so unit HP should be 95 since 100 maxHP set
    }
    @Test
    public void TestCutDamageProcRegDam(){ //case where status effect occurs and regular damage occurs
        GenericUnit u = new GenericUnit(null, "test",25, 100, 100, 100, 100, 100, 100, 100, 100, 0); //cutRes set to 25
        DamTypes.CutDamage newEffect = new DamTypes.CutDamage();
        newEffect.applyDamageEffect(u, 25, 5); // check against res is currently 25d3 so guarantee of status effect occurring
        u.update();
        assertTrue(u.effectList.get(0) instanceof StatusEffect.Bleed);
    }
    @Test
    public void TestSlowResCaseSingle(){ //black box test, statement coverage that ensures effect gets added and removed correctly
        GenericUnit u = new GenericUnit(null, "test", 10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistSlow newEffect = new DamTypes.ResistSlow();
        newEffect.applyDamageEffect(u, 10, 2);
        u.update();
        assertEquals(20, u.getSlowRes()); //did bonus get added?
        u.update();
        u.update();
        assertEquals(10, u.getSlowRes()); //did it get removed?
    }
    @Test
    public void TestSlowResCaseMultiple(){//white box test, statement coverage
        GenericUnit u = new GenericUnit(null, "test",10, 10, 10, 10, 10, 10, 10, 10, 10, 0);
        DamTypes.ResistSlow newEffect = new DamTypes.ResistSlow();
        newEffect.applyDamageEffect(u, 10, 5);
        u.update();
        DamTypes.ResistSlow secondEffect = new DamTypes.ResistSlow();
        secondEffect.applyDamageEffect(u, 20, 5); //should overwrite newEffect (greater magnitude) NOTE, not working because bonus isn't reapplied
        u.update();
        assertEquals(30, u.getSlowRes()); //30 because 10 from base stats + 20 core stats
    }
    @Test
    public void TestAllResAdded(){//integration test, ensuring no resistances affect each other (some were incorrectly written before), statement coverage
        GenericUnit u = new GenericUnit(null, "test",0,0,0,0,0,0,10,0,10, 0);
        DamTypes.ResistFire fireResEffect = new DamTypes.ResistFire();
        DamTypes.ResistIce iceResEffect = new DamTypes.ResistIce();
        DamTypes.ResistPhys physResEffect = new DamTypes.ResistPhys();
        DamTypes.ResistPoison poisonResEffect = new DamTypes.ResistPoison();
        DamTypes.ResistSlow slowResEffect = new DamTypes.ResistSlow();
        fireResEffect.applyDamageEffect(u, 30, 1);
        iceResEffect.applyDamageEffect(u, 30, 1);
        physResEffect.applyDamageEffect(u,30,1);
        poisonResEffect.applyDamageEffect(u, 30, 1);
        slowResEffect.applyDamageEffect(u, 30, 1);
        u.update();
        assertEquals(30, u.getFireRes());
        assertEquals(30, u.getIceRes());
        assertEquals(30, u.getPierceRes());
        assertEquals(30, u.getCutRes());
        assertEquals(30, u.getPoisonRes());
        assertEquals(30, u.getSlowRes());
        u.update();
        assertEquals(0, u.getFireRes());
        assertEquals(0, u.getIceRes());
        assertEquals(0, u.getPierceRes());
        assertEquals(0, u.getCutRes());
        assertEquals(0, u.getPoisonRes());
        assertEquals(0, u.getSlowRes());

    }
    @Test
    public void TestHealAfterPierceDMG(){ //white box test, statement coverage because multiple heals branch not tested
        GenericUnit u = new GenericUnit(null, "test",100,100,100,100,100,100,100,0,10, 0);
        DamTypes.PierceDamage damEffect = new DamTypes.PierceDamage();
        damEffect.applyDamage(u, 20);
        assertEquals(95, u.getHp(), 0);
        damEffect.applyDamage(u, 20);
        DamTypes.Heal healEffect = new DamTypes.Heal();
        healEffect.applyDamageEffect(u, 5, 1);
        u.update();
        u.update();
        assertEquals(95, u.getHp(), 0);

    }
    @Test
    public void TestHealWhenFull(){ //white box test, provides statement coverage
        GenericUnit u = new GenericUnit(null, "test",100,100,100,100,100,100,100,0,10, 0);
        DamTypes.Heal healEffect = new DamTypes.Heal();
        healEffect.applyDamageEffect(u, 500, 1);
        u.update();
        assertEquals(100, u.getHp(), 0);
    }
    @Test
    public void TestKillUnit(){ //white box test, provides function coverage (applyDamage statement not ran)
        GenericUnit u = new GenericUnit(null, "test",0,0,0,0,0,0,100,0,10, 0);
        DamTypes.CutDamage damEffect = new DamTypes.CutDamage();
        damEffect.applyDamageEffect(u, 500, 1);
        u.update();
        assertEquals(0, u.getHp(), 0);
    }
    @Test //this is an integration test that is top down, as I tested the DamTypes methods which then call the StatusEffect methods, provides statement coverage (applyDamage not tested)
    public void TestFireResThenFireDam(){ //gives unit resistance, applies damage less than enough to generate effect, applies enough damage to generate status effect
        MathUtils.random.setSeed(1);
        GenericUnit u = new GenericUnit(null, "test",0,0,0,0,0,0,100,0,10, 0);
        DamTypes.ResistFire resEffect = new DamTypes.ResistFire();
        resEffect.applyDamageEffect(u, 100, 10);
        u.update();
        DamTypes.FireDamage damEffect = new DamTypes.FireDamage();
        damEffect.applyDamageEffect(u, 20, 5);
        u.update();
        assertEquals(1, u.effectList.size()); //Burn effect not added
        damEffect.applyDamageEffect(u, 30, 5);
        u.update();
        assertEquals(2, u.effectList.size()); //Burn effect added
    }

    @Test
    public void TestMovingUnitsToSquareMap(){
        Hero h = new Hero(null, "firsthero", 0, 0, 0, 0, 0, 0, 100, 0, 20, 0);
        h.unitList.add(new GenericUnit(null, "test1",0,0,0,0,0,0,100,0,10, 0));
        h.unitList.add(new GenericUnit(null, "test2",0,0,0,0,0,0,100,0,10, 0));
        Hero e = new Hero(null, "secondhero", 0, 0, 0, 0, 0, 0, 100, 0, 20, 1);
        e.unitList.add(new GenericUnit(null, "test1",0,0,0,0,0,0,100,0,10, 0));
        e.unitList.add(new GenericUnit(null, "test2",0,0,0,0,0,0,100,0,10, 0));
    }
    @Test
    public void TestJsonParsing(){
        //DesktopLauncher.main();
        //UnitParser.loadFile();
    }
}
