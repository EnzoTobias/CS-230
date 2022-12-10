package coursework;
/**
 * 	Represents a player profile.
 * @author Marcus Siziba
 *
 */
public class Profile {
    private String playerName;
    private int currentLevel;
    /**
     * Create a new profile with the given profile name and level unlocked.
     * @param playerName The name of the new profile.
     * @param currentLevel The level unlocked of the new profile.
     */
    public Profile(String playerName, int currentLevel){
        this.currentLevel = currentLevel;
        this.playerName = playerName;
    }
    /**
     * Returns the name of this profile.
     * @return The profile name.
     */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * Set the name of this profile.
     * @param playerName Name to set.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
    /**
     * Return the level unlocked integer of this profile.
     * @return The level unlocked integer of this profile.
     */
    public int getCurrentLevel() {
        return currentLevel;
    }
    /**
     * Set the level unlocked integer of this profile.
     * @param currentLevel The level unlocked integer of this profile to set.
     */
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }
}
