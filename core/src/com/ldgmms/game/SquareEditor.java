package com.ldgmms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.ldgmms.game.ui.ResponsiveTextButton;

// TODO: Attempt to figure out com.badlogic.gdx.scenes.scene2d.ui.List for context menu.

/**
 * Map editor for square maps.
 * @author Matthew Rease
 */
public class SquareEditor implements Screen {
    /*
     * Class constants
     */
    private static final float camera_movement_speed = 60.0f;

    /*
     * Instance constants
     */
    // Game data
    private final TBDGame game;
    private final TiledMap map;
    // Window and Screen graphics
    private final OrthographicCamera ui_camera;
    private final OrthographicCamera game_camera;
    private final ScreenViewport ui_viewport;
    private final FitViewport game_viewport;
    private final Stage stage;
    private final OrthogonalTiledMapRenderer renderer;
    // UI
    private final ResponsiveTextButton btn_quit, btn_ctxmenu_1, btn_ctxmenu_2;
    private final ClickListener rmbListener;
    private final InputListener keyListener;
    private final DragListener cameraListener;

    /*
     * Instance variables
     */
    // Window and Screen graphics
    private int width, height;
    private float game_camera_dx; // Camera speed, horizontal
    private float game_camera_dy; // Camera speed, vertical
    // UI
    private boolean ctxmenu_visible;

    /**
     * Navigate to a different screen and dispose of this one.
     * @param newScreen destination
     */
    private void navigate(Screen newScreen) {
        game.setScreen(newScreen);
        dispose();
    }

    /**
     * Toggle visibility of drop-down context menu.
     */
    // TODO: Find longest button (string) and if menu will go offscreen, adjust accordingly.
    private void toggleCtxMenu() {
        if (ctxmenu_visible) {
            btn_ctxmenu_1.remove();
            btn_ctxmenu_2.remove();
        }
        else {
            btn_ctxmenu_1.setPosition(Gdx.input.getX(), height - Gdx.input.getY() - btn_ctxmenu_1.getHeight()); // Move to mouse cursor
            btn_ctxmenu_2.setPosition(btn_ctxmenu_1.getX(), btn_ctxmenu_1.getY() - btn_ctxmenu_2.getHeight()); // Move to below first button
            stage.addActor(btn_ctxmenu_1);
            stage.addActor(btn_ctxmenu_2);
        }
        ctxmenu_visible = !ctxmenu_visible;
    }

    /**
     * Free resources when this screen is no longer needed.
     * @see Screen#dispose
     */
    public void dispose() {
        stage.dispose();

        renderer.dispose();
        map.dispose();
    }

    /**
     * Called when this is no longer the current screen for a {@link Game}.
     * @see SquareEditor#navigate
     * @see Screen#hide
     */
    public void hide() {
        if (ctxmenu_visible)
            toggleCtxMenu();

        /*
         * Remove UI Element Listeners
         */
        btn_ctxmenu_2.removeListener();
        btn_ctxmenu_1.removeListener();
        btn_quit.removeListener();

        stage.removeListener(cameraListener);
        stage.removeListener(keyListener);
        stage.removeListener(rmbListener);
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
        // Move and update camera
        game_camera.position.x += game_camera_dx * delta * game_camera.zoom;
        game_camera.position.y += game_camera_dy * delta * game_camera.zoom;
        game_camera.update();

        ScreenUtils.clear(0, 0, 0, 1); // Set screen black
        Batch batch = renderer.getBatch();
        batch.setProjectionMatrix(game_camera.combined); // Specify coordinate system?

        // Render tasks
        renderer.render();
        batch.begin();
        game.font.draw(batch, "TEST", 50, 50);
        batch.end();
        // Render UI (actors)
        //batch.setProjectionMatrix(ui_camera.combined);
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

        // Update UI
        ui_viewport.update(width, height);
        ui_camera.position.x = width / 2.0f;
        ui_camera.position.y = height / 2.0f;
        ui_camera.update(); // Update matrices

        // Update game display while maintaining its position relative to the window
        Vector3 pos = game_camera.position.cpy();
        game_camera.setToOrtho(false, width, height);
        game_camera.position.set(pos);

        // Update UI elements
        btn_quit.setPosition(0.0f, height - btn_quit.getHeight());
    }

    /**
     * Run when game is "unpaused".
     * @see Screen#resume
     * @see SquareEditor#pause
     */
    public void resume() { }

