import java.util.ResourceBundle.Control;
import java.util.Timer;
import java.util.TimerTask;
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

	public Bomb() {
		// TODO Auto-generated constructor stub
	}

	private Timer timer;

	@Override
	void itemEffect(Tile tile, WalkingEntity entity) {
		timer = new Timer();
		nextStage(tile);
		
	}

	private void nextStage(Tile tile) {
		TimerTask task = new TimerTask() {
			public void run() {
				nextStage(tile);
			}
		};
		currentStage -= 1;
		if (currentStage == 0 && tile.hasItem()) {
			explode(tile);
			timer.cancel();
		} else {
			timer.schedule(task, BOMB_DELAY);
		}

	}

	public void explode(Tile tile) {
		Tile[][] tileGrid;
		tileGrid = tile.getLevelControl().getTileGrid();
		tile.setContainedItem(null);

		for (int i = tile.getX()+1; i < tileGrid.length; i++) {
			bombDestroy(tileGrid[i][tile.getY()]);
		}
		for (int i = tile.getX()-1; i >= 0; i--) {
			bombDestroy(tileGrid[i][tile.getY()]);
		}

		for (int i = tile.getY()+1; i < tileGrid[0].length; i++) {
			bombDestroy(tileGrid[tile.getX()][i]);
		}
		for (int i = tile.getY()-1; i >= 0; i--) {
			bombDestroy(tileGrid[tile.getX()][i]);
		}
	
	}

	private void bombDestroy(Tile tile) {
		if (tile.hasItem() && tile.getContainedItem() instanceof Bomb) {
			((Bomb)tile.getContainedItem()).explode(tile);
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
