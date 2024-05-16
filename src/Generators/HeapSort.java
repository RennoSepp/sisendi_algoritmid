package Generators;

import java.util.*;

public class HeapSort {

    // Executes the heap sort generation for given nodes and difficulty
    public static List<List<Integer>> execute(int nodes, int difficulty){
        return createHeapSortArrays(nodes, difficulty, 10);
    }

    // Creates a list of heap sort configurations based on node count, difficulty and the amount of configurations desired
    public static List<List<Integer>> createHeapSortArrays(int n, int x, int amount) {
        List<List<Integer>> result = new ArrayList<>();
        while (result.size() < amount) {
            List<Integer> combinations = new ArrayList<>(Arrays.asList(new Integer[n]));
            for (int i = 0; i < n; i++) {
                combinations.set(i, 1); // Initialize all values to 1
            }
            List<Integer> indexes = new ArrayList<>();
            indexes.add(0);
            List<Integer> temp = new ArrayList<>();
            temp = findHeapCombination(n, combinations, indexes, 0, 0, x);
            if (temp != null) {
                assignRandomNumbers(temp); // Randomize numbers after a valid combination is found
                result.add(temp); // Add the valid configuration to results
            }
        }
        return result;
    }

    // Assigns random numbers to the elements of the provided list and sorts them accordingly
    public static void assignRandomNumbers(List<Integer> inputList) {
        int n = inputList.size();
        List<Integer> randomNumbers = new ArrayList<>();
        List<Integer> sortedInputList = new ArrayList<>(inputList);

        // Generate n random numbers between 1 and 100
        for (int i = 0; i < n; i++) {
            randomNumbers.add((int) (Math.random() * 100) + 1);
        }

        // Sort the random numbers
        Collections.sort(randomNumbers);

        // Sort the input list to map indices correctly
        Collections.sort(sortedInputList);

        // Map the original elements to the random numbers
        for (int i = 0; i < n; i++) {
            int index = inputList.indexOf(sortedInputList.get(i));
            inputList.set(index, randomNumbers.get(i));
        }
    }

    // Attempts to find a heap combination that meets a specific sorting difficulty
    public static List<Integer> findHeapCombination(int n, List<Integer> combination, List<Integer> indexes, int level, int position, int difficulty) {
        if (level == n - 1) {
            List<Integer> test = new ArrayList<>(combination);
            if (sortHeapifiedList(test) == difficulty){
                return combination; // Return the combination if it meets the difficulty criteria
            }
        } else {
            int index = indexes.remove(position);
            combination.set(index, n - level);
            if (index * 2 + 1 < n) {
                indexes.add(index * 2 + 1);
            }
            if (index * 2 + 2 < n) {
                indexes.add(index * 2 + 2);
            }
            for (int i = 0; i < indexes.size(); i++) {
                // Recursion with copies of lists to maintain state
                List<Integer> newCombination = new ArrayList<>(combination);
                List<Integer> newIndexes = new ArrayList<>(indexes);
                Random random = new Random();
                return findHeapCombination(n, newCombination, newIndexes, level + 1, random.nextInt(newIndexes.size()), difficulty);
            }
        }
        return null;
    }

    // Simulates the sorting of a list arranged as a heap, counting the moves
    public static int sortHeapifiedList(List<Integer> list) {
        int n = list.size();
        int totalMoves = 0;

        // Extract elements one by one from the heap
        for (int i = n - 1; i > 0; i--) {
            swap(list, 0, i); // Swap the root with the last item
            totalMoves++; // Increment move count

            // Max heapify the reduced heap
            totalMoves += heapify(list, i, 0);
        }

        return totalMoves; // Return the total number of sorting moves made
    }

    // Heapify a subtree rooted at index i, n is the size of heap
    private static int heapify(List<Integer> list, int n, int i) {
        int largest = i; // Initialize largest as root
        int left = 2 * i + 1; // left child index
        int right = 2 * i + 2; // right child index
        int moves = 0;

        // If left child is larger than root
        if (left < n && list.get(left) > list.get(largest)) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && list.get(right) > list.get(largest)) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            swap(list, i, largest); // Swap elements
            moves++; // Increment move count

            // Recursively heapify the affected subtree
            moves += heapify(list, n, largest);
        }

        return moves; // Return the total number of moves made in heapifying
    }

    // Utility function to swap two elements in the list
    private static void swap(List<Integer> list, int i1, int i2) {
        int temp = list.get(i1);
        list.set(i1, list.get(i2));
        list.set(i2, temp);
    }

}
