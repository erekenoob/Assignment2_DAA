# Assignment2_DAA
# Assignment 2 — Algorithmic Analysis, Correctness and Performance Trade-offs

## 1. Overview
This project included the design and implementation of three basic data structures: **Dynamic Array**, **Linked List** (single linked list with a tail pointer) and **Min-Heap**. In this assignment, the first major objective is the proof of the algorithmic correctness using mathematical proofs and comparison of the performance of these structures with their Big-O asymptotic complexity in theory considering cache memory locality.

## 2. Complexity Analysis
Theoretical complexity of the implemented operations and auxiliary space requirements:

| Data Structure | Operation | Best Case | Average Case | Worst Case | Space |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Dynamic Array** | `get(i)` | $\Omega(1)$ | $\Theta(1)$ | $O(1)$ | $O(1)$ |
| | `add(x)` (end) | $\Omega(1)$ | $\Theta(1)$ | $O(N)$ (resize) | $O(1)$ / $O(N)$* |
| | `add(i, x)` | $\Omega(1)$ (end) | $\Theta(N)$ | $O(N)$ (start) | $O(1)$ / $O(N)$* |
| | `remove(i)` | $\Omega(1)$ (end) | $\Theta(N)$ | $O(N)$ (start) | $O(1)$ |
| | `contains(x)` | $\Omega(1)$ (first)| $\Theta(N)$ | $O(N)$ (not found)| $O(1)$ |
| **Linked List** | `get(i)` | $\Omega(1)$ (head)| $\Theta(N)$ | $O(N)$ (tail) | $O(1)$ |
| | `add(x)` (end) | $\Omega(1)$ | $\Theta(1)$ | $O(1)$ | $O(1)$ |
| | `add(i, x)` | $\Omega(1)$ (head)| $\Theta(N)$ | $O(N)$ (tail) | $O(1)$ |
| | `remove(i)` | $\Omega(1)$ (head)| $\Theta(N)$ | $O(N)$ (tail) | $O(1)$ |
| | `contains(x)` | $\Omega(1)$ (first)| $\Theta(N)$ | $O(N)$ (not found)| $O(1)$ |
| **Min-Heap** | `insert(x)` | $\Omega(1)$ | $\Theta(\log N)$ | $O(\log N)$ | $O(1)$ / $O(N)$* |
| | `peekMin()` | $\Omega(1)$ | $\Theta(1)$ | $O(1)$ | $O(1)$ |
| | `extractMin()`| $\Omega(1)$ | $\Theta(\log N)$ | $O(\log N)$ | $O(1)$ |

*(Space $O(N)$ is required only when `resize()` is triggered for array-based structures).*

**Brief Justification:** 
The Dynamic Array provides $O(1)$ access due to a contiguous memory block but requires $O(N)$ element shifts during insertions/removals. The Linked List requires $O(N)$ for index-based access (pointer traversal), but thanks to the `head` and `tail` pointers, insertions at the beginning and end are performed in $O(1)$. The Min-Heap maintains a tree structure with a height of $\log N$, so balancing operations (`heapifyUp` / `heapifyDown`) take logarithmic time.

## 3. Correctness (Loop Invariants)

### Proof 1: `contains(x)` in Dynamic Array
**Loop Invariant:** At the start of each iteration of the loop with variable `i`, the target element `x` is not contained in the subarray `data[0 ... i-1]`.
*   **Initialization:** Before the first iteration, $i = 0$. The subarray `data[0 ... -1]` is empty. An empty set by definition does not contain `x`. The invariant holds.
*   **Maintenance:** If `data[i] == x`, the loop terminates and the algorithm correctly returns `true`. If `data[i] != x`, we proceed to iteration $i + 1$. We now know for sure that `x` is neither in `data[0 ... i-1]` nor in `data[i]`. Therefore, it is not in `data[0 ... i]`. The invariant is maintained.
*   **Termination:** The loop terminates when $i = size$. According to the invariant, the element `x` is not in the subarray `data[0 ... size-1]`, which constitutes the entire populated array. The algorithm returns `false`, which is the mathematically correct result.

### Proof 2: `heapifyDown(index)` in Min-Heap
**Loop Invariant:** At the start of each iteration of the loop, the subtrees rooted at the left and right children of `index` are valid min-heaps, and the only possible violation of the heap property is between the `index` node and its children (the `index` node might be greater than its children).
*   **Initialization:** The method is called after replacing the root with the last element of the tree. Before this replacement, the tree was a valid heap. Therefore, the left and right subtrees of the root remain untouched and maintain the heap property. The only "problematic" node is the new root. The invariant holds.
*   **Maintenance:** Inside the loop, we find the smallest of the children, `smallestChildIndex`. If `heap[index] <= heap[smallestChildIndex]`, the heap property is restored and the loop terminates. Otherwise, the elements are swapped. Now, the subtree where the element was moved down might have a violation (at the new `index`), but the other branches remain valid. The invariant is carried over to the next level down.
*   **Termination:** The loop terminates either when the element becomes smaller than its children (heap property restored) or when we reach the bottom of the tree (the element becomes a leaf). In both cases, there are no more violations, and the entire tree is once again a valid min-heap.

## 4. Experimental Setup
*   **Input sizes ($n$):** 100; 1,000; 10,000; 100,000.
*   **Operations ($m$):** 10,000 for Random Access; 1,000 for Search; 1,000 for Insert/Remove; $n$ for Priority Processing.
*   **Repetitions:** Each configuration was run 5 times. The tables below present the arithmetic averages.
*   **Timing:** The `System.nanoTime()` method was used.
*   **Environment:** Data generation was strictly performed before starting the timer. A fixed seed `Random(42 + rep)` was used to ensure full experimental reproducibility. In the removal tests, the structure was restored to its original size using symmetrical operations.

