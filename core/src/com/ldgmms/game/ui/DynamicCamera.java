package com.ldgmms.game.ui;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class DynamicCamera extends OrthographicCamera {
    public int velocityX = 0, velocityY = 0;

    public void accelerate(int fx, int fy) {
        velocityX += fx;
        velocityY += fy;
    }

    public void move(float delta) {
        position.add(velocityX * delta * zoom, velocityY * delta * zoom, 0.0f);
        update();
    }

    public void update(int width, int height) {
        Vector3 pos = position.cpy();
        setToOrtho(false, width, height);
        position.set(pos);
    }

    public DynamicCamera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
        // Center camera
        position.x = viewportWidth / 2.0f;
        position.y = viewportHeight / 2.0f;
    }
}
