package coursework;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

/**
 * Centralises behaviours around the manipulation of the level and movement.
 * Provides utilities to Tile and Entity classes which are used for their
 * respective behaviours.
 * 
 * @author Enzo Tobias 2117781
 */
public class LevelControl {
	private final long ONE_TIME_UNIT = 1000l;
	private final int MOVEMENT_EVERY = 2;
	private Level level;
	private Player player;
	private ArrayList<WalkingEntity> entityList;
	public int timeLeft;
	// private Timer timer = new Timer();
	private boolean isGameOver = false;
	private int movementProgression = 0;
	public boolean isLootCollected;

	public boolean isLootCollected() {
		return isLootCollected;
	}
	public void setLootCollected(boolean isLootCollected) {
		this.isLootCollected = isLootCollected;
	}

	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public void displayGrid() {
		System.out.println(LevelFileReader.levelToString(this.getLevel()));
	}

	public Level getLevel() {
		return level;
	}
	/**
	 * Sets a level class to be handled by this LevelControl instance.
	 * 
	 * @param level
	 *            The level to be set.
	 */
	public void setLevel(Level level) {
		this.level = level;
		this.timeLeft = level.getInitialTime();
	}
	/**
	 * Returns the tilegrid of this instance's level instance for ease of
	 * access.
	 * 
	 * @return This instance's level instance tilegrid.
	 */
	public Tile[][] getTileGrid() {
		return level.getTileGrid();
	}
	/**
	 * One round of movement for all the entities.
	 */
	public void oneMovementRound() {
		this.listEntities();
		for (WalkingEntity entity : entityList) {
			if (!(entity instanceof Player)) {
				entity.nextMove(entity.getThisTile());
			}

		}
		displayGrid();

	}

	public void timeProgression() {
		if (!this.isGameOver) {
			movementProgression += 1;
			if (movementProgression == MOVEMENT_EVERY) {
				movementProgression = 0;
				this.oneMovementRound();
			}
			this.timeLeft -= 1;
			TimerTask task = new TimerTask() {
				public void run() {
					timeProgression();
				}
			};
			// timer.schedule(task, ONE_TIME_UNIT);
		} else {
			// timer.cancel();
		}
	}
	/**
	 * Locates and returns the tile that the given entity is currently on.
	 * 
	 * @param entity
	 *            The entity being looked for.
	 * @return The tile which the entity is on.
	 */
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
	/**
	 * Checks the tilegrid to see if all loot or levers have been collected.
	 * 
	 * @return A boolean value denoting if all loot has been collected.
	 */
	public boolean isAllLootCollected() {
		Tile[][] tileGrid = this.getTileGrid();
		for (int i = 0; i < tileGrid.length; i++) {
			for (int j = 0; j < tileGrid[0].length; j++) {
				if (tileGrid[i][j].hasItem() && (tileGrid[i][j]
						.getContainedItem() instanceof Collectable
						|| tileGrid[i][j]
								.getContainedItem() instanceof Lever)) {
					return false;
				}
			}
		}
		return true;
	}

	public void updateLootCollectedStatus() {
		this.setLootCollected(isAllLootCollected());
	}

	public WalkingEntity closestEntityToTile(Tile tile) {
		this.listEntities();
		int distance = 0;
		WalkingEntity returnEntity = null;
		for (WalkingEntity entity : entityList) {
			Tile entityTile = entity.getThisTile();
			int tempDistance = (int) Math
					.sqrt(Math.pow(tile.getX() - entityTile.getX(), 2)
							+ Math.pow(tile.getY() - entityTile.getY(), 2));
			if ((tempDistance < distance || distance == 0) && tempDistance != 0) {
				distance = tempDistance;
				returnEntity =  entity;
			}
		}

		return returnEntity;
	}

	public void playerWin() {
		displayGrid();

		System.out.println("you win gg");
		this.isGameOver = true;
	}

