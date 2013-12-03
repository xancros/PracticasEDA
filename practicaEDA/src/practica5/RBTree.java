package practica5;

import java.util.Comparator;

import practica2.InvalidPositionException;
import practica2.Position;


/** 
 * Realization of a red-black Tree by extending a binary search tree.
 *
 * @author Michael Goodrich, Roberto Tamassia, Eric Zamore
 */

/** Realization of a dictionary by means of a red-black tree. */
public class RBTree<E> extends BinarySearchTree<E> {

	public RBTree() {
		super(new FactoryRBNode<E>());
	}

	public RBTree(Comparator<E> c) {
		super(c, new FactoryRBNode<E>());
	}

	/**
	 * Inserts an element into the RBTree and returns the newly created postion.
	 */
	public Position<E> insert(E e) {
		Position<E> toReturn = super.insert(e);
		Position<E> posZ = toReturn; // start at the insertion position

		setRed(posZ);
		if (this.binTree.isRoot(posZ))
			setBlack(posZ);
		else
			remedyDoubleRed(posZ); // fix a double-red color violation
		return toReturn;
	}

	/** Remedies a double red violation at a given node caused by insertion. */
	protected void remedyDoubleRed(Position<E> posZ) {
		Position<E> posV = this.binTree.parent(posZ);
		if (this.binTree.isRoot(posV))
			return;
		if (!isPosRed(posV))
			return;
		// we have a double red: posZ and posV
		if (!isPosRed(this.binTree.sibling(posV))) { // Case 1: trinode
														// restructuring
			Restructurator<E> restructurator = new Restructurator<E>(new FactoryRBNode<E>());
			posV = restructurator.restructure(posZ, this);
			setBlack(posV);
			setRed(this.binTree.left(posV));
			setRed(this.binTree.right(posV));
		} else { // Case 2: recoloring
			setBlack(posV);
			setBlack(this.binTree.sibling(posV));
			Position<E> posU = this.binTree.parent(posV);
			if (this.binTree.isRoot(posU))
				return;
			setRed(posU);
			remedyDoubleRed(posU);
		}
	}

	/** Removes and returns an entry from the dictionary. */
	public E remove(Position<E> posV) throws InvalidPositionException {
		this.checkPosition(posV); // may throw an InvalidPositionException
		boolean wasParentRed = isPosRed(posV);
		
		E toReturn = posV.element(); // entry to be returned
		Position<E> posW = getLeafToRemove(posV);
		Position<E> posR = this.binTree.sibling(posW);
		removeLeaf(posW);

		if (wasParentRed || this.binTree.isRoot(posR) || isPosRed(posR))
			setBlack(posR);
		else
			remedyDoubleBlack(posR);

		return toReturn;
	}

	/** Remedies a double black violation at a given node caused by removal. */
	protected void remedyDoubleBlack(Position<E> posR) {
		Position<E> posX, posY, posZ;
		boolean oldColor;
		posX = this.binTree.parent(posR);
		posY = this.binTree.sibling(posR);
		
		Restructurator<E> restructurator = new Restructurator<E>(new FactoryRBNode<E>());
		
		if (!isPosRed(posY)) {
			posZ = redChild(posY);
			if (hasRedChild(posY)) { // Case 1: trinode restructuring
				oldColor = isPosRed(posX);
				posZ = restructurator.restructure(posZ, this);
				setColor(posZ, oldColor);
				setBlack(posR);
				setBlack(this.binTree.left(posZ));
				setBlack(this.binTree.right(posZ));
				return;
			}
			setBlack(posR);
			setRed(posY);
			if (!isPosRed(posX)) { // Case 2: recoloring
				if (!this.binTree.isRoot(posX))
					remedyDoubleBlack(posX);
				return;
			}
			setBlack(posX);
			return;
		} // Case 3: adjustment
		if (posY == this.binTree.right(posX))
			posZ = this.binTree.right(posY);
		else
			posZ = this.binTree.left(posY);
		restructurator.restructure(posZ, this);
		setBlack(posY);
		setRed(posX);
		remedyDoubleBlack(posR);
	}

	/** Returns whether a node is red. */
	protected boolean isPosRed(Position<E> position) {
		RBNode<E> node = (RBNode<E>) position;
		return node.isRed();
	}

	/** Returns whether the former parent of a node was red. */
	/*private boolean wasParentRed(Position<E> position) {
		if (!this.binTree.isRoot(position)) {
			if (!isPosRed(position) && !isPosRed(this.binTree.parent(position))) {
				if (this.binTree.isLeaf(this.binTree.sibling(position))
						|| (hasTwoExternalChildren(this.binTree
								.sibling(position)) && isPosRed(this.binTree
								.sibling(position))))
					return true; // then position's old parent was red
			}
		}
		return false;
	}*/

	/** Returns whether an internal node has two external children. */
	/*private boolean hasTwoExternalChildren(Position<E> position) {
		if (this.binTree.isLeaf(this.binTree.left(position))
				&& this.binTree.isLeaf(this.binTree.right(position)))
			return true;
		else
			return false;
	}*/

	/** Colors a node red. */
	protected void setRed(Position<E> position) {
		RBNode<E> node = (RBNode<E>) position;
		node.makeRed();
	}

	/** Colors a node black. */
	protected void setBlack(Position<E> position) {
		RBNode<E> node = (RBNode<E>) position;
		node.makeBlack();
	}

	/**
	 * Sets the color of a node.
	 * 
	 * @param color
	 *            <tt>true</tt> to color the node red, <tt>false</tt> to color
	 *            the node black
	 */
	protected void setColor(Position<E> position, boolean color) {
		RBNode<E> node = (RBNode<E>) position;
		node.setColor(color);

	}

	/** Returns a red child of a node. */
	protected Position<E> redChild(Position<E> position) {
		Position<E> child = this.binTree.left(position);
		if (isPosRed(child))
			return child;
		child = this.binTree.right(position);
		if (isPosRed(child))
			return child;
		return null;
	}

	/** Returns whether a node has a red child. */
	protected boolean hasRedChild(Position<E> position) {
		if (isPosRed(this.binTree.left(position))
				|| isPosRed(this.binTree.right(position)))
			return true;
		else
			return false;
	}

	/**
	 * Swaps the colors of <tt>a</tt> and <tt>b</tt> if they are different and
	 * returns whether <tt>a</tt> was red.
	 */
	protected boolean swapColor(Position<E> a, Position<E> b) {
		boolean wasRed = false;
		if (isPosRed(a) && !isPosRed(b)) {
			wasRed = true;
			setBlack(a);
			setRed(b);
		} else if (!isPosRed(a) && isPosRed(b)) {
			setBlack(b);
			setRed(a);
		}
		return wasRed;
	}

	/** Swaps the colors and elements at the two nodes. */
	protected void swap(Position<E> swapPos, Position<E> remPos) {
		swapColor(remPos, swapPos);
		this.binTree.swapElements(swapPos, remPos);
	}

	/** If v is a good tree node, cast to RBPosition, else throw exception */
	protected RBNode<E> checkPosition(Position<E> p)
			throws InvalidPositionException {
		if (p == null || !(p instanceof RBNode))
			throw new InvalidPositionException(
					"The position of the RB node is not valid");
		return (RBNode<E>) p;
	}

}
