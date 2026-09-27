public class DynamicArray {
    private int[] data;
    private int size;
    private static final int INITIAL_CAPACITY = 10;

    public DynamicArray() {
        data = new int[INITIAL_CAPACITY];
        size = 0;
    }

    public void add(int x) {
        if (size == data.length) {
            resize();
        }
        data[size] = x;
        size++;
    }

    public static long movementCount = 0;

    public void add(int index, int x) {
        checkIndexForAdd(index);
        if (size == data.length) {
            resize();
        }
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
            movementCount++;
        }
        data[index] = x;
        size++;
    }

    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
            movementCount++;
        }
        size--;
    }

    public static long accessCount = 0;

    public int get(int index) {
        checkIndex(index);
        accessCount++;
        return data[index];
    }

    public static long comparisonCount = 0;

    public boolean contains(int x) {
        for (int i = 0; i < size; i++) {
            comparisonCount++;
            if (data[i] == x) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    private void resize() {
        int newCapacity = data.length * 2;
        int[] newData = new int[newCapacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }

        data = newData;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }
}