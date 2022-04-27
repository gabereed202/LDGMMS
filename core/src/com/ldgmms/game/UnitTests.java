package com.ldgmms.game;

import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UnitTests {
    @Test // Acceptance
    public void test1() {
        Tile tile = new Tile(Tile.TileType.dirt, new Tile[]{ });

        assertEquals(Tile.TileType.dirt, tile.type());
    }

    @Test // Acceptance
    public void test2() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertEquals(tile1, tile2.getNeighbor(0));
    }

    @Test // Integration between Tile and GenericUnit. Specifically how the Tile handles the GenericUnit. Don't know what my "approach" is. It's a very white-box style test - I know exactly what *should* happen...
    public void test3() {
        Tile tile = new Tile(Tile.TileType.grass, new Tile[]{ });
        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 0, 0, 0);

        assertTrue(tile.isAvailable());
        assertTrue(tile.receiveUnit(unit));
        assertFalse(tile.isAvailable());
        assertEquals(unit, tile.takeUnit());
    }

    @Test // Acceptance
    public void testLess() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertNull(tile2.getNeighbor(-1));
    }

    @Test // Acceptance
    public void testGreater() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertNull(tile2.getNeighbor(50));
    }

    @Test // Acceptance
    public void testValid() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertEquals(tile1, tile2.getNeighbor(0));
    }

    @Test // Acceptance
    public void getHpTest() {
        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 50, 0, 0);

        assertEquals(50, unit.getHpMax());
        assertEquals(50, unit.getHp());
    }

    @Test // Coverage test of GenericUnit.setHp
    public void setHpTest() {
        // Depends on the assumption that GenericUnit.getHp works
        getHpTest();

        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 50, 0, 0);

        unit.setHp(-5);
        assertEquals(-5, unit.getHp());
        unit.setHp(1000000);
        assertEquals(50, unit.getHp());
    }

    @Test // Coverage test of GenericUnit.damageHp
    public void damageTest() {
        // Depends on the assumption that GenericUnit.getHp works
        getHpTest();

        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 50, 0, 0);

        unit.damageHp(25);
        assertEquals(25, unit.getHp());
        unit.damageHp(1000000);
        assertEquals(0, unit.getHp());
    }

    @Test // Acceptance
    public void statusTest() {
        // Depends on the assumption that GenericUnit.getHp works
        getHpTest();

        StatusEffect.Bleed effect = new StatusEffect.Bleed(5, 2);
        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 50, 0, 0);

        effect.apply(unit);
        assertEquals(45, unit.getHp());
        effect.apply(unit);
        assertEquals(40, unit.getHp());
        assertFalse(effect.finished(unit));
        effect.apply(unit);
        assertTrue(effect.finished(unit));
    }

    @Test // Integration between GenericUnit and StatusEffect. Kinda a coverage test too...
    public void effectTest() {
        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 50, 0, 0);
        StatusEffect.Bleed effect = new StatusEffect.Bleed(5, 1);

        ArrayList<StatusEffect.Effect> list = unit.getEffectList();
        assertNotNull(list);
        list.add(effect);
        unit.update();
        assertFalse(effect.finished(unit));
        unit.removeFinishedEffects();
        assertEquals(1, list.size());
        unit.update();
        assertTrue(effect.finished(unit));
        unit.removeFinishedEffects();
        assertEquals(0, list.size());
    }

    @Test // Primarily Coverage for GenericUnit.getAp - also Integration between GenericUnit and StatusEffect.
    public void frozenApTest() {
        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 50, 0, 10);
        StatusEffect.Frozen effect = new StatusEffect.Frozen(5, 1);

        assertEquals(10, unit.getAp());
        effect.apply(unit);
        assertEquals(0, unit.getAp());
    }
}
