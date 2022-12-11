package coursework;
/**
 * Implementation of the flying assassin.
 * 
 * @author Enzo Tobias 2117781
 *
 */
public class FlyingAssassin extends WalkingEntity {
	/**
	 * Processes the movement logic for this instance and returns a tile as the
	 * next move.
	 * 
	 * @param currentTile
	 *            The current tile this entity is on.
	 * @return The tile to move to.
	 */
	private Tile movementLogic(Tile currentTile) {
		Tile tileToMove = tileInDirection(currentTile, this.getDirection());
		if (tileToMove == null) {
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
	/**
	 * Returns the first tile in a direction.
	 * 
	 * @param tile
	 *            The current tile.
	 * @param direction
	 *            The direction.
	 * @return The first tile in that direction.
	 */
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
	/**
	 * Trigger this entity's next move
	 * 
	 * @param tile
	 *            The tile this entity is currently on.
	 * @return Boolean denoting if the move succeeded.
	 */
	@Override
	public boolean nextMove(Tile tile) {
		Tile tileToMove = movementLogic(tile);
		this.getLevelControl().flyingMove(tileToMove.getX(), tileToMove.getY(),
				this);
		return false;
	}

}
