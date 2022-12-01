public class Door extends Item {

	public Door() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		if (tile.getLevelControl().isAllLootCollected()) {
			if (entity instanceof Player) {
				tile.getLevelControl().playerWin();
			} else {
				tile.getLevelControl().playerLose();;
			}
		}

	}

}
