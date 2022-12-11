package coursework;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
/**
 * Represents the bomb item.
 * 
 * @author Enzo Tobias 2117781
 */
public class Bomb extends Item {
	// stage 4 = not activated
	private final int EXPLOSION_STAGES = 4;
	private final long BOMB_DELAY = 1000L;
	private int currentStage = EXPLOSION_STAGES;
	private Timer timer;
	private boolean aboutToKaboom;

	public int getCurrentStage() {
		return currentStage;
	}

	/**
	 * Initiates the Bomb instance's countdown to explosion.
	 * 
	 * @param tile
	 *            The tile that the Bomb instance is on, where to initiate the
	 *            explosion.
	 * @param entity
	 *            The entity that triggered the Bomb instance.
	 */
	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		if (aboutToKaboom == false) {
			timer = new Timer();
			aboutToKaboom = true;
			nextStage(tile);
		}

	}
	/**
	 * Schedules the next stage as a new task and calls the explosion when it
	 * reaches the last stage.
	 * 
	 * @param tile
	 *            The tile that the Bomb instance is on, where to initiate the
	 *            explosion.
	 */
	private void nextStage(Tile tile) {
		if (tile.getLevelControl().isGameOver()) {
			timer.cancel();
		} else {
			TimerTask task = new TimerTask() {
				public void run() {

					nextStage(tile);
				}
			};
			Platform.runLater(() -> {
				if (!tile.getLevelControl().getMyMain().isPaused()) {
					currentStage -= 1;
					Sound.StaticSound.bombTick();
				}
			});

			Platform.runLater(() -> {
				tile.getLevelControl().getMyMain().drawGame();
			});
			Platform.runLater(() -> {
				if (!tile.getLevelControl().getMyMain().isPaused()) {
					if (tile.hasItem()) {
						if (currentStage == 0) {
							explode(tile);
							timer.cancel();
						} else {
							timer.schedule(task, BOMB_DELAY);
						}
					}
				} else {
					timer.schedule(task, BOMB_DELAY);
				}
			});

		}

	}

	/**
	 * Activates the Bomb instance's explosion, affecting tiles appropriately in
	 * every direction.
	 * 
	 * @param tile
	 *            The tile that the Bomb instance is on, where to initiate the
	 *            explosion.
	 */
	public void explode(Tile tile) {
		Tile[][] tileGrid;
		tileGrid = tile.getLevelControl().getTileGrid();
		tile.setContainedItem(null);

		for (int i = tile.getX() + 1; i < tileGrid.length; i++) {
			bombDestroy(tileGrid[i][tile.getY()]);
		}
		for (int i = tile.getX() - 1; i >= 0; i--) {
			bombDestroy(tileGrid[i][tile.getY()]);
		}

		for (int i = tile.getY() + 1; i < tileGrid[0].length; i++) {
			bombDestroy(tileGrid[tile.getX()][i]);
		}
		for (int i = tile.getY() - 1; i >= 0; i--) {
			bombDestroy(tileGrid[tile.getX()][i]);
		}
		Sound.StaticSound.bombExplosion();
		Platform.runLater(() -> {
			tile.getLevelControl().getMyMain().drawGame();
		});

	}
	/**
	 * Applies the appropriate behaviour on tiles affected by the explosion.
	 * 
	 * @param tile
	 *            The tile that the Bomb instance is on, where to initiate the
	 *            explosion.
	 */
	private void bombDestroy(Tile tile) {
		if (tile.hasItem() && tile.getContainedItem() instanceof Bomb) {
			((Bomb) tile.getContainedItem()).explode(tile);
		}

		if (tile.hasItem() && !((tile.getContainedItem() instanceof Door)
				|| (tile.getContainedItem() instanceof Gate))) {
			tile.getContainedItem().deleteSelf(tile);
		}
		if (tile.hasEntity()) {
			tile.getContainedEntity().die();
		}
	}

}
