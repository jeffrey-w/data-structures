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
     * Removes the first {@code Entry} in this {@code OrderedMap}.
     *
     * @return the removed {@code Entry}
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     */
    Entry<K, V> removeFirst();

    /**
     * Removes the last {@code Entry} in this {@code OrderedMap}.
     *
     * @return the removed {@code Entry}
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     */
    Entry<K, V> removeLast();

    /**
     * Removes the {@code Entry} in this {@code OrderedMap} with the {@code key} immediately preceding that specified.
     *
     * @param key the specified key
     * @return the removed {@code Entry}
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     * @throws NoSuchElementException if there is no {@code Entry} in this {@code OrderedMap} with the specified {@code
     * key}
     */
    Entry<K, V> removePrevious(final K key);

    /**
     * Removes the {@code Entry} in this {@code OrderedMap} with the {@code key} immediately after that specified.
     *
     * @param key the specified key
     * @return the removed {@code Entry}
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     * @throws NoSuchElementException if there is no {@code Entry} in this {@code OrderedMap} with the specified {@code
     * key}
     */
    Entry<K, V> removeNext(final K key);

    /**
     * Retrieves the first {@code Entry} in this {@code OrderedMap}.
     *
     * @return the first {@code Entry} in this {@code OrderedMap}
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     */
    Entry<K, V> getFirst();

    /**
     * Retrieves the last {@code Entry} in this {@code OrderedMap}.
     *
     * @return the last {@code Entry} in this {@code OrderedMap}
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     */
    Entry<K, V> getLast();

    /**
     * Retrieves the {@code Entry} in this {@code OrderedMap} associated with the {@code key} immediately preceding
     * that specified.
     *
     * @param key the specified key
     * @return the {@code Entry} associated with the {@code key} before the specified one
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     * @throws NoSuchElementException if there is no {@code Entry} in this {@code OrderedMap} with the specified {@code
     * key}
     */
    Entry<K, V> getPrevious(final K key);

    /**
     * Retrieves the {@code Entry} in this {@code OrderedMap} associated with the {@code key} immediately after that
     * specified.
     *
     * @param key the specified key
     * @return the {@code Entry} associated with the {@code key} after the specified one
     * @throws IllegalStateException if this {@code OrderedMap} is empty
     * @throws NoSuchElementException if there is no {@code Entry} in this {@code OrderedMap} with the specified {@code
     * key}
     */
    Entry<K, V> getNext(final K key);

}
