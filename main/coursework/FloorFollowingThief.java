
/**
 * Implementation of the floor following thief.
 * @author Enzo Tobias 2117781
 *
 */
public class FloorFollowingThief extends Thief {

	private Colour colour;
	/**
	 * Returns this entity's colour.
	 * @return Colour to be returned.
	 */
	public Colour getColour() {
		return colour;
	}
	/**
	 * Sets this entity's colour
	 * @param colour Colour to be set.
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	/**
	 * Processes the movement logic for this instance and returns a tile as the next move.
	 * @param currentTile The tile this entity is currently on.
	 * @return The tile this entity should move to.
	 */
	private Tile movementLogic(Tile currentTile) {
		Tile[][] tileGrid = this.getLevelControl().getTileGrid();
		LevelControl control = this.getLevelControl();
		Direction leftAbsolute = relativeDirection(this.getDirection(),
				Direction.LEFT);
		Direction rightAbsolute = relativeDirection(this.getDirection(),
				Direction.RIGHT);
		Direction upAbsolute = relativeDirection(this.getDirection(),
				Direction.UP);
		Direction downAbsolute = relativeDirection(this.getDirection(),
				Direction.DOWN);
		Tile tileToLeft = tileInDirection(currentTile, leftAbsolute);
		Tile tileToRight = tileInDirection(currentTile, rightAbsolute);
		Tile tileToUp = tileInDirection(currentTile, upAbsolute);
		Tile tileToDown = tileInDirection(currentTile, downAbsolute);

		if (tileToLeft != null && control.canMoveToTile(tileToLeft.getX(),
				tileToLeft.getY(), this)) {
			this.setDirection(leftAbsolute);
			return tileToLeft;
		}
		if (tileToUp != null && control.canMoveToTile(tileToUp.getX(),
				tileToUp.getY(), this)) {
			this.setDirection(upAbsolute);
			return tileToUp;
		}
		if (tileToRight != null && control.canMoveToTile(tileToRight.getX(),
				tileToRight.getY(), this)) {
			this.setDirection(rightAbsolute);
			return tileToRight;
		}
		if (tileToDown != null && control.canMoveToTile(tileToDown.getX(),
				tileToDown.getY(), this)) {
			this.setDirection(downAbsolute);
			return tileToDown;
		}

		return currentTile;
	}
	/**
	 * Returns the first tile in a direction.
	 * @param tile The current tile.
	 * @param direction The direction.
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
	 * Returns the real direction given a direction this entity is facing and the direction wanted relative to itself. 
	 * @param facingDir The direction this entity should be facing.
	 * @param wantedDir The wanted relative direction.
	 * @return The real direction.
	 */
	private Direction relativeDirection(Direction facingDir,
			Direction wantedDir) {
		int shiftAmount = 0;
		switch (facingDir) {
			case UP :
				shiftAmount = 0;
				break;
			case RIGHT :
				shiftAmount = 1;
				break;
			case DOWN :
				shiftAmount = 2;
				break;
			case LEFT :
				shiftAmount = 3;
				break;
		}

		return directionShiftClockwise(shiftAmount, wantedDir);
	}
	/**
	 * Shifts a given direction clockwise a given amount of times.
	 * @param amount Amount of times to shift.
	 * @param direction Original direction.
	 * @return New shifted direction.
	 */
	private Direction directionShiftClockwise(int amount, Direction direction) {
		Direction up = Direction.UP;
		Direction right = Direction.RIGHT;
		Direction down = Direction.DOWN;
		Direction left = Direction.LEFT;

		for (int i = 0; i < amount; i++) {
			Direction tempUp = up;
			Direction tempRight = right;
			Direction tempDown = down;
			Direction tempLeft = left;

			up = tempRight;
			right = tempDown;
			down = tempLeft;
			left = tempUp;
		}

		switch (direction) {
			case UP :
				return up;
			case RIGHT :
				return right;
			case DOWN :
				return down;
			case LEFT :
				return left;
		}

		return null;
	}
	/**
	 * Trigger this entity's next move
	 * @param tile The tile this entity is currently on.
	 * @return Boolean denoting if the move succeeded.
	 */
	@Override
	public boolean nextMove(Tile tile) {
		Tile nextTile = movementLogic(tile);
		if (this.getLevelControl().moveToTile(nextTile.getX(), nextTile.getY(),
				this)) {
			return true;
		}
		return false;
	}

}
