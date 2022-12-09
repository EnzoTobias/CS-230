package coursework;

public class Gun extends Item {

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
