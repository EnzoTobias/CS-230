package coursework;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
/**
 * Implementation of the freezer extra feature which stops time when picked up
 * by a player.
 * 
 * @author EnzoTobias 2117781
 *
 */
public class Freezer extends Item {
	private Timer timer;
	private final long FREEZE_TIME = 5000L;
	/**
	 * The effect of this item when being picked up. It freezes time for a short
	 * period if picked up by the player.
	 * 
	 * @param tile
	 *            The tile this item is on.
	 * @param entity
	 *            The entity triggering this item effect.
	 */
	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {

		if (tile.getLevelControl().isGameOver() || !(entity instanceof Player)
				|| tile.getLevelControl().getMyMain().isFrozen()) {
			if (!tile.getLevelControl().getMyMain().isFrozen()) {
				// timer.cancel();
			}
		} else {
			timer = new Timer();
			Platform.runLater(() -> {
				tile.getLevelControl().getMyMain().setFrozen(true);
			});
			Sound.StaticSound.collectableSound();
			TimerTask task = new TimerTask() {
				public void run() {

					endFreeze(tile);
				}
			};
			timer.schedule(task, FREEZE_TIME);
		}
		if (!tile.getLevelControl().getMyMain().isFrozen()) {
			this.deleteSelf(tile);
		}

	}
	/**
	 * Ends the freeze time effect after the short period of action has elapsed.
	 * 
	 * @param tile
	 *            The tile this entity was on when it was activated.
	 */
	public void endFreeze(Tile tile) {
		if (!tile.getLevelControl().isGameOver()) {
			Platform.runLater(() -> {
				tile.getLevelControl().getMyMain().setFrozen(false);
			});
			timer.cancel();
		}

	}

}
