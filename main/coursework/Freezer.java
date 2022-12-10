package coursework;

import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;

public class Freezer extends Item {
	private Timer timer = new Timer();
	private final long FREEZE_TIME = 5000L;

	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {

		if (tile.getLevelControl().isGameOver() || !(entity instanceof Player)
				|| tile.getLevelControl().getMyMain().isFrozen()) {
			if (!tile.getLevelControl().getMyMain().isFrozen()) {
				timer.cancel();
			}
		} else {
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

	public void endFreeze(Tile tile) {
		if (!tile.getLevelControl().isGameOver()) {
			Platform.runLater(() -> {
				tile.getLevelControl().getMyMain().setFrozen(false);
			});
			timer.cancel();
		}

	}

}
