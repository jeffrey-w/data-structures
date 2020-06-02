package test;

import org.junit.jupiter.api.Test;
import util.DefaultComparator;
import util.Heapsort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.TestUtils.SIZE;
import static test.TestUtils.isSorted;

class HeapsortTest {

    @Test
    void sort() {
        TestObject[] objects = new TestObject[SIZE];
        do {
            for(int i = 0; i < SIZE; i++) {
                objects[i] = TestObject.random();
            }
        } while (isSorted(objects, null));
        Heapsort<TestObject> hs = new Heapsort<>();
        hs.sort(objects, new DefaultComparator<>());
        assertTrue(isSorted(objects, null));
    }

}