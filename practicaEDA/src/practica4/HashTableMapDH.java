package practica4;

import practica4.HashTableMap.HashEntryIndex;
import practica4.HashTableMap.OperationType;

public class HashTableMapDH<K,V> extends HashTableMap<K,V> {
	
	/** Hash function applying MAD method to default hash code. */
	protected int hashValue(K key, int i) {
		return (int) ((Math.abs(key.hashCode() * scale + shift) % prime + this.hashValue(key)*i) % capacity );
	}
	
	/**
	 * Collision solved with double hash probe - returns index of found key or -(a +
	 * 1), where a is * the index of the first empty or available slot found.
	 * The index value is negative because it is needed to distinguish when the
	 * key is in the table (positive) and when is not (negative)
	 */
	protected HashEntryIndex findEntry(K key) throws InvalidKeyException {
		int avail = -1;
		checkKey(key);
		int n=0;
		int i = hashValue(key,n);
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
			n++;
			i = this.hashValue(key, n); // keep looking
		} while (i != j);
		return new HashEntryIndex(avail,OperationType.notFound); // first empty or available slot
	}
}
