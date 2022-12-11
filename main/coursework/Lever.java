package coursework;
/**
 * Implements the lever item.
 * 
 * @author Enzo Tobias 2117781
 *
 */
public class Lever extends Item {
	private Colour colour;
	/**
	 * The effect of this item when activated, removing any gates of the same
	 * colour.
	 * 
	 * @param tile
	 *            The tile this item is on.
	 * @param entity
	 *            The entity triggering this effect.
	 */
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
		tile.getLevelControl().updateLootCollectedStatus();
		Sound.StaticSound.leverSound();
		tile.setContainedItem(null);
	}
	/**
	 * Return this item's colour.
	 * 
	 * @return The colour to return.
	 */
	public Colour getColour() {
		return colour;
	}
	/**
	 * Set this entity's colour.
	 * 
	 * @param colour
	 *            The colour to set.
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}

}
