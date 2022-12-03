package coursework;
import java.awt.event.*;   
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

public class Player extends WalkingEntity {
	
	
	@Override
	public void die() {
		this.getLevelControl().playerLose();
	};
	
	public void moveInDirection(Direction direction) {
		Tile nowTile = this.getLevelControl().findTileByEntity(this);
		LevelControl control = this.getLevelControl();
		Tile tileToMove = control.nextValidTile(direction, nowTile);
		if (!(tileToMove == null)) {
			control.moveToTile(tileToMove.getX(), tileToMove.getY(), this);
		}
		
		
	}

	@Override
	public boolean nextMove(Tile tile) {
		//no automatic movement for player
		return false;
	}

}