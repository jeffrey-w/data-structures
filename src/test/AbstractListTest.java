package test;

import main.AbstractList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static test.TestUtils.*;
import static util.Common.RAND;

@TestMethodOrder(OrderAnnotation.class)
class AbstractListTest {

    private static TestList copy(TestList list) {
        TestList copy = new TestList();
        for(TestObject obj : list) {
            copy.addLast(obj.clone());
        }
        return copy;
    }

    private TestList empty, full;

    @BeforeEach
    void setUp() {
        empty = new TestList();
        full = new TestList();
        for (int i = 0; i < SIZE; i++) {
            full.add(i, new TestObject(i));
        }
    }

    @Test
    void contains() {
        TestObject object = new TestObject(SIZE);
        assertFalse(full.contains(object));
        full.add(SIZE, object);
        assertTrue(full.contains(object));
    }

    @Test
    void indexOf() {
        int index = RAND.nextInt(SIZE);
        assertThrows(IllegalStateException.class, () -> empty.indexOf(null));
        assertThrows(NoSuchElementException.class, () -> full.indexOf(new TestObject(SIZE)));
        assertEquals(index, full.indexOf(new TestObject(index)));
    }

    @Test
    void sort() {
        full.sort(TestObject.DESCENDING_ORDER);
        assertTrue(isSorted(full, TestObject.DESCENDING_ORDER));
        try {
            Method isSorted = AbstractList.class.getDeclaredMethod("isSorted");
            isSorted.setAccessible(true);
            assertTrue((Boolean)isSorted.invoke(full));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            fail();
        }
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
    @Order(1)
    void writeObject() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PATH + "AbstractList.dat"))) {
            out.writeObject(full);
            PREV = full;
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(2)
    void readObject() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PATH + "AbstractList.datQ"))) {
            TestList list = (TestList)in.readObject();
            assertEquals(PREV, list);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            fail();
        }
    }

}