package com.ldgmms.game;

import java.util.ArrayList;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.MapObjects;

/**
 * A single tile on a game map.
 * Should work for different configurations (square/hexagonal) - you'll just
 *   need to treat the neighboring tiles slightly differently.
 * @author Matthew Rease
 */
public class Tile implements TiledMapTile {
	// Interface fields
	private int id;
	private TiledMapTile.BlendMode blend_mode;
	private TextureRegion texreg;
	private float off_x, off_y;
	private MapProperties props;
	private MapObjects objs;

	// Class fields
	private TileType tileId;
	private Tile neighbor[]; // Represents 3x3 grid of tiles, with the middle being `this`. Should be of length 8...
	GenericUnit unit = null;

	// Interface methods

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 *
	 * @return the {@link TiledMapTile.BlendMode} to use for rendering this tile
	 */
	public TiledMapTile.BlendMode getBlendMode() {
		return blend_mode;
	}

	/**
	 * Sets the {@link TiledMapTile.BlendMode} to use for rendering this tile
	 * @param blendMode the blend mode to use for rendering the tile
	 */
	public void setBlendMode(TiledMapTile.BlendMode blendMode) {
		blend_mode = blendMode;
	}

	/**
	 *
	 * @return texture region used to render the tile
	 */
	public TextureRegion getTextureRegion() {
		return texreg;
	}

	/**
	 *
	 * @param textureRegion
	 * @return Sets the texture region used to render the tile
	 */
	public void setTextureRegion(TextureRegion textureRegion) {
		texreg = textureRegion;
	}

	/**
	 *
	 * @return the amount to offset the x position when rendering the tile
	 */
	public float getOffsetX() {
		return off_x;
	}

	/**
	 * Set the amount to offset the x position when rendering the tile
	 * @param offsetX
	 */
	public void setOffsetX(float offsetX) {
		off_x = offsetX;
	}

	/**
	 *
	 * @return the amount to offset the y position when rendering the tile
	 */
	public float getOffsetY() {
		return off_y;
	}

	/**
	 * Set the amount to offset the y position when rendering the tile
	 * @param offsetY
	 */
	public void setOffsetY(float offsetY) {
		off_y = offsetY;
	}

	/**
	 *
	 * @return tile's properties set
	 */
	public MapProperties getProperties() {
		return props;
	}

	/**
	 *
	 * @return collection of objects contained in the tile
	 */
	public MapObjects getObjects() {
		return objs;
	}

	// Class methods

	/**
	 * Value used for things such as tile drawing.
	 */
	public enum TileType {
		grass,
		dirt,
		rock
	};

	public TileType type() {
		return tileId;
	}

	/**
	 * Get one of this tile's neighbors.
	 * @param index The index of the requested tile.
	 * @return The requested neighbor, or <code>null</code> if <code>index</code> is invalid.
	 */
	public Tile getNeighbor(int index) {
		if (index < 0 || index >= neighbor.length)
			return null;
		return neighbor[index];
	}

	/**
	 * If this tile has space for a {@link GenericUnit} available.
	 * @return <code>true</code> if there are no units on this tile, <code>false</code> otherwise.
	 */
	public boolean isAvailable() {
		return unit == null;
	}

	/**
	 * Whether or not all tiles along a given path are available.
	 * @param path A list of tile neighbor indexes. In order from index 0, to path.size - 1.
	 * @return <code>true</code> if path is clear, <code>false</code> otherwise.
	 * @see Tile#isAvailable
	 */
	public boolean pathIsAvailable(ArrayList<Integer> path) { // The performance of this would improve if we read the array from right to left, but that ordering would be far less untuitive to the programmer...
		if (!isAvailable())
			return false;
		Tile n = getNeighbor((int)path.get(0));
		if (n == null)
			return false;
		path.remove(0);
		return n.pathIsAvailable(path);
	}

	/**
	 * Place a unit on this tile.
	 * @param unit The unit moving to this tile.
	 * @return <code>true</code> if the move was successful, <code>false</code> if the unit could not be placed on this tile.
	 * @see GenericUnit
	 */
	public boolean receiveUnit(GenericUnit unit) {
		if (!isAvailable())
			return false;
		this.unit = unit;
		return true;
	}

	/**
	 * Remove a {@link GenericUnit} from this tile.
	 * @return The unit removed from the tile.
	 */
	public GenericUnit takeUnit() {
		GenericUnit leaving = unit;
		unit = null;
		return leaving;
	}

	/**
	 * Move a {@link GenericUnit} to a neighboring tile.
	 * @param index The index of the destination tile.
	 * @return <code>true</code> if the move was successful, <code>false</code> otherwise.
	 */
	public boolean moveUnitTo(int index) {
		Tile n = getNeighbor(index);
		if (n == null)
			return false;
		return n.receiveUnit(takeUnit());
	}

	/**
	 * Creates a new tile.
	 * @param neighbor The neighboring tiles to this one. Should be at least length 8.
	 */
	public Tile(TileType tileId, Tile neighbor[]) { // TODO: constructor should probably have more properties than just its neighbors - also may wish to use a class instead of an array?
		this.tileId = tileId;
		this.neighbor = neighbor;
	}
}
