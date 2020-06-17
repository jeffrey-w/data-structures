package test;

import main.LinkedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

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
        assertThrows(IndexOutOfBoundsException.class, () -> empty.add(1, null));
        assertEquals(full.add(SIZE, TestObject.random()).getElement(), full.getLast());
    }

    @Test
    void addFirst() {
        assertEquals(full.addFirst(TestObject.random()).getElement(), full.getFirst());
    }

    @Test
    void addLast() {
        assertEquals(full.addLast(TestObject.random()).getElement(), full.getLast());
    }

    @Test
    void addBefore() {
        assertThrows(NullPointerException.class, () -> full.addBefore(null, null));
        assertThrows(IllegalArgumentException.class, () -> full.addBefore(empty.addFirst(TestObject.random()), null));
        assertEquals(full.addBefore(full.addFirst(TestObject.random()), TestObject.random()).getElement(),
                     full.getFirst());
    }

    @Test
    void addAfter() {
        assertThrows(NullPointerException.class, () -> full.addAfter(null, null));
        assertThrows(IllegalArgumentException.class, () -> full.addAfter(empty.addLast(TestObject.random()), null));
        assertEquals(full.addAfter(full.addLast(TestObject.random()), TestObject.random()).getElement(),
                     full.getLast());
    }

    @Test
    void remove() {
        assertThrows(IllegalStateException.class, () -> empty.remove(0));
        assertThrows(IndexOutOfBoundsException.class, () -> full.remove(SIZE));
        assertEquals(0, full.remove(0).getState());
    }

    @Test
    void removeFirst() {
        assertThrows(IllegalStateException.class, () -> empty.removeFirst());
        assertEquals(0, full.removeFirst().getState());
    }

    @Test
    void removeLast() {
        assertThrows(IllegalStateException.class, () -> empty.removeLast());
        assertEquals(SIZE - 1, full.removeLast().getState());
    }

    @Test
    void removePrevious() {
        assertThrows(IllegalStateException.class, () -> empty.removePrevious(null));
        assertThrows(NullPointerException.class, () -> full.removePrevious(null));
        assertThrows(IllegalArgumentException.class, () -> full.removePrevious(empty.addLast(TestObject.random())));
        assertEquals(SIZE - 1, full.removePrevious(full.addLast(TestObject.random())).getState());
    }

    @Test
    void removeNext() {
        assertThrows(IllegalStateException.class, () -> empty.removeNext(null));
        assertThrows(NullPointerException.class, () -> full.removeNext(null));
        assertThrows(IllegalArgumentException.class, () -> full.removeNext(empty.addFirst(TestObject.random())));
        assertEquals(0, full.removeNext(full.addFirst(TestObject.random())).getState());
    }

    @Test
    void get() {
        assertThrows(IllegalStateException.class, () -> empty.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> full.get(SIZE));
        assertEquals(0, full.get(0).getState());
    }

    @Test
    void getFirst() {
        assertThrows(IllegalStateException.class, () -> empty.getFirst());
        assertEquals(0, full.getFirst().getState());
    }

    @Test
    void getLast() {
        assertThrows(IllegalStateException.class, () -> empty.getLast());
        assertEquals(SIZE - 1, full.getLast().getState());
    }

    @Test
    void getPrevious() {
        assertThrows(IllegalStateException.class, () -> empty.getPrevious(null));
        assertThrows(NullPointerException.class, () -> full.getPrevious(null));
        assertThrows(IllegalArgumentException.class, () -> full.getPrevious(empty.addLast(null)));
        assertEquals(SIZE - 1, full.getPrevious(full.addLast(TestObject.random())).getState());
    }

    @Test
    void getNext() {
        assertThrows(IllegalStateException.class, () -> empty.getNext(null));
        assertThrows(NullPointerException.class, () -> full.getNext(null));
        assertThrows(IllegalArgumentException.class, () -> full.getNext(empty.addFirst(null)));
        assertEquals(0, full.getNext(full.addFirst(TestObject.random())).getState());
    }

    @Test
    void set() {
        assertThrows(IllegalStateException.class, () -> empty.set(0, null));
        assertThrows(IndexOutOfBoundsException.class, () -> full.set(SIZE, null));
        assertEquals(0, full.set(0, null).getState());
        assertNull(full.getFirst());
    }

    @Test
    void positionOf() {
        assertThrows(NoSuchElementException.class, () -> full.positionOf(null));
        assertEquals(full.getLast(), full.positionOf(new TestObject(SIZE - 1)).getElement());
    }

    @Test
    void listIterator() {
        int count = 0;
        ListIterator<TestObject> one = full.listIterator(), two = full.listIterator();
        one.next();
        one.set(null);
        assertNull(full.getFirst());
        one.add(TestObject.random());
        assertThrows(IndexOutOfBoundsException.class, () -> full.listIterator(full.size() + 1));
        assertThrows(IllegalArgumentException.class, () -> full.listIterator(empty.addFirst(null)));
        assertThrows(IllegalStateException.class, one::remove);
        assertThrows(IllegalStateException.class, () -> one.set(null));
        assertEquals(2, one.nextIndex());
        while (two.hasNext()) {
            two.next();
            count++;
        }
        assertThrows(NoSuchElementException.class, two::next);
        assertEquals(count, two.nextIndex());
        assertEquals(count, full.size());
        while (two.hasPrevious()) {
            two.previous();
            two.remove();
            count--;
        }
        assertThrows(NoSuchElementException.class, two::previous);
        assertThrows(IllegalStateException.class, () -> two.set(null));
        assertThrows(IllegalStateException.class, two::remove);
        assertEquals(-1, two.previousIndex());
        assertEquals(count, full.size());
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

}