	public void playerLose() {
		displayGrid();
		System.out.println("you snooze you lose");
		this.isGameOver = true;
	}
	/**
	 * Returns the next valid tile that an entity can move to in a given
	 * direction.
	 * 
	 * @param direction
	 *            The direction which the entity is attempting to move in.
	 * @param tile
	 *            The tile which the entity is currently on.
	 * @return The first tile (if not null) which the entity can move to in the
	 *         given direction.
	 */
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
	/**
	 * Initiates movement of an entity into a tile. Triggers any appropriate
	 * item effects or other behaviours on movement. Will only move if the move
	 * is valid.
	 * 
	 * @param x
	 *            The x coordinate of the tile to move to.
	 * @param y
	 *            The Y coordinate of the tile to move to.
	 * @param entity
	 *            The entity attempting the move.
	 * @return Boolean value denoting if the move succeeded.
	 */
	public boolean moveToTile(int x, int y, WalkingEntity entity) {
		Tile previousTile = entity.getThisTile();
		Tile tileToMove = level.safeGetTile(x, y);
		if (!canMoveToTile(x, y, entity)) {
			return false;
		}
		tileToMove.setContainedEntity(entity);
		entity.setThisTile(tileToMove);
		if(previousTile.hasEntity() && previousTile.getContainedEntity().getID() == entity.getID()) {
			previousTile.setContainedEntity(null);
		}
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
	/**
	 * Checks if a movement of an entity is valid.
	 * 
	 * @param x
	 *            The x coordinate of the tile to check for move validity.
	 * @param y
	 *            The Y coordinate of the tile to check for move validity.
	 * @param entity
	 *            The entity attempting the move.
	 * @return Boolean value denoting if the move is valid.
	 */
	public boolean canMoveToTile(int x, int y, WalkingEntity entity) {
		Tile previousTile = entity.getThisTile();
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
	/**
	 * Checks if two tiles have at least one matching colour.
	 * 
	 * @param x
	 *            The x coordinate of the tile to check for colour match.
	 * @param y
	 *            The Y coordinate of the tile to check for colour match.
	 * @param currentTile
	 *            The tile being checked against.
	 * @return Boolean value denoting if there is a colour match.
	 */
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
				if (col2 == Colour.VOID) {
					return true;
				}
			}
		}
		
		

		return false;
	}
	/**
	 * Movement and appropriate effects for flying assassin. Destroys entities
	 * on the tile being moved to. No checks of colour or obstruction are made.
	 * Items are not activated.
	 * 
	 * @param x
	 *            The x coordinate of the tile to move to.
	 * @param y
	 *            The Y coordinate of the tile to move to.
	 * @param entity
	 *            The entity attempting the move.
	 * @return Boolean value denoting if the move succeeded.
	 */
	public boolean flyingMove(int x, int y, WalkingEntity entity) {
		Tile previousTile = entity.getThisTile();

		if (level.safeGetTile(x, y) != null
				&& level.safeGetTile(x, y).hasEntity()) {
			level.safeGetTile(x, y).getContainedEntity().die();
		}

		if (level.safeGetTile(x, y) != null && !level.safeGetTile(x, y).hasEntity()) {
			if(previousTile.hasEntity() && previousTile.getContainedEntity().getID() == entity.getID()) {
				previousTile.setContainedEntity(null);
			}
			level.safeGetTile(x, y).setContainedEntity(entity);
			entity.setThisTile(level.safeGetTile(x, y));
			return true;
		}
		return false;
	}
	/**
	 * Updates the entityList ArrayList in this instance with every entity in
	 * the grid.
	 */
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
	/**
	 * Returns a reference to the player entity in the tilegrid. There should
	 * only be one player in any level.
	 * 
	 * @return The player returned.
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * Sets the player for the level.
	 * 
	 * @param player
	 *            Player to be set.
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	public boolean isGameOver() {
		return isGameOver;
	}
}
