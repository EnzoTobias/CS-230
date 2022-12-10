package coursework;
import java.util.ArrayList;
import java.util.Random;
/**
 * Implementation of the smart thief.
 * @author Enzo Tobias 2117781
 *
 */
public class SmartThief extends Thief {
	private final Random RANDOM = new Random();
	private final int ERROR_RETURN = -1;
	private final int MAX_STEPS = 15;
	private Tile lastTile;
	private Tile tileToMoveCheat;
	/**
	 * Returns the length of the shortest path to the closest loot.
	 * @param nextTile The tile to start the pathfinding from.
	 * @param visitedTiles An array of every tile visited by this pathfinding chain.
	 * @param searchLevel The depth of this search (through how many tiles).
	 * @return The length of the shortest path to the closest loot.
	 */
	private int shortestPathLength(Tile nextTile, ArrayList<Tile> visitedTiles,
			int searchLevel) {
		if (visitedTiles.contains(nextTile)) {
			return ERROR_RETURN;
		}

		if (searchLevel > MAX_STEPS) {
			return ERROR_RETURN;
		}

		if (nextTile == this.lastTile) {
			return ERROR_RETURN;
		}

		visitedTiles.add(nextTile);

		if (nextTile == null) {
			return ERROR_RETURN;
		}

		if (this.getLevelControl().isLootCollected && nextTile.hasItem()
				&& nextTile.getContainedItem() instanceof Door) {
			visitedTiles = new ArrayList<Tile>();
			lastTile = null;
			return 0;
		}

		if (nextTile.hasItem()
				&& (nextTile.getContainedItem() instanceof Collectable
						|| nextTile.getContainedItem() instanceof Lever
						|| nextTile.getContainedItem() instanceof Clock
						|| nextTile.getContainedItem() instanceof Freezer
						|| nextTile.getContainedItem() instanceof Gun)) {
			visitedTiles = new ArrayList<Tile>();
			lastTile = null;
			return 0;
		}

		int toReturn;
		int currentReturn;
		Direction correctDirection = null;
		ArrayList<Tile> tempVisited = new ArrayList<Tile>(visitedTiles);
		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.LEFT, nextTile),
				tempVisited, searchLevel + 1);
		toReturn = chooseToReturn(-1, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.LEFT;
		}

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.RIGHT, nextTile),
				tempVisited, searchLevel + 1);
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.RIGHT;
		}

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.UP, nextTile),
				tempVisited, searchLevel + 1);
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.UP;
		}

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.DOWN, nextTile),
				tempVisited, searchLevel + 1);
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.DOWN;
		}

		if (toReturn == ERROR_RETURN) {
			return ERROR_RETURN;
		}

		if (searchLevel == 0) {
			this.tileToMoveCheat = this.getLevelControl()
					.nextValidTile(correctDirection, nextTile);
		}

		return toReturn + 1;

	}
	/**
	 * Choose the least value accounting for error returns.
	 * @param toReturn First value to check against the other.
	 * @param currentReturn Second value to check against the other.
	 * @return The chosen least value accounting for error returns.
	 */
	private int chooseToReturn(int toReturn, int currentReturn) {
		if (toReturn == ERROR_RETURN) {
			return currentReturn;
		}
		if (currentReturn != ERROR_RETURN && currentReturn <= toReturn) {
			return currentReturn;
		}
		return toReturn;
	}
	/**
	 * Move to the next tile of the shortest path to the target (the closest loot).
	 * @param tile The tile to move to.
	 * @return If the move was successful.
	 */
	@Override
	public boolean nextMove(Tile tile) {
		this.tileToMoveCheat = null;
		Tile tileToMove = null;
		int currentShortest = shortestPathLength(tile, new ArrayList<Tile>(),
				0);
		tileToMove = this.tileToMoveCheat;

		Direction moveDirection = null;
		Direction[] directions = Direction.values();
		for (Direction direction : directions) {
			if (this.getLevelControl().nextValidTile(direction,
					tile) == tileToMove) {
				moveDirection = direction;
			}
		}

		if (tileToMove == null) {
			Direction randomDirection = directions[RANDOM
					.nextInt(directions.length)];
			moveDirection = randomDirection;
			tileToMove = this.getLevelControl().nextValidTile(randomDirection,
					this.getLevelControl().findTileByEntity(this));
		}
		if (tileToMove == null) {
			return false;
		}

		this.lastTile = tile;
		this.setDirection(moveDirection);
		return this.getLevelControl().moveToTile(tileToMove.getX(),
				tileToMove.getY(), this);
	}

}
