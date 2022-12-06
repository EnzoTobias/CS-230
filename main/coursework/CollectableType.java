
/**
 * Type of collectable and its value.
 * @author Enzo Tobias 2117781
 */
public enum CollectableType {
	CENT(1), DOLLAR(10), RUBY(50), DIAMOND(100);

	private int value;
	/**
	 * Set type of collectable.
	 * @param value The value of the type.
	 */
	CollectableType(int value) {
		this.value = value;
	}
	/**
	 * Return type of collectable.
	 * @return value The value of the type.
	 */
	public int getValue() {
		return value;
	}

}
