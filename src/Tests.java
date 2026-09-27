import java.util.PriorityQueue;
import java.util.Random;

public class Tests {

    public static void main(String[] args) {
        System.out.println("Starting tests...");

        testDynamicArray();
        testLinkedList();
        testMinHeap();
        testLargeInputs();

        System.out.println("All tests passed successfully.");
    }

    private static void testDynamicArray() {
        DynamicArray list = new DynamicArray();

        assertEquals(0, list.size(), "Initial size should be 0");
        assertThrows(() -> list.get(0), IndexOutOfBoundsException.class);
        assertThrows(() -> list.remove(0), IndexOutOfBoundsException.class);
        assertThrows(() -> list.add(1, 10), IndexOutOfBoundsException.class);

        list.add(10);
        assertEquals(1, list.size(), "Size should be 1");
        assertEquals(10, list.get(0), "Element at index 0 should be 10");
        assertEquals(true, list.contains(10), "Should contain 10");
        assertEquals(false, list.contains(99), "Should not contain 99");

        list.add(20);
        list.add(30);
        list.add(20);
        assertEquals(4, list.size(), "Size should be 4");

        list.add(0, 5);
        assertEquals(5, list.get(0), "First element should be 5");
        list.add(list.size(), 40);
        assertEquals(40, list.get(list.size() - 1), "Last element should be 40");

        list.remove(0);
        assertEquals(10, list.get(0), "New first element should be 10");
        list.remove(list.size() - 1);
        assertEquals(20, list.get(list.size() - 1), "New last element should be 20");
    }

    private static void testLinkedList() {
        LinkedList list = new LinkedList();

        assertEquals(0, list.size(), "Initial size should be 0");
        assertThrows(() -> list.get(0), IndexOutOfBoundsException.class);
        assertThrows(() -> list.remove(0), IndexOutOfBoundsException.class);

        list.add(10);
        assertEquals(1, list.size(), "Size should be 1");
        assertEquals(10, list.get(0), "Element at index 0 should be 10");

        list.add(20);
        list.add(30);
        list.add(20);
        assertEquals(4, list.size(), "Size should be 4");

        list.add(0, 5);
        assertEquals(5, list.get(0), "First element should be 5");
        list.add(list.size(), 40);
        assertEquals(40, list.get(list.size() - 1), "Last element should be 40");

        assertEquals(true, list.contains(40), "Should contain 40");
        assertEquals(false, list.contains(99), "Should not contain 99");

        list.remove(0);
        assertEquals(10, list.get(0), "New first element should be 10");
        list.remove(list.size() - 1);
        assertEquals(20, list.get(list.size() - 1), "New last element should be 20");
    }

    private static void testMinHeap() {
        MinHeap heap = new MinHeap();

        assertEquals(0, heap.size(), "Initial size should be 0");
        assertThrows(() -> heap.peekMin(), IllegalStateException.class);
        assertThrows(() -> heap.extractMin(), IllegalStateException.class);

        heap.insert(10);
        assertEquals(1, heap.size(), "Size should be 1");
        assertEquals(10, heap.peekMin(), "Min should be 10");

        heap.insert(5);
        heap.insert(20);
        heap.insert(5);
        heap.insert(1);

        assertEquals(5, heap.size(), "Size should be 5");
        assertEquals(1, heap.peekMin(), "Min should be 1 after inserting 1");

        PriorityQueue<Integer> javaHeap = new PriorityQueue<>();
        javaHeap.add(10); javaHeap.add(5); javaHeap.add(20); javaHeap.add(5); javaHeap.add(1);

        while (heap.size() > 0) {
            int myMin = heap.extractMin();
            int javaMin = javaHeap.poll();
            assertEquals(javaMin, myMin, "Extracted min should match Java's PriorityQueue");
        }
        assertEquals(0, heap.size(), "Heap should be empty after extracting all elements");
    }

    private static void testLargeInputs() {
        DynamicArray array = new DynamicArray();
        LinkedList list = new LinkedList();
        MinHeap heap = new MinHeap();

        int n = 10000;
        Random rand = new Random(42);

        for (int i = 0; i < n; i++) {
            int val = rand.nextInt();
            array.add(val);
            list.add(val);
            heap.insert(val);
        }

        assertEquals(n, array.size(), "Array size should be 10000");
        assertEquals(n, list.size(), "List size should be 10000");
        assertEquals(n, heap.size(), "Heap size should be 10000");
    }


    private static void assertEquals(Object expected, Object actual, String message) {
        if (!expected.equals(actual)) {
            throw new AssertionError(message + " | Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static void assertThrows(Runnable runnable, Class<? extends Exception> expectedException) {
        try {
            runnable.run();
        } catch (Exception e) {
            if (expectedException.isInstance(e)) {
                return;
            }
            throw new AssertionError("Expected " + expectedException.getSimpleName() + " but got " + e.getClass().getSimpleName());
        }
        throw new AssertionError("Expected " + expectedException.getSimpleName() + " to be thrown, but nothing was thrown.");
    }
}