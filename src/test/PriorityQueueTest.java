package test;

import main.PriorityQueue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static test.TestUtils.*;

@TestMethodOrder(OrderAnnotation.class)
class PriorityQueueTest {

    PriorityQueue<TestObject> queue;

    @BeforeEach
    void setUp() {
        queue = new PriorityQueue<>();
        for (int i = 0; i < SIZE; i++) {
            queue.enqueue(TestObject.random(1, SIZE));
        }
    }

    @Test
    void enqueue() {
        TestObject zero = new TestObject(0);
        queue.enqueue(zero);
        assertEquals(zero, queue.first());
    }

    @Test
    void dequeue() {
        TestObject obj = queue.dequeue();
        for (int i = 0; i < SIZE; i++) {
            if (i < SIZE - 1) {
                assertTrue(obj.getState() <= queue.dequeue().getState());
            } else {
                assertThrows(IllegalStateException.class, () -> queue.dequeue());
            }
        }
    }

    @Test
    void first() {
        TestObject first = queue.first();
        for(TestObject obj : queue) {
            assertTrue(first.getState() <= obj.getState());
        }
    }

    @Test
    void sort() {
        queue.sort(TestObject.DESCENDING_ORDER);
        TestObject first = queue.dequeue();
        for(TestObject obj : queue) {
            assertTrue(obj.getState() <= first.getState());
        }
    }

    @Test
    @Order(1)
    void writeObject() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PATH + "PriorityQueue.dat"))) {
            out.writeObject(queue);
            PREV = queue;
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test
    @Order(2)
    void readObject() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PATH + "PriorityQueue.dat"))) {
            @SuppressWarnings("unchecked")
            PriorityQueue<TestObject> queue = (PriorityQueue<TestObject>)in.readObject();
            assertEquals(PREV, queue);
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            fail();
        }
    }

}