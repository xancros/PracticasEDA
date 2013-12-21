package practica5;

import java.util.Comparator;

import practica2.InvalidPositionException;
import practica2.Position;


 
/**  Implementation of an AVL tree. */
/**
 * AVLTree class - implements an AVL Tree by extending a binary search tree.
 * 
 * @author Michael Goodrich, Roberto Tamassia, Eric Zamore
 */

public class AVLTree<E> extends BinarySearchTree<E> {

	public AVLTree() {
		super(new FactoryAVLNode<E>());
	}
	
	public AVLTree(Comparator<E> c) {
		super(c, new FactoryAVLNode<E>());
	}

	/** Returns the height of a node (call back to an AVLNode). */
	protected int height(Position<E> p) {
		return ((AVLNode<E>) p).getHeight();
	}

	/** Sets the height of an internal node (call back to an AVLNode). */
	protected void setHeight(Position<E> p) {
		AVLNode<E> node = (AVLNode<E>) p;
		node.setHeight(1 + Math.max(height(this.binTree.left(p)),
				height(this.binTree.right(p))));
	}

	/** Returns whether a node has balance factor between -1 and 1. */
	protected boolean isBalanced(Position<E> p) {
		int bf = height(this.binTree.left(p)) - height(this.binTree.right(p));
		return ((-1 <= bf) && (bf <= 1));
	}

	/**
	 * Return a child of p with height no smaller than that of the other child.
	 */
	protected Position<E> tallerChild(Position<E> p) {
		if (height(this.binTree.left(p)) > height(this.binTree.right(p)))
			return this.binTree.left(p);
		else if (height(this.binTree.left(p)) < height(this.binTree.right(p)))
			return this.binTree.right(p);
		// equal height children - break tie using parent's type
		if (this.binTree.isRoot(p))
			return this.binTree.left(p);
		if (p == this.binTree.left(this.binTree.parent(p)))
			return this.binTree.left(p);
		else
			return this.binTree.right(p);
	}

	/**
	 * Rebalance method called by insert and remove. Traverses the path from
	 * zPos to the root. For each node encountered, we recompute its height and
	 * perform a trinode restructuring if it's unbalanced.
	 */
	protected void rebalance(Position<E> zPos) {
		if (this.binTree.isInternal(zPos))
			setHeight(zPos);
		
		Restructurator<E> restructurator = new Restructurator<E>(new FactoryAVLNode<E>());
		
		while (!this.binTree.isRoot(zPos)) { // traverse up the tree towards the
												// root
			zPos = this.binTree.parent(zPos);
			setHeight(zPos);
			if (!isBalanced(zPos)) {
				// perform a trinode restructuring at zPos's tallest grandchild
				Position<E> xPos = tallerChild(tallerChild(zPos));
				zPos = restructurator.restructure(xPos, this); 
				setHeight(this.binTree.left(zPos)); // recompute heights
				setHeight(this.binTree.right(zPos));
				setHeight(zPos);
			}
		}
	}

	// overridden methods of the dictionary ADT
	// end#fragment AVLTree2
	/**
	 * Inserts an item into the dictionary and returns the newly created entry.
	 */
	public Position<E> insert(E e) {
		Position<E> toReturn = super.insert(e); // calls our createNode method

		rebalance(toReturn); // rebalance up from the insertion position
		return toReturn;
	}

	/** Removes and returns an entry from the dictionary. */
	public E remove(Position<E> pos) throws InvalidPositionException {
		this.checkPosition(pos); // may throw an InvalidPositionException
		E toReturn = pos.element(); // entry to be returned
		Position<E> remPos  = getLeafToRemove(pos);
		Position<E> actionPos = this.binTree.sibling(remPos);
		removeLeaf(remPos);
		rebalance(actionPos); // rebalance up the tree
		return toReturn;
	}
	
	/** If v is a good tree node, cast to TreePosition, else throw exception */
	protected AVLNode<E> checkPosition(Position<E> p)
			throws InvalidPositionException {
		if (p == null || !(p instanceof AVLNode))
			throw new InvalidPositionException("The position of the AVL node is not valid");
		return (AVLNode<E>) p;
	}

} // end of AVLTree class

