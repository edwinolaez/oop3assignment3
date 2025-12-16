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
				current.setLeft(new BSTreeNode<>(newEntry));
				size++;
				return true;
			}
			return addRecursive(current.getLeft(), newEntry);
			
		} else if (comparison > 0) {
			
			if (current.getRight() == null) {
				current.setRight(new BSTreeNode<>(newEntry));
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
