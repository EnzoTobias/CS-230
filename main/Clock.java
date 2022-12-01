public class Clock extends Item {
	private final long TIME = 10l;


	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		tile.getLevelControl()
				.setTimeLeft(tile.getLevelControl().getTimeLeft() + TIME);

	}

}
