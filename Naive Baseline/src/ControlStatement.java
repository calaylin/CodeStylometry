public enum ControlStatement {

	ifStatement("if"), elifStatement("elif"), elseStatement("else"), switchStatement(
			"switch"), ternaryOperator("ternary");

	private final String name;

	private ControlStatement(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}