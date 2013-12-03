package practica3;


public class FactoryNode<E> {

	public BTNode<E> createNode (E element, BTNode<E> parent,
			BTNode<E> left, BTNode<E> right){
			
		return new BTNode<E> (element, parent, left, right);
	}
}
