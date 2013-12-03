package practica4;

import java.util.ArrayList;
import java.util.List;

public class HashTableMapSC<K,V> extends HashTableMap<K,V> {
	protected ArrayList<HashEntry<K, V>>[] bucket;// bucket array
	
	/** Returns an iterable object containing all of the keys. */
	public Iterable<K> keySet() {
		List<K> keys = new ArrayList<K>();
		for (int i = 0; i < capacity; i++)
			if ((bucket[i] != null) && (bucket[i] != AVAILABLE)){//TODO
				for(HashEntry<K,V> h : bucket[i]){//------------------------------------------------------
					keys.add((K) h.getKey());//modificacion recorrer cada lista del bucket
				}
			}
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
		do {//-------------------------------------------------------------------------//TODO
			//try{
			if (bucket[i]==null){
				bucket[i]= new ArrayList<HashEntry<K,V>>();
				//bucket[i].add(null);
			}
			if(bucket[i].size()==0){
				if (avail < 0)
					avail = i; // key is not in table
				break;
			}
				for(HashEntry<K,V> e : bucket[i]){//se recorre cada lista del bucket
			
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
				}
			//}catch(Exception e){
			//}
		} while (i != j);
		return new HashEntryIndex(avail,OperationType.notFound); // first empty or available slot
	}


}
