package com.ldgmms.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DropDownList {
    private final Stage stage;
    private final ResponsiveTextButton[] buttons;
    private final ClickListener listener;

    private int height = 0;
    private boolean visible = false;

    /**
     * Toggle visibility of drop-down list.
     */
    // TODO: Find longest button (string) and if menu will go offscreen, adjust accordingly.
    private void toggle() {
        if (visible) {
            for (ResponsiveTextButton button : buttons) {
                button.removeListener();
                button.remove();
            }
        }
        else {
            float x = Gdx.input.getX(); // Move to mouse cursor
            float y = height - Gdx.input.getY();
            for (ResponsiveTextButton button : buttons) {
                y -= button.getHeight(); // Move to below the previous button
                button.setPosition(x, y);
                stage.addActor(button);
                button.addListener();
            }
        }
        visible = !visible;
    }

    public void addListener() {
        stage.addListener(listener);
    }

    public void hide() {
        if (visible)
            toggle();
    }

    public void removeListener() {
        stage.removeListener(listener);
        hide();
    }

    public void update(int height) {
        this.height = height;
        hide();
    }

    public DropDownList(Stage stage, ResponsiveTextButton... buttons) {
        this.stage = stage;
        this.buttons = new ResponsiveTextButton[buttons.length];
        int index = 0;
        for (ResponsiveTextButton button : buttons)
            this.buttons[index++] = button;
        listener = new ClickListener(Input.Buttons.RIGHT) {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggle();
            }
        };
    }
}
