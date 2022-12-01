
public class FloorFollowingThief extends Thief {

	private Colour colour;

	@Override
	public void die() {
		// TODO Auto-generated method stub

	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

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
