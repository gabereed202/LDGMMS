package com.ldgmms.game;


import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    /**
     * Acceptance test.
     */
    @Test
    public void playerNotNullIfExists() {
        // should pass
        Player player = new Player();
        assertNotNull(player);
    }

    /**
     * Acceptance test.
     */
    @Test
    public void playerNullIfExists() {
        // should fail
        Player player = new Player();
        assertNull(player);
    }

    /**
     * Integration test: bottom-up approach.
     * Tests the view component (health color) and the model component (player's health).
     */
    @Test
    public void testPlayerFullHealthAndColorIsGreen() {
        // should pass
        Player player = new Player();
        assertTrue(player.getHP() == 100);
        assertTrue(player.getHealthColor().equals(HealthColor.GREEN));
    }

    /**
     * Integration test: bottom-up approach.
     * Tests the view component (health color) and the model component (player's health).
     */
    @Test
    public void testPlayerFullHealthAndColorNotGreen() {
        // should pass
        Player player = new Player();
        assertTrue(player.getHP() == 100);
        assertFalse(!player.getHealthColor().equals(HealthColor.GREEN));
    }

    /**
     * Acceptance test.
     */
    @Test
    public void testPlayerHealthIs0AndPlayerIsDead() {
        Player player = new Player();
        player.setHP(0);
        assertTrue(!player.isAlive());
    }

    /**
     * Acceptance test.
     */
    @Test
    public void testPlayerHealthIs0AndPlayerIsNotDead() {
        Player player = new Player();
        player.setHP(0);
        assertFalse(player.isAlive());
    }

    /**
     * The rest of the tests make up White-box tests which tests the view portion of the player's health.
     *
     * Below is the method being tested:
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * public void setHP(int hp) {
     *         this.hp = hp;
     *
     *         if (hp == 0) {
     *             setAlive(false);
     *         }
     *
     *         if (hp >= 75) {
     *             setHealthColor(HealthColor.GREEN);
     *         } else if (hp < 75 && hp >= 50) {
     *             setHealthColor(HealthColor.YELLOW);
     *         } else if (hp < 50 && hp >= 25) {
     *             setHealthColor(HealthColor.ORANGE);
     *         } else if (hp < 25) {
     *             setHealthColor(HealthColor.RED);
     *         }
     *     }
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */
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
    /**
     * End of White-box testing
     */
}