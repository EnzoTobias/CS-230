
public abstract class Item {
	
	abstract void itemEffect(Tile tile, WalkingEntity entity);
	public void deleteSelf(Tile tile) {
		tile.setContainedItem(null);
	}
}
