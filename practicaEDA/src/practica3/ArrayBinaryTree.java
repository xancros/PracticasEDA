package practica3;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import practica2.BoundaryViolationException;
import practica2.EmptyTreeException;
import practica2.InvalidPositionException;
import practica2.NonEmptyTreeException;
import practica2.Position;

public class ArrayBinaryTree<E> implements BinaryTree<E> {
		private BTPos<E>[] tree;
		private int MAX_SIZE=20;
		private int size;
		
		public ArrayBinaryTree(){
			tree =(BTPos<E>[]) new BTPos[MAX_SIZE];
		}
		public Position<E> addRoot(E elemento){
			if(this.size==0){
				BTPos<E> root = new BTPos<E>(elemento,1);
				this.tree[1]=root;
				size=1;
				return root;
			}else{
				throw new NonEmptyTreeException("Tree already has a root");
			}
		}
		public Position<E> insertLeft(Position<E> pos,E elemento){
			BTPos<E> padre = this.checkPosition(pos);
			int indice=2*padre.index();
			if(tree[indice]!=null){
				throw new NonEmptyTreeException("Node already has already a left child");
			}
			if(indice>this.MAX_SIZE)
				realojar();
			BTPos<E> hijoIzquierdo = new BTPos<E>(elemento,indice);
			hijoIzquierdo.setParent(padre.index());
			padre.setLeft(indice);
			size++;
			this.tree[indice]=hijoIzquierdo;
			return hijoIzquierdo;
		}
		
		public Position<E> insertRight(Position<E> pos,E elemento){
			BTPos<E> padre = this.checkPosition(pos);
			int indice=2*padre.index()+1;
			if(tree[indice]!=null){
				throw new NonEmptyTreeException("Node already has already a right child");
			}
			if(indice>this.MAX_SIZE)
				realojar();
			BTPos<E> hijoDerecho = new BTPos<E>(elemento,indice);
			hijoDerecho.setParent(padre.index());
			padre.setRight(indice);
			size++;
			this.tree[indice]=hijoDerecho;
			return hijoDerecho;
		}
		
