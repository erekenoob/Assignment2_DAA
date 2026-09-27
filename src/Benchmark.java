import java.util.Random;

public class Benchmark {

    private static final int[] N_VALUES = {100, 1000, 10000, 100000};
    private static final int M_OPERATIONS = 10000;
    private static final int REPETITIONS = 5;
    private static final int M_SEARCHES = 1000;
    private static final int M_INSERT_REMOVE = 1000;

    public static void main(String[] args) {
        System.out.println("Starting Benchmarks...\n");
        runWorkload1();
        runWorkload2();
        runWorkload3();
        runWorkload4();

    }

    private static void runWorkload1() {
        System.out.println(" Workload 1: Random Access (10,000 get() operations)");
        System.out.printf("%-10s | %-15s | %-20s | %-20s%n", "n", "Structure", "Avg Time (ns)", "Avg Accesses");
        System.out.println("--------------------------------------------------------------------------");

        for (int n : N_VALUES) {
            long totalTimeArray = 0;
            long totalAccessesArray = 0;

            long totalTimeList = 0;
            long totalAccessesList = 0;

            for (int rep = 0; rep < REPETITIONS; rep++) {
                Random rand = new Random(42 + rep);

                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();
                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    array.add(val);
                    list.add(val);
                }

                int[] randomIndices = new int[M_OPERATIONS];
                for (int i = 0; i < M_OPERATIONS; i++) {
                    randomIndices[i] = rand.nextInt(n);
                }

                DynamicArray.accessCount = 0;
                long startTime = System.nanoTime();

                for (int i = 0; i < M_OPERATIONS; i++) {
                    array.get(randomIndices[i]);
                }

                long endTime = System.nanoTime();
                totalTimeArray += (endTime - startTime);
                totalAccessesArray += DynamicArray.accessCount;

                LinkedList.accessCount = 0;
                startTime = System.nanoTime();

                for (int i = 0; i < M_OPERATIONS; i++) {
                    list.get(randomIndices[i]);
                }

                endTime = System.nanoTime();
                totalTimeList += (endTime - startTime);
                totalAccessesList += LinkedList.accessCount;
            }

            long avgTimeArray = totalTimeArray / REPETITIONS;
            long avgAccessesArray = totalAccessesArray / REPETITIONS;

            long avgTimeList = totalTimeList / REPETITIONS;
            long avgAccessesList = totalAccessesList / REPETITIONS;

            System.out.printf("%-10d | %-15s | %-20d | %-20d%n", n, "Dynamic Array", avgTimeArray, avgAccessesArray);
            System.out.printf("%-10s | %-15s | %-20d | %-20d%n", "", "Linked List", avgTimeList, avgAccessesList);
            System.out.println("--------------------------------------------------------------------------");
        }
    }
    private static void runWorkload2() {
        System.out.println(" Workload 2: Search (1,000 contains() operations)");
        System.out.printf("%-10s | %-15s | %-20s | %-20s%n", "n", "Structure", "Avg Time (ns)", "Avg Comparisons");
        System.out.println("--------------------------------------------------------------------------");

        for (int n : N_VALUES) {
            long totalTimeArray = 0;
            long totalComparisonsArray = 0;

            long totalTimeList = 0;
            long totalComparisonsList = 0;

            for (int rep = 0; rep < REPETITIONS; rep++) {
                Random rand = new Random(42 + rep);

                DynamicArray array = new DynamicArray();
                LinkedList list = new LinkedList();
                for (int i = 0; i < n; i++) {
                    int val = rand.nextInt();
                    array.add(val);
                    list.add(val);
                }

                int[] searchValues = new int[M_SEARCHES];
                for (int i = 0; i < M_SEARCHES; i++) {
                    searchValues[i] = rand.nextInt();
                }

                DynamicArray.comparisonCount = 0;
                long startTime = System.nanoTime();

                for (int i = 0; i < M_SEARCHES; i++) {
                    array.contains(searchValues[i]);
                }

                long endTime = System.nanoTime();
                totalTimeArray += (endTime - startTime);
                totalComparisonsArray += DynamicArray.comparisonCount;

                LinkedList.comparisonCount = 0;
                startTime = System.nanoTime();

                for (int i = 0; i < M_SEARCHES; i++) {
                    list.contains(searchValues[i]);
                }

                endTime = System.nanoTime();
                totalTimeList += (endTime - startTime);
                totalComparisonsList += LinkedList.comparisonCount;
            }

            long avgTimeArray = totalTimeArray / REPETITIONS;
            long avgComparisonsArray = totalComparisonsArray / REPETITIONS;

            long avgTimeList = totalTimeList / REPETITIONS;
            long avgComparisonsList = totalComparisonsList / REPETITIONS;

            System.out.printf("%-10d | %-15s | %-20d | %-20d%n", n, "Dynamic Array", avgTimeArray, avgComparisonsArray);
            System.out.printf("%-10s | %-15s | %-20d | %-20d%n", "", "Linked List", avgTimeList, avgComparisonsList);
            System.out.println("--------------------------------------------------------------------------");
        }

    }
    private static void runWorkload3() {
        System.out.println(" Workload 3: Insertion and Removal (1,000 operations at Head and Middle)");
        System.out.printf("%-8s | %-15s | %-18s | %-18s | %-18s%n", "n", "Structure", "Operation", "Avg Time (ns)", "Avg Movements");
        System.out.println("------------------------------------------------------------------------------------------");

        for (int n : N_VALUES) {
            long[] timeArray = new long[4]; long[] movesArray = new long[4];
            long[] timeList = new long[4];  long[] movesList = new long[4];

            for (int rep = 0; rep < REPETITIONS; rep++) {
                Random rand = new Random(42 + rep);

                java.util.function.Supplier<DynamicArray> newArray = () -> {
                    DynamicArray arr = new DynamicArray();
                    for (int i = 0; i < n; i++) arr.add(rand.nextInt());
                    return arr;
                };
                java.util.function.Supplier<LinkedList> newList = () -> {
                    LinkedList lst = new LinkedList();
                    for (int i = 0; i < n; i++) lst.add(rand.nextInt());
                    return lst;
                };

                DynamicArray arr = newArray.get();
                LinkedList lst = newList.get();

                DynamicArray.movementCount = 0;
                long start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) arr.add(0, 42);
                timeArray[0] += (System.nanoTime() - start); movesArray[0] += DynamicArray.movementCount;

                LinkedList.movementCount = 0;
                start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) lst.add(0, 42);
                timeList[0] += (System.nanoTime() - start); movesList[0] += LinkedList.movementCount;


                DynamicArray.movementCount = 0;
                start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) arr.remove(0);
                timeArray[1] += (System.nanoTime() - start); movesArray[1] += DynamicArray.movementCount;

                LinkedList.movementCount = 0;
                start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) lst.remove(0);
                timeList[1] += (System.nanoTime() - start); movesList[1] += LinkedList.movementCount;

                arr = newArray.get();
                lst = newList.get();

                DynamicArray.movementCount = 0;
                start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) arr.add(arr.size() / 2, 42);
                timeArray[2] += (System.nanoTime() - start); movesArray[2] += DynamicArray.movementCount;

                LinkedList.movementCount = 0;
                start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) lst.add(lst.size() / 2, 42);
                timeList[2] += (System.nanoTime() - start); movesList[2] += LinkedList.movementCount;


                DynamicArray.movementCount = 0;
                start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) arr.remove(arr.size() / 2);
                timeArray[3] += (System.nanoTime() - start); movesArray[3] += DynamicArray.movementCount;

                LinkedList.movementCount = 0;
                start = System.nanoTime();
                for (int i = 0; i < M_INSERT_REMOVE; i++) lst.remove(lst.size() / 2);
                timeList[3] += (System.nanoTime() - start); movesList[3] += LinkedList.movementCount;
            }

            String[] opNames = {"Insert(0)", "Remove(0)", "Insert(n/2)", "Remove(n/2)"};
            for (int i = 0; i < 4; i++) {
                System.out.printf("%-8d | %-15s | %-18s | %-18d | %-18d%n", n, "Dynamic Array", opNames[i], timeArray[i]/REPETITIONS, movesArray[i]/REPETITIONS);
                System.out.printf("%-8s | %-15s | %-18s | %-18d | %-18d%n", "", "Linked List", opNames[i], timeList[i]/REPETITIONS, movesList[i]/REPETITIONS);
            }
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }
    private static void runWorkload4() {
        System.out.println(" Workload 4: Priority Processing (n insertions, n extractions)");
        System.out.printf("%-8s | %-15s | %-18s | %-18s | %-18s%n", "n", "Structure", "Operation", "Avg Time (ns)", "Avg Comparisons");
        System.out.println("------------------------------------------------------------------------------------------");

        for (int n : N_VALUES) {
            long totalTimeInsert = 0;
            long totalCompInsert = 0;
            long totalTimeExtract = 0;
            long totalCompExtract = 0;

            for (int rep = 0; rep < REPETITIONS; rep++) {
                Random rand = new Random(42 + rep);
                MinHeap heap = new MinHeap();

                int[] inputValues = new int[n];
                for (int i = 0; i < n; i++) {
                    inputValues[i] = rand.nextInt();
                }

                MinHeap.comparisonCount = 0;
                long start = System.nanoTime();

                for (int i = 0; i < n; i++) {
                    heap.insert(inputValues[i]);
                }

                long end = System.nanoTime();
                totalTimeInsert += (end - start);
                totalCompInsert += MinHeap.comparisonCount;

                int[] extractedValues = new int[n];

                MinHeap.comparisonCount = 0;
                start = System.nanoTime();

                for (int i = 0; i < n; i++) {
                    extractedValues[i] = heap.extractMin();
                }

                end = System.nanoTime();
                totalTimeExtract += (end - start);
                totalCompExtract += MinHeap.comparisonCount;

                for (int i = 1; i < n; i++) {
                    if (extractedValues[i - 1] > extractedValues[i]) {
                        throw new RuntimeException("Heap property violated: elements are not sorted!");
                    }
                }
            }

            long avgTimeInsert = totalTimeInsert / REPETITIONS;
            long avgCompInsert = totalCompInsert / REPETITIONS;
            long avgTimeExtract = totalTimeExtract / REPETITIONS;
            long avgCompExtract = totalCompExtract / REPETITIONS;

            System.out.printf("%-8d | %-15s | %-18s | %-18d | %-18d%n", n, "Min-Heap", "Insert(n)", avgTimeInsert, avgCompInsert);
            System.out.printf("%-8s | %-15s | %-18s | %-18d | %-18d%n", "", "", "Extract(n)", avgTimeExtract, avgCompExtract);
            System.out.println("------------------------------------------------------------------------------------------");
        }
    }

}