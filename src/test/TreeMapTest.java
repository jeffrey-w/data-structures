package test;

import main.Entry;
import main.TreeMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static util.Common.RAND;

class TreeMapTest {

	private static final int SIZE = 100_000;
	private static final Object PRESENT = new Object();

	private static boolean isSorted(TreeMap<TestObject, Object> map) {
		TestObject last = null;
		for (TestObject key : map.keySet()) {
			if (last != null && last.compareTo(key) > 0) {
				return false;
			}
			last = key;
		}
		return true;
	}

	private static int treeHeight(TreeMap<TestObject, Object> map) {
		return heightOf(getNodeOf(map, "root"), getNodeOf(map, "nil"));
	}

	private static int heightOf(Entry<TestObject, Object> entry, Entry<TestObject, Object> nil) {
		if (entry == nil) {
			return 0;
		}
		return Math.max(heightOf(getChildOf(entry, "left"), nil) + 1, heightOf(getChildOf(entry, "right"), nil) + 1);
	}

	@SuppressWarnings("unchecked")
	private static Entry<TestObject, Object> getNodeOf(TreeMap<TestObject, Object> map, String field) {
		try {
			Field node = TreeMap.class.getDeclaredField(field);
			node.setAccessible(true);
			return (Entry<TestObject, Object>) node.get(map);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		throw new AssertionError();
	}

	@SuppressWarnings("unchecked")
	private static Entry<TestObject, Object> getChildOf(Entry<TestObject, Object> entry, String field) {
		Class<?>[] classes = TreeMap.class.getDeclaredClasses();
		for (Class<?> cls : classes) {
			try {
				Field childNode = cls.getDeclaredField(field);
				childNode.setAccessible(true);
				return (Entry<TestObject, Object>) childNode.get(entry);
			} catch (IllegalAccessException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		throw new AssertionError();
	}

	private static int expectedHeight(TreeMap<TestObject, Object> map) {
		return (int) ((Math.log10(map.size() + 1) / Math.log10(2)) * 2);
	}

	private static void halve(TreeMap<TestObject, Object> map) {
		int half = map.size() >> 1;
		for (int i = 0; i < half; i++) {
			if (RAND.nextBoolean()) {
				map.removeFirst();
			} else {
				map.removeLast();
			}
		}
	}

	private static void removeRoot(TreeMap<TestObject, Object> map) {
		map.remove(getNodeOf(map, "root").getKey());
	}

	private TreeMap<TestObject, Object> empty, sequential, random;

	@BeforeEach
	void setUp() {
		empty = new TreeMap<>();
		sequential = new TreeMap<>();
		random = new TreeMap<>();
		for (int i = 0; i < SIZE; i++) {
			sequential.put(new TestObject(i), PRESENT);
			random.put(TestObject.random(), PRESENT);
		}
		assertTrue(isSorted(random));
		assertTrue(treeHeight(random) <= expectedHeight(random));
	}

	@Test
	void contains() {
		TestObject key = new TestObject(SIZE);
		assertFalse(sequential.contains(key));
		sequential.put(key, PRESENT);
		assertTrue(sequential.contains(key));
	}

	@Test
	void put() {
		TestObject obj = new TestObject(sequential.size() - 1);
		assertNull(empty.put(obj, new Object()));
		assertEquals(PRESENT, sequential.put(obj, new Object()));
	}

	@Test
	void remove() {
		assertThrows(IllegalStateException.class, () -> empty.remove(null));
		assertThrows(NoSuchElementException.class, () -> sequential.remove(null));
		assertEquals(sequential.getLast().getValue(), sequential.remove(new TestObject(sequential.size() - 1)));
		halve(random);
		removeRoot(random);
		assertTrue(treeHeight(random) <= expectedHeight(random));
	}

	@Test
	void removeFirst() {
		assertThrows(IllegalStateException.class, () -> empty.removeFirst());
		assertEquals(0, sequential.removeFirst().getKey().getState());
	}

	@Test
	void removeLast() {
		assertThrows(IllegalStateException.class, () -> empty.removeLast());
		assertEquals(sequential.size() - 1, sequential.removeLast().getKey().getState());
	}

	@Test
	void removePrevious() {
		assertThrows(IllegalStateException.class, () -> empty.removePrevious(null));
		assertThrows(NoSuchElementException.class, () -> sequential.removePrevious(null));
		assertEquals(sequential.size() - 2, sequential.removePrevious(sequential.getLast().getKey()).getKey()
													  .getState());
	}

	@Test
	void removeNext() {
		assertThrows(IllegalStateException.class, () -> empty.removeNext(null));
		assertThrows(NoSuchElementException.class, () -> sequential.removeNext(null));
		assertEquals(1, sequential.removeNext(sequential.getFirst().getKey()).getKey().getState());
	}

	@Test
	void get() {
		TestObject key = new TestObject(random.size());
		Object value = new Object();
		assertThrows(IllegalStateException.class, () -> empty.get(null));
		assertThrows(NoSuchElementException.class, () -> sequential.get(null));
		sequential.put(key, value);
		assertEquals(value, sequential.get(key));
	}

	@Test
	void getFirst() {
		assertThrows(IllegalStateException.class, () -> empty.getFirst());
		assertEquals(0, sequential.getFirst().getKey().getState());
	}

	@Test
	void getLast() {
		assertThrows(IllegalStateException.class, () -> empty.getLast());
		assertEquals(sequential.size() - 1, sequential.getLast().getKey().getState());
	}

	@Test
	void getPrevious() {
		assertThrows(IllegalStateException.class, () -> empty.getPrevious(null));
		assertThrows(NoSuchElementException.class, () -> sequential.getPrevious(null));
		assertEquals(sequential.size() - 2, sequential.getPrevious(sequential.getLast().getKey()).getKey().getState());
	}

	@Test
	void getNext() {
		assertThrows(IllegalStateException.class, () -> empty.getNext(null));
		assertThrows(NoSuchElementException.class, () -> sequential.getNext(null));
		assertEquals(1, sequential.getNext(sequential.getFirst().getKey()).getKey().getState());
	}

	@Test
	void entrySet() {
		SetTest.testClear(sequential.entrySet());
		SetTest.testContains(random.entrySet(), random.getFirst());
		SetTest.testIterator(sequential.entrySet(), random.entrySet());
	}

}