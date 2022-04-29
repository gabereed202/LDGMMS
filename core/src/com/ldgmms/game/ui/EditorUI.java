package com.ldgmms.game.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class EditorUI {
    // Class constants
    private static final int CAMERA_MOVEMENT_SPEED = 60;

    // Instance constants
    private final OrthographicCamera camera;
    private final ScreenViewport viewport;
    private final Stage stage;
    private final InputListener keyListener;
    private final DragListener cameraListener;
    private final ResponsiveTextButton quitButton;

    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void hide() {
        // Actors
        quitButton.removeListener();

        stage.removeListener(cameraListener);
        stage.removeListener(keyListener);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(keyListener);
        stage.addListener(cameraListener);

        // Actors
        quitButton.addListener();
    }

    public void update(int width, int height) {
        // Camera/viewport
        viewport.update(width, height);
        camera.position.x = width / 2.0f;
        camera.position.y = height / 2.0f;
        camera.update(); // Update matrices

        // Actors
        quitButton.setPosition(0.0f, height - quitButton.getHeight());
    }

    public EditorUI(DynamicCamera game_camera, BitmapFont font, Game game, Screen callingScreen) {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport);

        keyListener = new InputListener() {
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
                        game_camera.accelerate(0, -CAMERA_MOVEMENT_SPEED);
                        break;
                    case Input.Keys.DOWN:
                        game_camera.accelerate(0, CAMERA_MOVEMENT_SPEED);
                        break;
                    case Input.Keys.LEFT:
                        game_camera.accelerate(CAMERA_MOVEMENT_SPEED, 0);
                        break;
                    case Input.Keys.RIGHT:
                        game_camera.accelerate(-CAMERA_MOVEMENT_SPEED, 0);
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
                        game_camera.accelerate(0, CAMERA_MOVEMENT_SPEED);
                        break;
                    case Input.Keys.DOWN:
                        game_camera.accelerate(0, -CAMERA_MOVEMENT_SPEED);
                        break;
                    case Input.Keys.LEFT:
                        game_camera.accelerate(-CAMERA_MOVEMENT_SPEED, 0);
                        break;
                    case Input.Keys.RIGHT:
                        game_camera.accelerate(CAMERA_MOVEMENT_SPEED, 0);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        };
        cameraListener = new DragListener() {
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
            }
        };
        quitButton = new ResponsiveTextButton("Return to Editor Menu", font) {
            @Override
            public void onClick() {
                game.setScreen(callingScreen);
                dispose();
            }
        }; // TODO: set graphic?
        stage.addActor(quitButton);
    }
}
