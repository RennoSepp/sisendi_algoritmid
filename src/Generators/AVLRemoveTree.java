package Generators;

import java.util.*;

public class AVLRemoveTree {
    // Node class for AVL tree
    private class AVLNode {
        int key, height;
        AVLNode left, right;

        // Constructor to initialize an AVL node
        AVLNode(int d) {
            key = d;
            height = 1;  // All new nodes are initially added as leaves
        }
    }

    private AVLNode root;  // Root node of the AVL tree

    // Create a random AVL tree with 'n' unique elements
    public void createRandomAVLTree(int n) {
        Random rand = new Random();
        Set<Integer> existingNumbers = new HashSet<>();
        while (existingNumbers.size() < n) {
            int randomValue = rand.nextInt(100);
            if (existingNumbers.add(randomValue)) {
                root = insert(root, randomValue);
            }
        }
    }

    // Print all leaf nodes of the AVL tree
    public void printLeafNodes() {
        printLeafNodes(root);
    }

    // Recursive method to print leaf nodes
    private void printLeafNodes(AVLNode node) {
        if (node != null) {
            if (node.left == null && node.right == null) {
                System.out.print(node.key + " ");
            } else {
                printLeafNodes(node.left);
                printLeafNodes(node.right);
            }
        }
    }

    // Public method to remove a node by key
    public void remove(int key) {
        root = remove(root, key);
    }

    // Recursive method to remove a node by key and rebalance the tree
    private AVLNode remove(AVLNode node, int key) {
        if (node == null)
            return node;

        // Perform standard BST deletion
        if (key < node.key)
            node.left = remove(node.left, key);
        else if (key > node.key)
            node.right = remove(node.right, key);
        else {
            // Node with only one child or no child
            if (node.left == null || node.right == null)
                node = (node.left != null) ? node.left : node.right;
            else {
                // Node with two children: Get inorder successor
                AVLNode temp = minValueNode(node.right);
                node.key = temp.key;
                node.right = remove(node.right, temp.key);
            }
        }

        if (node == null)
            return node;

        // Update height of the current node
        node.height = max(height(node.left), height(node.right)) + 1;

        // Rebalance the node if necessary
        return rebalance(node);
    }

    // Utility function to calculate height of a node
    private int height(AVLNode N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Utility function to get the maximum of two integers
    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    // Right rotation to rebalance the node 'y'
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Collect all elements of the tree with their heights
    public List<String> getAllElementsWithHeights() {
        List<String> elementsWithHeights = new ArrayList<>();
        collectElementsWithHeights(root, elementsWithHeights);
        return elementsWithHeights;
    }

    // Recursive helper method to collect keys and their heights
    private void collectElementsWithHeights(AVLNode node, List<String> elementsWithHeights) {
        if (node != null) {
            String elementDescription = "Key: " + node.key + ", Height: " + node.height;
            elementsWithHeights.add(elementDescription);
            collectElementsWithHeights(node.left, elementsWithHeights);
            collectElementsWithHeights(node.right, elementsWithHeights);
        }
    }

    // Left rotation to rebalance the node 'x'
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }

