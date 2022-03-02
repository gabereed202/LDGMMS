package com.ldgmms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SquareEditor implements Screen /*implements InputProcessor*/ {
    private final TBDGame game;
    private final Player player; // Unused by this class
    private final OrthographicCamera ui_camera;
    private final OrthographicCamera game_camera;
    private final ScreenViewport ui_viewport;
    private final FitViewport game_viewport;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    private int width, height;
    private Stage stage;
    private TextButton.TextButtonStyle default_button_style;
    private TextButton btn_quit;

    public void dispose() {
        stage.dispose();

        renderer.dispose();
        map.dispose();
    }

    public void hide() { }

    public void pause() { }

    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1); // Set screen black

        game_camera.update();

        //Batch batch = game.batch;
        Batch batch = renderer.getBatch();


        // Rendering tasks
        batch.setProjectionMatrix(game_camera.combined); // Specify coordinate system?
        renderer.render();
        batch.begin();
        // Render game
        //batch.setProjectionMatrix(game_camera.combined); // Specify coordinate system?
        game.font.draw(batch, "TEST", 50, 50);
        // Render UI
        batch.setProjectionMatrix(ui_camera.combined);
        stage.act(delta);
        stage.draw();
        batch.end();

        // User input
        if (Gdx.input.justTouched())
            System.out.println("Received input at " + Gdx.input.getX() + "," + Gdx.input.getY());
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            game_camera.position.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            game_camera.position.y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            game_camera.position.x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            game_camera.position.x -= 1;
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;

        ui_viewport.update(width, height);
        ui_camera.position.x = width / 2.0f;
        ui_camera.position.y = height / 2.0f;
        ui_camera.update(); // Update matrices

        game_camera.setToOrtho(false, width, height);
        /*game_camera.viewportWidth = width;
        game_camera.viewportHeight = height;*/
        //game_viewport.update(width, height);

        System.out.println("New size: " + width + "x" + height);
        //System.out.println("UI Position: " + ui_camera.position.x + "," + ui_camera.position.y);

        btn_quit.setPosition(0.0f, height - btn_quit.getHeight());
    }

    public void resume() { }

    public void show() {
        renderer.setView(game_camera);

        stage = new Stage(ui_viewport, game.batch);
        Gdx.input.setInputProcessor(stage); //Gdx.input.setInputProcessor(this);
        stage.addListener(new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            }
        });
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
                System.out.println("RMB 2 dragged " + dx + "," + dy);
                game_camera.translate(-dx, -dy, 0.0f);
                setDragStartX(x);
                setDragStartY(y);
            }
        });

        default_button_style = new TextButton.TextButtonStyle(); //default_button_style.font = new BitmapFont();
        default_button_style.font = game.font;
        default_button_style.fontColor = Color.SCARLET; //default_button_style.fontColor = Color.WHITE;
        btn_quit = new TextButton("Main Menu", default_button_style); // TODO: set graphic?
        btn_quit.setPosition(0.0f, 768.0f);
        /*btn_quit.setWidth(50.0f);
        btn_quit.setHeight(50.0f);*/
        btn_quit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked button.");
                game.setScreen(new MainMenuScreen(game, player, width, height));
                dispose();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                default_button_style.fontColor = Color.BLUE;
                System.out.println("Hovering over button.");
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                default_button_style.fontColor = Color.SCARLET;
                System.out.println("No longer hovering over button.");
            }
        });
        stage.addActor(btn_quit);
    }

    public SquareEditor(TBDGame game, Player player, int width, int height) {
        this.game = game;
        this.player = player;
        ui_camera = new OrthographicCamera(width, height);
        ui_viewport = new ScreenViewport(ui_camera);
        game_camera = new OrthographicCamera(width, height);
        game_viewport = new FitViewport(width, height, game_camera);
        map = new TmxMapLoader().load("map_squareMap.tmx"); // TODO: should not load a map, but create a fresh one!
        renderer = new OrthogonalTiledMapRenderer(map);

        this.width = width;
        this.height = height;
    }
}