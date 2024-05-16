package Generators;

import java.util.*;

public class AVLTree {
    // Inner class representing a node in the AVL tree
    class AVLNode {
        int key, height;
        AVLNode left, right;

        // Node constructor
        AVLNode(int d) {
            key = d;
            height = 1;
        }
    }

    private AVLNode root; // Root of the AVL Tree

    // Helper function to get the height of a node
    private static int height(AVLNode N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // Function to right rotate the subtree rooted with y
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x; // New root after rotation
    }

    // Function to left rotate the subtree rooted with x
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y; // New root after rotation
    }

    // Get balance factor of node N
    private static int getBalance(AVLNode N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

    // Convert tree to ArrayList using in-order traversal
    public ArrayList<Integer> toArrayList() {
        ArrayList<Integer> result = new ArrayList<>();
        inOrderToList(root, result);
        return result;
    }

    // Utility function for in-order traversal of the tree
    private void inOrderToList(AVLNode node, ArrayList<Integer> result) {
        if (node != null) {
            inOrderToList(node.left, result);
            result.add(node.key);
            inOrderToList(node.right, result);
        }
    }

    // Function to insert a new key in the AVL tree
    private AVLNode insert(AVLNode node, int key) {
        // Perform the normal BST insertion
        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);
        else if (key > node.key)
            node.right = insert(node.right, key);
        else  // Duplicate keys not allowed
            return node;

        // Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Get the balance factor of this ancestor node to check whether this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && key < node.left.key)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && key > node.right.key)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // Return the (unchanged) node pointer
        return node;
    }

    // Function to find a node with a given key
    public AVLNode find(int key) {
        return findNode(root, key);
    }

    // Utility function to find a node with a given key in the AVL tree
    private AVLNode findNode(AVLNode node, int key) {
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                return node; // key is found
            }
        }
        return null; // key is not found
    }

    // Function to collect nodes with balance factor of 1 or -1
    public List<Integer> getNodesWithBalanceOne() {
        List<Integer> result = new ArrayList<>();
        collectNodesWithBalanceOne(root, result);
        return result;
    }

    // Utility function to collect nodes with balance factor of 1 or -1
    private void collectNodesWithBalanceOne(AVLNode node, List<Integer> result) {
        if (node != null) {
            int balance = getBalance(node);
            if (balance == 1 || balance == -1) {
                result.add(node.key);
            }
            collectNodesWithBalanceOne(node.left, result);
            collectNodesWithBalanceOne(node.right, result);
        }
    }

    // Function to generate an AVL tree with n random keys
    public void createRandomAVLTree(int n) {
        Random rand = new Random();
        Set<Integer> existingNumbers = new HashSet<>(); // A set to track unique values
        while (existingNumbers.size() < n) { // Continue until 'n' unique values are added
            int randomValue = rand.nextInt(100); // Generate a random number between 0 and 99
            if (existingNumbers.add(randomValue)) { // Check if the number is already in the set
                root = insert(root, randomValue); // Insert the number into the AVL tree if it's unique
            }
        }
    }

    // Function to find a random node in the tree that may lead to an imbalance
    public static AVLNode findRandomImbalancePoint(AVLNode searchPoint){
        if (searchPoint == null){
            return null;
        }
        if (searchPoint.left == null && searchPoint.right == null) {
            return searchPoint;
        }
        if (searchPoint.left == null) {
            return findRandomImbalancePoint(searchPoint.right);
        } else if (searchPoint.right == null) {
            return findRandomImbalancePoint(searchPoint.left);
        } else if(getBalance(searchPoint) == -1){
            return findRandomImbalancePoint(searchPoint.right);
        } else if (getBalance(searchPoint) == 1) {
            return findRandomImbalancePoint(searchPoint.left);
        } else {
            Random random = new Random();
            if(random.nextBoolean()){
                return findRandomImbalancePoint(searchPoint.left);
            }else {
                return findRandomImbalancePoint(searchPoint.right);
            }
        }
    }

    // Generate AVL tree configurations for testing with a specific number of nodes and a difficulty level
    public static List<List<Integer>> generateAVLTreeWithAdditions(int nodes, int difficulty) {
        List<List<Integer>> answer;
        while (true) {
            answer = new ArrayList<>();
            List<Integer> additionsList = new ArrayList<>();
            AVLTree tree = new AVLTree();
            tree.createRandomAVLTree(nodes);
            List<Integer> baseTree = tree.toArrayList();
            for (int i = 0; i < difficulty; i++) {
                List<Integer> imbalancedNodes = tree.getNodesWithBalanceOne();
                if (imbalancedNodes.isEmpty()) {
                    break;
                }
                Random random = new Random();
                AVLNode focus = tree.find(imbalancedNodes.get(random.nextInt(imbalancedNodes.size())));
                AVLNode focusLeaf = findRandomImbalancePoint(focus);
                int new_element;
                if (random.nextBoolean()) {
                    new_element = focusLeaf.key + 1;
                } else {
                    new_element = focusLeaf.key - 1;
                }
                additionsList.add(new_element);
                tree.root = tree.insert(tree.root, new_element); // Correctly updating the root after insertion
                if (additionsList.size() == difficulty) {
                    List<List<Integer>> combo = new ArrayList<>();
                    combo.add(additionsList);
                    combo.add(baseTree);
                    return combo;
                }
            }
        }
    }

    // Execute the tree generation and testing for a given number of nodes and difficulty
    public static List<List<List<Integer>>> execute(int nodes, int difficulty) {
        List<List<List<Integer>>> answer = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            answer.add(generateAVLTreeWithAdditions(nodes, difficulty));
        }
        return answer;
    }
}
