import java.util.Timer;
import java.util.TimerTask;

public class Bomb extends Item {
	//stage 4 = not activated
	private final int EXPLOSION_STAGES = 4;
	private final long BOMB_DELAY = 10L;
	private int currentStage = EXPLOSION_STAGES;
	
	public Bomb() {
		// TODO Auto-generated constructor stub
	}
	
	private Timer timer = new Timer();
	
	@Override
	void itemEffect(Tile tile, WalkingEntity entity) {
		nextStage(tile);
	}
	
	private void nextStage(Tile tile) {
		currentStage -=1;
		if (currentStage == 0 && tile.hasItem()) {
			explode(tile);
		}
		
		TimerTask task = new TimerTask() {
			public void run() {
				nextStage(tile);
			}
		};
		timer.schedule(task, BOMB_DELAY);
		
	}
	
	private void explode(Tile tile) {
		Tile[][] tileGrid;
		tileGrid = tile.getLevel().getTileGrid();
		
		for (int i = tile.getX(); i <= tileGrid.length; i++) {
			bombDestroy(tileGrid[i][tile.getY()]);
		}
		for (int i = tile.getX(); i >= 0 ; i--) {
			bombDestroy(tileGrid[i][tile.getY()]);
		}
		
		for (int i = tile.getY(); i <= tileGrid[0].length; i++) {
			bombDestroy(tileGrid[tile.getX()][i]);
		}
		for (int i = tile.getY(); i >= 0; i--) {
			bombDestroy(tileGrid[tile.getX()][i]);
		}
	}
	
	private void bombDestroy(Tile tile) {
		if (tile.hasItem()) {
			tile.getContainedItem().deleteSelf(tile);
		}
		if (tile.hasEntity()) {
			tile.getContainedEntity().die();
		}
	}

}
