package coursework;
import java.util.ArrayList;
import java.util.Random;

public class SmartThief extends Thief {
	private final Random RANDOM = new Random();
	private final int ERROR_RETURN = -1;
	private final int MAX_STEPS = 15;
	private Tile lastTile;
	private Tile tileToMoveCheat;
	
	private int shortestPathLength(Tile nextTile,
			ArrayList<Tile> visitedTiles, int searchLevel) {
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
						|| nextTile.getContainedItem() instanceof Clock)) {
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
				tempVisited, searchLevel+1 );
		toReturn = chooseToReturn(-1, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.LEFT;
		}

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.RIGHT, nextTile),
				tempVisited, searchLevel+1);
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.RIGHT;
		}

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.UP, nextTile),
				tempVisited, searchLevel+1 );
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.UP;
		}


		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTile(Direction.DOWN, nextTile),
				tempVisited, searchLevel+1 );
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.DOWN;
		}
		

		if (toReturn == ERROR_RETURN) {
			return ERROR_RETURN;
		}
		
		if (searchLevel == 0) {
			this.tileToMoveCheat = this.getLevelControl().nextValidTile(correctDirection, nextTile);
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
		this.tileToMoveCheat = null;
		Tile tileToMove = null;
		int currentShortest = shortestPathLength(tile,new ArrayList<Tile>(),0);
		tileToMove = this.tileToMoveCheat;
		
		Direction moveDirection = null;
		Direction[] directions = Direction.values();
		for (Direction direction : directions) {
			if (this.getLevelControl().nextValidTile(direction, tile) == tileToMove) {
				moveDirection = direction;
			}
		}
		
		if (tileToMove == null) {
			Direction randomDirection = directions[RANDOM.nextInt(directions.length)];
			moveDirection = randomDirection;
			tileToMove =  this.getLevelControl().nextValidTile(randomDirection,
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
