package coursework;
/**
 * Represents any entity that walks on tiles
 * 
 * @author Enzo Tobias 2117781
 */
public abstract class WalkingEntity {
	private static int lastID = 0;
	private int ID;
	private int score;
	private Direction direction;
	private LevelControl control;
	private Tile thisTile;

	/**
	 * Abstract method to inherit which represents an entity's automatic
	 * movement.
	 * 
	 * @param tile
	 *            The tile this entity is currently on.
	 * @return A boolean denoting if the movement was successful.
	 */
	public abstract boolean nextMove(Tile tile);

	/**
	 * Create a new walking entity with a unique ID.
	 */
	public WalkingEntity() {
		lastID += 1;
		this.ID = lastID;
	}
	/**
	 * Return this entity's score.
	 * 
	 * @return The score to return.
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Set this entity's score
	 * 
	 * @param score
	 *            The score to set.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public void die() {
		if (this.getThisTile().hasEntity() && this.getID() == this.getThisTile()
				.getContainedEntity().getID())
			;
		this.getThisTile().setContainedEntity(null);
	};

	public int getID() {
		return ID;
	}

	public LevelControl getLevelControl() {
		return control;
	}

	public void setLevelControl(LevelControl control) {
		this.control = control;
	}

	public Tile getThisTile() {
		return thisTile;
	}

	public void setThisTile(Tile thisTile) {
		this.thisTile = thisTile;
	}

}
