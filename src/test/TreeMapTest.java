package test;

import main.Entry;
import main.TreeMap;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestUtils.*;
import static util.Common.RAND;

@TestMethodOrder(OrderAnnotation.class)
class TreeMapTest {

    private static int treeHeight(TreeMap<TestObject, TestObject> map) {
        return heightOf(getNodeOf(map, "root"), getNodeOf(map, "nil"));
    }

    private static int heightOf(Entry<TestObject, TestObject> entry, Entry<TestObject, TestObject> nil) {
        if (entry == nil) {
            return 0;
        }
        return Math.max(heightOf(getChildOf(entry, "left"), nil) + 1, heightOf(getChildOf(entry, "right"), nil) + 1);
    }

    @SuppressWarnings("unchecked")
    private static Entry<TestObject, TestObject> getNodeOf(TreeMap<TestObject, TestObject> map, String field) {
        try {
            Field node = TreeMap.class.getDeclaredField(field);
            node.setAccessible(true);
            return (Entry<TestObject, TestObject>)node.get(map);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static Entry<TestObject, TestObject> getChildOf(Entry<TestObject, TestObject> entry, String field) {
        Class<?>[] classes = TreeMap.class.getDeclaredClasses();
        for (Class<?> cls : classes) {
            try {
                Field childNode = cls.getDeclaredField(field);
                childNode.setAccessible(true);
                return (Entry<TestObject, TestObject>)childNode.get(entry);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
        throw new AssertionError();
    }

    private static int expectedHeight(TreeMap<TestObject, TestObject> map) {
        return (int)((Math.log10(map.size() + 1) / Math.log10(2)) * 2);
    }

    private static void halve(TreeMap<TestObject, TestObject> map) {
        int half = map.size() >> 1;
        for (int i = 0; i < half; i++) {
            if (RAND.nextBoolean()) {
                map.removeFirst();
            } else {
                map.removeLast();
            }
        }
    }

    private static void removeRoot(TreeMap<TestObject, TestObject> map) {
        map.remove(getNodeOf(map, "root").getKey());
    }

    private TreeMap<TestObject, TestObject> empty, sequential, random;

    @BeforeAll
    public static void beforeAll() throws IOException {
        openTestDir(PATH);
    }

    @BeforeEach
    void setUp() {
        empty = new TreeMap<>();
        sequential = new TreeMap<>();
        random = new TreeMap<>();
        for (int i = 0; i < SIZE; i++) {
            sequential.put(new TestObject(i), VALUE);
            random.put(TestObject.random(), VALUE);
        }
        assertTrue(isSorted(random.keySet(), null));
        assertTrue(treeHeight(random) <= expectedHeight(random));
    }

    @Test
    void contains() {
        TestObject key = new TestObject(SIZE);
        assertFalse(sequential.contains(key));
        sequential.put(key, VALUE);
        assertTrue(sequential.contains(key));
    }

    @Test
    void put() {
        TestObject key = new TestObject(SIZE - 1);
        assertNull(empty.put(key, VALUE));
        assertEquals(VALUE, sequential.put(key, TestObject.random()));
        sequential.put(key, TestObject.random());
        assertEquals(SIZE, sequential.size());
    }

    @Test
    void remove() {
        assertThrows(IllegalStateException.class, () -> empty.remove(null));
        assertThrows(NoSuchElementException.class, () -> sequential.remove(null));
        assertEquals(sequential.getLast().getValue(), sequential.remove(new TestObject(SIZE - 1)));
        assertEquals(SIZE - 1, sequential.size());
        halve(random);
        removeRoot(random);
        assertTrue(treeHeight(random) <= expectedHeight(random));
    }

    @Test
    void removeFirst() {
        assertThrows(IllegalStateException.class, () -> empty.removeFirst());
        assertEquals(0, sequential.removeFirst().getKey().getState());
    }

    @Test
    void removeLast() {
        assertThrows(IllegalStateException.class, () -> empty.removeLast());
        assertEquals(SIZE - 1, sequential.removeLast().getKey().getState());
    }

    @Test
    void removePrevious() {
        assertThrows(IllegalStateException.class, () -> empty.removePrevious(null));
        assertThrows(NoSuchElementException.class, () -> sequential.removePrevious(null));
        assertEquals(SIZE - 2, sequential.removePrevious(sequential.getLast().getKey()).getKey().getState());
    }

    @Test
    void removeNext() {
        assertThrows(IllegalStateException.class, () -> empty.removeNext(null));
        assertThrows(NoSuchElementException.class, () -> sequential.removeNext(null));
        assertEquals(1, sequential.removeNext(sequential.getFirst().getKey()).getKey().getState());
    }

    @Test
    void get() {
        TestObject key = new TestObject(random.size());
        TestObject value = TestObject.random();
        assertThrows(IllegalStateException.class, () -> empty.get(null));
        assertThrows(NoSuchElementException.class, () -> sequential.get(null));
        sequential.put(key, value);
        assertEquals(value, sequential.get(key));
    }

    @Test
    void getFirst() {
        assertThrows(IllegalStateException.class, () -> empty.getFirst());
        assertEquals(0, sequential.getFirst().getKey().getState());
    }

    @Test
    void getLast() {
        assertThrows(IllegalStateException.class, () -> empty.getLast());
        assertEquals(sequential.size() - 1, sequential.getLast().getKey().getState());
    }

    @Test
    void getPrevious() {
        assertThrows(IllegalStateException.class, () -> empty.getPrevious(null));
        assertThrows(NoSuchElementException.class, () -> sequential.getPrevious(null));
        assertEquals(SIZE - 2, sequential.getPrevious(sequential.getLast().getKey()).getKey().getState());
    }

    @Test
    void getNext() {
        assertThrows(IllegalStateException.class, () -> empty.getNext(null));
        assertThrows(NoSuchElementException.class, () -> sequential.getNext(null));
        assertEquals(1, sequential.getNext(sequential.getFirst().getKey()).getKey().getState());
    }

    @Test
    void entrySet() {
        Iterator<Entry<TestObject, TestObject>> iterator = random.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        assertTrue(random.entrySet().isEmpty());
        assertThrows(NoSuchElementException.class, iterator::next);
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    @Order(1)
    void writeObject() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PATH + "TreeMap.dat"))) {
            out.writeObject(random);
            PREV = random;
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(2)
    void readObject() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PATH + "TreeMap.dat"))) {
            @SuppressWarnings("unchecked")
            TreeMap<TestObject, TestObject> map = (TreeMap<TestObject, TestObject>)in.readObject();
            assertEquals(PREV, map);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}