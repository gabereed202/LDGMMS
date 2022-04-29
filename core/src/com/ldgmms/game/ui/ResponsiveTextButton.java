package com.ldgmms.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Enhanced {@link TextButton} that comes with a click handler and overridable functions for clicking the button, and
 * hovering (or un-hovering) over the button.
 * <br/>
 * By default, when the user hovers over the button, the text color changes. You may specify custom colors, but the
 * defaults are Scarlet for the normal color and Blue for the hovering color.
 * <br/>
 * When the button is created, its click listener will be created too, but not added.
 * @see ResponsiveTextButton#addListener
 * @see ResponsiveTextButton#removeListener
 * @author Matthew Rease
 */
public class ResponsiveTextButton extends TextButton {
    private final Color defaultColor;
    private final Color hoverColor;
    private final ClickListener listener;

    /**
     * Adds the click listener to the button.
     */
    public void addListener() {
        addListener(listener);
    }

    /**
     * Removes the click listener from the button.
     */
    public void removeListener() {
        removeListener(listener);
    }

    /**
     * Called when the button is clicked.
     * <br/>
     * Should be overridden with a useful function.
     * @see ClickListener#clicked(InputEvent, float, float)
     */
    public void onClick() {
        System.out.println("Clicked \"" + getText() + "\" button!");
    }

    /**
     * Called when the user is hovering over the button.
     * <br/>
     * Should be overridden with a useful function (if desired).
     * @see ClickListener#enter(InputEvent, float, float, int, Actor)
     */
    public void onEnter() { }

    /**
     * Called when the user is no longer hovering over the button.
     * <br/>
     * Should be overridden with a useful function (if desired).
     * @see ClickListener#exit(InputEvent, float, float, int, Actor)
     */
    public void onExit() { }

    /**
     * Creates a responsive {@link TextButton}, using the default colors.
     * @param text Display text
     * @param style Button style (<code>fontColor</code> <b>will</b> be changed)
     * @see Color#SCARLET
     * @see Color#BLUE
     */
    public ResponsiveTextButton(String text, TextButtonStyle style) {
        this(text, style, Color.SCARLET, Color.BLUE);
    }

    /**
     * Creates a responsive {@link TextButton}, with custom colors.
     * @param text Display text
     * @param style Button style (<code>fontColor</code> <b>will</b> be changed)
     * @param setDefaultColor Default display color
     * @param setHoverColor Display color when hovered over
     */
    public ResponsiveTextButton(String text, TextButtonStyle style, Color setDefaultColor, Color setHoverColor) {
        super(text, style);
        style.fontColor = defaultColor = setDefaultColor;
        hoverColor = setHoverColor;
        listener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                onClick();
            }
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                style.fontColor = hoverColor;
                onEnter();
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                style.fontColor = defaultColor;
                onExit();
            }
        };
    }
}
