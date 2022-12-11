package coursework;
/**
 * Represents a level.
 * 
 * @author Enzo Tobias 2117781
 *
 */
public class Level {
	private Tile[][] tileGrid;
	private int initialTime;
	/**
	 * Return this level's tilegrid.
	 * 
	 * @return The tilegrid to return.
	 */
	public Tile[][] getTileGrid() {
		return tileGrid;
	}
	/**
	 * Set this level's tilegrid.
	 * 
	 * @param tileGrid
	 *            The tilegrid to set.
	 */
	public void setTileGrid(Tile[][] tileGrid) {
		this.tileGrid = tileGrid;
	}
	/**
	 * Returns this level's initial time.
	 * 
	 * @return The initial time to return.
	 */
	public int getInitialTime() {
		return initialTime;
	}
	/**
	 * Set this level's initial time.
	 * 
	 * @param initialTime
	 *            The initial time to set.
	 */
	public void setInitialTime(int initialTime) {
		this.initialTime = initialTime;
	}
	/**
	 * Return a tile from the tilegrid safely with safeguards against null
	 * pointer exception.
	 * 
	 * @param x
	 *            The X coordinate of the tile to return.
	 * @param y
	 *            The Y coordinate of the tile to return.
	 * @return The tile safely returned.
	 */
	public Tile safeGetTile(int x, int y) {
		try {
			return tileGrid[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {

		}
		return null;
	}
}
