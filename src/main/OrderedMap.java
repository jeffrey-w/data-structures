package main;

import java.util.NoSuchElementException;

/**
 * The {@code OrderedMap} interface specifies operations on a collection of key-value pairs that are sorted on a
 * prescribed order.
 *
 * @param <K> the type of key that maps to values
 * @param <V> the type of mapped values
 * @author Jeff Wilgus
 */
public interface OrderedMap<K, V> extends Map<K, V> {

	/**
	 * Removes the first entry in this {@code OrderedMap}.
	 *
	 * @return the removed value
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 */
	V removeFirst();

	/**
	 * Removes the last entry in this {@code OrderedMap}.
	 *
	 * @return the removed value
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 */
	V removeLast();

	/**
	 * Removes the entry in this {@code OrderedMap} with the {@code key} immediately preceding that specified.
	 *
	 * @param key the specified key
	 * @return the removed value
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 * @throws NoSuchElementException if there is no entry in this {@code OrderedMap} with the specified {@code key}
	 */
	V removePrevious(K key);

	/**
	 * Removes the entry in this {@code OrderedMap} with the {@code key} immediately after that specified.
	 *
	 * @param key the specified key
	 * @return the removed value
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 * @throws NoSuchElementException if there is no entry in this {@code OrderedMap} with the specified {@code key}
	 */
	V removeNext(K key);

	/**
	 * Retrieves the first value in this {@code OrderedMap}.
	 *
	 * @return the first value in this {@code OrderedMap}
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 */
	V getFirst();

	/**
	 * Retrieves the last value in this {@code OrderedMap}.
	 *
	 * @return the last value in this {@code OrderedMap}
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 */
	V getLast();

	/**
	 * Retrieves the value in this {@code OrderedMap} associated with the {@code key} immediately preceding that
	 * specified.
	 *
	 * @param key the specified key
	 * @return the value associated with the {@code key} before the specified one
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 * @throws NoSuchElementException if there is no entry in this {@code OrderedMap} with the specified {@code key}
	 */
	V getPrevious(K key);

	/**
	 * Retrieves the value in this {@code OrderedMap} associated with the {@code key} immediately after that specified.
	 *
	 * @param key the specified key
	 * @return the value associated with the {@code key} after the specified one
	 * @throws IllegalStateException if this {@code OrderedMap} is empty
	 * @throws NoSuchElementException if there is no entry in this {@code OrderedMap} with the specified {@code key}
	 */
	V getNext(K key);

}
