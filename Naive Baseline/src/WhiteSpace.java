
public enum WhiteSpace {
	space("' '"), tab("'\\t'"), newLine("'\\n'");
	
	private final String name;
	
	private WhiteSpace(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}