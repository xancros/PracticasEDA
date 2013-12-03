package practica5;

import practica3.BTNode;





/** Nested class for the nodes of a red-black tree */
public class RBNode<E> extends BTNode<E> {
	protected boolean isRed; // we add a color field to a BTNode

	RBNode() {/* default constructor */
	}

	/** Preferred constructor */
	RBNode(E element, BTNode<E> parent,
			BTNode<E> left, BTNode<E> right) {
		super(element, parent, left, right);
		isRed = false;
	}

	public boolean isRed() {
		return isRed;
	}

	public void makeRed() {
		isRed = true;
	}

	public void makeBlack() {
		isRed = false;
	}

	public void setColor(boolean color) {
		isRed = color;
	}
}
