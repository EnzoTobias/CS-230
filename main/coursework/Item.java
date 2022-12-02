package coursework;
/**
 * Abstract superclass of all item classes
 * 
 * @author Enzo Tobias 2117781
 */
public abstract class Item {
	/**
	 * Abstract class to be inherited, represents the effect an item has when activated
	 * @param tile The tile this item is on.
	 * @param entity The entity triggering this effect.
	 */
	public abstract void itemEffect(Tile tile, WalkingEntity entity);
	/**
	 * Delete self.
	 * @param tile The tile this item is on.
	 */
	public void deleteSelf(Tile tile) {
		tile.setContainedItem(null);
	}
}
