package practica5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import practica2.BoundaryViolationException;
import practica2.InvalidPositionException;
import practica2.Position;
import practica3.BTNode;
import practica3.FactoryNode;
import practica3.LinkedBinaryTree;


/**
 * Realization of a dictionary by means of a binary search tree.
 * 
 * @author R. Cabido, A. Duarte and J. Vélez
 */

// Implementation of a dictionary by means of a binary search tree
public class BinarySearchTree<E> {

	protected LinkedBinaryTree<E> binTree;
	protected Comparator<E> comparator; // comparator
	protected int size = 0; // number of entries
	
	/** Creates a BinarySearchTree with a default comparator. */
	public BinarySearchTree() {
		this(null,null);
	}

	/** Creates a BinarySearchTree with the given comparator. */
	public BinarySearchTree(Comparator<E> c) {
		this(c,null);
	}
	
	/** Creates a BinarySearchTree with a default comparator. */
	public BinarySearchTree(FactoryNode<E> factoryNode) {
		this(null, factoryNode);
	}

	/** Creates a BinarySearchTree with the given comparator. */
	public BinarySearchTree(Comparator<E> c, FactoryNode<E> factoryNode) {
		if (c == null)
			this.comparator = new DefaultComparator<>();
		else 
			this.comparator = c;
		
		this.binTree = new LinkedBinaryTree<>(factoryNode);
		this.binTree.addRoot(null);
	}

	/** Extracts the value of the entry at a given node of the tree. */
	protected E value(Position<E> position) {
		return position.element();
	}

	/** Replaces an entry with a new entry (and reset the entry's location) */
	protected void replacecValue(Position<E> pos, E ent) {
		this.binTree.replace(pos, ent);
	}

	/**
	 * Expand an external node into an internal node with two external node
	 * children
	 */
	protected void expandLeaf(Position<E> p, E e1, E e2)
			throws InvalidPositionException {
		if (!this.binTree.isLeaf(p))
			throw new InvalidPositionException("Node is not external");
		this.binTree.insertLeft(p, e1);
		this.binTree.insertRight(p, e2);
	}

	/**
	 * Remove an external node v and replace its parent with v's sibling
	 */
	protected void removeAboveLeaf(Position<E> p)
			throws InvalidPositionException {

		Position<E> u = this.binTree.parent(p);
		this.binTree.remove(p);
		this.binTree.remove(u);

	}

	/** Auxiliary method for inserting an entry at an external node */
	protected Position<E> insertAtLeaf(Position<E> pos, E value) {
		this.expandLeaf(pos, null, null);
		this.binTree.replace(pos, value);
		size++;
		return pos;
	}

	/** Auxiliary method for removing an external node and its parent */
	protected void removeLeaf(Position<E> v) {
		removeAboveLeaf(v);
		size--;
	}

	/** Auxiliary method used by find, insert, and remove. */
	protected Position<E> treeSearch(E value, Position<E> pos)
			throws InvalidPositionException, BoundaryViolationException {
		if (this.binTree.isLeaf(pos))
			return pos; // key not found; return external node
		else {
			E curValue = pos.element();
			int comp = comparator.compare(value, curValue);
			if (comp < 0)
				return treeSearch(value, this.binTree.left(pos)); // search left
																	// subtree
			else if (comp > 0)
				return treeSearch(value, this.binTree.right(pos)); // search
																	// right
																	// subtree
			return pos; // return internal node where key is found
		}
	}

	/**
	 * Adds to L all entries in the subtree rooted at v having keys equal to k.
	 */
	protected void addAll(List<Position<E>> l, Position<E> pos, E value) {
		if (this.binTree.isLeaf(pos))
			return;
		Position<E> p = treeSearch(value, pos);
		if (!this.binTree.isLeaf(p)) { // we found an entry with key equal to k
			addAll(l, this.binTree.left(p), value);
			l.add(p); // add entries in inorder
			addAll(l, this.binTree.right(p), value);
		} // this recursive algorithm is simple, but it's not the fastest
	}

	/** Returns the number of entries in the tree. */
	public int size() {
		return size;
	}

	/** Returns whether the tree is empty. */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns an entry containing the given key. Returns null if no such entry
	 * exists.
	 */
	public Position<E> find(E value) {
		Position<E> curPos = treeSearch(value, this.binTree.root());
		if (this.binTree.isInternal(curPos))
			return curPos;
		return null;
	}

