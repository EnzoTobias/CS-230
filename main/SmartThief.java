import java.util.ArrayList;

public class SmartThief extends Thief {
	private final int ERROR_RETURN = -1;
	private int shortestPathLength(Tile nextTile,
			ArrayList<Tile> visitedTiles) {
		if (visitedTiles.contains(nextTile)) {
			return ERROR_RETURN;
		}
		visitedTiles.add(nextTile);

		if (nextTile == null) {
			return ERROR_RETURN;
		}

		if (this.getLevelControl().isAllLootCollected() && nextTile.hasItem()
				&& nextTile.getContainedItem() instanceof Door) {
			return 0;
		}

		if (nextTile.hasItem()
				&& (nextTile.getContainedItem() instanceof Collectable
						|| nextTile.getContainedItem() instanceof Lever)) {
			return 0;
		}

		int toReturn;
		int currentReturn;

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.LEFT, nextTile),
				visitedTiles);
		toReturn = currentReturn;

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.RIGHT, nextTile),
				visitedTiles);
		toReturn = chooseToReturn(toReturn, currentReturn);

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.UP, nextTile),
				visitedTiles);
		toReturn = chooseToReturn(toReturn, currentReturn);

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.DOWN, nextTile),
				visitedTiles);
		toReturn = chooseToReturn(toReturn, currentReturn);

		if (toReturn == ERROR_RETURN) {
			return ERROR_RETURN;
		}

		return toReturn + 1;

	}

	private int chooseToReturn(int toReturn, int currentReturn) {
		if (toReturn == ERROR_RETURN) {
			return currentReturn;
		}
		if (currentReturn != ERROR_RETURN && currentReturn < toReturn) {
			return currentReturn;
		}

		return toReturn;
	}

	@Override
	public boolean nextMove(Tile tile) {
		LevelControl control = this.getLevelControl();
		int shortest = ERROR_RETURN;
		Tile tileToMove = null;
		for (Direction direction : Direction.values()) {
			int currentReturn;
			currentReturn = shortestPathLength(
					this.getLevelControl().nextValidTile(direction,
							control.findTileByEntity(this)),
					new ArrayList<Tile>());
			shortest = chooseToReturn(shortest, currentReturn);
			if (currentReturn == shortest) {
				this.setDirection(direction);
				tileToMove = this.getLevelControl().nextValidTile(direction,
						control.findTileByEntity(this));
			}

		}
		if (tileToMove == null) {
			return false;
		}
		return this.getLevelControl().moveToTile(tileToMove.getX(),
				tileToMove.getY(), this);
	}

}
