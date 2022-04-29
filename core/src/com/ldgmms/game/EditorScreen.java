package com.ldgmms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ldgmms.game.ui.ResponsiveTextButton;

/**
 * Map editor menu screen.
 * <br/>
 * Copied and then reworked from MainMenuScreen.
 * @author Matthew Rease
 */
public class EditorScreen implements Screen {
    /*
     * Instance constants
     */
    // Game data
    private final TBDGame game;
    // Window and Screen graphics
    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final Stage stage;
    // UI
    private final ResponsiveTextButton btn_quit, btn_square, btn_hex;
    private final InputListener keyNavListener;

    /*
     * Instance variables
     */
    // Window and Screen graphics
    private int width, height;

    /**
     * Free resources when this screen is no longer needed.
     * @see Screen#dispose
     */
    public void dispose() {
        stage.dispose();
    }

    /**
     * Called when this is no longer the screen for a game. (For example, after navigating to {@link SquareEditor} or
     * {@link HexEditor}.)
     * @see Screen#hide
     */
    public void hide() {
        /*
         * Remove UI Element Listeners
         */
        btn_hex.removeListener();
        btn_square.removeListener();
        btn_quit.removeListener();

        stage.removeListener(keyNavListener);
    }

    /**
     * Runs when the game is "paused". Which seems to only happen when the window is minimized, and not just from losing
     * focus. It also runs when the program is closed.
     * @see Screen#pause
     */
    public void pause() { }

    /**
     * Display the map editor menu.
     * @param delta The time in seconds since the last render.
     * @see Screen#render(float)
     */
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // TODO: Consider switching to scene2d.ui Label so we don't need to use the batch system, just the stage
        /*
         * Render tasks
         */
        // Render sprites/fonts
        Batch batch = game.batch;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        game.font.draw(batch, "Map Editor Screen", width * 0.33f, height * 0.8f);
        batch.end();
        // Render UI (actors)
        stage.act(delta);
        stage.draw();
    }

    /**
     * Update window size and UI.
     * @param width New window width
     * @param height New window height
     * @see Screen#resize(int, int)
     */
    public void resize(int width, int height) {
        // Update local variables
        this.width = width;
        this.height = height;

        // Update things for rendering
        viewport.update(width, height);
        camera.position.x = width / 2.0f;
        camera.position.y = height / 2.0f;
        camera.update();

        // Reposition UI elements
        btn_quit.setPosition(0.0f, height - btn_quit.getHeight());
        btn_square.setPosition(width * 0.33f, height * 0.6f);
        btn_hex.setPosition(width * 0.33f, height * 0.4f);
    }

    /**
     * Run when game is "unpaused".
     * @see Screen#resume
     * @see EditorScreen#pause
     */
    public void resume() { }

    /**
     * Setup this screen when it becomes the current Screen for a game.
     * @see com.badlogic.gdx.Game
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(keyNavListener);

        /*
         * Setup UI Element Listeners
         */
        btn_quit.addListener(); // Return to Main Menu button
        btn_square.addListener(); // Square map editor button
        btn_hex.addListener(); // Hexagonal map editor button
    }

    /**
     * Create a new map editor menu.
     * @param game The current game state
     * @param callingScreen The screen this was called from, to be returned to when done
     */
    public EditorScreen(TBDGame game, Screen callingScreen) {
        Screen us = this;

        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        // Window and Screen graphics
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.batch);
        // UI
        btn_quit = new ResponsiveTextButton("Main Menu", game.font) {
            @Override
            public void onClick() {
                game.setScreen(callingScreen);
                dispose();
            }
        }; // TODO: set graphic?
        btn_square = new ResponsiveTextButton("Edit a Square Map", game.font) {
            @Override
            public void onClick() {
                game.setScreen(new SquareEditor(game, us));
            }
        };
        btn_hex = new ResponsiveTextButton("Edit a Hexagonal Map", game.font) {
            @Override
            public void onClick() {
                game.setScreen(new HexEditor(game, us));
            }
        };
        keyNavListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.H:
                        game.setScreen(new HexEditor(game, us));
                        break;
                    case Input.Keys.S:
                        game.setScreen(new SquareEditor(game, us));
                        break;
                    case Input.Keys.Q:
                        game.setScreen(callingScreen);
                        dispose();
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
        // Add UI to stage
        stage.addActor(btn_quit);
        stage.addActor(btn_square);
        stage.addActor(btn_hex);
    }
}

