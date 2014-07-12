import java.util.HashMap;
import java.util.Set;

public class MultiSet extends HashMap<String, Integer> implements Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MultiSet() {
		
	}
	
	public MultiSet(MultiSet copy) {
		Set<String> keys = copy.keySet();
		for (String s : keys) {
			this.put(s, copy.get(s));
		}
	}
	
	public void add(String key) {
		if (!this.containsKey(key)) {
			this.put(key, 1);
		} else {
			this.put(key, this.get(key) + 1);
		}
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		for (java.util.Map.Entry<String, Integer> e : this.entrySet()) {
			s.append(e.toString() + '\n');
		}
		return s.toString();
	}
}