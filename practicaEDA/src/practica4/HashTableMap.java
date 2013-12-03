package practica4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



/** A hash table with linear probing and the MAD hash function */
/**
 * A hash table data structure that uses linear probing to handle collisions.
 * The hash function uses the built-in hashCode method and the
 * multiply-add-and-divide method. The load factor is alwayas kept less than or
 * equal to 0.5. When the load factor reaches 0.5, the entries are rehashed into
 * a new bucket array with twice the capacity.
 * 
 * @author  R. Cabido, A. Duarte, and J. Velez
 */
public class HashTableMap<K, V> implements Map<K, V> {
	
	enum OperationType {found,notFound};

	protected class HashEntryIndex {
		int index;
		OperationType operation;
		
		public HashEntryIndex(int index, OperationType operation) {
			this.index = index;
			this.operation = operation;
		}
	}
	
	protected final Entry<K, V> AVAILABLE;
	protected int n; // number of entries in the dictionary
	protected int prime, capacity; // prime factor and capacity of bucket array
	protected HashEntry<K, V>[] bucket;// bucket array
	protected long scale, shift; // the shift and scaling factors

	/** Creates a hash table with prime factor 109345121 and capacity 1000. */
	
	public HashTableMap() {
		this(109345121, 1000); // reusing the constructor HashTableMap(int p, int cap)
	}

	
	/**
	 * Creates a hash table with prime factor 109345121 and given capacity.
	 * @param cap
	 */
	public HashTableMap(int cap) {
		this(109345121, cap); // reusing the constructor HashTableMap(int p, int cap)
	}

	/** Creates a hash table with the given prime factor and capacity. */
	public HashTableMap(int p, int cap) {
		this.AVAILABLE = new HashEntry<K, V>(null, null);
		this.n = 0;
		
		this.prime = p;
		this.capacity = cap;
		
		this.bucket = (HashEntry<K, V>[]) new HashEntry[capacity]; // safe cast
		Random rand = new Random();
		this.scale = rand.nextInt(prime - 1) + 1;
		this.shift = rand.nextInt(prime);
	}

	/** Determines whether a key is valid. */
	protected void checkKey(K k) {
		//We cannot check the second test (i.e., k instanceof K) since we do not know the class K 
		if (k == null )
			throw new InvalidKeyException("Invalid key: null.");
	}

	/** Hash function applying MAD method to default hash code. */
	protected int hashValue(K key) {
		return (int) ((Math.abs(key.hashCode() * scale + shift) % prime) % capacity);
	}

	/** Returns the number of entries in the hash table. */
	public int size() {
		return n;
	}

	/** Returns whether or not the table is empty. */
	public boolean isEmpty() {
		return (n == 0);
	}

	/** Returns an iterable object containing all of the keys. */
	public Iterable<K> keySet() {
		List<K> keys = new ArrayList<K>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))
				keys.add(bucket[i].getKey());
		return keys;
	}

	/**
	 * Collision solved with linear probe - returns index of found key or -(a +
	 * 1), where a is * the index of the first empty or available slot found.
	 * The index value is negative because it is needed to distinguish when the
	 * key is in the table (positive) and when is not (negative)
	 */
	protected HashEntryIndex findEntry(K key) throws InvalidKeyException {
		int avail = -1;
		checkKey(key);
		int i = hashValue(key);
		final int j = i;
		do {
			Entry<K, V> e = bucket[i];
			if (e == null) {
				if (avail < 0)
					avail = i; // key is not in table
				break;
			}
			else if (key.equals(e.getKey())) // we have found our key
				return new HashEntryIndex(i,OperationType.found); // key found
			else if (e == AVAILABLE) { // bucket is deactivated
				if (avail < 0)
					avail = i; // remember that this slot is available
			}
			i = (i + 1) % capacity; // keep looking
		} while (i != j);
		return new HashEntryIndex(avail,OperationType.notFound); // first empty or available slot
	}

	/** Returns the value associated with a key. */
	public V get(K key) throws InvalidKeyException {
		HashEntryIndex i = findEntry(key); // helper method for finding a key
		if (i.operation == OperationType.notFound)
			return null; // there is no value for this key, so return null
		return bucket[i.index].getValue(); // return the found value in this case
	}

	/** Put a key-value pair in the map, replacing previous one if it exists. */
	public V put(K key, V value) throws InvalidKeyException {
		HashEntryIndex i = findEntry(key); // find the appropriate spot for this entry
		if (i.operation == OperationType.found) // this key has a previous value
			return bucket[i.index].setValue(value); // set new value
		else if (n >= capacity / 2) {
			rehash(); // rehash to keep the load factor <= 0.5
			i = findEntry(key); // find again the appropriate spot for this entry
		}
		bucket[i.index] = new HashEntry<K, V>(key, value); // convert to proper index
		n++;
		return null; // there was no previous value
	}

	/** Doubles the size of the hash table and rehashes all the entries. */
	protected void rehash() {
		capacity = 2 * capacity;
		HashEntry<K, V>[] old = bucket;
		// new bucket is twice as big
		bucket = (HashEntry<K, V>[]) new HashEntry[capacity];
		java.util.Random rand = new java.util.Random();
		// new hash scaling factor
		scale = rand.nextInt(prime - 1) + 1;
		// new hash shifting factor
		shift = rand.nextInt(prime);
		for (int i = 0; i < old.length; i++) {
			HashEntry<K, V> e = old[i];
			if ((e != null) && (e != AVAILABLE)) { // a valid entry
				int j = findEntry(e.getKey()).index;
				bucket[j] = e;
			}
		}
	}

	/** Removes the key-value pair with a specified key. */
	public V remove(K key) throws InvalidKeyException {
		HashEntryIndex i = findEntry(key); // find this key first
		if (i.operation == OperationType.notFound)
			return null; // nothing to remove
		V toReturn = bucket[i.index].getValue();
		bucket[i.index] = (HashEntry<K, V>) AVAILABLE; // mark this slot as reactivated
		n--;
		return toReturn;
	}

	/** Returns an iterable object containing all of the entries. */
	public Iterable<Entry<K, V>> entrySet() {
		List<Entry<K, V>> entries = new ArrayList<Entry<K, V>>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))
				entries.add(bucket[i]);
		return entries;
	}

	/** Returns an iterable object containing all of the values. */
	public Iterable<V> values() {
		List<V> values = new ArrayList<V>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE))
				values.add(bucket[i].getValue());
		return values;
	}
}
