package implementations;

import java.io.Serializable;
import java.util.ArrayList;

import utilities.BSTreeADT;

/**
 * BSTree Implementation
 *
 * NOTE FOR HANS & XANDER:
 * I only implemented the inorder, preorder and postorder iterators and I left placeholders for everything else
 * - Yvana
 *
 * Xander
 * - getHeight()
 * - removeMin()
 * - removeMax()
 *
 * @param <E> Comparable element type
 */
public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>, Serializable {

    private static final long serialVersionUID = 1L;

    private BSTreeNode<E> root;
    private int size;

    /**
     * default constructor
     */
    public BSTree() {
        root = null;
        size = 0;
    }

    /**
     * Constructor with initial element
     * @param element the initial element to add
     */
    public BSTree(E element) {
        if (element == null) {
            throw new NullPointerException("Cannot create tree with null element");
        }
        root = new BSTreeNode<>(element);
        size = 1;
    }

    /**
     * Hans - Add: add a new element into the BST.
     */
    @Override
    public boolean add(E newEntry) throws NullPointerException {
        if (newEntry == null) {
            throw new NullPointerException("Cannot add null entry");
        }

        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            size++;
            return true;
        }
        return addRecursive(root, newEntry);
    }

    // recursive method to add (helper function)
    private boolean addRecursive(BSTreeNode<E> current, E newEntry) {
        int comparison = newEntry.compareTo(current.getElement());

        if (comparison < 0) {
            if (current.getLeft() == null) {
                BSTreeNode<E> newNode = new BSTreeNode<>(newEntry);
                newNode.setParent(current);
                current.setLeft(newNode);
                size++;
                return true;
            }
            return addRecursive(current.getLeft(), newEntry);

        } else if (comparison > 0) {

            if (current.getRight() == null) {
                BSTreeNode<E> newNode = new BSTreeNode<>(newEntry);
                newNode.setParent(current);
                current.setRight(newNode);
                size++;
                return true;
            }

            return addRecursive(current.getRight(), newEntry);

        } else {
            return false;
        }
    }

    /**
     * Hans - Size: returns the number of elements.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Hans - isEmpty: returns true if tree is empty.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * Hans - Search: search for element.
     */
    @Override
    public BSTreeNode<E> search (E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("Cannot search for null entry");
        }
        return searchRecursive(root, entry);
    }

    // recursive search helper function
    private BSTreeNode<E> searchRecursive(BSTreeNode<E> current, E entry) {
        if (current == null) {
            return null;
        }

        int comparison = entry.compareTo(current.getElement());

        if (comparison == 0) {
            return current;
        } else if (comparison < 0) {
            return searchRecursive(current.getLeft(), entry);
        } else {
            return searchRecursive(current.getRight(), entry);
        }
    }

    /**
     * Hans - Contains: check if element exists.
     */
    @Override
    public boolean contains(E entry) throws NullPointerException {
        if (entry == null) {
            throw new NullPointerException("Cannot search for null entry");
        }
        return search(entry) != null;
    }

    /**
     * Xander: return height of tree.
     */
    @Override
    public int getHeight() {
        return getHeightHelper(root);
    }

    /**
     * Recursive helper method to calculate height
     * @param node current node
     * @return height of subtree rooted at node
     */
    private int getHeightHelper(BSTreeNode<E> node) {
        // Empty tree has height 0
        if (node == null) {
            return 0;
        }

        // Recursively get height of left and right subtrees
        int leftHeight = getHeightHelper(node.getLeft());
        int rightHeight = getHeightHelper(node.getRight());

        // Height is 1 + max of left and right subtree heights
        return 1 + Math.max(leftHeight, rightHeight);
    }

    /**
     * Xander: remove smallest node.
     */
    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) {
            return null;
        }

        // Find the minimum node (leftmost node)
        BSTreeNode<E> minNode = root;
        while (minNode.getLeft() != null) {
            minNode = minNode.getLeft();
        }

        // Create a copy of the node to return
        BSTreeNode<E> result = new BSTreeNode<>(minNode.getElement());

        // Remove the minimum node
        removeNode(minNode);
        size--;

        return result;
    }

    /**
     * Xander: remove largest node.
     */
    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) {
            return null;
        }

        // Find the maximum node (rightmost node)
        BSTreeNode<E> maxNode = root;
        while (maxNode.getRight() != null) {
            maxNode = maxNode.getRight();
        }

        // Create a copy of the node to return
        BSTreeNode<E> result = new BSTreeNode<>(maxNode.getElement());

        // Remove the maximum node
        removeNode(maxNode);
        size--;

        return result;
    }

    /**
     * Helper method to remove a node from the tree
     * Handles three cases:
     * 1. Node is a leaf (no children)
     * 2. Node has one child
     * 3. Node has two children (not typically used in removeMin/removeMax)
     *
     * @param node the node to remove
     */
    private void removeNode(BSTreeNode<E> node) {
        if (node == null) {
            return;
        }

        BSTreeNode<E> parent = node.getParent();

        // Case 1: Node is a leaf (no children)
        if (node.isLeaf()) {
            if (parent == null) {
                // Removing the root
                root = null;
            } else if (parent.getLeft() == node) {
                parent.setLeft(null);
            } else {
                parent.setRight(null);
            }
        }
        // Case 2: Node has only a right child
        else if (node.getLeft() == null) {
            BSTreeNode<E> rightChild = node.getRight();

            if (parent == null) {
                // Node is root
                root = rightChild;
                rightChild.setParent(null);
            } else if (parent.getLeft() == node) {
                parent.setLeft(rightChild);
                rightChild.setParent(parent);
            } else {
                parent.setRight(rightChild);
                rightChild.setParent(parent);
            }
        }
        // Case 3: Node has only a left child
        else if (node.getRight() == null) {
            BSTreeNode<E> leftChild = node.getLeft();

            if (parent == null) {
                // Node is root
                root = leftChild;
                leftChild.setParent(null);
            } else if (parent.getLeft() == node) {
                parent.setLeft(leftChild);
                leftChild.setParent(parent);
            } else {
                parent.setRight(leftChild);
                leftChild.setParent(parent);
            }
        }
        // Case 4: Node has two children
        // This is rare for removeMin/removeMax but included for completeness
        else {
            // Find inorder successor (smallest node in right subtree)
            BSTreeNode<E> successor = node.getRight();
            while (successor.getLeft() != null) {
                successor = successor.getLeft();
            }

            // Copy successor's data to this node
            node.setElement(successor.getElement());

            // Remove the successor (which has at most one child)
            removeNode(successor);
            size++; // Compensate since we'll decrement in the calling method
        }
    }

    /**
     * Hans - Clear: clears the tree.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    // Hans - gets the root of the tree
    @Override
    public BSTreeNode<E> getRoot() throws NullPointerException
    {
        if (root == null) {
            throw new NullPointerException("Tree is empty - no root node");
        }
        return root;
    }

    /**
     * inorder iterator (left to root to right)
     * produces alphabetical order 
     */
    @Override
    public utilities.Iterator<E> inorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        inorderHelper(root, list);
        return new BSTreeIterator(list);
    }

    private void inorderHelper(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null)
            return;

        inorderHelper(node.getLeft(), list);
        list.add(node.getElement());
        inorderHelper(node.getRight(), list);
    }

    /**
     * preorder iterator (root to left to right)
     */
    @Override
    public utilities.Iterator<E> preorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        preorderHelper(root, list);
        return new BSTreeIterator(list);
    }

    private void preorderHelper(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null)
            return;

        list.add(node.getElement());
        preorderHelper(node.getLeft(), list);
        preorderHelper(node.getRight(), list);
    }

    /**
     * postorder iterator (left to right to root)
     */
    @Override
    public utilities.Iterator<E> postorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        postorderHelper(root, list);
        return new BSTreeIterator(list);
    }

    private void postorderHelper(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null)
            return;

        postorderHelper(node.getLeft(), list);
        postorderHelper(node.getRight(), list);
        list.add(node.getElement());
    }

    /**
     * Inner class for BSTree Iterator
     */
    private class BSTreeIterator implements utilities.Iterator<E> {
        private ArrayList<E> list;
        private int current;

        public BSTreeIterator(ArrayList<E> list) {
            this.list = list;
            this.current = 0;
        }

        @Override
        public boolean hasNext() {
            return current < list.size();
        }

        @Override
        public E next() throws java.util.NoSuchElementException {
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }
            return list.get(current++);
        }
    }
}