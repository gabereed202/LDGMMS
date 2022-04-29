package com.ldgmms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ldgmms.game.ui.ResponsiveTextButton;


/**
 * Main menu for the game.
 * <br/>
 * Allows the user to start a game, or go to the editor.
 * @author Sean
 * @author Matthew Rease
 */
/*
 * TODO: Make a full-featured main menu screen.
 *  As well as being the first screen you see, the user should be able to access the main menu again via the pause menu
 *  after saving (or not saving) the game state.
 */
public class MainMenuScreen implements Screen {
    /*
     * Instance constants
     */
    // Game data
    private final TBDGame game;
    // UI
    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final Stage stage;
    private final ResponsiveTextButton btn_square, btn_hex, btn_editor;
    private final InputListener keyNavListener;

    /*
     * Instance variables
     */
    // UI
    private int width, height;

    /**
     * Create a new main menu screen
     * @param game The current game state
     * @param player main Player passed around to each screen
     */
    public MainMenuScreen(TBDGame game, Player player) {
        Screen thisScreen = this;

        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        // UI
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.batch);
        btn_square = new ResponsiveTextButton("Play on the Square Map", game.font) {
            @Override
            public void onClick() {
                game.setScreen(new SquareScreen(game, player));
            }
        };
        btn_hex = new ResponsiveTextButton("Play on the Hexagonal Map", game.font) {
            @Override
            public void onClick() {
                game.setScreen(new HexScreen(game, player));
            }
        };
        btn_editor = new ResponsiveTextButton("Open the Map Editor", game.font) {
            @Override
            public void onClick() {
                game.setScreen(new EditorScreen(game, thisScreen));
            }
        }; // TODO: set graphic?
        keyNavListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.E:
                        game.setScreen(new EditorScreen(game, thisScreen));
                        break;
                    case Input.Keys.H:
                        game.setScreen(new HexScreen(game, player));
                        break;
                    case Input.Keys.S:
                        game.setScreen(new SquareScreen(game, player));
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
        // Add UI to stage
        stage.addActor(btn_square);
        stage.addActor(btn_hex);
        stage.addActor(btn_editor);
    }

    /**
     * Setup this screen when it becomes the current {@link Screen} for a {@link Game}.
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(keyNavListener);

        /*
         * Setup UI Element Listeners
         */
        btn_square.addListener(); // Square map button
        btn_hex.addListener(); // Hexagonal map button
        btn_editor.addListener(); // Map editor button
    }

    /**
     * Display the main menu.
     * @param delta The time in seconds since the last render.
     * @see Screen#render(float)
     */
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        /*
         * Window render tasks
         */
        // Draw sprites/fonts
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Main Menu Screen", width * 0.33f, height * 0.8f);
        game.batch.end();
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
        btn_square.setPosition(width * 0.33f, height * 0.6f);
        btn_hex.setPosition(width * 0.33f, height * 0.4f);
        btn_editor.setPosition(width * 0.33f, height * 0.2f);
    }

    /**
     * Runs when the game is "paused".
     * Which seems to only happen when the window is minimized, and not just from losing focus.
     * It also runs when the program is closed.
     * @see Screen#pause
     */
    public void pause() { }

    /**
     * Run when game is "unpaused".
     * @see Screen#resume
     * @see EditorScreen#pause
     */
    public void resume() { }

    /**
     * Called when this is no longer the current screen for a {@link Game}.
     * @see Screen#hide
     */
    public void hide() {
        /*
         * Remove UI Element Listeners
         */
        btn_editor.removeListener();
        btn_hex.removeListener();
        btn_square.removeListener();

        stage.removeListener(keyNavListener);
    }

    /**
     * Free resources when this screen is no longer needed.
     * @see Screen#dispose
     */
    public void dispose() {
        stage.dispose();
    }
}