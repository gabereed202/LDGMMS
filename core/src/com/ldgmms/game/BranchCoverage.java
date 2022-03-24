package com.ldgmms.game;

import org.testng.annotations.Test;
import static org.junit.Assert.*;

public class BranchCoverage {
    @Test
    public void testLess() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertNull(tile2.getNeighbor(-1));
    }

    @Test
    public void testGreater() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertNull(tile2.getNeighbor(50));
    }

    @Test
    public void testValid() {
        Tile tile1 = new Tile(Tile.TileType.grass, new Tile[]{ });
        Tile tile2 = new Tile(Tile.TileType.rock, new Tile[]{ tile1 });

        assertEquals(tile1, tile2.getNeighbor(0));
    }
}