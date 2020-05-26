package test;

import main.*;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static test.TestUtils.SIZE;
import static util.Common.areEqual;

final class TestMap extends AbstractMap<Integer, TestObject> {

	private static final class TestEntry extends AbstractEntry<Integer, TestObject> {

		public TestEntry(Integer key, TestObject value, TestMap owner) {
			super(key, value, owner);
		}

	}

	private transient TestEntry[] map;
	private transient boolean[] contains;

	TestMap() {
		init();
	}

	@Override
	protected void init() {
		setSize(0);
		map = new TestEntry[SIZE];
		contains = new boolean[SIZE];
	}

	@Override
	public boolean contains(final Integer key) {
		try {
			return contains[validateKey(key)];
		} catch (RuntimeException e) {
			return false;
		}
	}

	@Override
	public TestObject put(final Integer key, final TestObject value) {
		validateKey(key);
		TestObject result = contains[key] ? map[key].getValue() : null;
		map[key] = new TestEntry(key, value, this);
		contains[key] = true;
		setSize(size() + 1);
		return result;
	}

	@Override
	public TestObject remove(final Integer key) {
		validateKey(key);
		TestObject result = map[key].getValue();
		map[key].invalidate();
		map[key] = null;
		contains[key] = false;
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
	public TestObject get(final Integer key) {
		return map[validateKey(key)].getValue();
	}

	private Integer validateKey(Integer key) {
		if (key < 0 || key + 1 > SIZE) {
			throw new RuntimeException();
		}
		return key;
	}

	Set<Entry<Integer, TestObject>> entries;

	@Override
	public Set<Entry<Integer, TestObject>> entrySet() {
		Set<Entry<Integer, TestObject>> entries = this.entries;
		if (entries == null) {
			entries = new AbstractSet<>() {

				@Override
				public void clear() {
					TestMap.this.clear();
				}

				@Override
				public boolean contains(final Entry<Integer, TestObject> entry) {
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
				public boolean isEmpty() {
					return TestMap.this.isEmpty();
				}

				@Override
				public void add(final Entry<Integer, TestObject> element) {
					throw new UnsupportedOperationException();
				}

				@Override
				public void remove(final Entry<Integer, TestObject> element) {
					throw new UnsupportedOperationException();
				}

				@Override
				public Iterator<Entry<Integer, TestObject>> iterator() {
					return new EntryIter();
				}

				private static final long serialVersionUID = 4706430778067701683L;

			};
			this.entries = entries;
		}
		return entries;
	}

	private final class EntryIter implements Iterator<Entry<Integer, TestObject>> {

		int last;
		Queue<Entry<Integer, TestObject>> q;
		boolean removable;

		EntryIter() {
			q = new LinkedQueue<>();
			for (int i = 0; i < SIZE; i++) {
				if (contains[i]) {
					q.enqueue(map[i]);
				}
			}
		}

		@Override
		public boolean hasNext() {
			return !q.isEmpty();
		}

		@Override
		public Entry<Integer, TestObject> next() {
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

	private static final long serialVersionUID = -4049372969033166420L;

}
