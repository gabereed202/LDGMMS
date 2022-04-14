package com.ldgmms.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

/**
 * Player class extends the Sprite class to gain access to valuable methods within the Sprite superclass,
 * and implements InputProcessor in order to easily access multiple forms of user-input.
 *
 * You can either call Player in the TBDGame class or any Screen class.
 * -Sean
 */
public class Player extends Sprite implements InputProcessor {
    private TiledMapTileLayer collisionLayer;   // tells us which tiles are collidable
    private float squareX, squareY;             // remembers players square screen coordinates
    private float hexX, hexY;                   // temporary x and y locations while on a hex screen
    protected boolean isScreenHex;              // used to tell what kind of screen the player is currently on
    private boolean debugFlag = true;           // useful for testing
    private boolean isOffset = true;            // helpful for moving on hex maps (not a fan, but it works)

    /**
     * Constructor for a new Player object.
     * -Sean
     * @param sprite Texture used to visually represent the Player object.
     */
    public Player(Sprite sprite) {
        super(sprite);
        isScreenHex = false;
    }

    // empty player for testing
    public Player() {
        isScreenHex = false;
    }

    /**
     * Called if you need to create a new player while you're already in a Screen.
     * Example: Player1 is in SquareScreen and Player2 joins, you would call 'new Player()'
     * in that squareScreen  in order to initialize player2.
     * -Sean
     * @param sprite The Sprite used to visually represent the Player Actor.
     * @param collisionLayer Used to tell the player what tiles are collidable.
     */
    public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
        super(sprite);
        this.collisionLayer = collisionLayer;
        this.squareX = getX();
        this.squareY = getY();
        isScreenHex = false;
    }

    /**
     * Helpful if you need to calculate tile widths
     * -Sean
     * @return the collisionLayer for the player's current map
     */
    public TiledMapTileLayer getCollisionLayer() {
        return collisionLayer;
    }

    /**
     * Called before this Player is initialized on a Screen.
     * -Sean
     * @param collisionLayer used to tell the player what tiles are collidable.
     */
    public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
    }

    /**
     * Called when the player transitions from the hexMap to the squareMap.
     * -Sean
     * @return the Player's most recent x-coordinate in the squareMap.
     */
    public float getSquareX() {
        return squareX;
    }

    /**
     * Called when the player transitions from the squareMap to the hexMap.
     * -Sean
     * @param squareX the player's most recent x-coordinate in the squareMap
     */
    public void setSquareX(float squareX) {
        this.squareX = squareX;
    }

    /**
     * Called when the player transitions from the hexMap to the squareMap.
     * -Sean
     * @return the Player's most recent y-coordinate in the squareMap.
     */
    public float getSquareY() {
        return squareY;
    }

    /**
     * Called when the player transitions from the squareMap to the hexMap.
     * -Sean
     * @param squareY the player's most recent y-coordinate in the squareMap
     */
    public void setSquareY(float squareY) {
        this.squareY = squareY;
    }

    public float getHexX() {
        return hexX;
    }

    public void setHexX(float hexX) {
        this.hexX = hexX;
    }

    public float getHexY() {
        return hexY;
    }

    public void setHexY(float hexY) {
        this.hexY = hexY;
    }

    /**
     * Called when the player sprite is drawn.
     * -Sean
     * @param batch -
     */
    @Override
    public void draw(Batch batch) {
        super.draw(batch);
    }

    /**
     * Checks whether the given cell has the "blocked" property which is represented in the cell's tileset file.
     * Heavily inspired by Dermetfan's videos on youtube about tiledmaps in libGDX.
     * -Sean
     * @param x units measured in pixels
     * @param y units measured in pixels
     * @return
     */
    private boolean isCellBlocked(float x, float y) {
        Cell cell = collisionLayer.getCell( (int) (x / collisionLayer.getTileWidth()),
                (int) y/collisionLayer.getTileHeight());
        return cell != null && cell.getTile() != null && cell.getTile().getProperties().containsKey("blocked");
    }

    /**
     * Called when a key was pressed
     * -Sean
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyDown(int keycode) {
        // NOTE: getWidth() and getHeight() are in reference to the player sprite
        if (isScreenHex) { // currently on a hex map
            hexX = getX();
            hexY = getY();
            switch (keycode) {
                case Input.Keys.UP:
                    if (!isCellBlocked(getX(), getY() + getHeight() * 3/4)) {
                        setY( getY() + getHeight() * 3/4);
                        if (isOffset) {
                            isOffset = false;
                            setX(getX() + getWidth()/2);
                        } else {
                            isOffset = true;
                            setX(getX() - getWidth()/2);
                        }
                    }
                    break;
                case Input.Keys.DOWN:
                    if (!isCellBlocked(getX(), getY() + getHeight() * 3/4)) {
                        setY( getY() - getHeight() * 3/4);
                        if (isOffset) {
                            isOffset = false;
                            setX(getX() + getWidth()/2);
                        } else {
                            isOffset = true;
                            setX(getX() - getWidth()/2);
                        }
                    }
                    break;
                case Input.Keys.LEFT:
                    if (!isCellBlocked(getX() - getWidth(), getY())) {
                        setX(getX() - getWidth());
                    }
                    break;
                case Input.Keys.RIGHT:
                    if (!isCellBlocked(getX() + getWidth(), getY())) {
                        setX(getX() + getWidth());
                    }
                    break;
            }
        } else { // currently on a square map
            switch (keycode) {
                case Input.Keys.UP:
                    if (!isCellBlocked(getX(), getY() + getHeight())) {
                        setY(getY() + collisionLayer.getTileHeight() - 8);
                    }
                    break;
                case Input.Keys.DOWN:
                    if (!isCellBlocked(getX(), getY() - getHeight())) {
                        setY(getY() - collisionLayer.getTileHeight() - 8);
                    }
                    break;
                case Input.Keys.LEFT:
                    if (!isCellBlocked(getX() - getWidth(), getY())) {
                        setX(getX() - getWidth());
                    }
                    break;
                case Input.Keys.RIGHT:
                    if (!isCellBlocked(getX() + getWidth(), getY())) {
                        setX(getX() + getWidth());
                    }
                    break;
            }
        }

        // debug player movement
        if (debugFlag) {
            System.out.println("isMapHex: " + isScreenHex);
            System.out.println(getX() + " " + getY());
        }

        return true;
    }

    /**
     * Called when a key was released
     *
     * @param keycode one of the constants in {@link Input.Keys}
     * @return whether the input was processed
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * Called when a key was typed
     *
     * @param character The character
     * @return whether the input was processed
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Called when the screen was touched or a mouse button was pressed. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     *
     * @param screenX The x coordinate, origin is in the upper left corner
     * @param screenY The y coordinate, origin is in the upper left corner
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger was lifted or a mouse button was released. The button parameter will be {@link Input.Buttons#LEFT} on iOS.
     *
     * @param screenX -
     * @param screenY -
     * @param pointer the pointer for the event.
     * @param button  the button
     * @return whether the input was processed
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * Called when a finger or the mouse was dragged.
     *
     * @param screenX -
     * @param screenY -
     * @param pointer the pointer for the event.
     * @return whether the input was processed
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * Called when the mouse was moved without any buttons being pressed. Will not be called on iOS.
     *
     * @param screenX -
     * @param screenY -
     * @return whether the input was processed
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * Called when the mouse wheel was scrolled. Will not be called on iOS.
     *
     * @param amountX the horizontal scroll amount, negative or positive depending on the direction the wheel was scrolled.
     * @param amountY the vertical scroll amount, negative or positive depending on the direction the wheel was scrolled.
     * @return whether the input was processed.
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}