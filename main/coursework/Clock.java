package coursework;
/**
 * Represents the clock item which adds time to the level when activated by the player.
 * @author Enzo Tobias 2117781
 *
 */
public class Clock extends Item {
	private final int TIME = 15;

	/**
	 * Adds time to the level (when activated by player).
	 * @param tile The tile this clock instance is on.
	 * @param entity The entity that activated this clock instance.
	 */
	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		if(entity instanceof Player) {
			tile.getLevelControl()
			.setTimeLeft(tile.getLevelControl().getTimeLeft() + TIME);
		}
		this.deleteSelf(tile);
	}

}
