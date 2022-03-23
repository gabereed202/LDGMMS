package com.ldgmms.game;


import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void playerNotNullIfExists() {
        // should pass
        Player player = new Player();
        assertNotNull(player);
    }

    @Test
    public void playerNullIfExists() {
        // should fail
        Player player = new Player();
        assertNull(player);
    }

    @Test
    public void testPlayerFullHealthAndColorIsGreen() {
        // should pass
        Player player = new Player();
        assertTrue(player.getHealthColor().equals(HealthColor.GREEN));
    }

    @Test
    public void testPlayerFullHealthAndColorNotGreen() {
        // should pass
        Player player = new Player();
        assertFalse(!player.getHealthColor().equals(HealthColor.GREEN));
    }

    @Test
    public void testPlayerHealthBetween50And75AndColorIsYellow() {
        // should pass
        Player player = new Player();
        player.setHP(74);
        assertTrue(player.getHealthColor().equals(HealthColor.YELLOW));
    }

    @Test
    public void testPlayerHealthBetween50And75AndColorIsNotYellow() {
        // should pass
        Player player = new Player();
        player.setHP(74);
        assertFalse(!player.getHealthColor().equals(HealthColor.YELLOW));
    }

    @Test
    public void testPlayerHealthBetween25And50AndColorIsOrange() {
        // should pass
        Player player = new Player();
        player.setHP(49);
        assertTrue(player.getHealthColor().equals(HealthColor.ORANGE));
    }

    @Test
    public void testPlayerHealthBetween25And50AndColorIsNotOrange() {
        // should pass
        Player player = new Player();
        player.setHP(49);
        assertFalse(!player.getHealthColor().equals(HealthColor.ORANGE));
    }

    @Test
    public void testPlayerHealthLessThan25AndRed() {
        // should pass
        Player player = new Player();
        player.setHP(1);
        assertTrue(player.getHealthColor().equals(HealthColor.RED));
    }

    @Test
    public void testPlayerHealthLessThan25AndNotRed() {
        // should pass
        Player player = new Player();
        player.setHP(1);
        assertFalse(!player.getHealthColor().equals(HealthColor.RED));
    }

    @Test
    public void testPlayerHealthIs0AndPlayerIsDead() {
        Player player = new Player();
        player.setHP(0);
        assertTrue(!player.isAlive());
    }

    @Test
    public void testPlayerHealthIs0AndPlayerIsNotDead() {
        Player player = new Player();
        player.setHP(0);
        assertFalse(player.isAlive());
    }
}