    /**
     * Setup this screen when it becomes the current {@link Screen} for a {@link Game}.
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(rmbListener);
        stage.addListener(keyListener);
        stage.addListener(cameraListener);

        //btn_quit.setPosition(0.0f, 768.0f);
        btn_quit.addListener();
        btn_ctxmenu_1.addListener();
        btn_ctxmenu_2.addListener();
    }

    /**
     * Initialize new map editor.
     * @param game The current game state
     * @param player (Currently unused, just passed to other methods?)
     * @param width Current width of window
     * @param height Current height of window
     */
    public SquareEditor(TBDGame game, Player player, int width, int height) {
        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        map = new TmxMapLoader().load("map_squareMap.tmx"); // TODO: should not load a map, but create a fresh one!
        // Window and Screen graphics
        ui_camera = new OrthographicCamera(width, height);
        ui_viewport = new ScreenViewport(ui_camera);
        game_camera = new OrthographicCamera(2048, 1536);
        game_viewport = new FitViewport(2048, 1536, game_camera);
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(game_camera);
        stage = new Stage(ui_viewport, game.batch); // TODO: ???
        // UI
        TextButtonStyle buttonStyle = new TextButtonStyle();
        buttonStyle.font = game.font;
        btn_quit = new ResponsiveTextButton("Main Menu", new TextButtonStyle(buttonStyle)) {
            @Override
            public void onClick() {
                navigate(new MainMenuScreen(game, player, width, height));
            }
        }; // TODO: set graphic?
        btn_ctxmenu_1 = new ResponsiveTextButton("Thing 1", new TextButtonStyle(buttonStyle)) {
            @Override
            public void onClick() {
                System.out.println("Clicked menu button 1.");
            }
        };
        btn_ctxmenu_2 = new ResponsiveTextButton("Thing 2", new TextButtonStyle(buttonStyle)) {
            @Override
            public void onClick() {
                System.out.println("Clicked menu button 2.");
            }
        };
        rmbListener = new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleCtxMenu();
            }
        };
        keyListener = new InputListener() {
            @Override
            public boolean scrolled(InputEvent event, float x, float y, float amountX, float amountY) {
                if (amountY == 0)
                    return false;
                float old = game_camera.zoom;
                if (amountY > 0)
                    game_camera.zoom *= amountY * 1.5;
                else
                    game_camera.zoom /= amountY * -1.5;
                // Failsafe so we don't try dividing by zero... (would only happen when dividing the zoom value to a point beyond what a float's precision allows)
                if (game_camera.zoom == 0)
                    game_camera.zoom = old;
                return true;
            }
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        game_camera_dy -= camera_movement_speed;
                        break;
                    case Input.Keys.DOWN:
                        game_camera_dy += camera_movement_speed;
                        break;
                    case Input.Keys.LEFT:
                        game_camera_dx += camera_movement_speed;
                        break;
                    case Input.Keys.RIGHT:
                        game_camera_dx -= camera_movement_speed;
                        break;
                    case Input.Keys.M:
                        navigate(new MainMenuScreen(game, player, width, height));
                        break;
                    default:
                        return false;
                }
                return true;
            }
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        game_camera_dy += camera_movement_speed;
                        break;
                    case Input.Keys.DOWN:
                        game_camera_dy -= camera_movement_speed;
                        break;
                    case Input.Keys.LEFT:
                        game_camera_dx -= camera_movement_speed;
                        break;
                    case Input.Keys.RIGHT:
                        game_camera_dx += camera_movement_speed;
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
        cameraListener = new DragListener() {
            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                System.out.println("RMB 1 dragged " + x + "," + y);
            }
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button != Input.Buttons.RIGHT)
                    return false;
                setDragStartX(x);
                setDragStartY(y);
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                float dx = x - getDragStartX(), dy = y - getDragStartY();
                game_camera.translate(-dx * game_camera.zoom, -dy * game_camera.zoom, 0.0f);
                setDragStartX(x);
                setDragStartY(y);
                if (ctxmenu_visible)
                    toggleCtxMenu();
            }
        };
        // Add UI to stage
        stage.addActor(btn_quit);

        /*
         * Set instance variables
         */
        // Window and Screen graphics
        this.width = width;
        this.height = height;
        game_camera_dx = 0.0f;
        game_camera_dy = 0.0f;
        // UI
        ctxmenu_visible = false;
    }
}
