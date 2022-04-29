package com.ldgmms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.ldgmms.game.ui.DynamicCamera;
import com.ldgmms.game.ui.EditorUI;
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
    // Game data
    private static final int DEFAULT_MAP_WIDTH = 1024;
    private static final int DEFAULT_MAP_HEIGHT = 768;

    /*
     * Instance constants
     */
    // Game data
    private final TBDGame game;
    private final TiledMap map;
    // Window and Screen graphics
    private final DynamicCamera game_camera;
    private final OrthogonalTiledMapRenderer renderer;
    private final Batch batch;
    // UI
    private final EditorUI ui;

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
        ui.dispose();

        renderer.dispose();
        map.dispose();
    }

    /**
     * Called when this is no longer the current screen for a {@link Game}.
     * @see Screen#hide
     */
    public void hide() {
        // Remove UI Element Listeners
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
        // Move camera
        game_camera.move(delta);

        ScreenUtils.clear(0, 0, 0, 1); // Set screen black

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
        ui.render(delta);
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
        // Add UI Element Listeners
        ui.show();
    }

    /**
     * Initialize new map editor.
     * @param game The current game state
     * @param callingScreen The screen this was called from, to be returned to when done
     */
    public SquareEditor(TBDGame game, Screen callingScreen) {
        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        map = new TmxMapLoader().load("map_squareMap.tmx"); // TODO: should not load a map, but create a fresh one!
        // Window and Screen graphics
        game_camera = new DynamicCamera(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(game_camera);
        batch = renderer.getBatch();
        // UI
        ui = new EditorUI(
            game_camera,
            game.font,
            game,
            callingScreen,
            new ResponsiveTextButton("Thing 1", game.font) {
                @Override
                public void onClick() {
                    System.out.println("Clicked square menu button 1.");
                }
            },
            new ResponsiveTextButton("Thing 2", game.font) {
                @Override
                public void onClick() {
                    System.out.println("Clicked square menu button 2.");
                }
            }
        );
    }
}