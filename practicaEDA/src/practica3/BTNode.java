package practica3;

import practica2.Position;

public class BTNode<E> implements Position<E> {
	private E element; // element stored at this node
	private BTNode<E> left, right, parent; // adjacent nodes
			
	/** Default constructor */
	public BTNode() {
	}

	/** Main constructor */
	public BTNode(E element, BTNode<E> parent, BTNode<E> left, BTNode<E> right) {
		setElement(element);
		setParent(parent);
		setLeft(left);
		setRight(right);
	}

	/** Returns the element stored at this position */
	public E element() {
		return element;
	}

	/** Sets the element stored at this position */
	public void setElement(E o) {
		element = o;
	}

	/** Returns the left child of this position */
	public BTNode<E> getLeft() {
		return left;
	}

	/** Sets the left child of this position */
	public void setLeft(BTNode<E> v) {
		left = v;
	}

	/** Returns the right child of this position */
	public BTNode<E> getRight() {
		return right;
	}

	/** Sets the right child of this position */
	public void setRight(BTNode<E> v) {
		right = v;
	}

	/** Returns the parent of this position */
	public BTNode<E> getParent() {
		return parent;
	}

	/** Sets the parent of this position */
	public void setParent(BTNode<E> v) {
		parent = v;
	}
}
