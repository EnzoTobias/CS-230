package coursework;
/**
 * Represents an entry into a scoreboard.
 * 
 * @author Enzo Tobias 2117781
 *
 */
public class ScoreEntry {
	private String profileName;
	private int score;
	/**
	 * Returns this entry's score.
	 * 
	 * @return The score to return.
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Set this entry's score.
	 * 
	 * @param score
	 *            The score to set.
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * Returns this entry's profile name.
	 * 
	 * @return The profile name to return.
	 */
	public String getProfileName() {
		return profileName;
	}
	/**
	 * Sets this entry's profile name.
	 * 
	 * @param profileName
	 *            Profile name to set.
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
}
