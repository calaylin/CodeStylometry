import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class ExtractorCPP extends ExtractorC {

	public ExtractorCPP(File program) throws IOException {
		super(program);
	}

	@Override
	protected void prepareReservedWords() {
		this.reservedWords = new HashSet<String>();
		for (String s : ReservedCPP.reservedWords) {
			this.reservedWords.add(s);
		}
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
				|| s.matches("union[\\w\\W]*") || s.matches("void[\\w\\W]*")
				|| s.matches("char16_t[\\w\\W]*")
				|| s.matches("char32_t[\\w\\W]*")
				|| s.matches("wchar_t[\\w\\W]*") || s.matches("bool[\\w\\W]*")) {
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

}