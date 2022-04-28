package com.ldgmms.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SquareScreen implements Screen {
    private final TBDGame game;                     // reference to our game
    private TiledMap map;                           // this screen's map
    private OrthogonalTiledMapRenderer renderer;    // square renderer
    private OrthographicCamera camera;              // this screen's camera
    private FitViewport viewport;                   // enables smooth screen resizing
    private final Player player;                    // player reference

    private final Texture healthBar;
    private int healthBarHeight = 25;

    /**
     * Constructor for a new SquareScreen to represent a map with a square-based grid.
     * -Sean
     * @param game represents the game state
     */
    public SquareScreen(TBDGame game) {
        this.game = game;
        this.player = game.player;
        player.isScreenHex = false;
        this.healthBar = new Texture("blank_bar.png");
        //<<<Added by Daniel Fuchs
        checkHeroes();
        //>>>
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     * -Sean
     */
    @Override
    public void show() {
        map = new TmxMapLoader().load("map_squareMap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1024, 768);
        viewport = new FitViewport(1024, 768, camera);

        // grab the collision layer for the current map
        player.setCollisionLayer((TiledMapTileLayer) map.getLayers().get(0));
        player.setPosition(player.getSquareX(), player.getSquareY());

        Gdx.input.setInputProcessor(player);
    }

    /**
     * Called when the screen should render itself.
     * -Sean
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        assert(player != null);
        assert (camera != null);

        if (player.getHP() == 0) {
            game.setScreen(new GameOverScreen(game));
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);
        renderer.setView(camera);
        renderer.render();

        // make sure player stays within screen bounds
        // TODO: find a better way to handle this inside the Player class
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

        // draw UI elements over everything else
        renderer.getBatch().begin();
        if (player.getHealthColor() == HealthColor.GREEN) {
            renderer.getBatch().setColor(Color.GREEN);
        }
        if (player.getHealthColor() == HealthColor.YELLOW) {
            renderer.getBatch().setColor(Color.YELLOW);
        }
        if (player.getHealthColor() == HealthColor.ORANGE) {
            renderer.getBatch().setColor(Color.ORANGE);
        }
        if (player.getHealthColor() == HealthColor.RED) {
            renderer.getBatch().setColor(Color.RED);
        }

        // draw the players healthBar where width is proportional to player's current health
        renderer.getBatch().draw(healthBar, 0, viewport.getWorldHeight() - healthBarHeight,
                (0.25f * viewport.getWorldWidth() * (player.getHP()) / 100), healthBarHeight);
        // reset game batch tint for everything else
        renderer.getBatch().setColor(Color.WHITE);
        renderer.getBatch().end();



        // when you switch to the hexMap,
        // grab the squareMap coordinates first so the player remembers where it was.
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            player.setSquareX(player.getX());
            player.setSquareY(player.getY());
            game.setScreen(new HexScreen(game));
            dispose();
        }
    }

    /**
     * Called whenever you resize the window.
     * -Sean
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
    //<<<Added by Daniel Fuchs
    private void checkHeroes(){
        for(Hero h : game.liveHeroes){
            if (h.getHp() == 0){
                game.deadHeroes.add(h);
                game.liveHeroes.remove(h);
            }
        }
    }
    //>>>
}