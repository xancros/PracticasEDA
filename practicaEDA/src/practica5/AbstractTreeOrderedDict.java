package practica5;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import practica2.Position;
import practica4.InvalidEntryException;
import practica4.InvalidKeyException;



/**
 * Realization of a dictionary by means of a binary search tree.
 * 
 * @author Michael Goodrich, Eric Zamore
 */

// Realization of a dictionary by means of a binary search tree
abstract public class AbstractTreeOrderedDict<K, V> implements OrderedDictionary<K, V> {
	
	protected BinarySearchTree<Entry<K, V>> bsTree;
	protected Comparator<K> keyComparator;
		
	/** Creates a BinarySearchTree with a default comparator. */
	public AbstractTreeOrderedDict() {
		bsTree = this.createTree();
		this.keyComparator = null;
	}

	public AbstractTreeOrderedDict(Comparator<K> keyComparator) {
		bsTree = this.createTree();
		this.keyComparator = keyComparator;
	}
	
	abstract protected BinarySearchTree<Entry<K,V>> createTree ();
	
	/** Checks whether a given key is valid. */
	protected void checkKey(K key) throws InvalidKeyException {
		if (key == null) // just a simple test for now
			throw new InvalidKeyException("null key");
	}

	/** Checks whether a given entry is valid. */
	protected void checkEntry(Entry<K, V> ent) throws InvalidEntryException {
		if (ent == null || !(ent instanceof DictEntry))
			throw new InvalidEntryException("invalid entry");
	}

	/** Returns the number of entries in the tree. */
	public int size() {
		return this.bsTree.size();
	}

	
	/** Returns whether the tree is empty. */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Returns an entry containing the given key. Returns null if no such entry
	 * exists.
	 */
	public Entry<K, V> find(K key) throws InvalidKeyException {
		checkKey(key); // may throw an InvalidKeyException
		Entry< K, V> entry = new DictEntry<K, V>(key, null, null);
		Position<Entry<K,V>> pos = this.bsTree.find(entry);
		if (pos != null)
			return pos.element();
		else
			return null;
	}

	/**
	 * Returns an iterable collection of all the entries containing the given
	 * key.
	 */
	public Iterable<Entry<K, V>> findAll(K key) throws InvalidKeyException {
		checkKey(key); // may throw an InvalidKeyException
		Entry<K, V> entry = new DictEntry<K, V>(key, null, null);

		Iterable<Position<Entry<K,V>>> posList = this.bsTree.findAll(entry);
		List<Entry<K,V>> entryList = new ArrayList<Entry<K,V>>();
		
		for (Position<Entry<K,V>> pos: posList){
			entryList.add(pos.element());
		}

		return entryList;
	}

	/** Inserts an entry into the tree and returns the newly created entry. */
	public Entry<K, V> insert(K k, V v) throws InvalidKeyException {
		checkKey(k); // may throw an InvalidKeyException
		DictEntry<K, V> entry = new DictEntry<K, V>(k, v, null, keyComparator);
		Position<Entry<K, V>> pos= this.bsTree.insert(entry);
		entry.setPosition(pos);		
		return entry;
	}

	/** Removes and returns a given entry. */
	public Entry<K, V> remove(Entry<K, V> ent) throws InvalidEntryException {
		checkEntry(ent); // may throw an InvalidEntryException
		DictEntry<K, V> entry = (DictEntry<K, V>) ent;
		
		Position <Entry<K,V>> p = entry.position();
		
		return this.bsTree.remove(p);
	}

	/** Returns an iterator containing all entries in the tree. */
	public Iterable<Entry<K, V>> entries() {
		return this.bsTree.values();
	}
	
	/**Find range in ordered diccionaries. */ 
	public Iterable<Entry<K, V>> findRange (K minkey, K maxkey) 
	throws InvalidKeyException{
		DictEntry<K, V> minkEntry = new DictEntry<K, V>(minkey, null, null, keyComparator);
		DictEntry<K, V> maxkEntry = new DictEntry<K, V>(maxkey, null, null, keyComparator);
		Iterable<Position<Entry<K,V>>> keys=this.bsTree.findRange(minkEntry, maxkEntry);
		List<Entry<K,V>> lista=new ArrayList<Entry<K,V>>();
		for(Position<Entry<K,V>> elem:keys)
			lista.add(this.find(elem.element().getKey()));
		return lista;
	}
	//no se si está bien
	public Iterable<Entry<K,V>> autoComplete(String prefix){
			Iterable<Position<Entry<K,V>>> lista=this.bsTree.autoComplete(prefix);
			List<Entry<K,V>> listaEntry = new ArrayList<Entry<K,V>>();
			for(Position<Entry<K,V>> elem:lista){
				listaEntry.add(this.find(elem.element().getKey()));
			}
		return listaEntry;
	}


}