	/**
	 * Returns an iterable collection of all the entries containing the given
	 * key.
	 */
	public Iterable<Position<E>> findAll(E value) {
		List<Position<E>> l = new ArrayList<Position<E>>();
		addAll(l, this.binTree.root(), value);
		return l;
	}

	/** Inserts an entry into the tree and returns the newly created entry. */
	public Position<E> insert(E value) {
		Position<E> insPos = treeSearch(value, this.binTree.root());
		while (!this.binTree.isLeaf(insPos))
			// To consider nodes already in the tree wiht the same key
			insPos = treeSearch(value, this.binTree.right(insPos)); // iterative
																	// search
  														    		// for
		// insertion position
		return insertAtLeaf(insPos, value);
	}

	protected Position<E> getLeafToRemove(Position<E> pos) {
		Position<E> remPos = pos;
		
		if (this.binTree.isLeaf(this.binTree.left(remPos)))
			remPos = this.binTree.left(remPos); // left easy case
		else if (this.binTree.isLeaf(this.binTree.right(remPos)))
			remPos = this.binTree.right(remPos); // right easy case
		else { // entry is at a node with internal children
			Position<E> swapPos = remPos; // find node for moving
											// entry
			remPos = this.binTree.right(swapPos);
			do
				remPos = this.binTree.left(remPos);
			while (this.binTree.isInternal(remPos));
			replacecValue(swapPos, this.binTree.parent(remPos).element());
		}
		return remPos;
	}

	/** Removes and returns a given entry. */
	public E remove(Position<E> pos) throws InvalidPositionException {
		this.checkPosition(pos); // may throw an InvalidPositionException
		E toReturn = pos.element(); // entry to be returned
		Position<E> remPos  = getLeafToRemove(pos);
		removeLeaf(remPos);
		return toReturn;
	}

	/** Returns an iterator containing all entries in the tree. */
	public Iterable<E> values() {
		List<E> entries = new ArrayList<E>();
		Iterable<Position<E>> positer = this.binTree.positions();
		for (Position<E> cur : positer)
			if (this.binTree.isInternal(cur))
				entries.add(cur.element());
		return entries;
	}

	public Iterable<Position<E>> positions() {
		Iterable<Position<E>> positer = this.binTree.positions();

		Iterator<Position<E>> it = positer.iterator();
		while (it.hasNext()) {
			Position<E> cur = it.next();
			if (!this.binTree.isInternal(cur)) {
				it.remove();
			}
		}
		return positer;
	}
	
	
	/**
	 * Find range in binary search trees
	 * @param minRange
	 * @param maxRange
	 * @return
	 */
	public Iterable<Position<E>> findRange(E minRange, E maxRange){
		Iterable<Position<E>> values=this.positions();
		Position<E> p;
		int compareToMin;
		int compareToMax;
		E element;
		List<Position<E>>positions=new ArrayList<Position<E>>();
		//en el bucle para buscar el rango en todos los elementos se usa el comparador que devuelve -1 si es menor
		//0 si es igual o 1 si es mayor
		for(Position<E> posElement : values){
			element=posElement.element();
			compareToMin=this.comparator.compare(element, minRange);
			compareToMax=this.comparator.compare(element, maxRange);
			if( (compareToMin==0 || compareToMin > 0) 
					&& (compareToMax < 0 || compareToMax == 0) )
			{
				positions.add(posElement);
			}
		}
		return positions;
	}
	private boolean startWith(String prefix, E element){
		char[] pref=prefix.toCharArray();
		char[] elm=((String)element).toCharArray();
		boolean eq=true;
		for (int i=0;i<pref.length && eq;i++){
			if(pref[i]!=elm[i])
				eq=false;
		}
		return eq;
	}
	public Iterable<Position<E>> autoComplete(String prefix){
		Iterable<Position<E>> positions=this.positions();
		List<Position<E>> lista = new ArrayList<Position<E>>();
		
		for(Position<E> elem : positions){
			if(startWith(prefix,elem.element()))
				lista.add(elem);
		}
		
		return lista;
	}
	
	/** If v is a good tree node, cast to Position, else throw exception */
	protected BTNode<E> checkPosition(Position<E> p)
			throws InvalidPositionException {
		if (p == null || !(p instanceof BTNode))
			throw new InvalidPositionException(
					"The position of the BTNode in the BST is not valid");
		return (BTNode<E>) p;
	}

	
} // entries() method is omitted here

