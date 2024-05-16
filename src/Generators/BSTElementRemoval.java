package Generators;

import java.util.*;

public class BSTElementRemoval {
    private static final Random random = new Random(); // Singleton Random instance for uniform random operations

    // Public interface to execute the process of finding BST configurations
    public static List<List<List<Integer>>> execute(int nodes, int removals, int difficulty) {
        return findBSTWithRemovables(nodes, removals, difficulty, 10);
    }

    // Finds suitable Binary Search Trees (BSTs) where certain nodes can be removed
    public static List<List<List<Integer>>> findBSTWithRemovables(int nodes, int removals, int difficulty, int amount) {
        List<List<List<Integer>>> result = new ArrayList<>();
        List<List<Integer>> combinations = findCombinations(removals, difficulty); // Generate all potential removal combinations
        int i = 0;
        while (i < amount) {
            List<Integer> bst = createRandomBST(nodes); // Create a random BST
            for (int j = 0; j < 5; j++) { // Try multiple modifications on the same BST
                List<Integer> modifyBst = new ArrayList<>(bst);
                List<Integer> bstRemovables = new ArrayList<>();
                List<Integer> randomCombination = new ArrayList<>(combinations.get(random.nextInt(combinations.size())));
                List<Integer> kartul = new ArrayList<>(randomCombination); // Snapshot of the chosen combination
                for (int k = 0; k < removals; k++) {
                    List<Integer> modifyBstNodeLevels = evaluateArray(modifyBst);
                    if (randomCombination.isEmpty()) break;
                    int randomIndex = random.nextInt(randomCombination.size());
                    int removableLevel = randomCombination.remove(randomIndex);
                    int removableIndex = popRandomElementWithValue(modifyBstNodeLevels, removableLevel);
                    if (removableIndex == -1) break;
                    bstRemovables.add(modifyBst.get(removableIndex));
                    int removableValue = modifyBst.get(removableIndex);
                    modifyBst = removeValueFromArray(modifyBst, removableValue + (removableLevel < 3 ? 0 : 1));
                    modifyBst = incrementElementsLessThanX(modifyBst, removableValue + (removableLevel < 3 ? 0 : 1));
                }
                if (bstRemovables.size() == removals) {
                    adjustRemovables(bstRemovables); // Adjust values to maintain BST properties
                    List<List<Integer>> new_result = new ArrayList<>();
                    new_result.add(bstRemovables);
                    new_result.add(bst);
                    new_result.add(kartul);
                    result.add(new_result);
                    i++;
                    break;
                }
            }
        }
        return result;
    }

    // Adjusts the removable values to avoid collisions in the BST
    private static void adjustRemovables(List<Integer> removables) {
        for (int i = removables.size() - 1; i > 0; i--) {
            for (int j = i - 1; j >= 0; j--) {
                if (removables.get(j) >= removables.get(i)) {
                    removables.set(i, removables.get(i) - 1);
                }
            }
        }
    }

    // Increments all elements less than a given value, x, in a list
    private static List<Integer> incrementElementsLessThanX(List<Integer> arr, int x) {
        List<Integer> result = new ArrayList<>();
        for (int item : arr) {
            result.add(item < x ? item + 1 : item);
        }
        return result;
    }

    // Removes a specific value from an array and returns the new array
    private static List<Integer> removeValueFromArray(List<Integer> arr, int value) {
        List<Integer> result = new ArrayList<>();
        for (int item : arr) {
            if (item != value) result.add(item);
        }
        return result;
    }

    // Removes an element with a specific value from an array and returns its index
    private static int popRandomElementWithValue(List<Integer> arr, int value) {
        List<Integer> indices = new ArrayList<>();
        for (int index = 0; index < arr.size(); index++) {
            if (arr.get(index) == value) {
                indices.add(index);
            }
        }
        if (indices.isEmpty()) return -1;
        int chosenIndex = indices.get(random.nextInt(indices.size()));
        arr.remove(chosenIndex);
        return chosenIndex;
    }

    // Finds all combinations of integers that sum up to x, with each integer not exceeding 5
    private static List<List<Integer>> findCombinations(int n, int x) {
        if (n == 0 && x == 0) return Collections.singletonList(new ArrayList<>());
        if (n == 0 || x < 0) return new ArrayList<>();
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            for (List<Integer> combo : findCombinations(n - 1, x - i)) {
                List<Integer> newCombo = new ArrayList<>(combo);
                newCombo.add(0, i);
                result.add(newCombo);
            }
        }
        return result;
    }

    // Creates a BST with random values ranging from 1 to n
    private static List<Integer> createRandomBST(int n) {
        List<Integer> bst = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            bst.add(i);
        }
        Collections.shuffle(bst);
        return bst;
    }

    // Evaluates an array and assigns levels based on BST node properties
    public static List<Integer> evaluateArray(List<Integer> arr) {
        List<Integer> result = new ArrayList<>(Collections.nCopies(arr.size(), 1)); // Initially set all levels to 1

        for (int i = 0; i < arr.size(); i++) {
            int n = arr.get(i);
            boolean hasNPlus1 = false;
            boolean hasNMinus1 = false;
            Map<Integer, Integer> indices = new HashMap<>();

            for (int j = i + 1; j < arr.size(); j++) {
                indices.put(arr.get(j), j);
                if (arr.get(j) == n + 1) hasNPlus1 = true;
                if (arr.get(j) == n - 1) hasNMinus1 = true;
            }

            if (hasNMinus1 && hasNPlus1) result.set(i, 3);
            else if (hasNMinus1 || hasNPlus1) result.set(i, 2);

            if (hasNMinus1) {
                Integer nPlus2Index = indices.get(n + 2);
                if (nPlus2Index != null) {
                    Integer nPlus1Index = indices.get(n + 1);
                    if (nPlus1Index != null && nPlus1Index > nPlus2Index) result.set(i, 4);
                }
            }
        }
        return result;
    }
}