    // Get the balance factor of node N to check if it is balanced
    private int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Find the node with minimum value (used during deletion)
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null)
            current = current.left;
        return current;
    }

    // Insert a node in the AVL tree and rebalance it
    public void insert(int key) {
        root = insert(root, key);
    }

    // Recursive method to insert a new key in the AVL tree
    private AVLNode insert(AVLNode node, int key) {
        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else
            return node;

        node.height = max(height(node.left), height(node.right)) + 1;
        return rebalance(node);
    }

    // Rebalance a node according to AVL tree rules
    private AVLNode rebalance(AVLNode node) {
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0)
            return rightRotate(node);
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0)
            return leftRotate(node);
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    // Simulates the removal of each node in the AVL tree and counts the rotations needed to maintain balance
    public List<List<Integer>> countRotationsOnElementRemoval() {
        List<List<Integer>> results = new ArrayList<>();
        collectAndSimulateElementRemoval(root, results);
        return results;
    }

    // Helper method to traverse the tree, simulate the removal of each node, and count the rotations
    private void collectAndSimulateElementRemoval(AVLNode node, List<List<Integer>> results) {
        if (node == null) return;
        int rotations = simulateRemovalAndCountRotations(node.key);
        List<Integer> elementRotations = new ArrayList<>();
        elementRotations.add(node.key);
        elementRotations.add(rotations);
        results.add(elementRotations);
        collectAndSimulateElementRemoval(node.left, results);
        collectAndSimulateElementRemoval(node.right, results);
    }

    // Simulate the removal of a node and count the rotations required to rebalance the tree
    private int simulateRemovalAndCountRotations(int key) {
        AVLRemoveTree tempTree = cloneTree(); // Clone the tree to prevent changes to the original during simulation
        RotationCounter counter = new RotationCounter(); // Counter to keep track of rotations
        tempTree.removeAndCountRotations(tempTree.root, key, counter);
        return counter.count;
    }

    // Recursive method to remove a node by key in the cloned tree and count rotations during rebalancing
    private AVLNode removeAndCountRotations(AVLNode node, int key, RotationCounter counter) {
        if (node == null) return null;

        if (key < node.key) {
            node.left = removeAndCountRotations(node.left, key, counter);
        } else if (key > node.key) {
            node.right = removeAndCountRotations(node.right, key, counter);
        } else {
            if (node.left == null || node.right == null) {
                node = node.left != null ? node.left : node.right;
            } else {
                AVLNode temp = minValueNode(node.right);
                node.key = temp.key;
                node.right = removeAndCountRotations(node.right, temp.key, counter);
            }
        }

        if (node == null) return null;

        node.height = max(height(node.left), height(node.right)) + 1;
        return rebalance(node, counter);
    }

    // Rebalance method that also counts the rotations
    private AVLNode rebalance(AVLNode node, RotationCounter counter) {
        int balance = getBalance(node);

        if (balance > 1 && getBalance(node.left) >= 0) {
            counter.increment();
            return rightRotate(node);
        }
        if (balance > 1 && getBalance(node.left) < 0) {
            node.left = leftRotate(node.left);
            counter.increment();
            return rightRotate(node);
        }
        if (balance < -1 && getBalance(node.right) <= 0) {
            counter.increment();
            return leftRotate(node);
        }
        if (balance < -1 && getBalance(node.right) > 0) {
            node.right = rightRotate(node.right);
            counter.increment();
            return leftRotate(node);
        }
        return node;
    }

    // A simple class used to count the number of rotations during AVL rebalancing
    class RotationCounter {
        private int count = 0;

        void increment() { count++; }
        int getCount() { return count; }
    }

    // Clone the current AVL tree
    public AVLRemoveTree cloneTree() {
        AVLRemoveTree newTree = new AVLRemoveTree();
        newTree.root = cloneSubTree(this.root);
        return newTree;
    }

    // Helper method to recursively clone each node of the AVL tree
    private AVLNode cloneSubTree(AVLNode node) {
        if (node == null) return null;
        AVLNode newNode = new AVLNode(node.key);
        newNode.height = node.height;
        newNode.left = cloneSubTree(node.left);
        newNode.right = cloneSubTree(node.right);
        return newNode;
    }

    // Convert the AVL tree to an array using breadth-first traversal
    public List<Integer> toArray() {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<AVLNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            AVLNode current = queue.poll();
            result.add(current.key);
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        return result;
    }

    // Find all combinations of integers that sum to x using 'n' elements
    public static List<List<Integer>> findCombinations(int n, int x) {
        List<List<Integer>> result = new ArrayList<>();
        findCombinationsHelper(result, new ArrayList<>(), n, x, 0);
        return result;
    }

    // Helper method to recursively find combinations
    private static void findCombinationsHelper(List<List<Integer>> result, List<Integer> current, int n, int remain, int start) {
        if (remain == 0 && current.size() == n) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (current.size() == n || remain < 0) {
            return;
        }

        for (int i = start; i <= remain; i++) {
            current.add(i);
            findCombinationsHelper(result, current, n, remain - i, i);
            current.remove(current.size() - 1);
        }
    }

    // Find AVL trees that meet specific removal requirements
    public static List<List<List<Integer>>> findAVLTreeWithRemovals(int nodes, int removables, int difficulty, int amount) {
        List<List<List<Integer>>> answer = new ArrayList<>();
        List<List<Integer>> combinations = findCombinations(removables, difficulty);
        while (answer.size() < amount) {
            AVLRemoveTree tree = new AVLRemoveTree();
            Random random = new Random();
            tree.createRandomAVLTree(nodes);
            AVLRemoveTree answerCopy = tree.cloneTree();
            List<Integer> combination = new ArrayList<>(combinations.get(random.nextInt(combinations.size())));
            List<Integer> removedElements = new ArrayList<>();

            while (!combination.isEmpty()) {
                int removableRotations = combination.remove(random.nextInt(combination.size()));
                List<List<Integer>> possibleRotations = tree.countRotationsOnElementRemoval();
                for (int k = 0; k < possibleRotations.size(); k++) {
                    int randomElement = random.nextInt(possibleRotations.size());
                    if (possibleRotations.get(randomElement).get(1) == removableRotations) {
                        removedElements.add(possibleRotations.get(randomElement).get(0));
                        tree.remove(possibleRotations.get(randomElement).get(0));
                        if (removedElements.size() == removables) {
                            List<List<Integer>> instance = new ArrayList<>();
                            instance.add(answerCopy.toArray());
                            instance.add(removedElements);
                            answer.add(instance);
                        }
                        break;
                    } else {
                        possibleRotations.remove(randomElement);
                    }
                }
                if(removedElements.size() == removables || combination.size() < (removables - removedElements.size())){
                    break;
                }
            }
        }
        return answer;
    }

    // Execute the process to generate AVL trees that meet specific removal conditions
    public static Collection<List<List<Integer>>> execute(int nodes, int removables, int difficulty) {
        return findAVLTreeWithRemovals(nodes, removables, difficulty, 10);
    }

}
