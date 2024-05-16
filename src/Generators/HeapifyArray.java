package Generators;

import java.util.*;

public class HeapifyArray {

    // Executes the heap creation with specified number of nodes and difficulty
    public static List<List<Integer>> execute(int nodes, int difficulty){
        return createHeapArrays(nodes, difficulty, 10);
    }

    // Generates heap arrays based on node count, difficulty, and required number of arrays
    public static List<List<Integer>> createHeapArrays(int n, int difficulty, int amount) {
        List<Integer> maxComparisons = calculateMaxComparisons(n);
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            results.add(matchWithRandomNumbers(transformList(assignRanks(buildHeap(incrementPositionsByRandomWeights(maxComparisons, difficulty))))));
        }
        return results;
    }

    // Calculates the maximum depth of the heap based on node count
    public static int calculateMaxDepth(int n) {
        if (n == 0) {
            return 0;
        }
        return (int) (Math.log(n) / Math.log(2));
    }

    // Increments positions by random weights generated based on difficulty level
    public static List<Integer> incrementPositionsByRandomWeights(List<Integer> arr, int count) {
        int totalSum = arr.stream().mapToInt(Integer::intValue).sum();  // Calculate total sum of array elements
        Random rand = new Random();

        // Generate unique random positions within the total sum
        Set<Integer> positionsSet = new HashSet<>();
        while (positionsSet.size() < count) {
            positionsSet.add(rand.nextInt(totalSum) + 1);
        }
        List<Integer> randomPositions = new ArrayList<>(positionsSet);
        Collections.sort(randomPositions);

        // Create an output list initialized with zeros
        List<Integer> output = new ArrayList<>(Collections.nCopies(arr.size(), 0));
        int index = 0;
        int currentSum = 0;
        for (int pos : randomPositions) {
            while (currentSum < pos) {
                currentSum += arr.get(index);
                index++;
            }
            output.set(index - 1, output.get(index - 1) + 1);
        }
        return output;
    }

    // Calculates the maximum comparisons for each node based on its position in the heap
    public static List<Integer> calculateMaxComparisons(int n) {
        List<Integer> heights = new ArrayList<>(Collections.nCopies(n, -1));
        List<Integer> comparisons = new ArrayList<>(Collections.nCopies(n, 0));

        for (int i = 0; i < n; i++) {
            int height = calculateHeight(i, n, heights);
            comparisons.set(i, height);
        }
        return comparisons;
    }

    // Helper method to recursively calculate the height of nodes in the heap
    private static int calculateHeight(int i, int n, List<Integer> heights) {
        if (i >= n) {
            return -1;
        }
        if (heights.get(i) != -1) {
            return heights.get(i);
        }
        int leftHeight = calculateHeight(2 * i + 1, n, heights);
        int rightHeight = calculateHeight(2 * i + 2, n, heights);
        heights.set(i, 1 + Math.max(leftHeight, rightHeight));
        return heights.get(i);
    }

    // Builds the heap based on input swaps, adjusting node positions
    public static List<List<Integer>> buildHeap(List<Integer> swaps) {
        int nrOfElements = swaps.size();
        int leaves = calculateLeaves(nrOfElements);
        List<List<Integer>> heap = new ArrayList<>();

        // Initialize the heap with pairs of zero and their index
        for (int i = 0; i < nrOfElements; i++) {
            List<Integer> pair = new ArrayList<>();
            pair.add(0);  // Initial value
            pair.add(i);  // Index
            heap.add(pair);
        }

        Random random = new Random();

        // Perform swaps to build the heap
        for (int i = 0; i < nrOfElements; i++) {
            int index = nrOfElements - i - leaves - 1;
            if (index < 0) continue;  // Skip if index is negative

            int left = 2 * index + 1;
            int right = 2 * index + 2;

            // Check if swaps need to be performed and adjust heap accordingly
            if (swaps.get(index) == 0) {
                if (right < nrOfElements) {
                    if (heap.get(index).get(0) < heap.get(left).get(0) && heap.get(left).get(0) >= heap.get(right).get(0)) {
                        heap.get(index).set(0, heap.get(left).get(0) + 1);
                    } else if (heap.get(index).get(0) < heap.get(right).get(0) && heap.get(right).get(0) > heap.get(left).get(0)) {
                        heap.get(index).set(0, heap.get(right).get(0) + 1);
                    } else {
                        heap.get(index).set(0, heap.get(left).get(0) + 1);
                    }
                } else if (left < nrOfElements) {
                    heap.get(index).set(0, heap.get(left).get(0) + 1);
                }
            } else {
                int current_index = index;
                for (int j = 0; j < swaps.get(index); j++) {
                    // Simulate random swaps and increments based on the heap properties
                    // Detailed swap logic omitted for brevity
                }
            }
        }

        return heap;
    }

    // Calculates the number of leaf nodes in the heap
    private static int calculateLeaves(int n) {
        if (n == 0) {
            return 0;
        }
        int h = (int) Math.floor(Math.log(n) / Math.log(2)) + 1;
        int nodesFilled = (1 << (h - 1)) - 1;
        int nodesInLastLevel = n - nodesFilled;

        return nodesInLastLevel;
    }

    // Assigns a rank to each element based on its initial position and value
    public static List<Integer> assignRanks(List<List<Integer>> input) {
        // Sorts the list based on the second element (index)
        Collections.sort(input, new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> a, List<Integer> b) {
                return a.get(1).compareTo(b.get(1));
            }
        });

        // Collects the first elements from the sorted list
        List<Integer> firstElements = new ArrayList<>();
        for (List<Integer> list : input) {
            firstElements.add(list.get(0));
        }
        return firstElements;
    }

    // Transforms a list by applying a mathematical transformation to each element
    public static List<Integer> transformList(List<Integer> nums) {
        int n = nums.size();
        List<Integer> transformed = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int new_value = nums.get(i) * 100 + (n - i);
            transformed.add(new_value);
        }

        return transformed;
    }

    // Matches the transformed values with random numbers
    public static List<Integer> matchWithRandomNumbers(List<Integer> numbers) {
        List<IndexedValue> indexedValues = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            indexedValues.add(new IndexedValue(numbers.get(i), i));
        }

        Random random = new Random();
        List<Integer> randomNumbers = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            randomNumbers.add(random.nextInt(100)); // Generates a random number
        }

        Collections.sort(randomNumbers); // Sort the random numbers
        Collections.sort(indexedValues, Comparator.comparingInt((IndexedValue iv) -> iv.value)
                .thenComparingInt((IndexedValue iv) -> iv.index));

        // Maps sorted values to sorted random numbers
        List<Integer> matchedRandoms = new ArrayList<>(Collections.nCopies(numbers.size(), 0));
        int n = numbers.size();
        for (int i = 0; i < n; i++) {
            int targetIndex = indexedValues.get(i).index;
            matchedRandoms.set(targetIndex, randomNumbers.get(i));
        }

        return matchedRandoms;
    }

    // Helper class for storing values and their original indices
    private static class IndexedValue {
        int value;
        int index;

        IndexedValue(int value, int index) {
            this.value = value;
            this.index = index;
        }
    }

}
