package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;


/**
 * TODO: Make a full-featured main menu screen.
 * As well as being the first screen you see,
 * the user should be able to access the main menu again
 * via the pause menu after saving (or not saving) the game state.
 */

public class GameOverScreen implements Screen {
    private final TBDGame game;
    private final OrthographicCamera camera;

    /**
     * Constructor for the MainMenuScreen.
     * -Sean
     * @param game represents the game state
     */
    public GameOverScreen(TBDGame game) {
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
     * -Sean
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        int height = Gdx.graphics.getHeight();
        int width = Gdx.graphics.getWidth();
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.setColor(Color.RED);
        game.font.draw(game.batch, "YOU DIED.", width * 0.55f, height * 0.6f);
        game.font.draw(game.batch, "Press ENTER to go back to the main menu.", width * 0.40f, height * 0.33f);
        game.font.setColor(Color.WHITE); // set game font color back to white
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.player.setHP(100); // reset player HP when returning to main menu
            game.setScreen(new MainMenuScreen(game));
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