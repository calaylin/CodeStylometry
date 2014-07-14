import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 * when reading literals:
 * 
 * string/char are straightforward; be careful of numbers
 * 
 * 3.2, 40294724820L, 3.4f, asdf2134
 * 
 * comments are straightforward
 * 
 * TODO make nary tree data structure
 */

public abstract class AbstractExtractor {

	private File program;
	private String tokenDelimiter;
	
	public AbstractExtractor(File program) throws IOException {
		this.setTokenDelimiter();
		
		/* reading in the program contents into a StringBuffer */
		this.program = program;
		BufferedReader reader = new BufferedReader(new FileReader(program));
		StringBuffer source = new StringBuffer();
		char nextChar;
		while (reader.ready()) {
			nextChar = (char) reader.read();
			source.append(nextChar);
		}
		
		/* stripping out the String, character, integer, and floating point literals */
		List<String> literalList = new LinkedList<>();
		StringBuffer sink = new StringBuffer();
		boolean inLiteral = false;
		while (source.length() > 0) {
			if (matchesLiteral(source)) {
				// read entire literal
				literalList.add(readNextLiteral(source));
			} else {
				// read until the next delimiter (including the delim)
				readUntilNextToken(source, sink);
			}
		}
		
		source = sink;
		sink = new StringBuffer();
		
		/* filter out the comments */
		List<String> comments = new LinkedList<>();
		while (source.length() > 0) {
			if (matchesComment(source)) {
				readNextComment(source);
			} else {
				readUntilNextToken(source, sink);
			}
		}
		/*
		 * straightforward
		 */
		/* separate by block (don't forget prototypes) */
		/*
		 * the blocks are straightforward; the prototypes are not (TODO)
		 */
		/* separate by statement (manage preprocessor directives as well) */
		/*
		 * the normal statements are straightforward (semicolon), the directives are not
		 */
		
		/*
		 * BE SURE TO MAKE ABSTRACT THE METHODS THAT AREN'T CONCRETE FOR ALL LANGUAGES
		 */
	}
	
	protected abstract void readUntilNextToken(StringBuffer source, StringBuffer sink);
	protected abstract boolean matchesLiteral(StringBuffer source);
	protected abstract String readNextLiteral(StringBuffer source);
	protected abstract boolean matchesComment(StringBuffer source);
	protected abstract String readNextComment(StringBuffer source);
	
	protected void setTokenDelimiter() {
		this.setTokenDelimiter("[*;\\{\\}\\[\\]()+=\\-&/|%!?:,<>~`\\s]");
	}
	
	protected final void setTokenDelimiter(String s) {
		this.tokenDelimiter = s;
	}
	
	protected final void extractMultipleChars(StringBuffer source, StringBuffer sink, int num) {
		for (int i = 0; i < num; i++) {
			extractChar(source, sink);
		}
	}
	
	protected final void extractChar(StringBuffer source, StringBuffer sink) {
		sink.append(source.charAt(0));
		source.deleteCharAt(0);
	}
}