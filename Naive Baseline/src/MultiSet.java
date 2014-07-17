
import java.util.HashMap;
import java.util.Set;

/**
 * A data structure emulating a set that counts the number of repeated elements.
 * 
 * @author Andrew Liu
 *
 *@param <T> The type of element the MultiSet holds.
 */
public class MultiSet<T> extends HashMap<T, Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public MultiSet() {
	}
	
	/**
	 * Copy constructor.
	 * 
	 * @param copy MultiSet to copy.
	 */
	public MultiSet(MultiSet<T> copy) {
		Set<T> keys = copy.keySet();
		for (T s : keys) {
			this.put(s, (Integer) copy.get(s));
		}
	}
	
	/**
	 * Adds an element to the MultiSet, or increments its count by one if the element already exists.
	 * 
	 * @param key The element to add.
	 */
	public void add(T key) {
		if (!this.containsKey(key)) {
			this.put(key, 1);
		} else {
			this.put(key, this.get(key) + 1);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.AbstractMap#toString()
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (java.util.Map.Entry<T, Integer> e : this.entrySet()) {
			s.append(e.toString() + '\n');
		}
		return s.toString();
	}
}
