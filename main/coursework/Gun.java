package coursework;
/**
 * Implementation of the gun extra item which kills the closest entity when picked up by the player.
 * @author Enzo Tobias 2117781
 *
 */
public class Gun extends Item {
	/**
	 * The item effect of this item, it kills the closest entity when picked up by the player.
	 * @param tile The tile this item is on.
	 * @param entity The entity triggering this effect.
	 */
	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		WalkingEntity toKill = tile.getLevelControl().closestEntityToTile(tile);
		if (toKill != null && entity instanceof Player) {
			toKill.die();
			Sound.StaticSound.gunSound();
		} else {
			Sound.StaticSound.collectableSound();
		}
		this.deleteSelf(tile);
		
	}

}
