/**Represents any entity that walks on tiles
 * @author Enzo Tobias 2117781
 */
public abstract class WalkingEntity {
	private int score;

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public abstract void die();
}
