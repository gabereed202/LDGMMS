package com.ldgmms.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

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
        assertNotNull(player);
    }
}