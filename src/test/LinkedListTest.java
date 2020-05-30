package test;

import main.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestUtils.SIZE;

class LinkedListTest {

    private LinkedList<TestObject> empty, full;

    @BeforeEach
    void setUp() {
        empty = new LinkedList<>();
        full = new LinkedList<>();
        for (int i = 0; i < SIZE; i++) {
            full.add(i, new TestObject(i));
        }
    }

    @Test
    void add() {
        TestObject object = new TestObject(SIZE);
        assertThrows(IndexOutOfBoundsException.class, () -> empty.add(1, null));
        assertFalse(full.contains(object));
        full.add(SIZE, object);
        assertTrue(full.contains(object));
    }

    @Test
    void addFirst() {
    }

    @Test
    void addLast() {
    }

    @Test
    void addBefore() {
    }

    @Test
    void addAfter() {
    }

    @Test
    void remove() {
    }

    @Test
    void removeFirst() {
    }

    @Test
    void removeLast() {
    }

    @Test
    void removePrevious() {
    }

    @Test
    void removeNext() {
    }

    @Test
    void get() {
    }

    @Test
    void getFirst() {
    }

    @Test
    void getLast() {
    }

    @Test
    void getPrevious() {
    }

    @Test
    void getNext() {
    }

    @Test
    void set() {
    }

    @Test
    void positionOf() {
    }

    @Test
    void listIterator() {
    }

    @Test
    void testListIterator() {
    }

    @Test
    void testListIterator1() {
    }

    @Test
    void iterator() {
    }

}