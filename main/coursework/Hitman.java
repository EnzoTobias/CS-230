package coursework;
import java.util.ArrayList;
import java.util.Random;

public class Hitman extends Thief {
	private static final Random RANDOM = new Random();
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
		
		if (nextTile.hasEntity() && nextTile.getContainedEntity() instanceof Player) {
			lastTile = null;
			visitedTiles = new ArrayList<Tile>();
			return 0;
		}

		int toReturn;
		int currentReturn;
		Direction correctDirection = null;
		ArrayList<Tile> tempVisited = new ArrayList<Tile>(visitedTiles);
		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTileForHitman(Direction.LEFT, nextTile),
				tempVisited, searchLevel+1 );
		toReturn = chooseToReturn(-1, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.LEFT;
		}

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTileForHitman(Direction.RIGHT, nextTile),
				tempVisited, searchLevel+1);
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.RIGHT;
		}

		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTileForHitman(Direction.UP, nextTile),
				tempVisited, searchLevel+1 );
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.UP;
		}


		currentReturn = shortestPathLength(
				this.getLevelControl().nextValidTileForHitman(Direction.DOWN, nextTile),
				tempVisited, searchLevel+1 );
		toReturn = chooseToReturn(toReturn, currentReturn);
		if (toReturn == currentReturn) {
			correctDirection = Direction.DOWN;
		}
		

		if (toReturn == ERROR_RETURN) {
			return ERROR_RETURN;
		}
		
		if (searchLevel == 0) {
			this.tileToMoveCheat = this.getLevelControl().nextValidTileForHitman(correctDirection, nextTile);
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


	public boolean makeMove(Tile tile) {
		this.tileToMoveCheat = null;
		Tile tileToMove = null;
		int currentShortest = shortestPathLength(tile,new ArrayList<Tile>(),0);
		tileToMove = this.tileToMoveCheat;
		
		Direction moveDirection = null;
		Direction[] directions = Direction.values();
		for (Direction direction : directions) {
			if (this.getLevelControl().nextValidTileForHitman(direction, tile) == tileToMove) {
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
		if (tileToMove.hasEntity() && tileToMove.getContainedEntity() instanceof Player) {
			tileToMove.getContainedEntity().die();
			return true;
		} else {
			return this.getLevelControl().moveToTile(tileToMove.getX(),
					tileToMove.getY(), this);
		}
		
	}
	
	@Override
	public boolean nextMove(Tile tile) {
		
		return this.makeMove(this.getThisTile());
	}

}
