package main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static util.Common.areEqual;
import static util.Common.validateObject;

/**
 * The {@code OrderedHashMap} class is a hash table implementation of the {@code Map} interface. This class offers
 * constant average time performance for {@code put} and {@code get} operations. It additionally maintains insertion
 * order at the cost of linear time performance of other operations (e.g. {@code remove}). Performance may be tuned by
 * supplying a load factor that determines how keys are dispersed (a lower load factor results in a more sparsely
 * populated {@code Map}). Both keys and values may be {@code null}, however, only one key may be.
 *
 * @param <K> the type of key that maps to values
 * @param <V> the type of mapped values
 * @author Jeff Wilgus
 */
public class OrderedHashMap<K, V> extends AbstractMap<K, V> implements OrderedMap<K, V>, Sortable<K> {

    private final static class LinkedHashMapEntry<K, V> extends AbstractEntry<K, V> {

        LinkedHashMapEntry(K key, V value) {
            super(key, value);
        }

    }

    private AbstractMap<K, V> map;
    private AbstractList<K> list;

    /**
     * Constructs a new {@code OrderedHashMap} object.
     */
    public OrderedHashMap() {
        map = new HashMap<>();
        list = new LinkedList<>();
    }

    /**
     * Constructs a new {@code OrderedHashMapHashMap} object with the specified {@code loadFactor}.
     *
     * @param loadFactor the specified load factor
     * @throws IllegalArgumentException if the specified {@code loadFactor} is not on the open interval bounded by zero
     * and one
     */
    public OrderedHashMap(final double loadFactor) {
        map = new HashMap<>(loadFactor);
        list = new LinkedList<>();
    }

    @Override
    public boolean contains(final K key) {
        return map.contains(key);
    }

    @Override
    public V put(final K key, final V value) { // TODO this is incorrect (duplicates added to list twice)
        list.addLast(key);
        size = list.size;
        return map.put(key, value);
    }

    @Override
    public V remove(final K key) {
        list.remove(list.indexOf(key));
        size = list.size;
        return map.remove(key);
    }

    @Override
    public Entry<K, V> removeFirst() {
        K key = list.removeFirst();
        V value = map.remove(key);
        return new LinkedHashMapEntry<>(key, value);
    }

    @Override
    public Entry<K, V> removeLast() {
        K key = list.removeLast();
        V value = map.remove(key);
        return new LinkedHashMapEntry<>(key, value);
    }

    @Override
    public Entry<K, V> removePrevious(final K key) {
        K prev = list.removePrevious(list.positionOf(key));
        V value = map.remove(prev);
        return new LinkedHashMapEntry<>(prev, value);
    }

    @Override
    public Entry<K, V> removeNext(final K key) {
        K next = list.removeNext(list.positionOf(key));
        V value = map.remove(next);
        return new LinkedHashMapEntry<>(next, value);
    }

    @Override
    public V get(final K key) {
        return map.get(key);
    }

    @Override
    public Entry<K, V> getFirst() {
        K key = list.getFirst();
        V value = map.get(key);
        return new LinkedHashMapEntry<>(key, value);
    }

    @Override
    public Entry<K, V> getLast() {
        K key = list.getLast();
        V value = map.get(key);
        return new LinkedHashMapEntry<>(key, value);
    }

    @Override
    public Entry<K, V> getPrevious(final K key) {
        K prev = list.getPrevious(list.positionOf(key));
        V value = map.get(prev);
        return new LinkedHashMapEntry<>(prev, value);
    }

    @Override
    public Entry<K, V> getNext(final K key) {
        K next = list.getNext(list.positionOf(key));
        V value = map.get(next);
        return new LinkedHashMapEntry<>(next, value);
    }

    /**
     * Sorts the entries in this {@code OrderedHashMap} on the order induced by the specified {@code Comparator}, or,
     * if that is {@code null}, their natural ordering. Iteration over this {@code OrderedHashMap} will follow the
     * induced order rather than insertion order afterwards.
     *
     * @param comp the specified {@code Comparator}
     * @throws ClassCastException if the keys in this {@code OrderedHashMap} are not mutually comparable
     */
    @Override
    public void sort(final Comparator<K> comp) {
        list.sort(comp);
    }

    private transient Set<Entry<K, V>> entries;

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = this.entries;
        if (entries == null) {
            entries = new MinSet<>() {

                @Override
                public void clear() {
                    OrderedHashMap.this.clear();
                }

                @Override
                public boolean contains(final Entry<K, V> entry) {
                    if (OrderedHashMap.this.contains(entry.getKey())) {
                        return areEqual(get(entry.getKey()), entry.getValue());
                    }
                    return false;
                }

                @Override
                public int size() {
                    return OrderedHashMap.this.size();
                }

                @Override
                public boolean isEmpty() {
                    return OrderedHashMap.this.isEmpty();
                }

                @Override
                public Iterator<Entry<K, V>> iterator() {
                    return new Iterator<>() {

                        K last;
                        final Iterator<K> i = list.iterator();

                        @Override
                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        @Override
                        public Entry<K, V> next() {
                            if (!hasNext()) {
                                throw new NoSuchElementException();
                            }
                            last = i.next();
                            V value = OrderedHashMap.this.map.get(last);
                            return new LinkedHashMapEntry<>(last, value);
                        }

                        @Override
                        public void remove() {
                            if (last == null) {
                                throw new IllegalStateException();
                            }
                            last = null;
                            i.remove();
                            OrderedHashMap.this.map.remove(last);
                        }

                    };
                }

                private static final long serialVersionUID = 745193777362420692L;

            };
            this.entries = entries;
        }
        return entries;
    }

    @Override
    protected void init() {
        map.init();
        list.init();
    }

    private static final long serialVersionUID = -3092320301551003644L;

    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        stream.writeObject(map);
        stream.writeObject(list);
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        map = (AbstractMap<K, V>)validateObject(stream.readObject());
        list = (AbstractList<K>)validateObject(stream.readObject());
    }

}
