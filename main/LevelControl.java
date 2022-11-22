/**
 * Represents a level running in the game and centralises behaviours
 * 
 * @author Enzo Tobias 2117781
 */
public class LevelControl {
	private Tile[][] tileGrid;

	public LevelControl(Tile[][] tileGrid) {
		this.tileGrid = tileGrid;
	}

	public Tile[][] getTileGrid() {
		return tileGrid;
	}
}
