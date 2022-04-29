package com.ldgmms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * Main menu for the game.
 * <br/>
 * Allows the user to start a game, or go to the editor.
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
    private final Player player;
    // Window and Screen graphics
    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final Stage stage;
    // UI
    private final TextButton.TextButtonStyle buttonStyle, buttonSelectedStyle;
    private final TextButton btn_square, btn_hex, btn_editor;
    private final InputListener keyNavListener;

    /*
     * Instance variables
     */
    // Window and Screen graphics
    private int width, height;

    /**
     * Navigate to a different screen and dispose of this one.
     * @param newScreen destination
     * @author Matthew Rease
     */
    private void navigate(Screen newScreen) {
        game.setScreen(newScreen);
        dispose();
    }

    /**
     * Factory to provide a ClickListener for a TextButton that navigates to a screen when clicked.
     * @param button The button this listener will be attached to (for changing the text color).
     * @param destination Screen (class) to go to when clicked.
     * @return Customized ClickListener for <code>button</code>
     * @author Matthew Rease
     */
    private ClickListener textButtonListener(TextButton button, Class<? extends Screen> destination) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Screen screen = null;
                try {
                    screen = destination.getConstructor(new Class[]{ TBDGame.class, Player.class, int.class, int.class }).newInstance(game, player, width, height);
                }
                catch (Exception e) {
                    System.out.println("Class given to EditorScreen#textButtonListener does not contain Constructor(TBDGame, Player, int ,int)!");
                    e.printStackTrace();
                }
                if (screen != null)
                    navigate(screen);
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                button.setStyle(buttonSelectedStyle);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                button.setStyle(buttonStyle);
            }
        };
    }

    /**
     * Create a new main menu screen
     * @param game The current game state
     * @param player main Player passed around to each screen
     * @author Matthew Rease
     */
    public MainMenuScreen(TBDGame game, Player player, int width, int height) {
        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        this.player = player;
        // Window and Screen graphics
        camera = new OrthographicCamera(width, height);
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.batch);
        // UI
        buttonStyle = new TextButton.TextButtonStyle();
        buttonSelectedStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonSelectedStyle.font = game.font;
        buttonStyle.fontColor = Color.SCARLET;
        buttonSelectedStyle.fontColor = Color.BLUE;
        btn_square = new TextButton("Play on the Square Map", buttonStyle);
        btn_hex = new TextButton("Play on the Hexagonal Map", buttonStyle);
        btn_editor = new TextButton("Open the Map Editor", buttonStyle); // TODO: set graphic?
        keyNavListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.E:
                        navigate(new EditorScreen(game, player, width, height));
                        break;
                    case Input.Keys.H:
                        navigate(new HexScreen(game, player, width, height));
                        break;
                    case Input.Keys.S:
                        navigate(new SquareScreen(game, player, width, height));
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

        /*
         * Set instance variables
         */
        // Graphics/UI
        this.width = width;
        this.height = height;
    }

    /**
     * Setup this screen when it becomes the current {@link Screen} for a {@link Game}.
     * @author Matthew Rease
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(keyNavListener);

        /*
         * Setup UI Element Listeners
         */
        btn_square.addListener(textButtonListener(btn_square, SquareScreen.class)); // Square map button
        btn_hex.addListener(textButtonListener(btn_hex, HexScreen.class)); // Hexagonal map button
        btn_editor.addListener(textButtonListener(btn_editor, EditorScreen.class)); // Map editor button
    }

    /**
     * Display the main menu.
     * @param delta The time in seconds since the last render.
     * @see Screen#render(float)
     * @author (original?)
     * @author Matthew Rease
     */
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        Batch batch = game.batch;
        batch.setProjectionMatrix(camera.combined);

        // Rendering tasks
        batch.begin();
        game.font.draw(batch, "Main Menu Screen", width * 0.33f, height * 0.8f);
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
     * @author Matthew Rease
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
     * @see MainMenuScreen#navigate
     * @see Screen#hide
     * @author Matthew Rease
     */
    public void hide() {
        /*
         * Remove UI Element Listeners
         */
        btn_editor.clearListeners();
        btn_hex.clearListeners();
        btn_square.clearListeners();

        stage.removeListener(keyNavListener);
    }

    /**
     * Free resources when this screen is no longer needed.
     * @see Screen#dispose
     * @author Matthew Rease
     */
    public void dispose() {
        stage.dispose();
    }
}