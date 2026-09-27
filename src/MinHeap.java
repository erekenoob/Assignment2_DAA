public class MinHeap {
    private int[] heap;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public MinHeap() {
        heap = new int[INITIAL_CAPACITY];
        size = 0;
    }

    public void insert(int x) {
        if (size == heap.length) {
            resize();
        }

        heap[size] = x;

        heapifyUp(size);

        size++;
    }

    public int peekMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[0];
    }

    public int extractMin() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int min = heap[0];

        heap[0] = heap[size - 1];
        size--;

        if (size > 0) {
            heapifyDown(0);
        }

        return min;
    }

    public int size() {
        return size;
    }

    public static long comparisonCount = 0;

    private void heapifyUp(int index) {
        while (index > 0) {
            comparisonCount++;
            if (heap[index] < heap[parent(index)]) {
                swap(index, parent(index));
                index = parent(index);
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        while (leftChild(index) < size) {
            int smallestChildIndex = leftChild(index);

            if (rightChild(index) < size) {
                comparisonCount++;
                if (heap[rightChild(index)] < heap[smallestChildIndex]) {
                    smallestChildIndex = rightChild(index);
                }
            }

            comparisonCount++;
            if (heap[index] <= heap[smallestChildIndex]) {
                break;
            }

            swap(index, smallestChildIndex);
            index = smallestChildIndex;
        }
    }


    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private void resize() {
        int[] newHeap = new int[heap.length * 2];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }

    private int parent(int i) {
        return (i - 1) / 2;
    }

    private int leftChild(int i) {
        return 2 * i + 1;
    }

    private int rightChild(int i) {
        return 2 * i + 2;
    }
}