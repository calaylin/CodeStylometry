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
 * MAKE SURE THAT AFTER THE LOOPS THERES NOTHING LEFT OVER IN THE BUFFERS
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
		reader.close();
		
		/* stripping out the String, character, integer, and floating point literals */
		/* filtering out the comments as well*/

		List<String> literalList = new LinkedList<>();
		List<String> commentList = new LinkedList<>();
		StringBuffer sink = new StringBuffer();
		while (source.length() > 0) {
			if (matchesLiteral(source)) {
				// read entire literal
				literalList.add(readNextLiteral(source));
			} else if (matchesComment(source)) {
				commentList.add(readNextComment(source));
			} else {
				// read until the next delimiter (including the delim)
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
		
		String s = "/* comment in literal */";
		/* String s = "literal in comment"; */
		
		source = sink;
		sink = new StringBuffer();
		
		CodeBlock<String> blocks = new CodeBlock<String>(this.program.getName());
		CodeBlock<String> currentBlock = blocks;
		
		while (source.length() > 0) {
			// if its the start of a prototype
			// then extract the prototype
			// then start a new block
			// then break the current buffer into statements and add them
			// otherwise if its the end of a block
			// then break the current buffer into statements and add them
			// then change the reference to the parent block
			// otherwise extractChar into the buffer
		}
		// if executed correctly currentBlock should be null at this point
		
	}
	
	// check if is prototype + extract prototype
	// check if start block (merge with prototype check?)
	// check if end block
	// break code into statements --> need to manage preprocessor directives
	// extract into current block
	
	/**
	 * Implement this now or make a getter for the token delimiter. Also remember to read in the delimiter itself!
	 * 
	 * @param source
	 * @param sink
	 */
	protected abstract void readUntilNextToken(StringBuffer source, StringBuffer sink);
	protected abstract boolean matchesLiteral(StringBuffer source);
	protected abstract String readNextLiteral(StringBuffer source);
	protected abstract boolean matchesComment(StringBuffer source);
	protected abstract String readNextComment(StringBuffer source);
	
	protected abstract boolean isPrototype(StringBuffer source);
	/**
	 * Don't forget to remove the opening delimiter of the next block
	 * 
	 * @param source
	 * @return
	 */
	protected abstract String extractPrototype(StringBuffer source);
	protected abstract boolean isBlockEnd(StringBuffer source);
	protected abstract List<String> breakIntoStmts(StringBuffer source);
	
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