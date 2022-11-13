/**Represents a collectable item (e.g. ruby,cent etc).
 * @author Enzo Tobias 2117781
 */
public class Collectable extends Item {
	private CollectableType type;
	
	public Collectable(CollectableType value) {
		this.type = type;
	}

	@Override
	void itemEffect(Tile tile, WalkingEntity entity) {
		entity.setScore(entity.getScore() + type.getValue());
		this.deleteSelf(tile);
	}
	
}
