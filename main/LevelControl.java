import java.util.ArrayList;

/**
 * Represents a level running in the game and centralises behaviours
 * 
 * @author Enzo Tobias 2117781
 */
public class LevelControl {
	private Level level;
	private Player player;
	private ArrayList<WalkingEntity> entityList;
	public Level getLevel() {
		return level;
	}
	
	public void setLevel(Level level) {
		this.level = level;
	}

	public Tile[][] getTileGrid() {
		return level.getTileGrid();
	}
	
	
	public void oneMovementRound() {
		this.listEntities();
		for (WalkingEntity entity : entityList) {
			entity.nextMove(this.findTileByEntity(entity));
		}
		System.out.println(LevelFileReader.levelToString(this.getLevel()));
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

	public boolean isAllLootCollected() {
		Tile[][] tileGrid = this.getTileGrid();
		for (int i = 0; i < tileGrid.length; i++) {
			for (int j = 0; j < tileGrid[0].length; j++) {
				if (tileGrid[i][j].hasItem() && tileGrid[i][j]
						.getContainedItem() instanceof Collectable) {
					return false;
				}
			}
		}
		return true;
	}

	public Tile nextValidTile(Direction direction, Tile tile) {
		Tile[][] tileGrid = this.getTileGrid();
		switch (direction) {
			case RIGHT :
				for (int i = tile.getX() + 1; i < tileGrid.length; i++) {
					if (this.tileColourCheck(i, tile.getY(), tile)) {
						if (this.getLevel().safeGetTile(i, tile.getY())
								.isTileBlocked()) {
							return null;
						}
						return tileGrid[i][tile.getY()];
					}
				}
				break;
			case LEFT :
				for (int i = tile.getX() - 1; i >= 0; i--) {
					if (this.tileColourCheck(i, tile.getY(), tile)) {
						if (this.getLevel().safeGetTile(i, tile.getY())
								.isTileBlocked()) {
							return null;
						}
						return tileGrid[i][tile.getY()];
					}
				}
				break;
			case DOWN :
				for (int i = tile.getY() + 1; i < tileGrid[0].length; i++) {
					if (this.tileColourCheck(tile.getX(), i, tile)) {
						if (this.getLevel().safeGetTile(tile.getX(), i)
								.isTileBlocked()) {
							return null;
						}
						return tileGrid[tile.getX()][i];
					}
				}
				break;
			case UP :
				for (int i = tile.getY() - 1; i >= 0; i--) {
					if (this.tileColourCheck(tile.getX(), i, tile)) {
						if (this.getLevel().safeGetTile(tile.getX(), i)
								.isTileBlocked()) {
							return null;
						}
						return tileGrid[tile.getX()][i];
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
			if (tile != null && tile.hasItem()
					&& tile.getContainedItem() instanceof Bomb) {
				tile.getContainedItem().itemEffect(tile, entity);
			}
		}
		return true;
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

	public boolean tileColourCheck(int x, int y, Tile currentTile) {
		Tile tileToMove = level.safeGetTile(x, y);
		if (level.safeGetTile(x, y) == null) {
			return false;
		}

		for (Colour col : currentTile.getColours()) {
			for (Colour col2 : tileToMove.getColours()) {
				if (col == col2) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean flyingMove(int x, int y, WalkingEntity entity) {
		Tile previousTile = this.findTileByEntity(entity);
		
		if(level.safeGetTile(x, y) != null && level.safeGetTile(x, y).hasEntity()) {
			level.safeGetTile(x, y).getContainedEntity().die();
		}
		
		if (level.safeGetTile(x, y) != null) {
			previousTile.setContainedEntity(null);
			level.safeGetTile(x, y).setContainedEntity(entity);
			return true;
		}
		return false;
	}
	
	private void listEntities() {
		entityList = new ArrayList<WalkingEntity>();
		Tile[][] tileGrid = this.getTileGrid();
		for (int i = 0; i < tileGrid.length; i++) {
			for (int j = 0; j < tileGrid[0].length; j++) {
				if (tileGrid[i][j].hasEntity()) {
					entityList.add(tileGrid[i][j].getContainedEntity());
				}
			}
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}
