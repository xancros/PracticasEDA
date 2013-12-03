package practica3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import practica2.BoundaryViolationException;
import practica2.EmptyTreeException;
import practica2.InvalidPositionException;
import practica2.NonEmptyTreeException;
import practica2.Position;
import practica3.BinaryTree;



/**
 *
 * @author R. Cabido, A. Duarte, J. Vélez
 * @see BinaryTree
 */

public final class LinkedBinaryTree<E> implements BinaryTree<E> {
	protected BTNode<E> root; // reference to the root
	protected int size; // number of nodes
	protected FactoryNode<E> factoryNode;
	
	/** Creates an empty binary tree. */
	public LinkedBinaryTree() {
		root = null; // start with an empty tree
		size = 0;
		this.factoryNode = new FactoryNode<E>();
	}
	
	/** Creates an empty binary tree using a Node Factory */
	public LinkedBinaryTree(FactoryNode<E> factoryNode) {
		root = null; // start with an empty tree
		size = 0;
		if (factoryNode == null)
			this.factoryNode = new FactoryNode<E>();
		else
			this.factoryNode = factoryNode;
	}

	/** Returns the number of nodes in the tree. */
	public int size() {
		return size;
	}

	/** Returns whether the tree is empty. */
	public boolean isEmpty() {
		return (size == 0);
	}

