public enum Loops {

	forLoop("for"), doWhileLoop("do"), whileLoop("while");

	private final String name;

	private Loops(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}