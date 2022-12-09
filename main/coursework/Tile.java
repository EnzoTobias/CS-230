package coursework;
/**
 * Represents one tile
 * 
 * @author Enzo Tobias 2117781
 */
public class Tile {
	private Colour[] colours;
	private int x;
	private int y;
	private Item containedItem;
	private WalkingEntity containedEntity;
	private LevelControl level;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

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

	public LevelControl getLevelControl() {
		return level;
	}

	public void setLevelControl(LevelControl level) {
		this.level = level;
	}

	public WalkingEntity getContainedEntity() {
		return containedEntity;
	}

	public void setContainedEntity(WalkingEntity containedEntity) {
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

	public boolean isTileBlocked() {
		if (this.hasItem() && this.getContainedItem() instanceof Gate) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Bomb) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Door
				&& !this.getLevelControl().isAllLootCollected()) {
			return true;
		}
		
		for (Colour colour : this.getColours()) {
			if (colour == Colour.VOID) {
				return true;
			}
		}
		
		if (this.hasEntity()) {
			return true;
		}
		return false;
	}
	
	public boolean isTileBlockedForHitman() {
		if (this.hasItem() && this.getContainedItem() instanceof Gate) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Bomb) {
			return true;
		}

		if (this.hasItem() && this.getContainedItem() instanceof Door
				&& !this.getLevelControl().isAllLootCollected()) {
			return true;
		}
		
		for (Colour colour : this.getColours()) {
			if (colour == Colour.VOID) {
				return true;
			}
		}
		
		if (this.hasEntity() && !(this.getContainedEntity() instanceof Player)) {
			return true;
		}
		return false;
	}
}
