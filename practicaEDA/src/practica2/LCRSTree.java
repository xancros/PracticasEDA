/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2;


import java.util.*;

/**
 *
 * @author Aitor
 */
public class LCRSTree<E> implements Tree<E> {
    protected int size;
    protected LCRSNode<E> root;

    public LCRSTree() {
        size=0;
        root=null;
    }
    
        public int size() {
        return size;
    }

        public boolean isEmpty() {
        return (size==0);
    }
    
    /** Adds a root node to an empty tree */
	public Position<E> addRoot(E e) throws NonEmptyTreeException {
		if (!this.isEmpty())
			throw new NonEmptyTreeException("Tree already has a root");
		this.size = 1;
		
		this.root = createNode(e, null, null);
		return this.root;
	}
    
        /** Adds a new child to a given parent. */
	public Position<E> add(Position<E> posParent, E element)
			throws InvalidPositionException {
	
            LCRSNode<E> Parent = this.checkPosition(posParent);
            LCRSNode<E> nuevoNodo = new LCRSNode<E>(element,Parent,null,null);
            if(Parent.getLeftChild()!=null){
                //si tiene hijo izquierdo, interpretamos como hermano    
                Parent.getLeftChild().setRightSibbling(nuevoNodo);
                
            }
            else{
                Parent.setLeftChild(nuevoNodo);
            }
            size++;
            return nuevoNodo;
            
            
	}
        /** Creates a new  node */
	protected LCRSNode<E> createNode(E element, LCRSNode<E> parent,
			LCRSNode<E> children, LCRSNode<E> sibling) {
		return new LCRSNode<E>(element, parent, children,sibling);
	}
        protected LCRSNode<E> createNode(E element, LCRSNode<E> parent,
			LCRSNode<E> children) {
		return new LCRSNode<E>(element, parent, children,null);
	}

	/** Swap the elements at two nodes */
	public void swapElements(Position<E> p1, Position<E> p2)
			throws InvalidPositionException {
                        E elemenAux;
                        LCRSNode<E> node1=this.checkPosition(p1);
                        LCRSNode<E> node2=this.checkPosition(p2);
                        elemenAux=node1.element();
                        node1.setElement(node2.element());
                        node2.setElement(elemenAux);
		
	}
        

        
        
        //falta el remove
        
        
        
        private boolean isSibling (Position<E> p){
            LCRSNode<E> nodo = this.checkPosition(p);
            LCRSNode<E> padre = nodo.getParent();
            if (padre.getLeftChild().getRightSibbling()==nodo){
                return true;
            }
            return false;
        }
        
        public E remove(Position<E> p){
                
            LCRSNode<E> nodo=this.checkPosition(p);
            
            int a=nodos(nodo,0);
            size=size-a;
            nodo.getParent().setLeftChild(null);
            return nodo.element();
            
        }
        
        private int nodos(LCRSNode<E> node, int numero){
            
            if (node.getLeftChild()==null){//caso base
                node.getParent().setLeftChild(null);
                return numero++;
                
            }else{
                nodos(node.getLeftChild(),numero);
            }
            return numero++;
        }
        /** If v is a good tree node, cast to TreePosition, else throw exception */
	protected LCRSNode<E> checkPosition(Position<E> p)
			throws InvalidPositionException {
		if (p == null || !(p instanceof LCRSNode))
			throw new InvalidPositionException("The position is invalid");
		
		LCRSNode<E> node = (LCRSNode<E>) p;
		
		if ((node.getParent()==null)&& (this.root!=node)){
			throw new InvalidPositionException("The position was removed");
		}
		
		return node;
	}
        

    public Iterator<E> iterator() {
        //creamos un objeto iterable de los positions de E
        Iterable<Position<E>> positions =this.positions();
        //creamos un objeto tipo lista donde guardar los positions
        List<E> element = new ArrayList<E>();
        //recorremos todos los objetos del objeto iterable
        for (Position<E> pos : positions){
            //guardamos los elementos en la lista
            element.add(pos.element());
        }
        //devolvemos la lista
        return element.iterator();
    }


    public Iterable<Position<E>> positions() {
        List<Position<E>> positions = new ArrayList<Position<E>>();
		if (size != 0) {
            preorderPositions((Position<E>)this.root(), positions);
        } // assign positions in
														// preorder
		return positions;
    }

    public E replace(Position<E> v, E e) throws InvalidPositionException {
        LCRSNode<E> aux=checkPosition(v);
        E elem=aux.element();
        aux.setElement(e);
        return elem;
    }

    public Position<E> root() throws EmptyTreeException {
            if (this.root==null){
                throw new EmptyTreeException ("Tree is empty");
            }
            return this.root;
    }

    public Position<E> parent(Position<E> v) throws InvalidPositionException, BoundaryViolationException {
           LCRSNode<E> p=checkPosition(v);
           Position<E> posParent = p.getParent();
           if (posParent==null){
               throw new BoundaryViolationException("No parent");
           }
           return posParent;
    }

    
    public Iterable<? extends Position<E>> children(Position<E> v) throws InvalidPositionException {
        return null;
    }

    public boolean isInternal(Position<E> v) throws InvalidPositionException {
        return !this.isLeaf(v);
    }

    public boolean isLeaf(Position<E> v) throws InvalidPositionException {
        LCRSNode<E> aux=checkPosition(v);
        return (aux.getLeftChild()==null);
    }

    public boolean isRoot(Position<E> v) throws InvalidPositionException {
        LCRSNode<E> aux=checkPosition(v);
        return (aux.getParent()==null);
    }
    
    private void preorderPositions(Position<E> p, List<Position<E>> pos)
			throws InvalidPositionException {
		pos.add(p);
		for (Position<E> w : children(p)) {
                                preorderPositions(w, pos);
                            } // recurse on each child
		}

    
}
