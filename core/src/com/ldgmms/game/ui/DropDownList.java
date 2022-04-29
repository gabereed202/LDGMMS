package com.ldgmms.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class DropDownList {
    private final Stage stage;
    private final Actor[] actors;

    private int height = 0;
    private boolean visible = false;

    /**
     * Toggle visibility of drop-down list.
     */
    // TODO: Find longest button (string) and if menu will go offscreen, adjust accordingly.
    private void toggle() {
        if (visible)
            for (Actor item : actors)
                item.remove();
        else {
            float x = Gdx.input.getX(); // Move to mouse cursor
            float y = height - Gdx.input.getY();
            for (Actor item : actors) {
                y -= item.getHeight(); // Move to below the previous button
                item.setPosition(x, y);
                stage.addActor(item);
            }
        }
        visible = !visible;
    }

    public void addListeners() {
        for (Actor item : actors) {
            if (item instanceof ResponsiveTextButton)
                ((ResponsiveTextButton)item).addListener();
            else
                item.getListeners();
                item.clearListeners();
        }
    }

    public void hide() {
        if (visible)
            toggle();
    }

    public void removeListeners() {
        for (Actor item : actors) {
            if (item instanceof ResponsiveTextButton)
                ((ResponsiveTextButton)item).removeListener();
            else
                item.clearListeners();
        }
    }

    public void update(int height) {
        this.height = height;
    }

    public DropDownList(Stage stage, Actor... actors) {
        this.stage = stage;
        this.actors = new ResponsiveTextButton[actors.length];
        int index = 0;
        for (Actor actor : actors)
            this.actors[index++] = actor;
    }
}
