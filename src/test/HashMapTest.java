package test;

import main.HashMap;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.*;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestUtils.*;

@TestMethodOrder(OrderAnnotation.class)
class HashMapTest {

    private HashMap<TestObject, TestObject> empty, full;

    @BeforeAll
    static void beforeAll() throws IOException {
        openTestDir(PATH);
    }

    @BeforeEach
    void setUp() {
        empty = new HashMap<>();
        full = new HashMap<>();
        for (int i = 0; i < SIZE; i++) {
            full.put(new TestObject(i), VALUE);
        }
    }

    @Test
    void contains() {
        TestObject key = new TestObject(SIZE);
        assertFalse(full.contains(key));
        full.put(key, VALUE);
        assertTrue(full.contains(key));
    }

    @Test
    void put() {
        TestObject key = new TestObject(SIZE - 1);
        assertNull(empty.put(key, TestObject.random()));
        assertEquals(VALUE, full.put(key, TestObject.random()));
        full.put(key, TestObject.random());
        assertEquals(SIZE, full.size());
    }

    @Test
    void remove() {
        assertThrows(IllegalStateException.class, () -> empty.remove(null));
        assertThrows(NoSuchElementException.class, () -> full.remove(null));
        assertEquals(VALUE, full.remove(new TestObject(SIZE - 1)));
        assertEquals(SIZE - 1, full.size());
    }

    @Test
    void get() {
        assertThrows(IllegalStateException.class, () -> empty.get(null));
        assertThrows(NoSuchElementException.class, () -> full.get(null));
        assertEquals(VALUE, full.get(new TestObject(SIZE - 1)));
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

    @Test
    @Order(1)
    void writeObject() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PATH + "HashMap.dat"))) {
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PATH + "HashMap.dat"))) {
            @SuppressWarnings("unchecked")
            HashMap<TestObject, TestObject> map = (HashMap<TestObject, TestObject>)in.readObject();
            assertEquals(PREV, map);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}