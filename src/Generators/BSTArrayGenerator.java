package Generators;

import java.util.*;

public class BSTArrayGenerator {

    // TreeNode class represents each node of the binary search tree
    static class TreeNode {
        TreeNode left;   // Reference to the left child
        TreeNode right;  // Reference to the right child
        int index;       // Index associated with the node

        TreeNode(int index) {
            this.index = index;
        }
    }

    // Builds a binary search tree (BST) by a given height and lists the indexes in increasing order
    public static List<Integer> buildBSTByHeightAndListIndexes(int h) {
        int maxNodes = (int) Math.pow(2, h + 1) - 1; // Calculate maximum nodes in a full binary tree of height h
        TreeNode bst = buildTree(1, maxNodes); // Recursively build the tree starting from index 1
        List<Integer> sortedIndexes = new ArrayList<>();
        inorderTraversal(bst, sortedIndexes); // Perform inorder traversal to get indexes in sorted order
        return sortedIndexes;
    }

    // Recursively builds a full binary tree up to a given node index limit
    private static TreeNode buildTree(int nodeIndex, int maxNodes) {
        if (nodeIndex > maxNodes) {
            return null; // Base case: return null if the index exceeds the maxNodes
        }
        TreeNode node = new TreeNode(nodeIndex); // Create a new tree node
        node.left = buildTree(2 * nodeIndex, maxNodes); // Recursively build the left subtree
        node.right = buildTree(2 * nodeIndex + 1, maxNodes); // Recursively build the right subtree
        return node;
    }

    // Performs inorder traversal on a binary tree, adding node indexes to the result list
    private static void inorderTraversal(TreeNode node, List<Integer> result) {
        if (node != null) {
            inorderTraversal(node.left, result); // Visit left subtree
            result.add(node.index);              // Visit root
            inorderTraversal(node.right, result); // Visit right subtree
        }
    }

    // Generates random BST configurations based on a set number of nodes, a difficulty level, and the desired number of configurations
    public static List<List<Integer>> generateRandomBSTConfigurations(int n, int x, int numConfigs) {
        List<List<Integer>> configurations = new ArrayList<>();
        for (int i = 0; i < numConfigs; i++) {
            List<List<Integer>> result = new ArrayList<>();
            canPlaceNodes(new ArrayList<>(), new HashSet<>(List.of(1)), 0, x, n, result); // Determine possible node placements
            if (result.size() == 1) {
                configurations.add(result.get(0)); // Add the valid configuration to the list
            }
        }
        return configurations;
    }

    // Recursive function to determine if nodes can be placed in a sequence that results in a specific difficulty configuration
    private static void canPlaceNodes(List<Integer> currentLabels, Set<Integer> remainingLabels, int currentSum, int target, int n, List<List<Integer>> results) {
        if (!results.isEmpty()) return; // Exit if a result is already found

        if (currentLabels.size() == n) {
            if (currentSum == target) {
                results.add(new ArrayList<>(currentLabels)); // Add configuration to results if it matches the target
                return;
            }
            return;
        }

        if (remainingLabels.isEmpty() || currentSum > target || currentLabels.size() > n) {
            return; // Exit the recursion if conditions are not met
        }

        List<Integer> temp = new ArrayList<>(remainingLabels);
        Collections.shuffle(temp);  // Randomize the processing order of elements
        int depth;

        for (int label : temp) {
            depth = (int) (Math.log(label) / Math.log(2)); // Calculate depth based on label

            // Prepare for recursion
            remainingLabels.remove(label);
            remainingLabels.add(label * 2);
            remainingLabels.add(label * 2 + 1);
            currentLabels.add(label);

            // Recursive call
            canPlaceNodes(currentLabels, remainingLabels, currentSum + depth, target, n, results);

            // Backtrack to explore other possibilities
            currentLabels.remove(currentLabels.size() - 1);
            remainingLabels.remove(label * 2);
            remainingLabels.remove(label * 2 + 1);
            remainingLabels.add(label);
        }
    }

    // Maps each element of the configurations to the BST positions based on height
    public static List<List<Integer>> matchElementsToBSTPositions(List<List<Integer>> randomConfigs, int h) {
        List<Integer> bstIndexes = buildBSTByHeightAndListIndexes(h);
        List<List<Integer>> matchedConfigs = new ArrayList<>();

        for (List<Integer> config : randomConfigs) {
            int matchCounter = 1;
            List<Integer> matchedConfig = new ArrayList<>(Collections.nCopies(config.size(), 0));
            for (int i = 0; i < bstIndexes.size(); i++) {
                for (int j = 0; j < matchedConfig.size(); j++) {
                    if (Objects.equals(config.get(j), bstIndexes.get(i))) {
                        matchedConfig.set(j, matchCounter);
                        matchCounter += 1;
                    }
                }
            }
            matchedConfigs.add(matchedConfig);
        }
        return matchedConfigs;
    }

    // Assigns random values to elements of each list based on their mapped positions
    private static void assignRandomValuesToElements(List<Integer> elements, List<Integer> positions) {
        Random rand = new Random();
        int n = elements.size();
        Integer[] randomValues = new Integer[n];
        for (int i = 0; i < n; i++) {
            randomValues[i] = rand.nextInt(100) + 1;  // Generate random numbers between 1 and 100
        }
        Arrays.sort(randomValues); // Sort random values

        Integer[] mappedValues = new Integer[n];
        for (int i = 0; i < n; i++) {
            mappedValues[i] = randomValues[positions.get(i) - 1]; // Map sorted values to positions
        }

        for (int i = 0; i < n; i++) {
            elements.set(i, mappedValues[i]); // Set the mapped values
        }
    }

    // Maps arrays from listOfLists to corresponding positions for each list
    public static void mapArrays(List<List<Integer>> listOfLists, List<List<Integer>> positions) {
        for (int i = 0; i < listOfLists.size(); i++) {
            assignRandomValuesToElements(listOfLists.get(i), positions.get(i)); // Assign random values to elements based on their positions
        }
    }



    // Executes the main process to generate configurations, map them to positions, and assign random values
    public static List<List<Integer>> execute(int nodes, int difficulty) {
        List<List<Integer>> solutions = generateRandomBSTConfigurations(nodes, difficulty, 10);
        int maxValue = solutions.stream()
                .flatMap(List::stream)
                .max(Integer::compare)
                .orElse(Integer.MIN_VALUE); // Find maximum value to calculate tree height
        int height = (int) (Math.log(maxValue) / Math.log(2));
        List<List<Integer>> positions = matchElementsToBSTPositions(solutions, height);
        mapArrays(solutions, positions);
        return solutions;
    }
}
