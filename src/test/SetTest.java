package test;

import main.Set;

import static org.junit.jupiter.api.Assertions.*;

final class SetTest {

	static <E> void testClear(Set<E> set) {
		if (set.isEmpty()) {
			fail();
		}
		set.clear();
		assertTrue(set.isEmpty());
	}

	static <E> void testContains(Set<E> set, E element) {
		try {
			set.add(element);
		} catch (UnsupportedOperationException e) {
			// Nothing.
		}
		assertTrue(set.contains(element));
	}

	static void testIsEmpty(Set<?> set) {
		set.clear();
		assertTrue(set.isEmpty());
	}

	static void testAdd(Set<TestObject> set, TestObject element) {
		if (set.contains(element)) {
			fail();
		}
		int size = set.size();
		try {
			set.add(element);
			set.add(element);
		} catch (UnsupportedOperationException e) {
			return; // Pass.
		}
		assertEquals(++size, set.size());
		assertTrue(set.contains(element));
	}

	static void testRemove(Set<TestObject> set, TestObject element) {
		if (!set.contains(element)) {
			fail();
		}
		int size = set.size();
		try {
			set.remove(element);
		} catch (UnsupportedOperationException e) {
			return; // Pass.
		}
		assertEquals(--size, set.size());
		assertFalse(set.contains(element));
		set.clear();
		assertThrows(IllegalStateException.class, () -> set.remove(element));
	}

	static <E> void testIterator(Set<E> empty, Set<E> full) {
		IteratorTest.testHasNext(empty, full);
		IteratorTest.testNext(empty, full);
		IteratorTest.testRemove(full);
	}

	private SetTest() {
		throw new AssertionError();
	}

}
