package coursework;
public class Gate extends Item {
	private Colour colour;
	/**
	 * This item has no item effect.
	 * @param tile The tile this item is on.
	 * @param entity The entity triggering this effect.
	 */
	@Override
	public void itemEffect(Tile tile, WalkingEntity entity) {
		//This item has no item effect

	}
	/**
	 * Returns colour.
	 * @return The colour.
	 */
	public Colour getColour() {
		return colour;
	}
	/**
	 * Sets the colour.
	 * @param colour Colour to be set.
	 */
	public void setColour(Colour colour) {
		this.colour = colour;
	}

}
