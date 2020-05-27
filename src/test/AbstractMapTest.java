package test;

import main.Entry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestUtils.SIZE;
import static test.TestUtils.VALUE;

class AbstractMapTest {

    private static TestMap copy(TestMap map) {
        TestMap copy = new TestMap();
        for (Entry<Integer, TestObject> entry : map.entrySet()) {
            copy.put(entry.getKey(), entry.getValue().clone());
        }
        return copy;
    }

    private TestMap empty, full;

    @BeforeEach
    void setUp() {
        empty = new TestMap();
        full = new TestMap();
        for (int i = 0; i < SIZE; i++) {
            full.put(i, VALUE);
        }
    }

    @Test
    void clear() {
        full.clear();
        assertTrue(full.isEmpty());
    }

    @Test
    void size() {
        assertEquals(SIZE, full.size());
    }

    @Test
    void isEmpty() {
        assertTrue(empty.isEmpty());
        assertFalse(full.isEmpty());
    }

    @Test
    void putIfAbsent() {
        assertTrue(empty.putIfAbsent(0, VALUE));
        assertFalse(empty.putIfAbsent(0, null));
    }

    @Test
    void removeIfPresent() {
        assertTrue(full.removeIfPresent(0, VALUE));
        assertFalse(full.removeIfPresent(0, VALUE));
    }

    @Test
    void replace() {
        assertNull(empty.replace(0, null));
        assertFalse(empty.contains(0));
        assertEquals(VALUE, full.replace(0, null));
    }

    @Test
    void testEquals() {
        assertEquals(full, full);
        assertNotEquals(full, null);
        assertNotEquals(full, new Object());
        assertNotEquals(full, empty);
        TestMap copy = copy(full);
        assertEquals(full, copy);
    }

    @Test
    void testHashCode() {
        assertNotEquals(empty.hashCode(), full.hashCode());
        assertEquals(copy(full).hashCode(), full.hashCode());
    }

    @Test
    void testToString() {
        int index = 0;
        StringBuilder string = new StringBuilder("[");
        assertEquals("[]", empty.toString());
        for (int i = 0; i < SIZE; i++) {
            string.append("{").append(i).append(" : ").append(full.get(i)).append("}");
            if (i < SIZE - 1) {
                string.append(", ");
            }
        }
        string.append("]");
        assertEquals(string.toString(), full.toString());
    }

    @Test
    void keySet() {
        Iterator<Integer> iterator = full.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        assertTrue(full.keySet().isEmpty());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    void values() {
        Iterator<TestObject> iterator = full.values().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        assertTrue(full.values().isEmpty());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(IllegalStateException.class, iterator::remove);
    }

}