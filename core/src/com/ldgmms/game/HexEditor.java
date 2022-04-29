package com.ldgmms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ldgmms.game.ui.DynamicCamera;
import com.ldgmms.game.ui.EditorUI;
import com.ldgmms.game.ui.ResponsiveTextButton;

/**
 * Map editor for hex maps.
 * @author Matthew Rease
 */
public class HexEditor implements Screen {
    /*
     * Class constants
     */
    // Game data
    private static final int DEFAULT_MAP_WIDTH = 528;
    private static final int DEFAULT_MAP_HEIGHT = 392;

    /*
     * Instance constants
     */
    // Game data
    private final TBDGame game;
    private final TiledMap map;
    // Window and Screen graphics
    private final DynamicCamera game_camera;
    private final HexagonalTiledMapRenderer renderer;
    private final Batch batch;
    // UI
    private final EditorUI ui;
    private final Stage stage;
    private final ResponsiveTextButton btn_ctxmenu_1, btn_ctxmenu_2;
    private final ClickListener rmbListener;
    private final InputListener keyListener;
    private final DragListener cameraListener;

    /*
     * Instance variables
     */
    // Window and Screen graphics
    private int width, height;
    // UI
    private boolean ctxmenu_visible;

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
        ui.dispose();

        renderer.dispose();
        map.dispose();
    }

    /**
     * Called when this is no longer the current screen for a {@link Game}.
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

        stage.removeListener(cameraListener);
        stage.removeListener(keyListener);
        stage.removeListener(rmbListener);

        ui.hide();
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
        game_camera.move(delta);

        ScreenUtils.clear(0, 0, 0.2f, 1); // Set screen dark blue

        /*
         * Game render tasks
         */
        // Render map
        renderer.render();
        // Draw sprites
        batch.setProjectionMatrix(game_camera.combined); // Specify coordinate system?
        batch.begin();
        game.font.draw(batch, "TEST", 50, 50);
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


        ui.update(width, height); // Update UI
        game_camera.update(width, height); // Update game display while maintaining its position relative to the window

        // Update UI elements
        if (ctxmenu_visible)
            toggleCtxMenu();
    }

    /**
     * Run when game is "unpaused".
     * @see Screen#resume
     * @see HexEditor#pause
     */
    public void resume() { }

    /**
     * Setup this screen when it becomes the current {@link Screen} for a {@link Game}.
     */
    public void show() {
        ui.show();

        stage.addListener(rmbListener);
        stage.addListener(keyListener);
        stage.addListener(cameraListener);

        btn_ctxmenu_1.addListener();
        btn_ctxmenu_2.addListener();
    }

    /**
     * Initialize new map editor.
     * @param game The current game state
     * @param callingScreen The screen this was called from, to be returned to when done
     */
    public HexEditor(TBDGame game, Screen callingScreen) {
        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        map = new TmxMapLoader().load("map_hexMap.tmx"); // TODO: should not load a map, but create a fresh one!
        // Window and Screen graphics
        game_camera = new DynamicCamera(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
        renderer = new HexagonalTiledMapRenderer(map);
        renderer.setView(game_camera);
        batch = renderer.getBatch();
        // UI
        ui = new EditorUI(game_camera, game.font, game, callingScreen);
        stage = ui.getStage();
        btn_ctxmenu_1 = new ResponsiveTextButton("Thing 1", game.font) {
            @Override
            public void onClick() {
                System.out.println("Clicked menu button 1.");
            }
        };
        btn_ctxmenu_2 = new ResponsiveTextButton("Thing 2", game.font) {
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
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.Q) {
                    game.setScreen(callingScreen);
                    dispose();
                    return true;
                }
                return false;
            }
        };
        cameraListener = new DragListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (ctxmenu_visible)
                    toggleCtxMenu();
            }
        };

        /*
         * Set instance variables
         */
        // UI
        ctxmenu_visible = false;
    }
}