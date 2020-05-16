package main;

import java.util.NoSuchElementException;

/**
 * The {@code Map} interface specifies operations on a collection of key-value pairs.
 *
 * @param <K> the type of key that maps to values
 * @param <V> the type of mapped values
 * @author Jeff Wilgus
 */
public interface Map<K, V> {

	/**
	 * Removes all entries from this {@code Map}.
	 */
	void clear();

	/**
	 * Determines whether or not there is an association between the specified {@code key} and a value in this {@code
	 * Map}.
	 *
	 * @param key the specified key
	 * @return {@code true} if there exists an association between the specified {@code key} and a value in this {@code
	 * Map}
	 */
	boolean contains(K key);

	/**
	 * Provides the number of entries in this {@code Map}.
	 *
	 * @return the size of this {@code Map}
	 */
	int size();

	/**
	 * Determines whether or not there are any entries in this {@code Map}.
	 *
	 * @return {@code true} if this {@code Map} has no entries in it
	 */
	boolean isEmpty();

	/**
	 * Adds an entry to this {@code Map} associating the specified {@code key} to the specified {@code value}.
	 *
	 * @param key the specified key
	 * @param value the specified value
	 * @return the value previously associated with the specified {@code key} or {@code null} if none existed
	 */
	V put(K key, V value);

	/**
	 * Deletes an entry from this {@code Map} associating some value with the specified {@code key}.
	 *
	 * @param key the specified key
	 * @return the value associated with the specified {@code key}
	 * @throws IllegalStateException if this {@code Map} is empty
	 * @throws NoSuchElementException if there is no mapping between the specified {@code key} and a value in this
	 * {@code Map}
	 */
	V remove(K key);

	/**
	 * Retrieves the value in this {@code Map} associated with the specified {@code key}.
	 *
	 * @param key the specified key
	 * @throws IllegalStateException if this {@code Map} is empty
	 * @throws NoSuchElementException if there is no mapping between the specified {@code key} and a value in this
	 * {@code Map}
	 */
	V get(K key);

	/**
	 * Replaces the {@code value} associated with the specified {@code key} with that specified if another value is
	 *
	 * @param key the specified key
	 * @param value the specified value
	 * @return the value associated with the specified {@code key}, or {@code null} if no entry in this {@code Map}
	 * associates the specified {@code key} with a value
	 */
	V replace(K key, V value);

	/**
	 * Provides an {@code Iterable} collection of the keys in this {@code Map}.
	 *
	 * @return the keys in this {@code Map}
	 */
	Set<K> keySet();

	/**
	 * Provides an {@code Iterable} collection of the values in this {@code Map}.
	 *
	 * @return the values in this {@code Map}
	 */
	Collection<V> values();

	/**
	 * Provides an {@code Iterable} collection of the entries in this {@code Map}.
	 *
	 * @return the entries in this {@code Map}
	 */
	Set<Entry<K, V>> entrySet();

}
