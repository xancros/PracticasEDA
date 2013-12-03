package practica5;

import java.util.Comparator;

import practica2.Position;




/** Nested class for location-aware binary search tree entries */
public class DictEntry<K, V> implements Entry<K, V>, Comparable<Entry<K,V>> {
	protected K key;
	protected V value;
	protected Position<Entry<K, V>> pos;
	protected Comparator<K> keyComparator;

	DictEntry(K k, V v, Position<Entry<K, V>> p) {
		this.key = k;
		this.value = v;
		this.pos = p;
		this.keyComparator = null;
	}
	
	DictEntry(K k, V v, Position<Entry<K, V>> p, Comparator<K> keyComparator) {
		this(k,v,p);
		this.keyComparator = keyComparator;
	}

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	public void setPosition(Position<Entry<K, V>> pos) {
		this.pos = pos;
	}
	
	public Position<Entry<K, V>> position() {
		return pos;
	}

	protected int compareHash (K k){
		if (this.hashCode() == k.hashCode())
			return 0;
		else if (this.hashCode() > k.hashCode())
			return 1;
		else
			return -1;
	}
	
	@Override
	public int compareTo(Entry<K, V> o) {
		if (this.keyComparator !=null)
			return this.keyComparator.compare(key, o.getKey());
		else if (this.key instanceof Comparable<?>)
			return ((Comparable<K>)this.key).compareTo(o.getKey());
		else 
			return compareHash(o.getKey());
	}

}

