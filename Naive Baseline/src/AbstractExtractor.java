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
 * watch out for preprocessor directives when processing statements
 * 
 * MAKE SURE THAT AFTER THE LOOPS THERES NOTHING LEFT OVER IN THE BUFFERS
 */

/**
 * Two big assumptions: the code is valid and no silly macros
 * 
 * @author andrew.liu
 *
 */
public abstract class AbstractExtractor {

	private File file;
	protected String tokenDelimiter;
	private MultiSet<String> literals;
	private List<String> commentList;
	CodeBlock<String> blocks; // visibility level?
	
	public AbstractExtractor(File program) throws IOException {
		this.setTokenDelimiter(); // set what separates a token
		
		/* reading in the program contents into a StringBuffer */
		this.file = program;
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
		this.literals = new MultiSet<String>();
		this.commentList = new LinkedList<>();
		StringBuffer sink = new StringBuffer();
		
		while (source.length() > 0) {
			if (matchesLiteral(source)) {
				// read in the literal
				this.literals.add(readNextLiteral(source));
			} else if (matchesComment(source)) {
				// read in the comment
				this.commentList.add(readNextComment(source));
			} else {
				// read in the code until after the next delimiter
				readUntilNextToken(source, sink);
			}
		}
		
		/* putting the leftover code back into the source */
		source = sink;
		sink = new StringBuffer();
		
		/* separating the code by blocks */
		this.blocks = new CodeBlock<String>(this.file.getName());
		CodeBlock<String> currentBlock = blocks;
		
		while (source.length() > 0) {
			if (isPrototype(source)) {
				// adding all statements into the previous block
				currentBlock.addStatements(breakIntoStmts(sink));
				sink = new StringBuffer();
				// creating a child block to use
				CodeBlock<String> temp = new CodeBlock<String>(extractPrototype(source));
				currentBlock.addChild(temp);
				currentBlock = temp;
			} else if (isBlockEnd(source, sink)) {
				// adding all statements into the previous block
				currentBlock.addStatements(breakIntoStmts(sink));
				sink = new StringBuffer();
				// using the parent block
				currentBlock = currentBlock.getParent();
			} else {
				readUntilNextToken(source, sink);
			}
		}
		// if executed correctly currentBlock should be null at this point
		//TODO fill out other stuff
	}
	
	/**
	 * Implement this now or make a getter for the token delimiter. Also remember to read in the delimiter itself!
	 * 
	 * @param source
	 * @param sink
	 */
	abstract void readUntilNextToken(StringBuffer source, StringBuffer sink);
	abstract boolean matchesLiteral(StringBuffer source);
	abstract String readNextLiteral(StringBuffer source);
	abstract boolean matchesComment(StringBuffer source);
	abstract String readNextComment(StringBuffer source);
	
	abstract boolean isPrototype(StringBuffer source);
	/**
	 * Don't forget to remove the opening delimiter of the next block
	 * 
	 * @param source
	 * @return
	 */
	abstract String extractPrototype(StringBuffer source);
	/**
	 * Will put the "while" part into sink if it detects a do-while
	 * 
	 * @param source
	 * @param sink
	 * @return
	 */
	abstract boolean isBlockEnd(StringBuffer source, StringBuffer sink);
	/**
	 * Does NOT empty buffer when done.
	 * 
	 * @param source
	 * @return
	 */
	abstract List<String> breakIntoStmts(StringBuffer source);
	
	void setTokenDelimiter() {
		this.tokenDelimiter = "[*;\\{\\}\\[\\]()+=\\-&/|%!?:,<>~`\\s]";
	}
	
	final void extractMultipleChars(StringBuffer source, StringBuffer sink, int num) {
		for (int i = 0; i < num; i++) {
			extractChar(source, sink);
		}
	}
	
	final void extractChar(StringBuffer source, StringBuffer sink) {
		sink.append(source.charAt(0));
		source.deleteCharAt(0);
	}
	
	
	/**
	 * Remember this eats up the regex char!
	 * 
	 * @param source
	 * @param sink
	 * @param regex
	 */
	final void readUntil(StringBuffer source, StringBuffer sink, String regex) {
		while (!source.substring(0, 1).matches(regex)) {
			this.extractChar(source, sink);
		}
		this.extractChar(source, sink);
	}
	
	String getTokenDelimiter() {
		return this.tokenDelimiter;
	}
	
	public File getFile() {
		return this.file;
	}
	
	// TODO public getters and other features
}