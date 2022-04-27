package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final TBDGame game;
    private final Player player;

    private int width, height;
    private Stage stage;
    private TextButton.TextButtonStyle style_quit, style_square, style_hex;
    private TextButton btn_quit, btn_square, btn_hex;

    private void navigate(Screen newScreen) {
        game.setScreen(newScreen);
        dispose();
    }

    /** @see ApplicationListener#dispose */
    @Override
    public void dispose() {
        stage.dispose();
    }

    /** @see ApplicationListener#hide */
    @Override
    public void hide() { }

    /** @see ApplicationListener#pause */
    @Override
    public void pause() { }

    /**
     * Display the map editor menu.
     * @param delta (Unused)
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        Batch batch = game.batch;

        // Render tasks
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        game.font.draw(batch, "Map Editor Screen", width * 0.33f, height * 0.8f);
        batch.end();
        stage.act(delta);
        stage.draw();

        // User input (Refactor)
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            navigate(new SquareEditor(game, player, width, height));
        if (Gdx.input.isKeyPressed(Input.Keys.H))
            navigate(new HexEditor(game, player, width, height));
        if (Gdx.input.isKeyPressed(Input.Keys.Q))
            navigate(new MainMenuScreen(game, player, width, height));
    }

    /**
     * Update window size.
     * @param width New window width
     * @param height New window heigh
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        // Update local variables
        this.width = width;
        this.height = height;

        // Update things for rendering
        viewport.update(width, height);
        camera.position.x = width / 2.0f; // Refactor (added 2 lines)
        camera.position.y = height / 2.0f;
        camera.update();

        // Reposition UI elements
        btn_quit.setPosition(0.0f, height - btn_quit.getHeight());
        btn_square.setPosition(width * 0.33f, height * 0.6f);
        btn_hex.setPosition(width * 0.33f, height * 0.4f);
    }

    /** @see ApplicationListener#resume */
    @Override
    public void resume() { }

    /** @see ApplicationListener#show */
    @Override
    public void show() {
        stage = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(stage);

        // All buttons need their own style so we can individually change their colors

        // Create return to main menu button
        style_quit = new TextButton.TextButtonStyle();
        style_quit.font = game.font;
        style_quit.fontColor = Color.SCARLET;
        btn_quit = new TextButton("Main Menu", style_quit); // TODO: set graphic?
        btn_quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                navigate(new MainMenuScreen(game, player, width, height)); // Refactored
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_quit.fontColor = Color.BLUE;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_quit.fontColor = Color.SCARLET;
            }
        });
        stage.addActor(btn_quit);

        // Create square map button
        style_square = new TextButton.TextButtonStyle();
        style_square.font = game.font;
        style_square.fontColor = Color.SCARLET;
        btn_square = new TextButton("Edit a Square Map", style_square);
        btn_square.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                navigate(new SquareEditor(game, player, width, height)); // Refactored
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_square.fontColor = Color.BLUE;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_square.fontColor = Color.SCARLET;
            }
        });
        stage.addActor(btn_square);

        // Create hexagonal map button
        style_hex = new TextButton.TextButtonStyle();
        style_hex.font = game.font;
        style_hex.fontColor = Color.SCARLET;
        btn_hex = new TextButton("Edit a Hexagonal Map", style_hex);
        btn_hex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                navigate(new HexEditor(game, player, width, height)); // Refactored
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_hex.fontColor = Color.BLUE;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_hex.fontColor = Color.SCARLET;
            }
        });
        stage.addActor(btn_hex);
    }

    /**
     * Create a new map editor menu.
     * @param game The current game state
     * @param player (Currently unused, just passed to other methods?)
     */
    public EditorScreen(TBDGame game, Player player, int width, int height) {
        // Set final (private) fields
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        viewport = new ScreenViewport(camera);

        // Set regular (private) fields
        this.width = width;
        this.height = height;
    }
}

