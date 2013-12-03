package practica5;

import java.util.Comparator;

import practica4.InvalidKeyException;



/**
 * Realization of a dictionary by means of a binary search tree.
 * 
 * @author Michael Goodrich, Eric Zamore
 */

// Realization of a dictionary by means of a binary search tree
public class BSTOrderedDict<K, V> extends AbstractTreeOrderedDict<K, V> {
	
	public BSTOrderedDict() {
		super();
	}
	
	public BSTOrderedDict(Comparator<K> keyComparator) {
		super(keyComparator);
	}
	
	protected BinarySearchTree<Entry<K,V>> createTree (){
		return new BinarySearchTree<Entry<K,V>>();		
	}

	
	
}

