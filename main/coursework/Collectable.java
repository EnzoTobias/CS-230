package coursework;
/**
 * Represents a collectable item (e.g. ruby,cent etc).
 * 
 * @author Enzo Tobias 2117781
 */
public class Collectable extends Item {
	private CollectableType type;
	/**
	 * Create a new collectable with the given type.
	 * 
	 * @param type
	 *            of collectable.
	 */
	public Collectable(CollectableType type) {
		this.type = type;
	}
	/**
	 * Returns the type of collectable of this instance.
	 * 
	 * @return type of collectable.
	 */
	public CollectableType getCollectableType() {
		return type;
	}
	/**
	 * Give score to the entity picking up this collectable.
	 * 
	 * @param tile
	 *            The tile this item is on.
	 * @param entity
	 *            The entity activating this effect.
	 */
	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		tile.getLevelControl().updateLootCollectedStatus();
		entity.setScore(entity.getScore() + type.getValue());
		Sound.StaticSound.collectableSound();
		this.deleteSelf(tile);
	}

}
