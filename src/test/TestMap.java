package test;

import main.*;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static test.TestUtils.SIZE;
import static util.Common.areEqual;

final class TestMap extends AbstractMap<TestObject, TestObject> {

    private static final class TestEntry extends AbstractEntry<TestObject, TestObject> {

        public TestEntry(TestObject key, TestObject value) {
            super(key, value);
        }

    }

    private static final TestObject nil = new TestObject(SIZE);

    private transient TestEntry[] map;

    TestMap() {
        init();
    }

    @Override
    protected void init() {
        setSize(0);
        map = new TestEntry[SIZE];
    }

    @Override
    public boolean contains(final TestObject key) {
        try {
            return map[validateKey(key, false)] != null;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Override
    public TestObject put(final TestObject key, final TestObject value) {
        validateKey(key, false);
        TestObject result = contains(key) ? map[key.getState()].getValue() : nil;
        map[key.getState()] = new TestEntry(key, value);
        if (result == nil) {
            result = null;
            setSize(size() + 1);
        }
        return result;
    }

    @Override
    public TestObject remove(final TestObject key) {
        validateKey(key, true);
        TestObject result = map[key.getState()].getValue();
        map[key.getState()] = null;
        setSize(size() - 1);
        return result;
    }

    private void setSize(int size) {
        try {
            Field s = AbstractMap.class.getDeclaredField("size");
            s.setAccessible(true);
            s.set(this, size);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public TestObject get(final TestObject key) {
        return map[validateKey(key, true)].getValue();
    }

    private int validateKey(TestObject key, boolean checkEmpty) {
        if (checkEmpty && isEmpty()) {
            throw new IllegalStateException();
        }
        if (key == null || key.getState() < 0 || key.getState() > SIZE - 1) {
            throw new NoSuchElementException();
        }
        return key.getState();
    }

    Set<Entry<TestObject, TestObject>> entries;

    @Override
    public Set<Entry<TestObject, TestObject>> entrySet() {
        Set<Entry<TestObject, TestObject>> entries = this.entries;
        if (entries == null) {
            entries = new AbstractSet<>() {

                @Override
                public void clear() {
                    TestMap.this.clear();
                }

                @Override
                public boolean contains(final Entry<TestObject, TestObject> entry) {
                    if (TestMap.this.contains(entry.getKey())) {
                        return areEqual(get(entry.getKey()), entry.getValue());
                    }
                    return false;
                }

                @Override
                public int size() {
                    return TestMap.this.size();
                }

                @Override
                public void add(Entry<TestObject, TestObject> element) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public void remove(Entry<TestObject, TestObject> element) {
                    throw new UnsupportedOperationException();
                }

                @Override
                public Iterator<Entry<TestObject, TestObject>> iterator() {
                    return new EntryIter();
                }

                private static final long serialVersionUID = 5208264548321051267L;

            };
            this.entries = entries;
        }
        return entries;
    }

    private final class EntryIter implements Iterator<Entry<TestObject, TestObject>> {

        TestObject last;
        Queue<Entry<TestObject, TestObject>> q;
        boolean removable;

        EntryIter() {
            q = new LinkedQueue<>();
            for (int i = 0; i < SIZE; i++) {
                if (map[i] != null) {
                    q.enqueue(map[i]);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !q.isEmpty();
        }

        @Override
        public Entry<TestObject, TestObject> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            last = q.first().getKey();
            removable = true;
            return q.dequeue();
        }

        @Override
        public void remove() {
            if (!removable) {
                throw new IllegalStateException();
            }
            removable = false;
            TestMap.this.remove(last);
        }
    }

    private static final long serialVersionUID = -8425440739593113306L;

}
