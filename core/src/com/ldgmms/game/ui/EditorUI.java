package com.ldgmms.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
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

    public void dispose() {
        stage.dispose();
    }

    public Stage getStage() {
        return stage;
    }

    public void hide() {
        stage.removeListener(cameraListener);
        stage.removeListener(keyListener);
    }

    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.addListener(keyListener);
        stage.addListener(cameraListener);
    }

    public void update(int width, int height) {
        viewport.update(width, height);
        camera.position.x = width / 2.0f;
        camera.position.y = height / 2.0f;
        camera.update(); // Update matrices
    }

    public EditorUI(Batch batch, DynamicCamera game_camera) {
        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, batch);

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
    }
}
