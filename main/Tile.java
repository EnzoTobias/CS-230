/**Represents one tile
 * @author Enzo Tobias 2117781
 */
public class Tile {
	private Colour[] colours;
	private int x;
	private int y;
	private Item containedItem;
	private WalkingEntity containedEntity;
	private LevelControl level;
	
	public Tile(Colour[] colours) {
		this.colours = colours;
	}
	
	public Colour[] getColours() {
		return colours;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Item getContainedItem() {
		return containedItem;
	}

	public void setContainedItem(Item containedItem) {
		this.containedItem = containedItem;
	}

	public LevelControl getLevel() {
		return level;
	}

	public WalkingEntity getContainedEntity() {
		return containedEntity;
	}
	
	public void setContainedEntity() {
		this.containedEntity = containedEntity;
	}
	
	public Boolean hasItem() {
		if (containedItem == null) {
			return false;
		}
		return true;
	}
	
	public Boolean hasEntity() {
		if (containedEntity == null) {
			return false;
		}
		return true;
	}
}
