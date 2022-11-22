
public abstract class Thief extends WalkingEntity {
	public void die(Tile tile) {
		tile.setContainedEntity(null);
	};
}
