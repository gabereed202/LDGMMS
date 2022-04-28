package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SquareScreen implements Screen {
    private final TBDGame game;
    private final Player player;
    private final OrthographicCamera ui_camera; // Matthew Rease
    private final OrthographicCamera game_camera;
    private final ScreenViewport ui_viewport; // Allows the game graphics/camera to be moved around separate from the actual window (Matthew Rease)
    private final FitViewport game_viewport; // used for scaling the game as the window dynamically resizes
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    // Matthew Rease
    private int width, height;
    private Stage stage;

    /**
     * Constructor for a new SquareScreen to represent a map with a square-based grid.
     * -Sean
     * @param game represents the game state
     * @param player main Player passed to the screen
     */
    public SquareScreen(TBDGame game, Player player, int width, int height) {
        this.game = game;
        this.player = player;
        ui_camera = new OrthographicCamera(width, height); // Matthew Rease
        ui_viewport = new ScreenViewport(ui_camera); // Matthew Rease
        game_camera = new OrthographicCamera(2048, 1536); // Matthew Rease
        game_viewport = new FitViewport(2048, 1536, game_camera);
        map = new TmxMapLoader().load("map_squareMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        // Set private fields
        this.width = width;
        this.height = height;

        // grab the collision layer for the current map
        player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(player.getSquareX(), player.getSquareY());
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     * -Sean
     */
    @Override
    public void show() {
        // Matthew Rease
        renderer.setView(game_camera);
        game_camera.translate(320, 240, 0.0f); // Hack to get the bottom left corner of both cameras to line up lol

        // Matthew Rease
        stage = new Stage(ui_viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
        stage.addListener(new DragListener() {
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
        });
        // Can't use player as InputProcessor unless drag events are added to it, and it is made aware of the game's camera
        //Gdx.input.setInputProcessor(player);
        stage.addListener(new InputListener() {
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
                        // make sure player stays within screen bounds - x
                        float x = player.getX();
                        if (x < 0)
                            player.setX(0);
                        else if (x > 1024 - 32)
                            player.setX(1024 - 32);
                        // "" - y
                        float y = player.getY();
                        if (y < 0)
                            player.setY(0);
                        else if (y > 768 - 32)
                            player.setY(768 - 32);
                        return res;
                }
                //return false;
            }
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return player.keyUp(keycode);
            }
            @Override
            public boolean keyTyped(InputEvent event, char character) {
                return player.keyTyped(character);
            }
        });
    }

    /**
     * Called when the screen should render itself.
     * -Sean
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // From here until user input by Matthew Rease
        game_camera.update();

        Batch batch = renderer.getBatch();

        // Rendering tasks
        batch.setProjectionMatrix(game_camera.combined); // Specify coordinate system?
        renderer.render();
        batch.begin();
        // Render game
        //game.font.draw(batch, "TEST", 50, 50);
        player.draw(batch);
        // Render UI
        batch.setProjectionMatrix(ui_camera.combined);
        stage.act(delta);
        stage.draw();
        batch.end();
    }

    /**
     * Called whenever you resize the window.
     * @param width -
     * @param height -
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        // Update local variables
        this.width = width;
        this.height = height;

        // Update UI - Matthew Rease
        ui_viewport.update(width, height);
        ui_camera.position.x = width / 2.0f;
        ui_camera.position.y = height / 2.0f;
        ui_camera.update(); // Update matrices

        // Update game display while maintaining its position relative to the window - Matthew Rease
        Vector3 pos = game_camera.position.cpy();
        game_camera.setToOrtho(false, width, height);
        game_camera.position.set(pos);
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
        stage.dispose(); // Matthew Rease

        map.dispose();
        renderer.dispose();
    }
}
