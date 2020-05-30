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

    private static final TestObject KEY = new TestObject(0);

    private static TestMap copy(TestMap map) {
        TestMap copy = new TestMap();
        for (Entry<TestObject, TestObject> entry : map.entrySet()) {
            copy.put(entry.getKey().clone(), entry.getValue().clone());
        }
        return copy;
    }

    private TestMap empty, full;

    @BeforeEach
    void setUp() {
        empty = new TestMap();
        full = new TestMap();
        for (int i = 0; i < SIZE; i++) {
            full.put(new TestObject(i), VALUE);
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
        assertTrue(empty.putIfAbsent(KEY, VALUE));
        assertFalse(empty.putIfAbsent(KEY, VALUE));
    }

    @Test
    void removeIfPresent() {
        assertTrue(full.removeIfPresent(KEY, VALUE));
        assertFalse(full.removeIfPresent(KEY, VALUE));
    }

    @Test
    void replace() {
        assertNull(empty.replace(KEY, null));
        assertFalse(empty.contains(KEY));
        assertEquals(VALUE, full.replace(KEY, null));
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

    @Test
    void testToString() {
        StringBuilder string = new StringBuilder("[");
        assertEquals("[]", empty.toString());
        for (int i = 0; i < SIZE; i++) {
            string.append("{").append(i).append(" : ").append(full.get(new TestObject(i))).append("}");
            if (i < SIZE - 1) {
                string.append(", ");
            }
        }
        string.append("]");
        assertEquals(string.toString(), full.toString());
    }

    @Test
    void keySet() {
        Iterator<TestObject> iterator = full.keySet().iterator();
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