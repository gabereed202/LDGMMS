package com.ldgmms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class SquareEditor implements Screen /*implements InputProcessor*/ {
    private TBDGame game;
    private Player player; // Unused by this class
    private OrthographicCamera camera;
    private FitViewport viewport;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

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

        camera.update(); // Update matrices

        game.batch.setProjectionMatrix(camera.combined); // Specify coordinate system?

        // Rendering tasks
        Batch batch = game.batch; //Batch batch = renderer.getBatch();
        batch.begin();
        game.font.draw(batch, "TEST", 50, 50);
        batch.end();
        stage.act(delta);
        stage.draw();

        // User input
        if (Gdx.input.justTouched()) {
            Vector3 pos = new Vector3();
            pos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            System.out.println("Received input at " + pos.x + "," + pos.y);
//            System.out.println("Location of button: " + btn_quit.getOriginX() + "," + btn_quit.getOriginY());
//            System.out.println("or possibly: " + btn_quit.getX() + "," + btn_quit.getY());
//            System.out.println("Its dimensions are: " + btn_quit.getWidth() + "," + btn_quit.getHeight());
        }
        if (btn_quit.isTouchFocusTarget())
            System.out.println("Touching quit button (1).");
//        if (btn_quit.isOver())
//            System.out.println("Touching quit button (2).");
//        if (btn_quit.getClickListener().isOver())
//            System.out.println("Touching quit button (3).");
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public void resume() { }

    public void show() {
        renderer.setView(camera);

        stage = new Stage(viewport, game.batch);

        //Gdx.input.setInputProcessor(this);
        Gdx.input.setInputProcessor(stage);
        default_button_style = new TextButton.TextButtonStyle(); //default_button_style.font = new BitmapFont();
        default_button_style.font = game.font;
        default_button_style.fontColor = Color.SCARLET; //default_button_style.fontColor = Color.WHITE;
        btn_quit = new TextButton("Main Menu", default_button_style); // TODO: set graphic?
        btn_quit.setPosition(0.0f, 0.0f);
        /*btn_quit.setWidth(50.0f);
        btn_quit.setHeight(50.0f);*/
        btn_quit.addListener(new ClickListener(-1) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked button.");
                game.setScreen(new MainMenuScreen(game, player));
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

    public SquareEditor(TBDGame game, Player player) {
        this.game = game;
        this.player = player;
        camera = new OrthographicCamera(1024, 768);
        //camera.setToOrtho(false, 800, 480);
        //camera.setToOrtho(true);
        viewport = new FitViewport(1024, 768, camera);
        map = new TmxMapLoader().load("map_squareMap.tmx"); // TODO: should not load a map, but create a fresh one!
        renderer = new OrthogonalTiledMapRenderer(map);
    }
}
