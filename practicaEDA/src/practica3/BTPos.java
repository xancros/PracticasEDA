package practica3;

import practica2.Position;



public class BTPos<E> implements Position<E> {
	E element; // element stored at this position
	int index; // index of this position in the array list
	int left, right, parent;

	public BTPos(E elt, int i) {
		element = elt;
		index = i;
		left = -1;
		right = -1;
		parent = -1;
	}
        
        

	/** Returns the element stored at this position */
	public E element() {
		return element;
	}

	/** Sets the element stored at this position */
	public E setElement(E elt) {
		E temp = element;
		element = elt;
		return temp;
	}

	/** Returns the left child of this position */
	public int getLeft() {
		return left;
	}

	/** Sets the left child of this position */
	public void setLeft(int v) {
		left = v;
	}

	/** Returns the right child of this position */
	public int getRight() {
		return right;
	}

	/** Sets the right child of this position */
	public void setRight(int v) {
		right = v;
	}

	/** Returns the parent of this position */
	public int getParent() {
		return parent;
	}

	/** Sets the parent of this position */
	public void setParent(int v) {
		parent = v;
	}

	public int index() {
		return index;
	}

	public String toString() {
		return ("[" + element + "," + index + "]");
	}

	public void setIndex(int i) {
		index = i;
	}
}
