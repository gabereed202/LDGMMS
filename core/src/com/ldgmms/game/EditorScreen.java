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

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        game.font.draw(batch, "Map Editor Screen", width * 0.33f, height * 0.8f);
        //game.font.draw(batch, "Press S to edit a square map.", width * 0.33f, height * 0.6f);
        //game.font.draw(batch, "Press H to edit a hexagonal map.", width * 0.33f, height * 0.4f);
        batch.end();
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.setScreen(new SquareEditor(game, player, width, height));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            game.setScreen(new HexScreen(game, player, width, height)); // TODO: replace with hexagonal map editor
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            game.setScreen(new MainMenuScreen(game, player, width, height));
            dispose();
        }
    }

    /**
     * Update window size.
     * @param width New window width
     * @param height New window heigh
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;

        viewport.update(width, height);
        camera.update();

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

        style_quit = new TextButton.TextButtonStyle();
        style_quit.font = game.font;
        style_quit.fontColor = Color.SCARLET;
        btn_quit = new TextButton("Main Menu", style_quit); // TODO: set graphic?
        //btn_quit.setPosition(0.0f, 768.0f);
        btn_quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked quit button.");
                game.setScreen(new MainMenuScreen(game, player, width, height));
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_quit.fontColor = Color.BLUE;
                System.out.println("Hovering over quit button.");
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_quit.fontColor = Color.SCARLET;
                System.out.println("No longer hovering over quit button.");
            }
        });
        stage.addActor(btn_quit);

        style_square = new TextButton.TextButtonStyle();
        style_square.font = game.font;
        style_square.fontColor = Color.SCARLET;
        btn_square = new TextButton("Edit a Square Map", style_square);
        btn_square.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked square button.");
                game.setScreen(new SquareEditor(game, player, width, height));
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_square.fontColor = Color.BLUE;
                System.out.println("Hovering over square button.");
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_square.fontColor = Color.SCARLET;
                System.out.println("No longer hovering over square button.");
            }
        });
        stage.addActor(btn_square);

        style_hex = new TextButton.TextButtonStyle();
        style_hex.font = game.font;
        style_hex.fontColor = Color.SCARLET;
        btn_hex = new TextButton("Edit a Hexagonal Map", style_hex);
        btn_hex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked hex button.");
                game.setScreen(new HexScreen(game, player, width, height)); // TODO: HexEditor
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_hex.fontColor = Color.BLUE;
                System.out.println("Hovering over hex button.");
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_hex.fontColor = Color.SCARLET;
                System.out.println("No longer hovering over hex button.");
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
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        viewport = new ScreenViewport(camera);

        this.width = width;
        this.height = height;
    }
}

