package coursework;
/**
 * Type of collectable and its value.
 */
public enum CollectableType {
	CENT(1), DOLLAR(10), RUBY(50), DIAMOND(100);

	private int value;

	CollectableType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
