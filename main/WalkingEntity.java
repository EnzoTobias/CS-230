/**
 * Represents any entity that walks on tiles
 * 
 * @author Enzo Tobias 2117781
 */
public abstract class WalkingEntity {
	private int score;
	private Direction direction;

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

	public abstract void die();

}
