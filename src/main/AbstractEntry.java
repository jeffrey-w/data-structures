package main;

/**
 * The {@code AbstractEntry} class is the base class from which all {@code Map} nodes shall be derived.
 *
 * @param <K> the type of key to this {@code Entry}
 * @param <V> the type of value of this {@code Entry}
 * @author Jeff Wilgus
 */
abstract class AbstractEntry<K, V> extends AbstractOwnable<Map<K, V>> implements Entry<K, V> {

	private final K key;
	private V value;

	/**
	 * Constructs a new {@code AbstractEntry} object that pairs the specified {@code key} to the specified {@code
	 * value}
	 * and belongs to the specified {@code Map}.
	 *
	 * @param key the specified key
	 * @param value the specified value
	 * @param owner the {@code Map} that owns this {@code AbstractEntry}
	 * @throws NullPointerException if the specified {@code Map} is {@code null}
	 */
	AbstractEntry(final K key, final V value, final Map<K, V> owner) {
		super(owner);
		this.key = key;
		this.value = value;
	}

	@Override
	public K getKey() {
		return key;
	}

	@Override
	public V getValue() {
		return value;
	}

	/**
	 * Sets the {@code value} associated with this {@code AbstractEntry}'s key to the one specified.
	 *
	 * @param value the specified value
	 * @return the value previously associated with this {@code AbstractEntry}'s key
	 */
	V setValue(final V value) {
		V result = this.value;
		this.value = value;
		return result;
	}

}
