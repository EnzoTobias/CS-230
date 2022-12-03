package coursework;
import java.util.ArrayList;
import java.util.Random;

public class SmartThief extends Thief {
	private final int ERROR_RETURN = -1;
	private Tile lastTile;
	private static final Random RANDOM = new Random();
	
	private int shortestPathLength(Tile nextTile,
			ArrayList<Tile> visitedTiles) {
		if (visitedTiles.contains(nextTile)) {
			return ERROR_RETURN;
		}
		if (nextTile == this.lastTile) {
			return ERROR_RETURN;
		}
		
		visitedTiles.add(nextTile);

		if (nextTile == null) {
			return ERROR_RETURN;
		}

		if (this.getLevelControl().isAllLootCollected() && nextTile.hasItem()
				&& nextTile.getContainedItem() instanceof Door) {
			visitedTiles = new ArrayList<Tile>();
			lastTile = null;
			return 0;
		}

		if (nextTile.hasItem()
				&& (nextTile.getContainedItem() instanceof Collectable
						|| nextTile.getContainedItem() instanceof Lever
						|| nextTile.getContainedItem() instanceof Clock)) {
			visitedTiles = new ArrayList<Tile>();
			lastTile = null;
			return 0;
		}

		int toReturn;
		int currentReturn;
		ArrayList<Tile> tempVisited = new ArrayList<Tile>(visitedTiles);
		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.LEFT, nextTile),
				tempVisited );
		toReturn = chooseToReturn(-1, currentReturn);

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.RIGHT, nextTile),
				tempVisited );
		toReturn = chooseToReturn(toReturn, currentReturn);

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.UP, nextTile),
				tempVisited );
		toReturn = chooseToReturn(toReturn, currentReturn);

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.DOWN, nextTile),
				tempVisited );
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
		if (currentReturn != ERROR_RETURN && currentReturn <= toReturn) {
			return currentReturn;
		}

		return toReturn;
	}

	@Override
	public boolean nextMove(Tile tile) {
		LevelControl control = this.getLevelControl();
		int shortest = ERROR_RETURN;
		Tile tileToMove = null;
		int currentShortest = shortestPathLength(tile,new ArrayList<Tile>());
		for (Direction direction : Direction.values()) {
			int currentReturn;
			currentReturn = shortestPathLength(
					this.getLevelControl().nextValidTile(direction,
							control.findTileByEntity(this)),
					new ArrayList<Tile>());
			shortest = chooseToReturn(shortest, currentReturn);
			if (currentReturn == currentShortest - 1) {
				this.setDirection(direction);
				tileToMove = this.getLevelControl().nextValidTile(direction,
						control.findTileByEntity(this));
			}

		}
		
		
		Direction[] directions = Direction.values();
		Direction randomDirection = directions[RANDOM.nextInt(directions.length)];
		
		if (shortest == ERROR_RETURN) {
			tileToMove =  this.getLevelControl().nextValidTile(randomDirection,
					control.findTileByEntity(this));
		}
		
		if (tileToMove == null) {
			return false;
		}
		this.lastTile = tile;
		return this.getLevelControl().moveToTile(tileToMove.getX(),
				tileToMove.getY(), this);
	}

}
