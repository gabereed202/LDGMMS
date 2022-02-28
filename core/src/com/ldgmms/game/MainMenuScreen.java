package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;


/**
 * TODO: Make a full-featured main menu screen.
 * As well as being the first screen you see,
 * the user should be able to access the main menu again
 * via the pause menu after saving (or not saving) the game state.
 */

public class MainMenuScreen implements Screen {
    private final TBDGame game;
    private final OrthographicCamera camera;

    /**
     * Constructor for the MainMenuScreen.
     * -Sean
     * @param game represents the game state
     */
    public MainMenuScreen(TBDGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        ScreenUtils.clear(0, 0, 0.2f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        game.font.draw(game.batch, "Main Menu Screen", width * 0.33f, height * 0.75f);
        game.font.draw(game.batch, "Press S to play on the square map.", width * 0.33f, height * 0.5f);
        game.font.draw(game.batch, "Press H to play on the hexagonal map.", width * 0.33f, height * 0.25f);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.setScreen(new SquareScreen(game));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            game.setScreen(new HexScreen(game));
            dispose();
        }
    }


    /**
     * @param width -
     * @param height -
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {

    }


    /**
     * @see ApplicationListener#resume()
     */

    @Override
    public void resume() {

    }


    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {

    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {

    }

}


