package com.ldgmms.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class HexEditor implements Screen /*implements InputProcessor*/ {
    // Constants
    private static final float camera_movement_speed = 60.0f;

    // Instance constants
    private final TBDGame game;
    private final Player player; // Unused by this class
    private final OrthographicCamera ui_camera;
    private final OrthographicCamera game_camera;
    private final ScreenViewport ui_viewport; // Allows the game graphics/camera to be moved around separate from the actual window
    private final FitViewport game_viewport;
    private final TiledMap map;
    private final HexagonalTiledMapRenderer renderer;

    // Instances variables
    private int width, height;
    private Stage stage;
    private TextButton.TextButtonStyle style_quit, style_ctxmenu_1, style_ctxmenu_2;
    private TextButton btn_quit, btn_ctxmenu_1, btn_ctxmenu_2;
    private boolean ctxmenu_visible;
    private float game_camera_dx = 0.0f; // Camera speed, horizontal
    private float game_camera_dy = 0.0f; // Camera speed, vertical

    private void navigate(Screen newScreen) {
        game.setScreen(newScreen);
        dispose();
    }

    private void toggleCtxMenu() {
        if (ctxmenu_visible) {
            btn_ctxmenu_1.remove();
            btn_ctxmenu_2.remove();
        }
        else {
            btn_ctxmenu_1.setPosition(Gdx.input.getX(), height - Gdx.input.getY() - btn_ctxmenu_1.getHeight()); // Move to mouse cursor
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
        ScreenUtils.clear(0, 0, 0.2f, 1);

        // Update camera
        game_camera.position.x += game_camera_dx * delta * game_camera.zoom;
        game_camera.position.y += game_camera_dy * delta * game_camera.zoom;
        game_camera.update();

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
    }

    public void resize(int width, int height) {
        // Update local variables
        this.width = width;
        this.height = height;

        // Update UI
        ui_viewport.update(width, height);
        ui_camera.position.x = width / 2.0f;
        ui_camera.position.y = height / 2.0f;
        ui_camera.update(); // Update matrices

        // Update game display while maintaining its position relative to the window
        Vector3 pos = game_camera.position.cpy();
        game_camera.setToOrtho(false, width, height);
        game_camera.position.set(pos);

        // Update UI elements
        btn_quit.setPosition(0.0f, height - btn_quit.getHeight());
    }

    public void resume() { }

    public void show() {
        renderer.setView(game_camera);

        stage = new Stage(ui_viewport, game.batch);
        Gdx.input.setInputProcessor(stage);
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
                game_camera.translate(-dx * game_camera.zoom, -dy * game_camera.zoom, 0.0f);
                setDragStartX(x);
                setDragStartY(y);
                if (ctxmenu_visible)
                    toggleCtxMenu();
            }
        });
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
                    case Input.Keys.UP:
                        game_camera_dy -= camera_movement_speed;
                        break;
                    case Input.Keys.DOWN:
                        game_camera_dy += camera_movement_speed;
                        break;
                    case Input.Keys.LEFT:
                        game_camera_dx += camera_movement_speed;
                        break;
                    case Input.Keys.RIGHT:
                        game_camera_dx -= camera_movement_speed;
                        break;
                    case Input.Keys.M:
                        navigate(new MainMenuScreen(game, player, width, height));
                        break;
                    default:
                        return false;
                }
                return true;
            }
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.UP:
                        game_camera_dy += camera_movement_speed;
                        break;
                    case Input.Keys.DOWN:
                        game_camera_dy -= camera_movement_speed;
                        break;
                    case Input.Keys.LEFT:
                        game_camera_dx -= camera_movement_speed;
                        break;
                    case Input.Keys.RIGHT:
                        game_camera_dx += camera_movement_speed;
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        style_quit = new TextButton.TextButtonStyle();
        style_quit.font = game.font;
        style_quit.fontColor = Color.SCARLET;
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

        ctxmenu_visible = false;
    }

    public HexEditor(TBDGame game, Player player, int width, int height) {
        this.game = game;
        this.player = player;
        ui_camera = new OrthographicCamera(width, height);
        ui_viewport = new ScreenViewport(ui_camera);
        game_camera = new OrthographicCamera(1056, 784);
        game_viewport = new FitViewport(1056, 784, game_camera);
        map = new TmxMapLoader().load("map_hexMap.tmx"); // TODO: should not load a map, but create a fresh one!
        renderer = new HexagonalTiledMapRenderer(map);

        // Set private fields
        this.width = width;
        this.height = height;
    }
}

