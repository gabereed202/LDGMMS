package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SquareScreen implements Screen {
    private final TBDGame game;
    private final Player player;
    private final OrthographicCamera camera;
    private final FitViewport viewport; // used for scaling the game as the window dynamically resizes
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private int width, height;

    /**
     * Constructor for a new SquareScreen to represent a map with a square-based grid.
     * -Sean
     * @param game represents the game state
     * @param player main Player passed to the screen
     */
    public SquareScreen(TBDGame game, Player player, int width, int height) {
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768);
        viewport = new FitViewport(1024, 768, camera);
        map = new TmxMapLoader().load("map_squareMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

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
        renderer.setView(camera);

        Gdx.input.setInputProcessor(player);
    }

    /**
     * Called when the screen should render itself.
     * -Sean
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        renderer.render();

        // make sure player stays within screen bounds
        // TODO: find a better way to handle this inside the Player class without magic numbers
        if (player.getX() < 0)
            player.setX(0);
        if (player.getX() > 1024 - 32)
            player.setX(1024 - 32);
        if (player.getY() < 0)
            player.setY(0);
        if (player.getY() > 768 - 32)
            player.setY(768 - 32);

        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        // when you switch to the hexMap,
        // grab the squareMap coordinates first so the player remembers where it was.
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            player.setSquareX(player.getX());
            player.setSquareY(player.getY());
            game.setScreen(new HexScreen(game, player, width, height));
            dispose();
        }
    }

    /**
     * Called whenever you resize the window.
     * @param width -
     * @param height -
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;

        viewport.update(width, height);
        camera.position.x = 512; // 1024 / 2
        camera.position.y = 384; // 768 / 2
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
        map.dispose();
        renderer.dispose();
    }
}
