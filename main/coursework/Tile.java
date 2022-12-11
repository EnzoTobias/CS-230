package coursework;
/**
 * Represents one tile in the tilegrid.
 * 
 * @author Enzo Tobias 2117781
 */
public class Tile {
	private Colour[] colours;
	private int x;
	private int y;
	private Item containedItem;
	private WalkingEntity containedEntity;
	private LevelControl level;
	/**
	 * Set this tile's X coordinate
	 * 
	 * @param x
	 *            The X coordinate to set.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Set this tile's Y coordinate
	 * 
	 * @param y
	 *            The Y coordinate to set.
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * Create a new tile with an array representing its colours.
	 * 
	 * @param colours
	 *            The colours of the tile in an array, order: top left, top
	 *            right, bottom left, bottom right.
	 */
	public Tile(Colour[] colours) {
		this.colours = colours;
	}
	/**
	 * Return this tile's colours array.
	 * 
	 * @return The colours in an array to be returned.
	 */
	public Colour[] getColours() {
		return colours;
	}
	/**
	 * Return this tile's X coordinate.
	 * 
	 * @return The X coordinate to return.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Return this tile's Y coordinate.
	 * 
	 * @return The Y coordinate to return.
	 */
	public int getY() {
		return y;
	}
	/**
	 * Return this tile's contained item.
	 * 
	 * @return The contained item to return.
	 */
	public Item getContainedItem() {
		return containedItem;
	}
	/**
	 * Set this item's contained item.
	 * 
	 * @param containedItem
	 *            The contained item to set.
	 */
	public void setContainedItem(Item containedItem) {
		this.containedItem = containedItem;
	}
	/**
	 * Return this tile's assigned level control instance.
	 * 
	 * @return This tile's assigned level control instance to return.
	 */
	public LevelControl getLevelControl() {
		return level;
	}
	/**
	 * Set this tile's assigned level control instance.
	 * 
	 * @param level
	 *            This tile's assigned level control instance.
	 */
	public void setLevelControl(LevelControl level) {
		this.level = level;
	}
	/**
	 * Return This tile's contained entity.
	 * 
	 * @return This tile's contained entity.
	 */
	public WalkingEntity getContainedEntity() {
		return containedEntity;
	}
	/**
	 * Set this tile's contained entity.
	 * 
	 * @param containedEntity
	 *            The contained entity to set.
	 */
	public void setContainedEntity(WalkingEntity containedEntity) {
		this.containedEntity = containedEntity;
	}
	/**
	 * Returns if this tile has an item.
	 * 
	 * @return Boolean denoting if this tile has an item.
	 */
	public Boolean hasItem() {
		if (containedItem == null) {
			return false;
		}
		return true;
	}
	/**
	 * Returns if this tile has an entity.
	 * 
	 * @return Boolean denoting if this tile has an entity.
	 */
	public Boolean hasEntity() {
		if (containedEntity == null) {
			return false;
		}
		return true;
	}
	/**
	 * Returns if this tile is blocked for movement.
	 * 
	 * @return Boolean denoting if this tile is blocked for movement.
	 */
	public boolean isTileBlocked() {
		if (this.hasItem() && this.getContainedItem() instanceof Gate) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Bomb) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Door
				&& !this.getLevelControl().isAllLootCollected()) {
			return true;
		}

		for (Colour colour : this.getColours()) {
			if (colour == Colour.VOID) {
				return true;
			}
		}

		if (this.hasEntity()) {
			return true;
		}
		return false;
	}
	/**
	 * Returns if this tile is blocked for movement for the hitman entity.
	 * 
	 * @return Boolean denoting if this tile is blocked for movement for the
	 *         hitman entity.
	 */
	public boolean isTileBlockedForHitman() {
		if (this.hasItem() && this.getContainedItem() instanceof Gate) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Bomb) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Door
				&& !this.getLevelControl().isAllLootCollected()) {
			return true;
		}

		for (Colour colour : this.getColours()) {
			if (colour == Colour.VOID) {
				return true;
			}
		}

		if (this.hasEntity()
				&& !(this.getContainedEntity() instanceof Player)) {
			return true;
		}
		return false;
	}
}
