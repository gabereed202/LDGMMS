package com.ldgmms.game;

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
 * Map editor screen.
 * Copied and then reworked from MainMenuScreen.
 * @author Matthew Rease
 */
public class EditorScreen implements Screen {
    /*
     * Instance constants
     */
    // Game data
    private final TBDGame game;
    private final Player player; // Unused!
    // Window and Screen graphics
    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final Stage stage;
    // UI
    private final TextButton.TextButtonStyle buttonStyle, buttonSelectedStyle;
    private final TextButton btn_quit, btn_square, btn_hex;
    private final InputListener keyNavListener;

    /*
     * Instance variables
     */
    // Window and Screen graphics
    private int width, height;

    /**
     * Navigate to a different screen and dispose of this one.
     * @param newScreen destination
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
     * Free resources when this screen is no longer needed.
     * @see Screen#dispose
     */
    public void dispose() {
        stage.dispose();
    }

    /**
     * Called when this is no longer the screen for a game.
     * Likely unused with EditorScreen, since this is just a link to other screens.
     * TODO: Consider storing previous screen and implementing a back button instead of a main menu button.
     * @see EditorScreen#navigate
     * @see Screen#hide
     */
    public void hide() {
        /*
         * Remove UI Element Listeners
         */
        btn_hex.clearListeners();
        btn_square.clearListeners();
        btn_quit.clearListeners();

        stage.removeListener(keyNavListener);
    }

    /**
     * Runs when the game is "paused".
     * Which seems to only happen when the window is minimized, and not just from losing focus.
     * It also runs when the program is closed.
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
        Batch batch = game.batch;
        batch.setProjectionMatrix(camera.combined);

        // Render tasks
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
        btn_quit.addListener(textButtonListener(btn_quit, MainMenuScreen.class)); // Return to Main Menu button
        btn_square.addListener(textButtonListener(btn_square, SquareEditor.class)); // Square map editor button
        btn_hex.addListener(textButtonListener(btn_hex, HexEditor.class)); // Hexagonal map editor button
    }

    /**
     * Create a new map editor menu.
     * @param game The current game state
     * @param player (Currently unused, just passed to other methods?)
     */
    public EditorScreen(TBDGame game, Player player, int width, int height) {
        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        this.player = player;
        // Window and Screen graphics
        camera = new OrthographicCamera(800, 480);
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, game.batch);
        // UI
        buttonStyle = new TextButton.TextButtonStyle();
        buttonSelectedStyle = new TextButton.TextButtonStyle();
        buttonStyle.font = buttonSelectedStyle.font = game.font;
        buttonStyle.fontColor = Color.SCARLET;
        buttonSelectedStyle.fontColor = Color.BLUE;
        btn_quit = new TextButton("Main Menu", buttonStyle); // TODO: set graphic?
        btn_square = new TextButton("Edit a Square Map", buttonStyle);
        btn_hex = new TextButton("Edit a Hexagonal Map", buttonStyle);
        keyNavListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.H:
                        navigate(new HexEditor(game, player, width, height));
                        break;
                    case Input.Keys.S:
                        navigate(new SquareEditor(game, player, width, height));
                        break;
                    case Input.Keys.Q:
                        navigate(new MainMenuScreen(game, player, width, height));
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

        /*
         * Set instance variables
         */
        // Graphics/UI
        this.width = width;
        this.height = height;
    }
}