	/** Returns whether a node is internal. */
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v); // auxiliary method
		return (hasLeft(v) || hasRight(v));
	}

	/** Returns whether a node is external. */
	public boolean isLeaf(Position<E> p) throws InvalidPositionException {
		return !isInternal(p);
	}

	/** Returns whether a node is the root. */
	public boolean isRoot(Position<E> p) throws InvalidPositionException {
		checkPosition(p);
		return (p == root());
	}

	/** Returns whether a node has a left child. */
	public boolean hasLeft(Position<E> p) throws InvalidPositionException {
		BTNode<E> node = checkPosition(p);
		return (node.getLeft() != null);
	}

	/** Returns whether a node has a right child. */
	public boolean hasRight(Position<E> p) throws InvalidPositionException {
		BTNode<E> node = checkPosition(p);
		return (node.getRight() != null);
	}

	/** Returns the root of the tree. */
	public Position<E> root() throws EmptyTreeException {
		if (root == null)
			throw new EmptyTreeException("The tree is empty");
		return root;
	}

	/** Returns the left child of a node. */
	public Position<E> left(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		BTNode<E> node = checkPosition(p);
		Position<E> leftPos = node.getLeft();
		if (leftPos == null)
			throw new BoundaryViolationException("No left child");
		return leftPos;
	}

	/** Returns the right child of a node. */
	public Position<E> right(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		BTNode<E> node = checkPosition(p);
		Position<E> rightPos = node.getRight();
		if (rightPos == null)
			throw new BoundaryViolationException("No right child");
		return rightPos;
	}

	/** Returns the parent of a node. */
	public Position<E> parent(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		BTNode<E> node = checkPosition(p);
		Position<E> parentPos = node.getParent();
		if (parentPos == null)
			throw new BoundaryViolationException("No parent");
		return parentPos;
	}

	/** Returns an iterable collection of the children of a node. */
	// public Iterable<? extends Position<E>> children(Position<E> v)
	public Iterable<Position<E>> children(Position<E> p)
			throws InvalidPositionException {
		List<Position<E>> children = new ArrayList<Position<E>>();
		if (hasLeft(p))
			children.add(left(p));
		if (hasRight(p))
			children.add(right(p));
		return children;
	}

	/** Returns an iterable collection of the tree nodes. */
	public Iterable<Position<E>> positions() {
		List<Position<E>> positions = new ArrayList<Position<E>>();
		if (size != 0)
			preorderPositions(root(), positions); // assign positions in
													// preorder
		return positions;
	}

	/** Returns an iterator of the elements stored at the nodes. */
	public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		List<E> elements = new ArrayList<E>();
		for (Position<E> pos : positions)
			elements.add(pos.element());
		return elements.iterator(); // An iterator of elements
	}

	/**
	 * Replaces the element at a node.
	 * @param p Posición que se reemplaza.
	 * @param e Elemento que reemplaza.
	 */
	public E replace(Position<E> p, E e) throws InvalidPositionException {
		BTNode<E> node = checkPosition(p);
		E temp = p.element();
		node.setElement(e);
		return temp;
	}

	// Additional accessor method
	/** Return the sibling of a node */
	public Position<E> sibling(Position<E> p) throws InvalidPositionException,
			BoundaryViolationException {
		BTNode<E> node = checkPosition(p);
		BTNode<E> parentPos = node.getParent();
		if (parentPos != null) {
			BTNode<E> sibPos;
			BTNode<E> leftPos = parentPos.getLeft();
			if (leftPos == node)
				sibPos = parentPos.getRight();
			else
				sibPos = parentPos.getLeft();
			if (sibPos != null)
				return sibPos;
		}
		throw new BoundaryViolationException("No sibling");
	}

	// Additional update methods
	/** Adds a root node to an empty tree */
	public Position<E> addRoot(E e) throws NonEmptyTreeException {
		if (!isEmpty())
			throw new NonEmptyTreeException("Tree already has a root");
		size = 1;
		root = createNode(e, null, null, null);
		return root;
	}

	/** Inserts a left child at a given node. */
	public Position<E> insertLeft(Position<E> p, E e)
			throws InvalidPositionException {
		BTNode<E> node = checkPosition(p);
		Position<E> leftPos = node.getLeft();
		if (leftPos != null)
			throw new InvalidPositionException("Node already has a left child");
		BTNode<E> ww = createNode(e, node, null, null);
		node.setLeft(ww);
		ww.setParent(node);		
		size++;
		return ww;
	}

	/** Inserts a right child at a given node. */
	public Position<E> insertRight(Position<E> p, E e)
			throws InvalidPositionException {
		BTNode<E> node = checkPosition(p);
		Position<E> rightPos = node.getRight();
		if (rightPos != null)
			throw new InvalidPositionException("Node already has a right child");
		BTNode<E> w = createNode(e, node, null, null);
		node.setRight(w);
		w.setParent(node);		
		size++;
		return w;
	}

	/** Removes a node with zero or one child. */
	public E remove(Position<E> p) throws InvalidPositionException {
		BTNode<E> node = checkPosition(p);
		BTNode<E> leftPos = node.getLeft();
		BTNode<E> rightPos = node.getRight();
		if (leftPos != null && rightPos != null)
			throw new InvalidPositionException(
					"Cannot remove node with two children");
		BTNode<E> child; // the only child of v, if any
		if (leftPos != null)
			child = leftPos;
		else if (rightPos != null)
			child = rightPos;
		else
			// v is a leaf
			child = null;
		if (node == root) { // v is the root
			if (child != null)
				child.setParent(null);
			root = child;
		} else { // v is not the root
			BTNode<E> parent = node.getParent();
			if (node == parent.getLeft())
				parent.setLeft(child);
			else
				parent.setRight(child);
			if (child != null)
				child.setParent(parent);
		}
		size--;
		return p.element();
	}

	/** Attaches two trees to be subtrees of a leaf node. */
	public void attach(Position<E> p, BinaryTree<E> t1, BinaryTree<E> t2)
			throws InvalidPositionException {
		BTNode<E> node = checkPosition(p);
		if (isInternal(p))
			throw new InvalidPositionException(
					"Cannot attach from internal node");
		int newSize = size + t1.size() + t2.size();
		if (!t1.isEmpty()) {
			BTNode<E> r1 = checkPosition(t1.root());
			node.setLeft(r1);
			r1.setParent(node); // T1 should be invalidated
		}
		if (!t2.isEmpty()) {
			BTNode<E> r2 = checkPosition(t2.root());
			node.setRight(r2);
			r2.setParent(node); // T2 should be invalidated
		}
		size = newSize;
	}

	/** Swap the elements at two nodes */
	public void swapElements(Position<E> p1, Position<E> p2)
			throws InvalidPositionException {
		BTNode<E> node1 = checkPosition(p1);
		BTNode<E> node2 = checkPosition(p2);
		E temp = p2.element();
		node2.setElement(p1.element());
		node1.setElement(temp);
	}

	// Auxiliary methods
	/**
	 * If v is a good binary tree node, cast to BTPosition, else throw exception
	 */
	protected BTNode<E> checkPosition(Position<E> p)
			throws InvalidPositionException {
		if (p == null || !(p instanceof BTNode))
			throw new InvalidPositionException("The position is invalid");
		return (BTNode<E>) p;
	}

	/** Creates a new binary tree node */
	protected BTNode<E> createNode(E element, BTNode<E> parent, BTNode<E> left,
			BTNode<E> right) {
		return factoryNode.createNode(element, parent, left, right);
			
	}

	/**
	 * Creates a list storing the the nodes in the subtree of a node, ordered
	 * according to the preorder traversal of the subtree.
	 */
	protected void preorderPositions(Position<E> v, List<Position<E>> pos)
			throws InvalidPositionException {
		pos.add(v);
		if (hasLeft(v))
			preorderPositions(left(v), pos); // recurse on left child
		if (hasRight(v))
			preorderPositions(right(v), pos); // recurse on right child
	}

	// end#fragment LinkedBinaryTree5
	/**
	 * Creates a list storing the the nodes in the subtree of a node, ordered
	 * according to the inorder traversal of the subtree.
	 */
	protected void inorderPositions(Position<E> v, List<Position<E>> pos)
			throws InvalidPositionException {
		if (hasLeft(v))
			inorderPositions(left(v), pos); // recurse on left child
		pos.add(v);
		if (hasRight(v))
			inorderPositions(right(v), pos); // recurse on right child
	}
        /*
        Give a tree the method returns a new tree where all left childs
        become roght childs and vice versa
        */
        /**
         * Give a tree the method returns a new tree where all left childs
         * become right childs and vice versa
         * @return 
         */
        public LinkedBinaryTree<E> mirror(){
            LinkedBinaryTree<E> mirrorTree=new LinkedBinaryTree<E>();
            
            mirrorTree.root=subMirror(this.root,new BTNode<E>());
            mirrorTree.size=this.size;
            return mirrorTree;
        }
        /**
         * Coger raiz y colocarla en el nuevo arbol, e ir con la misma tecnica que en el de
         * recorrer el arbol para un elemento con el for 0,1 cogiendo 0 izquierda 1 derecha
         * y añadir los hijos izquierdos de la izquierda del arbol original a la derecha y al reves
         * @param nodeOriginal
         * @param newNode
         * @return 
         */
        private BTNode<E> subMirror(BTNode<E> nodeOriginal,BTNode<E> newNode){
            //raiz
            if(nodeOriginal.getParent()==null){  
                newNode.setElement(nodeOriginal.element());
                newNode.setParent(null);
                //camino de la izquierda hacia la derecha del arbol viejo al arbol nuevo
                newNode.setLeft(subMirror(nodeOriginal.getRight(),newNode));
                newNode.setRight(subMirror(nodeOriginal.getLeft(),newNode));
                return newNode;
            }else{
                BTNode<E> node;
                //nodo hoja
                if(nodeOriginal.getLeft()==null && nodeOriginal.getRight()==null ){
                    node=new BTNode<E>(nodeOriginal.element(),newNode,null,null);
                }else{//cualquier otro caso
                    node=new BTNode<E>();
                    node.setElement(nodeOriginal.element());
                    node.setParent(newNode);
                    if(nodeOriginal.getLeft()!=null)//llamamos por la izquierda del arbol original
                        node.setRight(subMirror(nodeOriginal.getLeft(),node));
                    if(nodeOriginal.getRight()!=null)//llamamos por la derecha del arbol original
                        node.setLeft(subMirror(nodeOriginal.getRight(),node));
                    
                }
                return node;
            }
        }
        /**
         * 
        * Determines whether the element e is the tree or not
        * @param e
        * @return 
        */
        
        public boolean contais (E e){
            
            return this.elementInTree(e,this.root);
        }
        private boolean elementInTree (E e,BTNode<E> node){
            boolean exist=false;
            //recorremos el arbol binario empezando por la izquierda
            for(int i=0;i<2 && exist==false;i++){
                if(e==node.element()){
                        return true;
                }
                if(i==0){
                    Position<E> posL=node.getLeft();
                    if(posL!=null){
                        exist=elementInTree (e,node.getLeft());
                    }
                }else{
                    Position<E> posR = node.getRight();
                    if(posR!=null){
                        exist=elementInTree (e,node.getRight());
                    }
                }
            }
            return exist;
        }
        //Determines the level of a node in the tree
        /**
         * Determines the level of a node in the tree
         * @param p
         * @return 
         */
        public int level(Position<E> p){
            //BTNode<E> node = this.checkPosition(p);
            BTNode<E> n = this.checkPosition(p);
            int lvl=lvl(0,n,this.root);
            return lvl;
        }
        public int lvlAux(Position<E> p){
        	BTNode<E> n = this.checkPosition(p);
            int lvl=lvlNode(n);
            return lvl;
        }
        private int lvlNode (BTNode<E> node){
        	int level=0;
        	BTNode<E> nod=node;
        	if(nod.getParent()==null){//nodo raiz
        		return level;
        	}
        	BTNode<E> parent=nod.getParent();
        	while(parent!=null){
        		nod=parent;
        		parent=nod.getParent();
        		level++;
        	}
        	return level;
        }
        private int lvl (int level,BTNode<E> nodeToBeFound,BTNode<E> subRoot){
            
            
            
            for(int k=0;k<2;k++){
            
                    if(k==0){
                        if(! (nodeToBeFound.element().equals(subRoot.element())) ){
                            if(subRoot.getLeft()!=null)
                                return lvl(level+1,nodeToBeFound,subRoot.getLeft());
                        }else{
                            
                            return level;
                        }
                    }else{
                        if(! (nodeToBeFound.element().equals(subRoot.element())) ){
                               if(subRoot.getRight()!=null)
                                return lvl(level+1,nodeToBeFound,subRoot.getRight());
                        }else{
                            return level;
                        }
                    }
            
            }
            
            return -1;
        }
        public void mostrarArbol(LinkedBinaryTree<E> tree){
            System.out.println("Raiz "+tree.root.element());
            mostrarNodo(tree.root);
            
        }
        private void mostrarNodo(BTNode<E> node){
          
            if(node!=null){
                if(node.getLeft()!=null){
                    System.out.println("IZquierda "+node.getLeft().element());
                    mostrarNodo(node.getLeft());
                }else{
                    System.out.println("NO tiene hijo izquierdo el nodo "+ node.element());
                }
                if(node.getRight()!=null){
                    System.out.println("Derecha "+node.getRight().element());
                    mostrarNodo(node.getRight());
                }
                else
                    System.out.println("No tiene hijo derecho el nodo "+node.element());
                
            }
        }
       
        
        
}
