package practica4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HashTableMapSC<K, V> extends HashTableMap<K, V>{
	
	private LinkedList<HashEntry<K, V>>[] bucket;// bucket array
	
	public HashTableMapSC() {
		this(109345121, 1000); // reusing the constructor HashTableMap(int p, int cap)
	}
	/** Creates a hash table with prime factor 109345121 and given capacity. */
	public HashTableMapSC(int cap) {
		this(109345121, cap); 
	}

	/** Creates a hash table with the given prime factor and capacity. */
	public HashTableMapSC(int p, int cap) {
		this.n = 0;
		
		this.prime = p;
		this.capacity = cap;
		
		this.bucket = (LinkedList<HashEntry<K, V>>[]) new LinkedList[capacity]; // safe cast
		Random rand = new Random();
		this.scale = rand.nextInt(prime - 1) + 1;
		this.shift = rand.nextInt(prime);
	}
	
	
	/**
	 * Collision solved with linear probe - returns index of found key or -(a +
	 * 1), where a is * the index of the first empty or available slot found.
	 * The index value is negative because it is needed to distinguish when the
	 * key is in the table (positive) and when is not (negative)
	 */
	protected HashEntryIndex findEntry(K key) throws InvalidKeyException {
		checkKey(key);
		int i = hashValue(key);
		if (bucket[i] != null) { // Hay otras entradas con la misma clave
			return new HashEntryIndex(i,OperationType.found); // key found
		}
		return new HashEntryIndex(i,OperationType.notFound); // first empty or available slot
	}	
	
	/** Doubles the size of the hash table and rehashes all the entries. */
	protected void rehash() {
		capacity = 2 * capacity;
		LinkedList<HashEntry<K, V>>[] old = bucket;
		// new bucket is twice as big
		bucket = (LinkedList<HashEntry<K, V>>[]) new LinkedList[capacity];
		java.util.Random rand = new java.util.Random();
		// new hash scaling factor
		scale = rand.nextInt(prime - 1) + 1;
		// new hash shifting factor
		shift = rand.nextInt(prime);
		for (int i = 0; i < old.length; i++) {
			LinkedList<HashEntry<K, V>> e = old[i];
			if ((e != null)) { // a valid entry
				int j = -1;
				for(Entry<K, V> e2 : e){
					j = findEntry(e2.getKey()).index;
				}
				if (j != -1)
					bucket[j] = e;
			}
		}
	}
	
	public V get(K key) throws InvalidKeyException {
		HashEntryIndex i = findEntry(key); // helper method for finding a key
		if (i.operation == OperationType.notFound)
			return null; // there is no value for this key, so return null
		for(Entry<K, V> e : bucket[i.index]){
			return e.getValue(); //Devolvemos la primera ocurrencia, ya veremos si lo modeifico
		}
		return null; // return the found value in this case
	}
	
	
	/** Put a key-value pair in the map, replacing previous one if it exists. */
	public V put(K key, V value) throws InvalidKeyException {
		HashEntryIndex i = findEntry(key); // find the appropriate spot for this entry
		if (i.operation == OperationType.found){ // this key has a previous value
			if (n  >= capacity* 0.75) {
				rehash(); // rehash to keep the load factor <= 0.75
				i = findEntry(key); // find again the appropriate spot for this entry
			}
			bucket[i.index].add(new HashEntry<K, V>(key, value));
			n++;
			return null;
		}// set new value
		else if (n  >= capacity* 0.75) { //Nunca ha habido valores en esta clave
			rehash(); // rehash to keep the load factor <= 0.75
			i = findEntry(key); // find again the appropriate spot for this entry
		}
		bucket[i.index] = new LinkedList<HashEntry<K, V>>();
		bucket[i.index].add(new HashEntry<K, V>(key, value)); // convert to proper index
		n++;
		return null; // there was no previous value
	}
	
	public V remove(K key) throws InvalidKeyException {
		HashEntryIndex i = findEntry(key); // find this key first
		if (i.operation == OperationType.notFound)
			return null; // nothing to remove
		V toReturn = bucket[i.index].get(0).getValue();
		bucket[i.index] = null; // mark this slot as reactivated
		n--;
		return toReturn;
	}
	public Iterable<K> keySet() {
		List<K> keys = new ArrayList<K>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null)){
				for(Entry<K, V> e : bucket[i]){
					keys.add(e.getKey());
				}
			}
		return keys;
	}
	
	/** Returns an iterable object containing all of the entries. */
	public Iterable<Entry<K, V>> entrySet() {
		List<Entry<K, V>> entries = new ArrayList<Entry<K, V>>();
		for (int i = 0; i < capacity; i++)
			if (bucket[i] != null)
				for(Entry<K, V> e : bucket[i]){
					entries.add(e);
				}
		return entries;
	}

	/** Returns an iterable object containing all of the values. */
	public Iterable<V> values() {
		List<V> values = new ArrayList<V>();
		for (int i = 0; i < capacity; i++)
			if (bucket[i] != null)
				for(Entry<K, V> e : bucket[i]){
					values.add(e.getValue());
				}
		return values;
	}
}