package main;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static util.Common.areEqual;

public class LinkedHashMap<K, V> extends AbstractMap<K, V> implements OrderedMap<K, V> {

    private final static class LinkedHashMapEntry<K, V> extends AbstractEntry<K, V> {

        public LinkedHashMapEntry(final K key, final V value) {
            super(key, value);
        }

    }

    private final AbstractMap<K, V> map;
    private final AbstractList<K> list;

    public LinkedHashMap() {
        map = new HashMap<>();
        list = new LinkedList<>();
    }

    public LinkedHashMap(final double loadFactor) {
        map = new HashMap<>(loadFactor);
        list = new LinkedList<>();
    }

    @Override
    public boolean contains(K key) {
        return map.contains(key);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public V put(K key, V value) {
        list.addLast(key);
        return map.put(key, value);
    }

    @Override
    public V remove(K key) {
        list.remove(list.indexOf(key));
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
    public Entry<K, V> removePrevious(K key) {
        K prev = list.removePrevious(list.positionOf(key));
        V value = map.remove(prev);
        return new LinkedHashMapEntry<>(prev, value);
    }

    @Override
    public Entry<K, V> removeNext(K key) {
        K next = list.removeNext(list.positionOf(key));
        V value = map.remove(next);
        return new LinkedHashMapEntry<>(next, value);
    }

    @Override
    public V get(K key) {
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
    public Entry<K, V> getPrevious(K key) {
        K prev = list.getPrevious(list.positionOf(key));
        V value = map.get(prev);
        return new LinkedHashMapEntry<>(prev, value);
    }

    @Override
    public Entry<K, V> getNext(K key) {
        K next = list.getNext(list.positionOf(key));
        V value = map.get(next);
        return new LinkedHashMapEntry<>(next, value);
    }

    private transient Set<Entry<K, V>> entries;

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = this.entries;
        if(entries == null) {
            entries = new MinSet<>() {

                @Override
                public void clear() {
                    LinkedHashMap.this.clear();
                }

                @Override
                public boolean contains(final Entry<K, V> entry) {
                    if(LinkedHashMap.this.contains(entry.getKey())) {
                        return areEqual(get(entry.getKey()), entry.getValue());
                    }
                    return false;
                }

                @Override
                public int size() {
                    return LinkedHashMap.this.size();
                }

                @Override
                public boolean isEmpty() {
                    return LinkedHashMap.this.isEmpty();
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
                            V value = LinkedHashMap.this.map.get(last);
                            return new LinkedHashMapEntry<>(last, value);
                        }

                        @Override
                        public void remove() {
                            if (last == null) {
                                throw new IllegalStateException();
                            }
                            last = null;
                            i.remove();
                            LinkedHashMap.this.map.remove(last);
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

}
