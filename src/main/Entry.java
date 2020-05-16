package main;

/**
 * The {@code Entry} interface abstracts the association between a key and a value.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 */
public interface Entry<K, V> {

	/**
	 * Provides the key to this {@code Entry}.
	 *
	 * @return the key to this {@code Entry}
	 */
	K getKey();

	/**
	 * Provides the value of this {@code Entry}
	 *
	 * @return the value of this {@code Entry}
	 */
	V getValue();

}
