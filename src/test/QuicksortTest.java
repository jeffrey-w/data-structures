package test;

import org.junit.jupiter.api.Test;
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
        Quicksort<TestObject> qs = new Quicksort<>(null);
        qs.sort(objects);
        assertTrue(isSorted(objects, null));
    }
}