
/**
 * The door item which allows the player or a thief to end the level.
 * @author Enzo Tobias 2117781
 *
 */
public class Door extends Item {
	
	/**
	 * Effect allows thief or player to end the game when all loot is collected.
	 * Triggers a loss or win for the player.
	 * @param tile The tile this item is on.
	 * @param entity The entity triggering this effect.
	 */
	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		if (tile.getLevelControl().isAllLootCollected()) {
			if (entity instanceof Player) {
				tile.getLevelControl().playerWin();
			} else {
				tile.getLevelControl().playerLose();
			}
		}

	}

}
