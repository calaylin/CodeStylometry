import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ExtractorC extends AbstractExtractor {

	public ExtractorC(File program) throws IOException {
		super(program);
	}

	@Override
	void readUntilNextToken(StringBuffer source, StringBuffer sink) {
		this.readUntil(source, sink, this.tokenDelimiter);
	}

	@Override
	boolean matchesLiteral(StringBuffer source) {
		return source.charAt(0) == '"' || source.charAt(0) == '\'' || source.toString().matches("[\\d]+[\\w\\W]*") || source.toString().matches("[.][\\d]+[\\w\\W]*");
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
		return source.length() >= 2 && (source.substring(0, 2).equals("//") || source.substring(0, 2).equals("/*"));
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
		if (s.matches("for[\\w\\W]*") || s.matches("while[\\w\\W]*") || s.matches("do [\\w\\W]*") || s.matches("struct[\\w\\W]*") || s.matches("typedef struct[\\w\\W]*") || s.matches("if[\\w\\W]*") || s.matches("else[\\w\\W]*")) {
			return true; // notice the space after the "do" regex (avoids matching "double"
		}
		if (s.matches("static[\\w\\W]*") || s.matches("extern[\\w\\W]*") || s.matches("unsigned[\\w\\W]*") || s.matches("signed[\\w\\W]*") || s.matches("char[\\w\\W]*") || s.matches("short[\\w\\W]*") || s.matches("int[\\w\\W]*") || s.matches("long[\\w\\W]*") || s.matches("float[\\w\\W]*") || s.matches("double[\\w\\W]*")) {
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
		this.readUntil(source, sink, "\\{");
		return sink.substring(0, sink.length() - 1); // we don't want to include the '{'
	}

	@Override
	boolean isBlockEnd(StringBuffer source, StringBuffer sink) {
		if (source.charAt(0) == '}') {
			source.deleteCharAt(0); // get rid of the '}'
			if (source.charAt(0) == ';') {
				source.deleteCharAt(0); // get rid of the ';' after the '}'
			} else if (source.toString().matches("[\\s]*while")){ // in case of a do-while
				int semicolonIndex = source.indexOf(";");
				this.extractMultipleChars(source, sink, semicolonIndex + 1);
			}
			return true;
		}
		return false;
	}

	@Override
	List<String> breakIntoStmts(StringBuffer source) {
		return Arrays.asList(source.toString().split("[\\};]")); // TODO cover preprocessor directives
	}

}