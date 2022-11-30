
public class FlyingAssassin extends WalkingEntity {

	private Tile movementLogic(Tile currentTile) {
		Tile tileToMove = tileInDirection(currentTile, this.getDirection());
		if(tileToMove == null)  {
			Direction newDirection = null;
			switch (this.getDirection()) {
				case UP :
					newDirection = Direction.DOWN;
					break;
				case RIGHT :
					newDirection = Direction.LEFT;
					break;
				case DOWN :
					newDirection = Direction.UP;
					break;
				case LEFT :
					newDirection = Direction.RIGHT;
					break;
			}
			this.setDirection(newDirection);
			tileToMove = tileInDirection(currentTile, newDirection);
		}
		return tileToMove;
	}

	private Tile tileInDirection(Tile tile, Direction direction) {
		Level level = this.getLevelControl().getLevel();
		switch (direction) {
			case UP :
				return level.safeGetTile(tile.getX(), tile.getY() - 1);
			case RIGHT :
				return level.safeGetTile(tile.getX() + 1, tile.getY());
			case DOWN :
				return level.safeGetTile(tile.getX(), tile.getY() + 1);
			case LEFT :
				return level.safeGetTile(tile.getX() - 1, tile.getY());
		}
		return null;
	}

	@Override
	public boolean nextMove(Tile tile) {
		Tile tileToMove = movementLogic(tile);
		this.getLevelControl().flyingMove(tileToMove.getX(), tileToMove.getY(), this);
		return false;
	}

}
