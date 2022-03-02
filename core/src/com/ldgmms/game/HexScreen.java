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
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HexScreen implements Screen {
    private final TBDGame game;
    private TiledMap map;
    private HexagonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private FitViewport viewport; // used for scaling the game as the window dynamically resizes

    private final Player player;

    /**
     * Constructor for a new HexScreen to represent a map with a hex-based grid.
     * -Sean
     * @param game represents the game state
     */
    public HexScreen(TBDGame game) {
        this.game = game;
        this.player = game.player;
        player.isScreenHex = true;
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     * -Sean
     */
    @Override
    public void show() {
        map = new TmxMapLoader().load("map_hexMap.tmx");
        renderer = new HexagonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 528, 392);
        viewport = new FitViewport(528, 392, camera);

        // grab the collision layer for the current map
        player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));

        // initialize player location on the current map
        player.setPosition(0 * player.getCollisionLayer().getTileWidth(),
                0 * player.getCollisionLayer().getTileHeight());

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
        renderer.setView(camera);
        renderer.render();

        // make sure the player stays within screen bounds
        // TODO: find a better way to do this inside the Player class
        if (player.getX() < 0)
            player.setX(0);
        if (player.getX() > viewport.getWorldWidth() - player.getWidth())
            player.setX(viewport.getWorldWidth() - player.getWidth());
        if (player.getY() < 0)
            player.setY(0);
        if (player.getY() > viewport.getWorldHeight() - player.getHeight())
            player.setY(viewport.getWorldHeight() - player.getHeight());

        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.setScreen(new SquareScreen(game));
            dispose();
        }
    }

    /**
     * @param width -
     * @param height -
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
     * - sean
     */
    @Override
    public void hide() {
        // reset player's hex location when leaving a hex map
        player.setHexX(1 * player.getWidth());
        player.setHexY(1 * player.getHeight());
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