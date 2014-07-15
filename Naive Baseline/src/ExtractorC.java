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
		return source.charAt(0) == '"' || source.charAt(0) == '\'' || source.toString().matches("[\\d]+.*") || source.toString().matches("[.][\\d]+");
	}

	@Override
	String readNextLiteral(StringBuffer source) {
		StringBuffer sink = new StringBuffer();
		if (source.charAt(0) == '"') {
			// strings
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
				this.extractMultipleChars(source, sink, 3);
			} else {
				this.extractMultipleChars(source, sink, 2);
			}
		} else { 
			// numbers
			this.readUntil(source, sink, "\\D");
			if (source.charAt(0) == 'l' || source.charAt(0) == 'L') {
				this.extractChar(source, sink);
			} else if (source.charAt(0) == '.') {
				// is a floating point number
				this.extractChar(source, sink);
				this.readUntil(source, sink, "\\D");
				if (source.charAt(0) == 'f' || source.charAt(0) == 'F') {
					this.extractChar(source, sink);
				}
			}
		}
		return sink.toString();
	}

	@Override
	boolean matchesComment(StringBuffer source) {
		return source.substring(0, 2).equals("//") || source.substring(0, 2).equals("/*");
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
		// TODO Auto-generated method stub
		//tricky --> might need to do case by case
		// for while do
		// typedef
		// struct
		// void int other stuff
		return false;
	}

	@Override
	String extractPrototype(StringBuffer source) {
		StringBuffer sink = new StringBuffer();
		this.readUntil(source, sink, "{");
		return sink.substring(0, sink.length() - 1); // we don't want to include the '{'
	}

	@Override
	boolean isBlockEnd(StringBuffer source) {
		if (source.charAt(0) == '}') {
			source.deleteCharAt(0); // get rid of the '}'
			return true;
		}
		return false;
	}

	@Override
	List<String> breakIntoStmts(StringBuffer source) {
		return Arrays.asList(source.toString().split(this.tokenDelimiter));
	}

}