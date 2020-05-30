package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestUtils.SIZE;

class AbstractSetTest {

    private static TestSet copy(TestSet set) {
        TestSet copy = new TestSet();
        for (TestObject obj : set) {
            copy.add(obj.clone());
        }
        return copy;
    }

    private TestSet empty, full;

    @BeforeEach
    void setUp() {
        empty = new TestSet();
        full = new TestSet();
        for (int i = 0; i < SIZE; i++) {
            full.add(new TestObject(i));
        }
    }

    @Test
    void clear() {
        full.clear();
        assertTrue(full.isEmpty());
    }

    @Test
    void contains() {
        TestObject element = TestObject.random(0, SIZE);
        assertFalse(empty.contains(element));
        assertTrue(full.contains(element));
    }

    @Test
    void isEmpty() {
        assertTrue(empty.isEmpty());
        assertFalse(full.isEmpty());
    }

    @Test
    void add() {
        int size = full.size();
        full.add(TestObject.random(0, size));
        assertEquals(size, full.size());
    }

    @Test
    void remove() {
        assertThrows(IllegalStateException.class, () -> empty.remove(null));
        assertThrows(NoSuchElementException.class, () -> full.remove(null));
        full.remove(new TestObject(SIZE - 1));
        assertEquals(SIZE - 1, full.size());
    }

    @Test
    void iterator() {
        Iterator<TestObject> iterator = full.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        assertTrue(full.isEmpty());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void testEquals() {
        assertNotEquals(full, null);
        assertNotEquals(full, new Object());
        assertNotEquals(full, empty);
        assertEquals(full, full);
        assertEquals(full, copy(full));
    }

    @Test
    void testHashCode() {
        assertNotEquals(full.hashCode(), empty.hashCode());
        assertEquals(full.hashCode(), copy(full).hashCode());
    }

}