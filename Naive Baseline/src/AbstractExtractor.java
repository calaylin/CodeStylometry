import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Two big assumptions: the code is valid and no silly macros
 * 
 * @author Andrew Liu
 * 
 */
public abstract class AbstractExtractor implements FeatureSet {

	private File file;
	static String tokenDelimiter = "[*;\\{\\}\\[\\]()+=\\-&/|%!?:,<>~`\\s]";
	MultiSet<String> literals;
	List<String> commentList;
	CodeBlock<String> blocks;
	final String code; // source strippped of literals and comments
	int length = 0;
	int numWhiteSpaceChars = 0;
	List<String> lines;

	public AbstractExtractor(File program) throws IOException {
		setTokenDelimiter(); // set what separates a token

		/* reading in the program contents into a StringBuffer */
		this.file = program;
		BufferedReader reader = new BufferedReader(new FileReader(program));
		StringBuffer source = new StringBuffer();
		char nextChar;
		while (reader.ready()) { // TODO can extract features here
			this.length++;
			nextChar = (char) reader.read();
			String charStr = "" + nextChar;
			if (charStr.matches("\\s")) {
				numWhiteSpaceChars++;
			}
			source.append(nextChar);
		}
		reader.close();

		/*
		 * stripping out the String, character, integer, and floating point
		 * literals
		 */
		/* filtering out the comments as well */
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
		this.code = source.toString(); // setting the code without literals or
										// comments

		/* separating the code by blocks */
		this.blocks = new CodeBlock<String>(this.file.getName());
		CodeBlock<String> currentBlock = blocks;
		while (source.length() > 0) {
			if (isPrototype(source)) {
				// adding all statements into the previous block
				currentBlock.addStatements(breakIntoStmts(sink));
				sink = new StringBuffer();
				// creating a child block to use
				CodeBlock<String> temp = new CodeBlock<String>(
						extractPrototype(source));
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
		Scanner sc = new Scanner(this.file);
		this.lines = new LinkedList<String>();
		while (sc.hasNextLine()) {
			this.lines.add(sc.nextLine());
		}
		sc.close();
	}

	/**
	 * Implement this now or make a getter for the token delimiter. Also
	 * remember to read in the delimiter itself!
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

	static void setTokenDelimiter() {
		// override if you want
	}

	final void extractMultipleChars(StringBuffer source, StringBuffer sink,
			int num) {
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
		this.readBefore(source, sink, regex);
		this.extractChar(source, sink);
	}

	/**
	 * Same as readUntil except it doesn't eat the regex.
	 * 
	 * @param source
	 * @param sink
	 * @param regex
	 */
	final void readBefore(StringBuffer source, StringBuffer sink, String regex) {
		while (source.length() > 1 && !source.substring(0, 1).matches(regex)) {
			this.extractChar(source, sink);
		}
	}

	static String getTokenDelimiter() {
		return tokenDelimiter;
	}

	public File getFile() {
		return this.file;
	}

	@Override
	public int nestingDepth() {
		return this.blocks.getHeight();
	}

	@Override
	public int numComments() {
		return this.commentList.size();
	}

	@Override
	public List<String> getComments() {
		return new LinkedList<String>(this.commentList);
	}

	@Override
	public Map<String, Integer> getLiterals() {
		return new HashMap<String, Integer>(this.literals);
	}

	@Override
	public int length() {
		return this.length;
	}

	@Override
	public int numEmptyLines() {
		int count = 0;
		for (String line : this.lines) {
			if (line.matches("[\\s]*")) {
				count++;
			}
		}
		return count;
	}

	@Override
	public List<Integer> lineLengths() {
		List<Integer> lengths = new LinkedList<Integer>();
		for (String line : this.lines) {
			lengths.add(line.length());
		}
		return lengths;
	}

	@Override
	public double avgLineLength() {
		int sum = 0;
		int count = 0;
		Iterator<Integer> iter = this.lineLengths().iterator();
		while (iter.hasNext()) {
			sum += iter.next();
			count++;
		}
		return sum / (double) count;
	}

	@Override
	public double whiteSpaceRatio() {
		return this.length / (double) this.numWhiteSpaceChars;
	}

}