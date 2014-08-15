import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExtractorC extends AbstractExtractor {

	protected Set<String> reservedWords;

	public ExtractorC(File program) throws IOException {
		super(program);
		this.prepareReservedWords();
	}

	protected void prepareReservedWords() {
		this.reservedWords = new HashSet<String>();
		for (String s : ReservedC.reservedWords) {
			this.reservedWords.add(s);
		}
	}

	@Override
	void readUntilNextToken(StringBuffer source, StringBuffer sink) {
		this.readUntil(source, sink, tokenDelimiter);
	}

	@Override
	boolean matchesLiteral(StringBuffer source) {
		return source.charAt(0) == '"' || source.charAt(0) == '\''
				|| source.toString().matches("[\\d]+[\\w\\W]*")
				|| source.toString().matches("[.][\\d]+[\\w\\W]*");
	}

	@Override
	String readNextLiteral(StringBuffer source) {
		StringBuffer sink = new StringBuffer();
		if (source.charAt(0) == '"') {
			// strings
			this.extractChar(source, sink); // get opening quote
			char prev = '\0';
			char next;
			while (source.length() > 0) {
				next = source.charAt(0);
				this.extractChar(source, sink);
				if (prev != '\\' && next == '"') {
					break;
				}
				prev = next;
			}
		} else if (source.charAt(0) == '\'') {
			// characters
			if (source.charAt(1) == '\\') {
				this.extractMultipleChars(source, sink, 4);
			} else {
				this.extractMultipleChars(source, sink, 3);
			}
		} else {
			// numbers
			this.readBefore(source, sink, "\\D");
			if (source.charAt(0) == 'l' || source.charAt(0) == 'L') {
				this.extractChar(source, sink);
			} else if (source.charAt(0) == '.') {
				// is a floating point number
				this.extractChar(source, sink);
				this.readBefore(source, sink, "\\D");
				if (source.charAt(0) == 'f' || source.charAt(0) == 'F') {
					this.extractChar(source, sink);
				}
			}
		}
		return sink.toString();
	}

	@Override
	boolean matchesComment(StringBuffer source) {
		return source.length() >= 2
				&& (source.substring(0, 2).equals("//") || source.substring(0,
						2).equals("/*"));
	}

	@Override
	String readNextComment(StringBuffer source) {
		StringBuffer sink = new StringBuffer();
		if (source.substring(0, 2).equals("//")) {
			this.readUntil(source, sink, "\n");
		} else {
			int endIndex = source.toString().indexOf("*/") + 2;
			this.extractMultipleChars(source, sink, endIndex);
		}
		return sink.toString();
	}

	@Override
	boolean isPrototype(StringBuffer source) {
		String s = source.toString();
		if (s.matches(".*\\{[\\w\\W]*") || s.matches(".*\\n\\{[\\w\\W]*")) {
			return true;
		}
		if (s.matches("for[\\w\\W]*") || s.matches("while[\\w\\W]*")
				|| s.matches("do [\\w\\W]*") || s.matches("struct[\\w\\W]*")
				|| s.matches("if[\\w\\W]*") || s.matches("else[\\w\\W]*")
				|| s.matches("switch[\\w\\W]*")) {
			return true; // notice the space after the "do" regex (avoids
							// matching "double"
		}
		if (s.matches("static[\\w\\W]*") || s.matches("extern[\\w\\W]*")
				|| s.matches("unsigned[\\w\\W]*")
				|| s.matches("signed[\\w\\W]*") || s.matches("char[\\w\\W]*")
				|| s.matches("short[\\w\\W]*") || s.matches("int[\\w\\W]*")
				|| s.matches("long[\\w\\W]*") || s.matches("float[\\w\\W]*")
				|| s.matches("double[\\w\\W]*") || s.matches("enum[\\w\\W]*")
				|| s.matches("typedef[\\w\\W]*")
				|| s.matches("register[\\w\\W]*")
				|| s.matches("union[\\w\\W]*") || s.matches("void[\\w\\W]*")) {
			int braceIndex = s.indexOf('{');
			int semicolonIndex = s.indexOf(';');
			if (braceIndex == -1) {
				return false;
			}
			if (semicolonIndex == -1) {
				return true;
			}
			return braceIndex < semicolonIndex;
		}
		return false;
	}

	@Override
	String extractPrototype(StringBuffer source) {
		StringBuffer sink = new StringBuffer();

		String s = source.toString();
		if (s.matches("for[\\w\\W]*") || s.matches("while[\\w\\W]*")
				|| s.matches("do [\\w\\W]*") || s.matches("struct[\\w\\W]*")
				|| s.matches("if[\\w\\W]*") || s.matches("else[\\w\\W]*")
				|| s.matches("switch[\\w\\W]*")) {
			int lineIndex = s.indexOf("\n");
			int braceIndex = s.indexOf("{");
			if (braceIndex == -1 || braceIndex < lineIndex
					|| s.substring(lineIndex, braceIndex).matches("[\\s]*")) {
				this.readBefore(source, sink, "\\n");
				return sink.toString();
			}
		}

		this.readUntil(source, sink, "\\{");
		return sink.substring(0, sink.length() - 1); // we don't want to
														// include
														// the '{'
	}

	@Override
	boolean isBlockEnd(StringBuffer source, StringBuffer sink) {
		if (source.charAt(0) == '}') {
			source.deleteCharAt(0); // get rid of the '}'
			if (source.length() > 0 && source.charAt(0) == ';') {
				source.deleteCharAt(0); // get rid of the ';' after the '}'
			} else if (source.length() > 0 && source.toString().matches("[\\s]*while")) {
				// in case of a do-while
				int semicolonIndex = source.indexOf(";");
				this.extractMultipleChars(source, sink, semicolonIndex + 1);
			}
			return true;
		}
		return false;
	}

	@Override
	List<String> breakIntoStmts(StringBuffer source) {
		List<String> stmts = new LinkedList<String>();
		List<String> fragments = Arrays.asList(source.toString()
				.split("[\\n;]"));
		Iterator<String> iter = fragments.iterator();
		while (iter.hasNext()) {
			String s = iter.next();
			if (s.matches("[\\s]*")) {
				continue;
			}
			stmts.add(s.trim());
		}
		return stmts;
	}

	@Override
	public boolean newLineBrace() {
		int onLineBrace = 0;
		int newLineBrace = 0;
		for (String s : this.code.split("\\{")) {
			if (s.length() == 0) {
				continue;
			}
			if (s.charAt(s.length() - 1) == '\n') {
				newLineBrace++;
			} else {
				onLineBrace++;
			}
		}
		return newLineBrace >= onLineBrace;
	}

	@Override
	public int numFunctions() {
		int count = 0;
		for (String s : this.blocks.getPrototypesRecursively()) {
			if (isFunction(s)) { // need to double check
				count++;
			}
		}
		return count;
	}

	protected static boolean isFunction(String s) {
		return !(s.matches("for[\\w\\W]*") || s.matches("while[\\w\\W]*")
				|| s.matches("do [\\w\\W]*") || s.matches("struct[\\w\\W]*")
				|| s.matches("if[\\w\\W]*") || s.matches("else[\\w\\W]*")
				|| s.matches("switch[\\w\\W]*") || s.matches("enum[\\w\\W]*")
				|| s.matches("typedef[\\w\\W]*")
				|| s.matches("register[\\w\\W]*") || s
					.matches("union[\\w\\W]*"));
	}

	@Override
	public int numTokens() {
		return this.code.split(tokenDelimiter).length;
	} // need to double check

	@Override
	public Map<String, Integer> getReservedWords() {
		MultiSet<String> reservedWords = new MultiSet<>();
		String[] tokens = this.code.split(tokenDelimiter);
		for (String token : tokens) {
			if (this.reservedWords.contains(token)) {
				reservedWords.add(token);
			}
		}
		return reservedWords;
	}

	@Override
	public Map<String, Integer> getUserDefinedWords() {
		MultiSet<String> reservedWords = new MultiSet<>();
		String[] tokens = this.code.split(tokenDelimiter);
		for (String token : tokens) {
			if (!this.reservedWords.contains(token)) {
				reservedWords.add(token);
			}
		}
		return reservedWords;
	}

	@Override
	public Map<Loops, Integer> getLoops() {
		MultiSet<Loops> myLoops = new MultiSet<>();
		myLoops.put(Loops.doWhileLoop, 0);
		myLoops.put(Loops.forLoop, 0);
		myLoops.put(Loops.whileLoop, 0);
		for (String s : this.blocks.getPrototypesRecursively()) {
			if (s.matches("do [\\w\\W]*")) {
				myLoops.add(Loops.doWhileLoop);
			} else if (s.matches("for [\\w\\W]*")) {
				myLoops.add(Loops.forLoop);
			} else if (s.matches("while [\\w\\W]*")) {
				myLoops.add(Loops.whileLoop);
			}
		}
		return myLoops;
	}

	@Override
	public Map<ControlStatement, Integer> getControlStructures() {
		MultiSet<ControlStatement> myControls = new MultiSet<>();
		myControls.put(ControlStatement.elifStatement, 0);
		myControls.put(ControlStatement.elseStatement, 0);
		myControls.put(ControlStatement.ifStatement, 0);
		myControls.put(ControlStatement.switchStatement, 0);
		myControls.put(ControlStatement.ternaryOperator, 0);
		for (String s : this.blocks.getPrototypesRecursively()) {
			if (s.matches("else if[\\w\\W]*")) {
				myControls.add(ControlStatement.elifStatement);
			} else if (s.matches("else [\\w\\W]*")) {
				myControls.add(ControlStatement.elseStatement);
			} else if (s.matches("if [\\w\\W]*")) {
				myControls.add(ControlStatement.ifStatement);
			} else if (s.matches("switch [\\w\\W]*")) {
				myControls.add(ControlStatement.switchStatement);
			}
		}
		// get ternaries by splitting via "?"
		myControls.put(ControlStatement.ternaryOperator, this.code.split("\\?").length - 1);
		return myControls;
	}

	@Override
	public Map<Integer, Integer> numFunctionParams() {
		MultiSet<Integer> params = new MultiSet<>();
		for (String s : this.blocks.getPrototypesRecursively()) {
			if (!isFunction(s)) {
				continue;
			}
			String[] s2 = s.split(",");
			params.add(s2.length - 1);
		}
		return params;
	}

	@Override
	public double avgParamsPerFunction() {
		Map<Integer, Integer> params = this.numFunctionParams();
		Set<Integer> keys = params.keySet();
		int totalParams = 0;
		for (Integer key : keys) {
			totalParams += key * params.get(key);
		}
		return totalParams / (double) this.numFunctions();
	}

	@Override
	public Map<Integer, Integer> getVariableLocality() {
		// check var in nary tree with its tree depth
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	@Override
	public int numMacros() {
		int count = 0;
		for (String s : this.code.split("\\n")) {
			if (s.matches("#.*")) {
				count++;
			}
		}
		return count;
	}

}