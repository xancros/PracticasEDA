package practica2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import practica2.Position;
import practica2.Tree;
import practica2.BoundaryViolationException;
import practica2.EmptyTreeException;
import practica2.InvalidPositionException;
import practica2.NonEmptyTreeException;


public class LinkedTree<E>
		implements Tree<E> {
	protected TreeNode<E> root; // reference to the root
	protected int size; // number of nodes

	/** Creates an empty tree. */
	public LinkedTree() {
		root = null; // start with an empty tree
		size = 0;
	}

	/**
	 * Returns the number of nodes in the tree.
	 * 
	 * @return The size.
	 * */
	public int size() {
		return size;
	}

	/**
	 * Returns whether the tree is empty.
	 * 
	 * @return True if is empty.
	 * */
	public boolean isEmpty() {
		return (size == 0);
	}

	/** Returns whether a node is internal. */
	public boolean isInternal(
			Position<E> v)
			throws InvalidPositionException {
		return !isLeaf(v);
	}

	/** Returns whether a node is external. */
	public boolean isLeaf(
			Position<E> v)
			throws InvalidPositionException {
		TreeNode<E> vv = checkPosition(v); // auxiliary method
		return (vv
				.getChildren() == null)
				|| vv.getChildren().isEmpty();
	}

	/** Returns whether a node is the root. */
	public boolean isRoot(
			Position<E> v)
			throws InvalidPositionException {
		checkPosition(v);
		// return (v == root());
		return ((TreeNode<E>) v == root());
	}

	/** Returns the root of the tree. */
	public Position<E> root()
			throws EmptyTreeException {
		if (root == null)
			throw new EmptyTreeException(
					"The tree is empty");
		return (Position<E>) root;
	}

	/** Returns the parent of a node. */
	public Position<E> parent(
			Position<E> v)
			throws InvalidPositionException, BoundaryViolationException {
		TreeNode<E> vv = checkPosition(v);
		Position<E> parentPos = (Position<E>) vv
				.getParent();
		if (parentPos == null)
			throw new BoundaryViolationException(
					"No parent");
		return parentPos;
	}

	/** Returns an iterable collection of the children of a node. */
	// ? extends -> significa no sï¿½lo que se puede devolver Iterable de
	// Position<E> sino que se puede devolver tambiï¿½ncualquier cosa que herede
	// de Position<E>, como por ejemplo TreeNode
	// public Iterable<? extends Position<E>> children(Position<E> v)
	public Iterable<Position<E>> children(
			Position<E> v)
			throws InvalidPositionException {
		TreeNode<E> vv = checkPosition(v);
		if (isLeaf(v))
			throw new InvalidPositionException(
					"External nodes have no children");

		// return Collections.unmodifiableCollection(vv.getChildren());
		return (Iterable) (vv
				.getChildren());
	}

	/** Returns an iterable collection of the tree nodes. */
	public Iterable<Position<E>> positions() {
		List<Position<E>> positions = new ArrayList<Position<E>>();
		if (size != 0)
			preorderPositions(
					root(), positions); // assign positions in
										// preorder
		return positions;
	}

	/** Returns an iterator of the elements stored at the nodes */
	public Iterator<E> iterator() {

		Iterable<Position<E>> positions = positions();
		List<E> elements = new ArrayList<E>();
		for (Position<E> pos : positions)
			elements.add(pos
					.element());
		return elements
				.iterator(); // An iterator of elements
	}

	/** Replaces the element at a node. */
	public E replace(
			Position<E> v, E o)
			throws InvalidPositionException {
		TreeNode<E> vv = checkPosition(v);
		E temp = v
				.element();
		vv.setElement(o);
		return temp;
	}

	// Additional update methods
	/** Adds a root node to an empty tree */
	public Position<E> addRoot(
			E e)
			throws NonEmptyTreeException {
		if (!isEmpty())
			throw new NonEmptyTreeException(
					"Tree already has a root");
		size = 1;
		root = createNode(
				e, null, new LinkedList<TreeNode<E>>());
		return (Position<E>) root;
	}

	/** Swap the elements at two nodes */
	public void swapElements(
			Position<E> v, Position<E> w)
			throws InvalidPositionException {
		TreeNode<E> vv = checkPosition(v);
		TreeNode<E> ww = checkPosition(w);
		E temp = w
				.element();
		ww.setElement(v
				.element());
		vv.setElement(temp);
	}

	// Auxiliary methods
	/** If v is a good tree node, cast to TreePosition, else throw exception */
	protected TreeNode<E> checkPosition(
			Position<E> v)
			throws InvalidPositionException {
		if (v == null
				|| !(v instanceof TreeNode))
			throw new InvalidPositionException(
					"The position is invalid");
		return (TreeNode<E>) v;
	}

	/** Creates a new tree node */
	protected TreeNode<E> createNode(
			E element, TreeNode<E> parent, List<TreeNode<E>> children) {
		return new TreeNode<E>(
				element, parent, children);
	}

	public Position<E> add(
			E element, Position<E> parent) {
		Position<E> newNode = (Position<E>) createNode(
				element, (TreeNode) parent, new LinkedList<TreeNode<E>>());
		TreeNode parentDesencapsulado = (TreeNode) parent;
		List<Position<E>> l = parentDesencapsulado
				.getChildren();
		l.add(newNode);
		size++;
		return newNode;
	}

	public void removeNode(
			Position<E> p)
			throws InvalidPositionException {
		TreeNode<E> vv = checkPosition(p);
		TreeNode<E> par = vv
				.getParent();

		par.getChildren().remove(
				p);

		List<Position<E>> list = new ArrayList<Position<E>>();
		this.preorderPositions(
				p, list);

		size = size
				- list.size(); //

	}
//####################################################################
	/**
	* Returns an iterable collection of the the leaf nodes
	*/
        //FUNCIONA
	public Iterable<Position<E>> front(){
		List<Position<E>> cosas = leafNodes (this.root);
		return cosas;
	}
	/**
	 * Metodo recursivo, entra en cada uno de los nodos del arbol y en cada caso, va añadiendo
	 * nodos hoja a una lista que será la que devolverá el algoritmo.
	 * @param n
	 * @return
	 */
	private List<Position<E>> leafNodes (TreeNode<E> n){
		if(n.getChildren().isEmpty()){
			List<Position<E>> base= new ArrayList<Position<E>>();
			Position<E> pos = n;
			base.add(pos);
			return base;
		}else{
			Iterable<TreeNode<E>> hijos = n.getChildren();
			List<Position<E>> fin= new ArrayList<Position<E>>();
			for (TreeNode<E> node : hijos){
				fin.addAll(leafNodes(node));
			}
			return fin;
		}
	}
	
	//#######################################################################
	/**
	* Returns an integer with the depth of the tree
     * @return 
	*/
        //FUNCIONA
	public int depth(){
		
		return recorrer(1,this.root);
	}
	/**
	 * Metodo recursivo que se mete en cada nodo a recorrerlo y devuelve en cada caso
	 * el maximo de nodos, así para cada uno de los hijos que tenga siempre se quedará con el máximo.
	 * @param nivel
	 * @param node
	 * @return
	 */
	private int recorrer(int nivel,TreeNode<E> node){
                int lvl=0;
                Iterable<TreeNode<E>> hijos = node.getChildren();
                for(TreeNode<E> n : hijos){
                    if(n.getChildren().isEmpty()){
                        return Math.max(lvl,nivel);
                    }else{
                        lvl=recorrer(nivel+1,n);
                    }
                }
                return lvl;
	}
	/**
	* Returns an integer with the degree of the tree
     * @return 
	*/
	public int degree(){
		return subDegree();
	}
	/**
	 * Crea un Iterable de las posiciones para usar despues un for mejorador
	 * y comprobar en cada nodo que tenga, el numero de hijos y escoge el máximo en cada caso
	 * @return
	 */
	private int subDegree(){
		int degree=0;
		Iterable<Position<E>> iterable = this.positions();
		for(Position<E> n : iterable){
			TreeNode<E> nodeInList = this.checkPosition(n);
			degree=Math.max(degree, nodeInList.getChildren().size());
		}
		return degree;
	}
	/**
	 * Creates a list storing the the nodes in the subtree of a node, ordered according to the preorder traversal of the subtree.
     * @param v
     * @param pos
	 */
	protected void preorderPositions(Position<E> v, List<Position<E>> pos)
			throws InvalidPositionException {
		pos.add(v);
		if (!this.isLeaf(v)) {
			for (Position<E> w : children(v))
                            preorderPositions(w, pos); // recurse on each child
		}
	}
}
