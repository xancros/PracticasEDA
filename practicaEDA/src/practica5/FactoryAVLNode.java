package practica5;

import practica3.BTNode;
import practica3.FactoryNode;


public class FactoryAVLNode<E> extends FactoryNode<E>{

	public BTNode<E> createNode (E element, BTNode<E> parent,
			BTNode<E> left, BTNode<E> right){
			
		return new AVLNode<E> (element, parent, left, right);
	}
}
