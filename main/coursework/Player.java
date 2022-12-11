package coursework;
import java.awt.event.*;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
/**
 * Implementation of the player entity, the actual entity that will be
 * controlled by the user.
 * 
 * @author Enzo Tobias 2117781
 *
 */
public class Player extends WalkingEntity {
	private boolean dead = false;
	/**
	 * Special death behaviour for player, losing the game.
	 */
	@Override
	public void die() {
		this.getLevelControl().setPlayerExploded(true);
		this.getLevelControl().getMyMain().drawGame();
		dead = true;
	};
	/**
	 * Move in a given direction.
	 * 
	 * @param direction
	 *            The direction to move in.
	 */
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
	/**
	 * The player entity has no automatic movement therefore nextMove does
	 * nothing for player.
	 */
	@Override
	public boolean nextMove(Tile tile) {
		// no automatic movement for player
		return false;
	}

}
