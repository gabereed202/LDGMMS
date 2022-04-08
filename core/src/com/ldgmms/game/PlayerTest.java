package com.ldgmms.game;


import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    /**
     * Acceptance test.
     */
    @Test
    public void playerNotNullIfExists() {
        Player player = new Player();
        assertNotNull(player);
    }

    /**
     * Integration test: bottom-up approach.
     * Tests the view component (health color) and the model component (player's health).
     */
    @Test
    public void testPlayerFullHealthAndColorIsGreen() {
        Player player = new Player();
        assertTrue(player.getHP() == 100);
        assertTrue(player.getHealthColor().equals(HealthColor.GREEN));
    }

    /**
     * Integration test: bottom-up approach.
     * Tests the view component (health color) and the model component (player's health).
     */
    @Test
    public void testPlayerFullHealthAndColorNotRedOrangeYellow() {
        Player player = new Player();
        assertTrue(player.getHP() == 100);
        boolean testRed = player.getHealthColor().equals(HealthColor.RED);
        boolean testOrange = player.getHealthColor().equals(HealthColor.ORANGE);
        boolean testYellow = player.getHealthColor().equals(HealthColor.YELLOW);
        assertFalse(testRed | testOrange | testYellow);
    }

    /**
     * Acceptance test.
     */
    @Test
    public void testPlayerHealthIs0AndPlayerIsDead() {
        Player player = new Player();
        player.setHP(0);
        assertFalse(player.isAlive());
    }

    /**
     * Acceptance test.
     */
    @Test
    public void testPlayerHealthIsNot0AndPlayerIsNotDead() {
        Player player = new Player();
        player.setHP(1);
        assertTrue(player.isAlive());
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

    // test remaining green

    @Test
    public void testPlayerHealthIs75AndColorIsGreen() {
        Player player = new Player();
        player.setHP(75);
        assertTrue(player.getHealthColor().equals(HealthColor.GREEN));
    }

    // test yellow
    @Test
    public void testPlayerHealthIs74AndColorIsYellow() {
        Player player = new Player();
        player.setHP(74);
        assertTrue(player.getHealthColor().equals(HealthColor.YELLOW));
    }

    @Test
    public void testPlayerHealthIsNot74AndColorIsNotYellow() {
        Player player = new Player();
        player.setHP(75);
        assertFalse(player.getHealthColor().equals(HealthColor.YELLOW));
    }

    @Test
    public void testPlayerHealthIs50AndColorIsYellow() {
        Player player = new Player();
        player.setHP(50);
        assertTrue(player.getHealthColor().equals(HealthColor.YELLOW));
    }

    @Test
    public void testPlayerHealthIsNot50AndColorIsNotYellow() {
        Player player = new Player();
        player.setHP(49);
        assertFalse(player.getHealthColor().equals(HealthColor.YELLOW));
    }

    // test orange
    @Test
    public void testPlayerHealthIs49AndColorIsOrange() {
        Player player = new Player();
        player.setHP(49);
        assertTrue(player.getHealthColor().equals(HealthColor.ORANGE));
    }

    @Test
    public void testPlayerHealthIsNot49AndColorIsNotOrange() {
        Player player = new Player();
        player.setHP(50);
        assertFalse(player.getHealthColor().equals(HealthColor.ORANGE));
    }

    @Test
    public void testPlayerHealthIs25AndColorIsOrange() {
        Player player = new Player();
        player.setHP(25);
        assertTrue(player.getHealthColor().equals(HealthColor.ORANGE));
    }

    @Test
    public void testPlayerHealthIsNot25AndColorIsNotOrange() {
        Player player = new Player();
        player.setHP(24);
        assertFalse(player.getHealthColor().equals(HealthColor.ORANGE));
    }

    // test red
    @Test
    public void testPlayerHealthIs24AndColorIsRed() {
        Player player = new Player();
        player.setHP(24);
        assertTrue(player.getHealthColor().equals(HealthColor.RED));
    }

    @Test
    public void testPlayerIsNot24AndColorIsNotRed() {
        Player player = new Player();
        player.setHP(25);
        assertFalse(player.getHealthColor().equals(HealthColor.RED));
    }

    @Test
    public void testPlayerIs1AndColorIsRed() {
        Player player = new Player();
        player.setHP(1);
        assertTrue(player.getHealthColor().equals(HealthColor.RED));
    }

    @Test
    public void testPlayerIs0AndColorIsRed() {
        Player player = new Player();
        player.setHP(0);
        assertTrue(player.getHealthColor().equals(HealthColor.RED));
    }
    /**
     * End of White-box testing
     */
}