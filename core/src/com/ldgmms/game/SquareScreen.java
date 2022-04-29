package com.ldgmms.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * Game screen for square maps.
 * @author Sean
 * @author Matthew Rease
 */
public class SquareScreen implements Screen {
    /*
     * Class constants
     */
    // Game Data
    private static final String default_map = "map_squareMap.tmx";
    private static final int default_map_width = 1024;
    private static final int default_map_height = 768;
    private static final int default_map_tile_length = 32;

    /*
     * Instance constants
     */
    // Game data
    private final TBDGame game;
    private final Player player;
    private final TiledMap map;
    // Window and Screen graphics
    private final OrthographicCamera game_camera;
    private final OrthogonalTiledMapRenderer renderer;
    private final Batch batch;
    // UI
    private final OrthographicCamera ui_camera;
    private final ScreenViewport ui_viewport; // Allows the game graphics/camera to be moved around separate from the actual window
    private final Stage stage;
    private final InputListener keyListener;
    private final DragListener cameraListener;

    /*
     * Instance variables
     */
    // Window and Screen graphics
    private int width, height;

    /**
     * Initialize new {@link Screen} to represent a map with a square-based grid.
     * @param game The current game state
     * @param player Main player of the game
     * @param width Current width of window
     * @param height Current height of window
     */
    public SquareScreen(TBDGame game, Player player, int width, int height) {
        /*
         * Set instance constants
         */
        // Game data
        this.game = game;
        this.player = player;
        map = new TmxMapLoader().load(default_map);
        // Window and Screen graphics
        game_camera = new OrthographicCamera(default_map_width, default_map_height);
        game_camera.position.x = default_map_width / 2.0f; // Center camera
        game_camera.position.y = default_map_height / 2.0f;
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(game_camera);
        batch = renderer.getBatch();
        // UI
        ui_camera = new OrthographicCamera(width, height);
        ui_viewport = new ScreenViewport(ui_camera);
        stage = new Stage(ui_viewport, game.batch);
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
                    // when you switch to the hexMap,
                    // grab the squareMap coordinates first so the player remembers where it was.
                    case Input.Keys.H:
                        player.setSquareX(player.getX());
                        player.setSquareY(player.getY());
                        game.setScreen(new HexScreen(game, player, width, height));
                        dispose();
                        return true;
                    default:
                        boolean res = player.keyDown(keycode);
                        // TODO: find a better way to handle this inside the Player class without magic numbers
                        //  Note from Matthew: while I'm not sure how we would get the bounds of the map, we could at
                        //  least pass them to the player and have the movement code in the player run the checks
                        // make sure player stays within screen bounds - x
                        float x = player.getX();
                        if (x < 0)
                            player.setX(0);
                        else if (x > default_map_width - default_map_tile_length)
                            player.setX(default_map_width - default_map_tile_length);
                        // "" - y
                        float y = player.getY();
                        if (y < 0)
                            player.setY(0);
                        else if (y > default_map_height - default_map_tile_length)
                            player.setY(default_map_height - default_map_tile_length);
                        return res;
                }
            }
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return player.keyUp(keycode);
            }
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return player.keyTyped(character);
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
            }
        };

        /*
         * Set instance variables
         */
        // Window and Screen graphics
        this.width = width;
        this.height = height;

        // Update player with collision data for the map
        player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(player.getSquareX(), player.getSquareY());
    }

    /**
     * Setup this screen when it becomes the current {@link Screen} for a {@link Game}.
     */
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(cameraListener);
        stage.addListener(keyListener);
        // Can't use player as InputProcessor unless drag events are added to it, and it is made aware of the game's camera
        //Gdx.input.setInputProcessor(player);
    }

    /**
     * Display the map, sprites, and UI.
     * @param delta The time in seconds since the last render.
     * @see Screen#render(float)
     */
    public void render(float delta) {
        game_camera.update(); // Update camera

        ScreenUtils.clear(0, 0, 0.2f, 1); // Dark blue background

        /*
         * Game render tasks
         */
        // Render map
        renderer.render();
        // Draw sprites
        batch.setProjectionMatrix(game_camera.combined); // Specify coordinate system?
        batch.begin();
        player.draw(batch);
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

        // Update UI
        ui_viewport.update(width, height);
        ui_camera.position.x = width / 2.0f;
        ui_camera.position.y = height / 2.0f;
        ui_camera.update(); // Update matrices

        // Update game display while maintaining its position relative to the window
        Vector3 pos = game_camera.position.cpy();
        game_camera.setToOrtho(false, width, height);
        game_camera.position.set(pos);
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
     * @see SquareScreen#pause
     */
    public void resume() { }

    /**
     * Called when this is no longer the current screen for a {@link Game}.
     * @see Screen#hide
     */
    public void hide() {
        stage.removeListener(cameraListener);
        stage.removeListener(keyListener);
    }

    /**
     * Free resources when this screen is no longer needed.
     * @see Screen#dispose
     */
    public void dispose() {
        stage.dispose();

        map.dispose();
        renderer.dispose();
    }
}
