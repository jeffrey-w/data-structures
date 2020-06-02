package test;

import org.junit.jupiter.api.Test;
import util.DefaultComparator;
import util.Quicksort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.TestUtils.SIZE;
import static test.TestUtils.isSorted;

class QuicksortTest {

    @Test
    void sort() {
        TestObject[] objects = new TestObject[SIZE];
        do {
            for (int i = 0; i < SIZE; i++) {
                objects[i] = TestObject.random();
            }
        } while (isSorted(objects, null));
        Quicksort<TestObject> qs = new Quicksort<>();
        qs.sort(objects, new DefaultComparator<>());
        assertTrue(isSorted(objects, null));
    }
}