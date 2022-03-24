package com.ldgmms.game;

import org.testng.annotations.Test;
import static org.junit.Assert.*;

public class UnitTests {
    @Test
    public void test1() {
        Tile tile = new Tile(Tile.TileType.dirt, new Tile[]{ });

        assertEquals(Tile.TileType.dirt, tile.type());
    }

    @Test
    public void test2() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertEquals(tile1, tile2.getNeighbor(0));
    }

    @Test
    public void test3() {
        Tile tile = new Tile(Tile.TileType.grass, new Tile[]{ });
        GenericUnit unit = new GenericUnit(0, 0, 0, 0, 0, 0, 0, 0, 0);

        assertTrue(tile.isAvailable());
        assertTrue(tile.receiveUnit(unit));
        assertFalse(tile.isAvailable());
        assertEquals(unit, tile.takeUnit());
    }
}
