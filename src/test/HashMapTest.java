package test;

import main.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

	private static final int SIZE = 2 << 10;
	private static final TestObject PRESENT = TestObject.random();
	private static HashMap<TestObject, TestObject> PREV;

	private HashMap<TestObject, TestObject> empty, full;

	@BeforeEach
	void setUp() {
		empty = new HashMap<>();
		full = new HashMap<>();
		for(int i = 0; i < SIZE; i++) {
			full.put(new TestObject(i), PRESENT);
		}
	}

	@Test
	void contains() {
		TestObject key = new TestObject(SIZE);
		assertFalse(full.contains(key));
		full.put(key, PRESENT);
		assertTrue(full.contains(key));
	}

	@Test
	void put() {
		TestObject key = new TestObject(SIZE - 1);
		assertNull(empty.put(key, TestObject.random()));
		assertEquals(PRESENT, full.put(key, TestObject.random()));
	}

	@Test
	void remove() {
		assertThrows(IllegalStateException.class, () -> empty.remove(null));
		assertThrows(NoSuchElementException.class, () -> full.remove(null));
		assertEquals(PRESENT, full.remove(new TestObject(SIZE - 1)));
	}

	@Test
	void get() {
		assertThrows(IllegalStateException.class, () -> empty.get(null));
		assertThrows(NoSuchElementException.class, () -> full.get(null));
		assertEquals(PRESENT, full.get(new TestObject(SIZE - 1)));
	}

	@Test
	void entrySet() {
		var iterator = full.entrySet().iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		assertTrue(full.entrySet().isEmpty());
		assertThrows(NoSuchElementException.class, iterator::next);
		assertThrows(IllegalStateException.class, iterator::remove);
	}
}