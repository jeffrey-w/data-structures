package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestUtils.SIZE;
import static util.Common.RAND;

class AbstractSetTest {

	private TestSet empty, full;

	@BeforeEach
	void setUp() {
		empty = new TestSet();
		full = new TestSet();
		for(int i = 0; i < SIZE; i++) {
			full.add(i);
		}
	}

	@Test
	void clear() {
		full.clear();
		assertTrue(full.isEmpty());
	}

	@Test
	void contains() {
		int element = RAND.nextInt(0, SIZE);
		assertFalse(empty.contains(element));
		assertTrue(full.contains(element));
	}

	@Test
	void isEmpty() {
	}

	@Test
	void add() {
	}

	@Test
	void remove() {
	}

	@Test
	void iterator() {
	}

	@Test
	void testEquals() {
	}

	@Test
	void testHashCode() {
	}
}