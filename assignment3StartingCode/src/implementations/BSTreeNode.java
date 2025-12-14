package implementations;

import java.io.Serializable;

/**
 * BSTreeNode 
 * 
 * @param <E> Comparable element type
 * @author Xander
 */
public class BSTreeNode<E> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private E element;
    private BSTreeNode<E> left;
    private BSTreeNode<E> right;
    private BSTreeNode<E> parent;
    
    /**
     * Constructor with element only
     * @param element the data to store in this node
     */
    public BSTreeNode(E element) {
        this.element = element;
        this.left = null;
        this.right = null;
        this.parent = null;
    }
    
    /**
     * Constructor with element and parent
     * @param element the data to store in this node
     * @param parent the parent node
     */
    public BSTreeNode(E element, BSTreeNode<E> parent) {
        this.element = element;
        this.left = null;
        this.right = null;
        this.parent = parent;
    }
    
    /**
     * Get the element stored in this node
     * @return the element
     */
    public E getElement() {
        return element;
    }
    
    /**
     * Set the element for this node
     * @param element the new element
     */
    public void setElement(E element) {
        this.element = element;
    }
    
    /**
     * Get the left child node
     * @return left child node
     */
    public BSTreeNode<E> getLeft() {
        return left;
    }
    
    /**
     * Set the left child node
     * @param left the new left child
     */
    public void setLeft(BSTreeNode<E> left) {
        this.left = left;
    }
    
    /**
     * Get the right child node
     * @return right child node
     */
    public BSTreeNode<E> getRight() {
        return right;
    }
    
    /**
     * Set the right child node
     * @param right the new right child
     */
    public void setRight(BSTreeNode<E> right) {
        this.right = right;
    }
    
    /**
     * Get the parent node
     * @return parent node
     */
    public BSTreeNode<E> getParent() {
        return parent;
    }
    
    /**
     * Set the parent node
     * @param parent the new parent
     */
    public void setParent(BSTreeNode<E> parent) {
        this.parent = parent;
    }
    
    /**
     * Check if this node is a leaf (has no children)
     * @return true if leaf node, false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }
    
    /**
     * Check if this node has a left child
     * @return true if left child exists
     */
    public boolean hasLeft() {
        return left != null;
    }
    
    /**
     * Check if this node has a right child
     * @return true if right child exists
     */
    public boolean hasRight() {
        return right != null;
    }
}