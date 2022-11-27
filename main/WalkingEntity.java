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


	public abstract boolean nextMove(Tile tile);
	
	
	public WalkingEntity() {
		lastID += 1;
		this.ID = lastID;
	}
	
	public int getScore() {
		return score;
	}

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
		this.getLevelControl().findTileByEntity(this).setContainedEntity(null);
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

}
