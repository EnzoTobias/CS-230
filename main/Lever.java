public class Lever extends Item {
	private Colour colour;

	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		Tile[][] tileGrid = tile.getLevelControl().getTileGrid();

		for (int i = 0; i < tileGrid.length; i++) {
			for (int j = 0; j < tileGrid[0].length; j++) {
				Tile tileChecked = tileGrid[i][j];
				if (tileChecked.hasItem()
						&& tileChecked.getContainedItem() instanceof Gate) {
					if (((Gate) tileChecked.getContainedItem())
							.getColour() == this.getColour()) {
						tileChecked.setContainedItem(null);
					}
				}
			}
		}
		tile.setContainedItem(null);
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

}
