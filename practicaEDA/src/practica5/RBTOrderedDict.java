package practica5;



import java.util.Comparator;

import practica4.InvalidKeyException;



public class RBTOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {

	public RBTOrderedDict() {
		super();
	}

	public RBTOrderedDict(Comparator<K> keyComparator) {
		super(keyComparator);
	}
	
	@Override
	protected BinarySearchTree<Entry<K,V>> createTree() {
		return new RBTree<Entry<K, V>>();
	}

	
	
}
