package com.ldgmms.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
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
 * TODO: Make a full-featured main menu screen.
 * As well as being the first screen you see,
 * the user should be able to access the main menu again
 * via the pause menu after saving (or not saving) the game state.
 */

public class MainMenuScreen implements Screen {
    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final TBDGame game;
    private final Player player;

    // Matthew Rease
    private int width, height;
    private Stage stage;
    private TextButton.TextButtonStyle style_square, style_hex, style_editor;
    private TextButton btn_square, btn_hex, btn_editor;

    /**
     * Constructor for the MainMenuScreen.
     * -Sean
     * @param game represents the game state
     * @param player main Player passed to the screen
     */
    public MainMenuScreen(TBDGame game, Player player, int width, int height) {
        // Set final (private) fields
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        viewport = new ScreenViewport(camera);

        // Set regular (private) fields
        this.width = width;
        this.height = height;
    }


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
		 * @author Matthew Rease
     */
    @Override
    public void show() {
        stage = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(stage);

        // All buttons need their own style so we can individually change their colors

        // Create square map button
        style_square = new TextButton.TextButtonStyle();
        style_square.font = game.font;
        style_square.fontColor = Color.SCARLET;
        btn_square = new TextButton("Play on the Square Map", style_square);
        btn_square.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked square button.");
                game.setScreen(new SquareScreen(game, player, width, height));
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

        // Create hexagonal map button
        style_hex = new TextButton.TextButtonStyle();
        style_hex.font = game.font;
        style_hex.fontColor = Color.SCARLET;
        btn_hex = new TextButton("Play on the Hexagonal Map", style_hex);
        btn_hex.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked hex button.");
                game.setScreen(new HexScreen(game, player, width, height));
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

        // Create map editor button
        style_editor = new TextButton.TextButtonStyle();
        style_editor.font = game.font;
        style_editor.fontColor = Color.SCARLET;
        btn_editor = new TextButton("Open the Map Editor", style_editor);
        btn_editor.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked editor button.");
                game.setScreen(new EditorScreen(game, player, width, height));
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_editor.fontColor = Color.BLUE;
                System.out.println("Hovering over editor button.");
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_editor.fontColor = Color.SCARLET;
                System.out.println("No longer hovering over editor button.");
            }
        });
        stage.addActor(btn_editor);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        Batch batch = game.batch; // For ease of typing

        // Rendering tasks
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        game.font.draw(batch, "Main Menu Screen", width * 0.33f, height * 0.8f);
        batch.end();
        stage.act(delta);
        stage.draw();

        // User input
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            game.setScreen(new SquareScreen(game, player, width, height));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            game.setScreen(new HexScreen(game, player, width, height));
            dispose();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            game.setScreen(new EditorScreen(game, player, width, height));
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
        // Set local variables
        this.width = width;
        this.height = height;
        System.out.println("New size: " + width + "x" + height);

        // Update things for rendering
        viewport.update(width, height);
        camera.position.x = width / 2.0f;
        camera.position.y = height / 2.0f;
        camera.update();

        // Reposition UI elements
        btn_square.setPosition(width * 0.33f, height * 0.6f);
        btn_hex.setPosition(width * 0.33f, height * 0.4f);
        btn_editor.setPosition(width * 0.33f, height * 0.2f);
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
        stage.dispose();
    }

}