## 5. Results

### Workload 1: Random Access (10,000 get() operations)
| n | Structure | Avg Time (ns) | Avg Accesses |
| :--- | :--- | :--- | :--- |
| 100 | Dynamic Array | 330,840 | 10,000 |
| | Linked List | 1,528,000 | 506,935 |
| 1,000 | Dynamic Array | 69,160 | 10,000 |
| | Linked List | 8,187,760 | 5,004,956 |
| 10,000 | Dynamic Array | 7,880 | 10,000 |
| | Linked List | 83,680,240 | 50,080,614 |
| 100,000 | Dynamic Array | 51,640 | 10,000 |
| | Linked List | 931,676,500 | 499,473,863 |

### Workload 2: Search (1,000 contains() operations)
| n | Structure | Avg Time (ns) | Avg Comparisons |
| :--- | :--- | :--- | :--- |
| 100 | Dynamic Array | 372,000 | 100,000 |
| | Linked List | 343,340 | 100,000 |
| 1,000 | Dynamic Array | 1,226,820 | 1,000,000 |
| | Linked List | 2,593,500 | 1,000,000 |
| 10,000 | Dynamic Array | 2,543,280 | 10,000,000 |
| | Linked List | 24,490,680 | 10,000,000 |
| 100,000 | Dynamic Array | 29,827,220 | 100,000,000 |
| | Linked List | 253,926,940 | 100,000,000 |

### Workload 3: Insertion and Removal (1,000 operations)
*(Subset for $n=100,000$)*
| n | Structure | Operation | Avg Time (ns) | Avg Movements |
| :--- | :--- | :--- | :--- | :--- |
| 100,000 | Dynamic Array | Insert(0) | 10,927,100 | 100,499,500 |
| 100,000 | Linked List | Insert(0) | 16,760 | 0 |
| 100,000 | Dynamic Array | Remove(0) | 10,246,020 | 100,499,500 |
| 100,000 | Linked List | Remove(0) | 13,140 | 0 |
| 100,000 | Dynamic Array | Insert(n/2) | 4,404,160 | 50,250,000 |
| 100,000 | Linked List | Insert(n/2) | 91,447,040 | 50,248,500 |
| 100,000 | Dynamic Array | Remove(n/2) | 5,218,300 | 50,249,500 |
| 100,000 | Linked List | Remove(n/2) | 101,210,120 | 50,249,000 |

### Workload 4: Priority Processing (n insertions, n extractions)
| n | Operation | Avg Time (ns) | Avg Comparisons |
| :--- | :--- | :--- | :--- |
| 100,000 | Insert(n) | 2,735,940 | 228,142 |
| 100,000 | Extract(n) | 13,348,760 | 2,831,649 |

### Plots
![Plot 1 - Execution Time](results/plots/plot1.jpg)
![Plot 2 - Operations vs N](results/plots/plot2.jpg)

## 6. Discussion (Theoretical vs. Empirical)
**Agreement with Theory:** The experiments fully confirmed the theoretical predictions.
1. For the array in Workload 1, `get(i)` operation was done in constant time $O(1)$ regardless of $n$, while in the case of `LinkedList`, the accesses increased in a linear way $O(N)$ and achieved 500 million for $n=100,000$.
2. In Workload 3, the `headInsertion()` for `LinkedList` worked in $O(1)$ (zero movements, ~16 µs), while the array worked in $O(N)$ (100 million shifts, ~10 ms).
3. In Workload 4, the comparisons were made for $n$ extraction operations in the amount of ~2.8 million, which matches the $O(N \log N)$ function exactly.


**Discrepancies and the Impact of Constant Factors:** The most interesting behavior is observed in Workload 2 (Search) and Workload 3 (operations in the middle). 
From the theoretical standpoint, the `contains(x)` function executes at an order of $O(N)$ for both the array and the list. According to the results, **the number of comparisons is absolutely the same** (100,000,000 times each). Yet, the array finished its work in 29 milliseconds, whereas the list worked for 253 milliseconds (about 9 times slower).
The explanation is the **Cache Locality**: elements of the `DynamicArray` are stored sequentially in memory and loaded to the fast CPU cache L1/L2 as chunks. On the other hand, the nodes of the `LinkedList` are dispersed in memory resulting in the frequent cache misses and CPU idleness when the CPU waits for data from RAM.


## 7. Design Recommendations
Selection of the data structure must depend on the expected workload profile:
1. **Dynamic Array:** Best for most general workloads. Suitable for applications with heavy index accesses (`get`) and additions always at the end of the array. It should be used where the total efficiency of traversal (search) algorithms matters, since dynamic array is very friendly to CPU cache.
2. **Linked List:** A very specialized structure. Should be used **only** in systems with lots of insertions/deletions always at the beginning of the list (at the end if there is a pointer to it), in which case an array would have $O(N)$ operations. It is completely wrong to use it in systems with frequent accesses by random indexes.
3. **Min-Heap:** The right data structure for task scheduling systems, timers, and priority queues. In Min-Heap one can extract minimal element in $O(\log N)$ time and never shift any elements in $O(N)$ like in sorted array.

## 8. Conclusion
As part of this lab, Dynamic Array, Linked List and Min-Heap were implemented and analyzed. Asymptotic complexity calculations were empirically confirmed with counter values (comparisons, accesses, movements). The key result from this lab is that while designing high-load systems, one can not solely rely on Big-O notation. Due to memory structure used by modern processors (Cache Locality), contiguous data structures (arrays, heap arrays) are many orders faster than node-based ones given the same algorithmic complexity.
