public class LinkedList {
    private Node head;
    private Node tail;
    private int size;

    private static class Node {
        int value;
        Node next;

        Node(int value) {
            this.value = value;
            this.next = null;
        }
    }

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    public void add(int x) {
        Node newNode = new Node(x);
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public static long movementCount = 0;

    public void add(int index, int x) {
        checkIndexForAdd(index);
        if (index == size) {
            add(x);
            return;
        }
        Node newNode = new Node(x);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                movementCount++;
            }
            newNode.next = prev.next;
            prev.next = newNode;
        }
        size++;
    }

    public void remove(int index) {
        checkIndex(index);
        if (index == 0) {
            head = head.next;
            if (head == null) tail = null;
        } else {
            Node prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
                movementCount++;
            }
            prev.next = prev.next.next;
            if (index == size - 1) {
                tail = prev;
            }
        }
        size--;
    }

    public static long accessCount = 0;

    public int get(int index) {
        checkIndex(index);
        Node current = head;
        accessCount++;
        for (int i = 0; i < index; i++) {
            current = current.next;
            accessCount++;
        }
        return current.value;
    }

    public static long comparisonCount = 0;

    public boolean contains(int x) {
        Node current = head;
        while (current != null) {
            comparisonCount++;
            if (current.value == x) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public int size() {
        return size;
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