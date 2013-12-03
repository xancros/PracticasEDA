package practica5;

import practica2.Position;
import practica3.BTNode;
import practica3.FactoryNode;
import practica3.LinkedBinaryTree;



public class Restructurator<E> {

	private FactoryNode<E> factory;
	
	public Restructurator(FactoryNode<E> factory) {
		this.factory = factory;
	}
	/**
	 * Performs a tri-node restructuring. Assumes the nodes are in one of
	 * following configurations:
	 * 
	 * <pre>
	 *          z=c       z=c        z=a         z=a
	 *         /  \      /  \       /  \        /  \
	 *       y=b  t4   y=a  t4    t1  y=c     t1  y=b
	 *      /  \      /  \           /  \         /  \
	 *    x=a  t3    t1 x=b        x=b  t4       t2 x=c
	 *   /  \          /  \       /  \             /  \
	 *  t1  t2        t2  t3     t2  t3           t3  t4
	 * </pre>
	 * 
	 * @return the new root of the restructured subtree
	 */
	public Position<E> restructure(Position<E> posNode, BinarySearchTree<E> bst) {
		BTNode<E> lowKey, midKey, highKey, t1, t2, t3, t4;
		Position<E> posParent = bst.binTree.parent(posNode); // assumes x has a parent
		Position<E> posGrandParent = bst.binTree.parent(posParent); // assumes y has a parent
		boolean nodeLeft = (posNode == bst.binTree.left(posParent));
		boolean parentLeft = (posParent == bst.binTree.left(posGrandParent));
		BTNode<E> node = (BTNode<E>) posNode, parent = (BTNode<E>) posParent, grandParent = (BTNode<E>) posGrandParent;
		if (nodeLeft && parentLeft) {// Desequilibrio izda-izda
			lowKey = node;
			midKey = parent;
			highKey = grandParent;
			t1 = lowKey.getLeft();
			t2 = lowKey.getRight();
			t3 = midKey.getRight();
			t4 = highKey.getRight();
		} else if (!nodeLeft && parentLeft) {// Desequilibrio izda-dcha
			lowKey = parent;
			midKey = node;
			highKey = grandParent;
			t1 = lowKey.getLeft();
			t2 = midKey.getLeft();
			t3 = midKey.getRight();
			t4 = highKey.getRight();
		} else if (nodeLeft && !parentLeft) {// Desequilibrio dcha-izda
			lowKey = grandParent;
			midKey = node;
			highKey = parent;
			t1 = lowKey.getLeft();
			t2 = midKey.getLeft();
			t3 = midKey.getRight();
			t4 = highKey.getRight();
		} else { // Desequilibrio dcha-dcha
			lowKey = grandParent;
			midKey = parent;
			highKey = node;
			t1 = lowKey.getLeft();
			t2 = midKey.getLeft();
			t3 = highKey.getLeft();
			t4 = highKey.getRight();
		}
		
		// put b at z's place
		if (bst.binTree.isRoot(posGrandParent)) {
			bst.binTree = new LinkedBinaryTree<E>(factory);
			midKey = (BTNode<E>)bst.binTree.addRoot(midKey.element()); 
						
		} else {
			BTNode<E> zParent = (BTNode<E>) bst.binTree.parent(posGrandParent);
			if (posGrandParent == bst.binTree.left(zParent)) {
				midKey.setParent(zParent);
				zParent.setLeft(midKey);
			} else { // z was a right child
				midKey.setParent(zParent);
				zParent.setRight(midKey);
			}
		}
		// place the rest of the nodes and their children
		midKey.setLeft(lowKey);
		lowKey.setParent(midKey);
		midKey.setRight(highKey);
		highKey.setParent(midKey);
		lowKey.setLeft(t1);
		t1.setParent(lowKey);
		lowKey.setRight(t2);
		t2.setParent(lowKey);
		highKey.setLeft(t3);
		t3.setParent(highKey);
		highKey.setRight(t4);
		t4.setParent(highKey);
		
		return midKey; // the new root of this subtree
	}
}
