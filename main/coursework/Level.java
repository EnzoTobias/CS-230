

public class Level {
	private Tile[][] tileGrid;
	private int initialTime;
	public Tile[][] getTileGrid() {
		return tileGrid;
	}
	public void setTileGrid(Tile[][] tileGrid) {
		this.tileGrid = tileGrid;
	}
	public int getInitialTime() {
		return initialTime;
	}
	public void setInitialTime(int initialTime) {
		this.initialTime = initialTime;
	}
	
	public Tile safeGetTile(int x, int y) {
		try {
			return tileGrid[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
		return null;
	}
}
