package practica5;

import java.util.Comparator;

import practica4.InvalidKeyException;

public class AVLOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {

	public AVLOrderedDict() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AVLOrderedDict(Comparator<K> keyComparator) {
		super(keyComparator);
	}
	
	
	@Override
	protected BinarySearchTree<Entry<K,V>> createTree() {
		return new AVLTree<Entry<K, V>>();
	}

	
	
}
