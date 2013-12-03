package practica5;

import practica3.BTNode;

public class AVLNode<E> extends BTNode<E> {
	protected int height; // we add a height field to a BTNode

	AVLNode() {/* default constructor */
	}

	/** Preferred constructor */
	AVLNode(E element, BTNode<E> parent, BTNode<E> left, BTNode<E> right) {
		super(element, parent, left, right);
		height = 0;
		if (left != null)
			height = Math.max(height, 1 + ((AVLNode<E>) left).getHeight());
		if (right != null)
			height = Math.max(height, 1 + ((AVLNode<E>) right).getHeight());
	} // we assume that the parent will revise its height if needed

	public void setHeight(int h) {
		height = h;
	}

	public int getHeight() {
		return height;
	}
}
