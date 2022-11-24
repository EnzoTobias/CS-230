/**
 * Represents a level running in the game and centralises behaviours
 * 
 * @author Enzo Tobias 2117781
 */
public class LevelControl {
	private Level level;
	private Player player;
	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public Tile[][] getTileGrid() {
		return level.getTileGrid();
	}

	public Tile findTileByEntity(WalkingEntity entity) {
		Tile[][] tileGrid = level.getTileGrid();
		for (int i = 0; i < tileGrid.length; i++) {
			for (int j = 0; j < tileGrid[0].length; j++) {
				if (tileGrid[i][j].hasEntity() && tileGrid[i][j]
						.getContainedEntity().getID() == entity.getID()) {
					return tileGrid[i][j];

				}
			}
		}
		return null;
	}

	public boolean moveToTile(int x, int y, WalkingEntity entity) {
		Tile previousTile = this.findTileByEntity(entity);
		Tile tileToMove = level.safeGetTile(x, y);
		if (!canMoveToTile(x, y, entity)) {
			return false;
		}
		tileToMove.setContainedEntity(entity);
		previousTile.setContainedEntity(null);
		if (tileToMove.hasItem()) {
			tileToMove.getContainedItem().itemEffect(tileToMove, entity);
		}

		Tile[] neighbouringTiles = {level.safeGetTile(x + 1, y),
				level.safeGetTile(x - 1, y), level.safeGetTile(x, y + 1),
				level.safeGetTile(x, y - 1)};
		for (Tile tile : neighbouringTiles) {
			if (tile != null && tile.hasItem() && tile.getContainedItem() instanceof Bomb) {
				tile.getContainedItem().itemEffect(tile, entity);
			}
		}
		return false;
	}

	public boolean canMoveToTile(int x, int y, WalkingEntity entity) {
		Tile previousTile = this.findTileByEntity(entity);
		Tile tileToMove = level.safeGetTile(x, y);
		if (level.safeGetTile(x, y) == null) {
			return false;
		}
		
		if (tileToMove.isTileBlocked()) {
			return false;
		}

		for (Colour col : previousTile.getColours()) {
			for (Colour col2 : tileToMove.getColours()) {
				if (col == col2) {
					return true;
				}
			}
		}

		return false;
	}
	
	public void flyingMove(int x, int y, WalkingEntity entity) {
		Tile previousTile = this.findTileByEntity(entity);
		if (level.safeGetTile(x, y) != null) {
			previousTile.setContainedEntity(null);
			level.safeGetTile(x, y).setContainedEntity(entity);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
