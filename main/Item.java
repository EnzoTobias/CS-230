/**
 * Abstract superclass of all item classes
 * 
 * @author Enzo Tobias 2117781
 */
public abstract class Item {

	abstract void itemEffect(Tile tile, WalkingEntity entity);
	public void deleteSelf(Tile tile) {
		tile.setContainedItem(null);
	}
}
