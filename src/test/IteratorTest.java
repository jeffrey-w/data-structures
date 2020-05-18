package test;

import main.Collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

final class IteratorTest {

	static <E> void testHasNext(Collection<E> empty, Collection<E> full) {
		assertFalse(empty.iterator().hasNext());
		assertTrue(full.iterator().hasNext());
	}

	static <E> void testNext(Collection<E> empty, Collection<E> full) {
		assertThrows(NoSuchElementException.class, () -> empty.iterator().next());
		assertDoesNotThrow(() -> full.iterator().next());
	}

	static <E> void testRemove(Collection<E> collection) {
		if (collection.isEmpty()) {
			fail();
		}
		Iterator<E> iterator = collection.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			try {
				iterator.remove();
			} catch (UnsupportedOperationException e) {
				return; // Pass.
			}
		}
		assertThrows(IllegalStateException.class, iterator::remove);
		assertTrue(collection.isEmpty());
	}

	private IteratorTest() {
		throw new AssertionError();
	}

}
