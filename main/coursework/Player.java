package coursework;
import java.awt.event.*;   
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

public class Player extends WalkingEntity {
	private boolean dead = false;
	
	@Override
	public void die() {
		this.getLevelControl().setPlayerExploded(true);
		this.getLevelControl().getMyMain().drawGame();
		dead = true;
	};
	
	public void moveInDirection(Direction direction) {
		if (dead == false) {
			Tile nowTile = this.getLevelControl().findTileByEntity(this);
			LevelControl control = this.getLevelControl();
			Tile tileToMove = control.nextValidTile(direction, nowTile);
			if (!(tileToMove == null)) {
				control.moveToTile(tileToMove.getX(), tileToMove.getY(), this);
			}
			this.setDirection(direction);
		}
		
		
		
	}

	@Override
	public boolean nextMove(Tile tile) {
		//no automatic movement for player
		return false;
	}

}
