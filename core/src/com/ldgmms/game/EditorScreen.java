package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * Map editor screen.
 * Copied and then reworked from MainMenuScreen.
 */
public class EditorScreen implements Screen {
    private final OrthographicCamera camera;
    private final TBDGame game;
    private final Player player;

    /**
     * Display the map editor menu.
     * @param delta (Unused)
     */
    @Override
    public void render(float delta) {
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Map Editor Screen", width * 0.33f, height * 0.8f);
        game.font.draw(game.batch, "Press S to edit a square map.", width * 0.33f, height * 0.6f);
        game.font.draw(game.batch, "Press H to edit a hexagonal map.", width * 0.33f, height * 0.4f);
        game.font.draw(game.batch, "Press Q to return to the menu.", width * 0.33f, height * 0.2f);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.setScreen(new SquareScreen(game, player)); // TODO: replace with square map editor
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            game.setScreen(new HexScreen(game, player)); // TODO: replace with hexagonal map editor
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            game.setScreen(new MainMenuScreen(game, player));
            dispose();
        }
    }

    /**
		 * Update window size.
     * @param width New window width
     * @param height New window heigh
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    /** @see ApplicationListener#show */
    @Override
    public void show() { }
    /** @see ApplicationListener#pause */
    @Override
    public void pause() { }
    /** @see ApplicationListener#resume */
    @Override
    public void resume() { }
		/** @see ApplicationListener#hide */
    @Override
    public void hide() { }
		/** @see ApplicationListener#dispose */
    @Override
    public void dispose() { }

    /**
     * Create a new map editor menu.
     * @param game The current game state
     * @param player (Currently unused, just passed to other methods?)
     */
    public EditorScreen(TBDGame game, Player player) {
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }
}