		private void realojar(){
			int nuevo=MAX_SIZE*2;
			BTPos<E>[] treeCopy = (BTPos<E>[]) new BTPos[nuevo];
			for(int i=0;i<MAX_SIZE;i++)
				treeCopy[i]=this.tree[i];
			MAX_SIZE=nuevo;
			tree=treeCopy;
		}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return this.size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return this.size==0;
	}

	@Override
	public Iterator<E> iterator() {
		Iterable<Position<E>> positions = positions();
		List<E> elements = new ArrayList<E>();
		for (Position<E> pos : positions)
			elements.add(pos.element());
		return elements.iterator(); 
	}

	@Override
	public Iterable<Position<E>> positions() {
		 List<Position<E>> positions = new ArrayList<Position<E>>();
	        if ( (this.size()) != 0)
	            preorderPositions(1, positions);
	        return positions;
	}

	@Override
	public E replace(Position<E> v, E e) throws InvalidPositionException {
		// TODO Auto-generated method stub
		BTPos<E> nodo = this.checkPosition(v);
		E element = nodo.element();
		nodo.setElement(e);
		return element;
	}

	@Override
	public Position<E> root() throws EmptyTreeException {
		// TODO Auto-generated method stub
		return this.tree[1];
	}

	@Override
	public Position<E> parent(Position<E> v) throws InvalidPositionException,
			BoundaryViolationException {
		// TODO Auto-generated method stub
		BTPos<E> node = this.checkPosition(v);
		
		return tree[node.getParent()];
	}

	@Override
	public Iterable<? extends Position<E>> children(Position<E> v)
			throws InvalidPositionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isInternal(Position<E> v) throws InvalidPositionException {
		checkPosition(v); // auxiliary method
		return (hasLeft(v) || hasRight(v));
	}

	@Override
	public boolean isLeaf(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRoot(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Position<E> left(Position<E> v) throws InvalidPositionException,
			BoundaryViolationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Position<E> right(Position<E> v) throws InvalidPositionException,
			BoundaryViolationException {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public boolean hasLeft(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		BTPos<E> node = this.checkPosition(v);
		return node.getLeft()!=-1;
	}

	@Override
	public boolean hasRight(Position<E> v) throws InvalidPositionException {
		// TODO Auto-generated method stub
		BTPos<E> node = this.checkPosition(v);
		return node.getRight()!=-1;
	}
	public void swapElements(Position<E> p1,Position<E> p2)throws InvalidPositionException{
		BTPos<E> nodo1 = this.checkPosition(p1);
		BTPos<E> nodo2 = this.checkPosition(p2);
		E elemento= nodo1.element();
		nodo1.setElement(nodo2.element());
		nodo2.setElement(elemento);
		
	}
	public E remove (Position<E> pos) throws InvalidPositionException{
		BTPos<E> node = this.checkPosition(pos);
		if(this.hasLeft(pos) && this.hasRight(pos))
			throw new InvalidPositionException("Node has two childs");
		else if (!this.isInternal(pos)){	
				size--;
				this.tree[node.index()]=null;
			BTPos<E> padre = this.tree[node.getParent()];
			if(padre.getLeft()==node.index())
				padre.setLeft(-1);
			else
				padre.setRight(-1);
			
			return node.element();
		}else{
			return removeInternal(node);
		}
		
		
	}
	private E removeInternal(BTPos<E> pos) throws InvalidPositionException{
		BTPos<E> padre = tree[pos.getParent()];
		//if(padre.getLeft()==pos.index()){
			if(pos.getLeft()!=-1){
				int indice=pos.index();
				tree[indice]=tree[pos.getLeft()];
				tree[pos.getLeft()]=null;
				tree[indice].setIndex(indice);
				this.size--;
			}else{
				int indice=pos.index();
				tree[indice]=tree[pos.getRight()];
				tree[pos.getRight()]=null;
				tree[indice].setIndex(indice);
				this.size--;
			}
		//	}
		/*}else{
			if(pos.getLeft()!=-1){
				int indice=pos.index();
				tree[indice]=tree[pos.getLeft()];
				tree[pos.getLeft()]=null;
				tree[indice].setIndex(indice);
				this.size--;
			}else{
				int indice=pos.index();
				tree[indice]=tree[pos.getRight()];
				tree[pos.getRight()]=null;
				tree[indice].setIndex(indice);
				this.size--;
			}
		}*/
		return pos.element();
		
	}
	private int numberOfChilds(int pos){
		BTPos<E> nodo = this.tree[pos];
		int numero=0;
		if(this.hasLeft(nodo)){
			numero+=numberOfChilds(nodo.getLeft());
		}
		if (this.hasRight(nodo)){
			numero+=numberOfChilds(nodo.getRight());
		}
		return numero+1;
	}
	 protected BTPos<E> checkPosition (Position<E> p)
		        throws InvalidPositionException {
		        if ( (p==null) || !(p instanceof BTPos) )
		            throw new InvalidPositionException("The position is invalid");
		        return (BTPos<E>) p;
		    }
	 
	 protected void preorderPositions(int index, List<Position<E>> pos)throws InvalidPositionException{
		 pos.add(tree[index]);
		 if(hasLeft(tree[index]))
			 preorderPositions(tree[index].getLeft(),pos);
		 if (hasRight(tree[index]))
			 preorderPositions(tree[index].getRight(),pos);
	 }
	protected void preorderPositions(Position<E> v, List<Position<E>> pos)
			throws InvalidPositionException {
		pos.add(v);
		if (hasLeft(v))
			preorderPositions(left(v), pos); // recurse on left child
		if (hasRight(v))
			preorderPositions(right(v), pos); // recurse on right child
    }

}
