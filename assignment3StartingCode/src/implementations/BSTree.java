package implementations;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import utilities.BSTreeADT;

/**
 * BSTree Implementation
 *
 * NOTE FOR HANS & XANDER:
 * I only implemented the inorder, preorder and postorder iterators and I left placeholders for everything else
 * - Yvana
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
     * Placeholder for Hans: add a new element into the BST.
     */
    @Override
    public void add(E toAdd) {
        // TODO: Hans implements BST insertion
    }

    /**
     * Placeholder for Hans: returns the number of elements.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Placeholder for Hans: returns true if tree is empty.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Placeholder for Hans/Xander: search for element.
     */
    @Override
    public E search(E toSearch) {
        // TODO: Hans implements search
        return null;
    }

    /**
     * Placeholder for Hans: check if element exists.
     */
    @Override
    public boolean contains(E toFind) {
        // TODO: Hans implements contains
        return false;
    }

    /**
     * Placeholder for Xander: return height of tree.
     */
    @Override
    public int getHeight() {
        // TODO: Xander implements height calculation
        return 0;
    }

    /**
     * Placeholder for Xander: remove smallest node.
     */
    @Override
    public E removeMin() {
        // TODO: Xander implements removeMin
        return null;
    }

    /**
     * Placeholder for Xander: remove largest node.
     */
    @Override
    public E removeMax() {
        // TODO: Xander implements removeMax
        return null;
    }

    /**
     * Placeholder for Hans: clears the tree.
     */
    @Override
    public void clear() {
        // TODO: Hans implements clear()
        root = null;
        size = 0;
    }

    /**
     * inorder iterator (left → root → right)
     * produces alphabetical order — used by WordTracker.
     */
    @Override
    public Iterator<E> inorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        inorderHelper(root, list);
        return list.iterator();
    }

    private void inorderHelper(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null)
            return;

        inorderHelper(node.getLeft(), list);
        list.add(node.getElement());
        inorderHelper(node.getRight(), list);
    }

    /**
     * preorder iterator (root → left → right)
     */
    @Override
    public Iterator<E> preorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        preorderHelper(root, list);
        return list.iterator();
    }

    private void preorderHelper(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null)
            return;

        list.add(node.getElement());
        preorderHelper(node.getLeft(), list);
        preorderHelper(node.getRight(), list);
    }

    /**
     * postorder iterator (left → right → root)
     */
    @Override
    public Iterator<E> postorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        postorderHelper(root, list);
        return list.iterator();
    }

    private void postorderHelper(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null)
            return;

        postorderHelper(node.getLeft(), list);
        postorderHelper(node.getRight(), list);
        list.add(node.getElement());
    }
}
