package com.ldgmms.game;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
}