
public class Player extends WalkingEntity {
	@Override
	public void die() {
		// to do
	};
	
	public void testingMoveUp() {
		Tile nowTile = this.getLevelControl().findTileByEntity(this);
		this.getLevelControl().moveToTile(nowTile.getX(), nowTile.getY()-1, this);
	}

	@Override
	public boolean nextMove(Tile tile) {
		// TODO Auto-generated method stub
		return false;
	}

}
