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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
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
    private TextButton.TextButtonStyle style_quit, style_ctxmenu_1, style_ctxmenu_2;
    private TextButton btn_quit, btn_ctxmenu_1, btn_ctxmenu_2;
    /*private List.ListStyle style_ctx_menu;
    private List<String> context_menu;*/
    private boolean ctxmenu_visible;

    private void navigate(Screen newScreen) {
        game.setScreen(newScreen);
        dispose();
    }

    private void toggleCtxMenu() {
        if (ctxmenu_visible) {
            //context_menu.remove();
            btn_ctxmenu_1.remove();
            btn_ctxmenu_2.remove();
        }
        else {
            //stage.addActor(context_menu);
            btn_ctxmenu_1.setPosition(Gdx.input.getX(), height - Gdx.input.getY() - btn_ctxmenu_1.getHeight()); // Move to mouse cursor - Refactored these two lines
            btn_ctxmenu_2.setPosition(btn_ctxmenu_1.getX(), btn_ctxmenu_1.getY() - btn_ctxmenu_2.getHeight()); // Move to below first button
            stage.addActor(btn_ctxmenu_1);
            stage.addActor(btn_ctxmenu_2);
        }
        ctxmenu_visible = !ctxmenu_visible;
    }

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
        game.font.draw(batch, "TEST", 50, 50);
        // Render UI
        batch.setProjectionMatrix(ui_camera.combined);
        stage.act(delta);
        stage.draw();
        batch.end();

        // User input
        /*if (Gdx.input.justTouched())
            System.out.println("Received input at " + Gdx.input.getX() + "," + Gdx.input.getY());*/
        if (Gdx.input.isKeyPressed(Input.Keys.UP))
            game_camera.position.y -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            game_camera.position.y += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            game_camera.position.x += 1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            game_camera.position.x -= 1;
        if (Gdx.input.isKeyPressed(Input.Keys.M)) // Refactored (added)
            navigate(new MainMenuScreen(game, player, width, height));
    }

    public void resize(int width, int height) {
        this.width = width;
        this.height = height;

        ui_viewport.update(width, height);
        ui_camera.position.x = width / 2.0f;
        ui_camera.position.y = height / 2.0f;
        ui_camera.update(); // Update matrices

        Vector3 pos = game_camera.position.cpy();
        game_camera.setToOrtho(false, width, height);
        game_camera.position.set(pos);
        /*game_camera.viewportWidth = width;
        game_camera.viewportHeight = height;*/
        //game_viewport.update(width, height);

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
                toggleCtxMenu();
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
                game_camera.translate(-dx, -dy, 0.0f);
                setDragStartX(x);
                setDragStartY(y);
                if (ctxmenu_visible)
                    toggleCtxMenu();
            }
        });

        style_quit = new TextButton.TextButtonStyle(); //default_button_style.font = new BitmapFont();
        style_quit.font = game.font;
        style_quit.fontColor = Color.SCARLET; //default_button_style.fontColor = Color.WHITE;
        btn_quit = new TextButton("Main Menu", style_quit); // TODO: set graphic?
        btn_quit.setPosition(0.0f, 768.0f);
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

        style_ctxmenu_1 = new TextButton.TextButtonStyle();
        style_ctxmenu_1.font = game.font;
        style_ctxmenu_1.fontColor = Color.SCARLET;
        btn_ctxmenu_1 = new TextButton("Thing 1", style_ctxmenu_1);
        btn_ctxmenu_1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked menu button 1.");
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_ctxmenu_1.fontColor = Color.BLUE;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_ctxmenu_1.fontColor = Color.SCARLET;
            }
        });

        style_ctxmenu_2 = new TextButton.TextButtonStyle();
        style_ctxmenu_2.font = game.font;
        style_ctxmenu_2.fontColor = Color.SCARLET;
        btn_ctxmenu_2 = new TextButton("Thing 2", style_ctxmenu_2);
        btn_ctxmenu_2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Clicked menu button 2.");
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style_ctxmenu_2.fontColor = Color.BLUE;
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style_ctxmenu_2.fontColor = Color.SCARLET;
            }
        });

        /*style_ctx_menu = new List.ListStyle(game.font, Color.BLUE, Color.SCARLET, new BaseDrawable());
        style_ctx_menu.selection.setMinWidth(10.0f);
        style_ctx_menu.selection.setMinHeight(10.0f);
        context_menu = new List<String>(style_ctx_menu);
        context_menu.setPosition(100, 100);
        context_menu.setItems(new String[]{ "Thing 1", "Thing 2" });
        context_menu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (isOver()) {
                    System.out.println("User clicked menu option " + context_menu.getItemIndexAt(y));
                }
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                int index = context_menu.getItemIndexAt(y);
                System.out.println("Over index " + index);
                context_menu.setSelectedIndex(index);
            }
        });*/
        ctxmenu_visible = false;
    }

    public SquareEditor(TBDGame game, Player player, int width, int height) {
        this.game = game;
        this.player = player;
        ui_camera = new OrthographicCamera(width, height);
        ui_viewport = new ScreenViewport(ui_camera);
        game_camera = new OrthographicCamera(2048, 1536);
        game_viewport = new FitViewport(2048, 1536, game_camera);
        map = new TmxMapLoader().load("map_squareMap.tmx"); // TODO: should not load a map, but create a fresh one!
        renderer = new OrthogonalTiledMapRenderer(map);

        this.width = width;
        this.height = height;
    }
}
