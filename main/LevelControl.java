/**
 * Represents a level running in the game and centralises behaviours
 * 
 * @author Enzo Tobias 2117781
 */
public class LevelControl {
	private Level level;

	public LevelControl(Level level) {
		this.level = level;
	}

	public Tile[][] getTileGrid() {
		return level.getTileGrid();
	}
}
