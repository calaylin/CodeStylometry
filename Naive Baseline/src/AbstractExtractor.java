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
 * future issues
 * 
 * generics
 * "." --> methods
 */

/*
 * # chars
 * # words
 * 
 * most/least common words
 * 
 * mean/stddev of numbers
 * 
 * # uniques
 * cardinality of all sets
 * 
 */

public abstract class AbstractExtractor {

	/* statistical private variables */
	private int numChars;
	private int numComments;
	private int numLiterals;
	private int numTokens;// change to numTokens?????
	// private int numUniqueWords; just a getter should suffice (return the sum)
	private int numReservedWords;
	private int numUniqueReservedWords;
	private int numNonReservedWords;
	private int numUniqueNonReservedWords;
	private int numAnnotations;
	private int numUniqueAnnotations;
		
	private File javaFile;

	final String regex = "[*;\\{\\}\\[\\]()+=\\-&/|%!?:,.<>\\s]+"; // reconsider
																// the '.'
	// it may kill off methods and stuff
	// regexp digits might be flawed --> ex. int var1able = 5; --> done!

	// protected MultiSet literals;
	protected MultiSet keywords;
	protected MultiSet nonkeywords; // these will be used later (hopefully)

	protected MultiSet annotations = new MultiSet();
	protected MultiSet allWords = new MultiSet();

	protected List<String> normalCode = new LinkedList<String>();
	protected List<String> literalCode = new LinkedList<String>();
	protected List<String> commentCode = new LinkedList<String>();

	protected List<String> directives;

	// 4 states : none, string, multi-comment, single-comment --> 2 booleans
	private boolean state1;
	private boolean state2;

	private boolean ignore = true; // ignore null character

	private StringBuffer normalBuffer;
	private StringBuffer literalBuffer;
	private StringBuffer commentBuffer;
	private StringBuffer normalBuffer2;
	
	private char literalDelim = '"';

	public AbstractExtractor(File javaFile) throws IOException {
		this.javaFile = javaFile;
		BufferedReader reader = startReader();

		// literals = new MultiSet(); I'll deprecate/remove this
		keywords = new MultiSet();
		nonkeywords = new MultiSet();

		setNormalCode(); // make both booleans false

		normalBuffer = new StringBuffer();
		literalBuffer = new StringBuffer();
		commentBuffer = new StringBuffer();

		normalBuffer2 = new StringBuffer();

		char prev = '\0';
		char next;

		numTokens = 0;
		boolean whiteSpace = false;
		numChars = 0;
				
		while (reader.ready()) {
			numChars++;
			next = (char) reader.read();
			byte[] b = { (byte) next };
			String check = new String(b);
			if ((whiteSpace && !check.matches("\\s"))
					|| (!whiteSpace && check.matches("\\s"))) {
				numTokens++;
				whiteSpace = !whiteSpace;
			}
			// System.out.print(next);

			if (isInNormalCode()) {
				// check to start other three states
				if (startLiteral(prev, next)) {
					fillBuffer(prev);
					fillList();
					setLiteral();
				} else if (startSingleComment(prev, next)) {
					fillList();
					setSingleComment();
					fillBuffer(prev);
				} else if (startMultiComment(prev, next)) {
					fillList();
					setMultiComment();
					fillBuffer(prev);
				} else {
					fillBuffer(prev);
				}
			} else if (isInLiteral()) {
				// check to end this state
				if (endLiteral(prev, next) /*&& literalFlag*/) {
					fillBuffer(prev);
					fillBuffer(next);
					fillList();
					setNormalCode();
					ignore = true; // still needed
				} else {
					fillBuffer(prev);
				}
			} else if (isInSingleComment()) {
				// check to end this state
				if (endSingleComment(next)) {
					fillBuffer(prev);
					fillBuffer(next);
					fillList();
					setNormalCode();
					ignore = true;
				} else {
					fillBuffer(prev);
				}
			} else if (isInMultiComment()) {
				// check to end this state
				if (endMultiComment(prev, next)) {
					fillBuffer(prev);
					fillBuffer(next);
					fillList();
					setNormalCode();
					ignore = true;
				} else {
					fillBuffer(prev);
				}
			}
			if (prev == '\\' && next == '\\') {
				fillBuffer(next);
				next = '\0';
				ignore = true;
			}
			prev = next;
			
		}
		fillList(); // need to do this for the last chunk

		String[] packageImport = normalBuffer2.toString().split(";+");
		int numChars = 0;
		directives = new LinkedList<String>();
		for (String s : packageImport) {
			int x = s.length();
			// System.out.println(x);
			// System.out.println(":D "+ s + " :D");
			s = s.trim();
			if (s.matches("package .*") || s.matches("import .*")) {
				directives.add(s);
				numChars += (x + 1); // account for the semicolon
			} else {
				break;
			}
		}
		// System.out.println(numChars);
		normalBuffer2 = normalBuffer2.delete(0, numChars);
		// System.out.println(normalBuffer2);
		String[] codeSrc = normalBuffer2.toString().split(regex);
		// for (String s : codeSrc) {
		// System.out.println(s);
		// }

		List<String> srcWords = new LinkedList<String>(Arrays.asList(codeSrc));
		// Iterator<String> srcIter = srcWords.iterator();
		//
		// while (srcIter.hasNext()) {
		// String s = srcIter.next();
		// if (s.matches("@.*")) {
		// srcIter.remove();
		// annotations.add(s);
		// }
		// }

		// make all words
		makeMultiSet(new LinkedList<String>(srcWords), allWords);
		List<String> directives2 = bla();
//		makeMultiSet(directives2, allWords); // TODO this may be noise
		makeMultiSet(new LinkedList<String>(literalCode), allWords);
		makeMultiSet(new LinkedList<String>(commentCode), allWords);

		// make annotations
		// srcWords = new LinkedList<String>(Arrays.asList(codeSrc)); // need to
		// renew this list
		// // since the allWords set emptied srcWords
		makeMultiSet(srcWords, annotations, "@.*");
		
		// remove digits --> add to literal list
		Iterator<String> iter = srcWords.iterator();
		while (iter.hasNext()) {
			String s = iter.next();
			if (s.matches("\\d+")) {
				literalCode.add(s);
				iter.remove();
			}
		}
		
		int var1able = 5;
		// just a test...

		// make keywords
		// user srcWords
		makeMultiSet(srcWords, keywords, makeKeywordRegex());

		// make nonkeywords (the leftovers)
		makeMultiSet(srcWords, nonkeywords);

		numUniqueAnnotations = annotations.keySet().size();
		numUniqueNonReservedWords = nonkeywords.keySet().size();
		numUniqueReservedWords = keywords.keySet().size();

		numAnnotations = count(annotations);
		numNonReservedWords = count(nonkeywords);
		numReservedWords = count(keywords);

		numComments = commentCode.size();
		numLiterals = literalCode.size();


		// I'm done!?!?!?!?!

	}

	private int count(MultiSet m) {
		int count = 0;
		Iterator<String> iter = m.keySet().iterator();
		while (iter.hasNext()) {
			count += m.get(iter.next());
		}
		return count;
	}

	// TODO stats

	private String makeKeywordRegex() throws FileNotFoundException {
		Scanner sc1 = new Scanner(new File("./src/asdf/javalang.txt"));
		Scanner sc2 = new Scanner(new File("./src/asdf/keywords.txt"));
		StringBuffer s = new StringBuffer();
		boolean first = true;
		while (sc1.hasNextLine()) {
			if (!first) {
				s.append('|');
			} else {
				first = false;
			}
			s.append(sc1.nextLine());
		}
		while (sc2.hasNextLine()) {
			s.append('|');
			s.append(sc2.nextLine());
		}
		sc1.close();
		sc2.close();
		return s.toString();
	}

	private List<String> bla() {
		List<String> list = new LinkedList<String>(directives);
		Iterator<String> iter = list.iterator();
		List<String> list2 = new LinkedList<String>();
		while (iter.hasNext()) {
			String q = iter.next();
			String[] s = q.split("[\\s.]+");
			iter.remove();
			for (String s2 : s) {
				list2.add(s2);
			}
		}
		return list2;
	}

	/**
	 * will remove() from src be sure to copy src if you dont want to remove
	 * elements!
	 * 
	 * @param src
	 * @param dest
	 * @param regex
	 */
	public static void makeMultiSet(Iterable<String> src, MultiSet dest,
			String regex) {
		Iterator<String> iter = src.iterator();
		while (iter.hasNext()) {
			String s = iter.next();
			if (s.matches(regex)) {
				iter.remove(); //
				dest.add(s);
			}
		}
	}

	public static void makeMultiSet(Iterable<String> src, MultiSet dest) {
		makeMultiSet(src, dest, "[\\s\\S]+");
	}

	private void fillList() {
		if (isInNormalCode()) {
			normalCode.add(normalBuffer.toString());
			normalBuffer2.append(normalBuffer.toString());
			normalBuffer = new StringBuffer();
		} else if (isInLiteral()) {
			literalCode.add(literalBuffer.toString());
			literalBuffer = new StringBuffer();
		} else {
			commentCode.add(commentBuffer.toString().trim());
			commentBuffer = new StringBuffer();
		}
	}

	private void fillBuffer(char add) {
		if (ignore) {
			ignore = false;
		} else if (isInNormalCode()) {
			normalBuffer.append(add);
		} else if (isInLiteral()) {
			literalBuffer.append(add);
		} else {
			commentBuffer.append(add);
		}
	}

	protected BufferedReader startReader() throws FileNotFoundException {
		return new BufferedReader(new FileReader(javaFile));
	}

	protected Scanner startScanner() throws FileNotFoundException {
		return new Scanner(javaFile);
	}

//	private boolean startLiteral(char prev, char next) {
//		if (next != '"' && next != '\'') {
//			return false;
//		}
//		next = literalDelim;
//		return prev != '\\';
//	}
//
//	private boolean endLiteral(char prev, char next) {
//		return prev != '\\' && next == literalDelim;
//	}
	
	
	private boolean startLiteral(char prev, char next) {
		literalDelim = next;
		return prev != '\\' && (next == '\'' || next == '"');
	}

	private boolean endLiteral(char prev, char next) {
		return prev != '\\' && next == literalDelim;
	}

	private boolean startSingleComment(char prev, char next) {
		return prev == '/' && next == '/';
	}

	private boolean endSingleComment(char next) {
		return next == '\n';
	}

	private boolean startMultiComment(char prev, char next) {
		return prev == '/' && next == '*';
	}

	private boolean endMultiComment(char prev, char next) {
		return startMultiComment(next, prev);
	}

	private boolean isInNormalCode() {
		return !state1 && !state2;
	}

	private boolean isInLiteral() {
		return state1 && state2;
	}

	private boolean isInSingleComment() {
		return !state1 && state2;
	}

	private boolean isInMultiComment() {
		return state1 && !state2;
	}

	private void setNormalCode() {
		state1 = false;
		state2 = false;
	}

	private void setLiteral() {
		state1 = true;
		state2 = true;
	}

	private void setSingleComment() {
		state1 = false;
		state2 = true;
	}

	private void setMultiComment() {
		state1 = true;
		state2 = false;
	}

	/**
	 * @return list of the import + package statements
	 */
	public List<String> getDirectives() {
		return new LinkedList<String>(directives);
	}

	/**
	 * @return list of the fragments of code that aren't comments or literals
	 */
	public List<String> getCodePieces() {
		return new LinkedList<String>(normalCode);
	}

	/**
	 * @return list of the string/char literals
	 */
	public List<String> getLiteralPieces() {
		return new LinkedList<String>(literalCode);
	}

	/**
	 * @return list of the comments
	 */
	public List<String> getCommentPieces() {
		return new LinkedList<String>(commentCode);
	}

	/**
	 * @return the read file
	 */
	public File getJavaFile() {
		return this.javaFile;
	}

	// public Set<String> getLiterals() {
	// return this.literals.keySet();
	// }
	//
	// public Map<String, Integer> getCardinalLiterals() {
	// return new MultiSet(this.literals);
	// }

	/**
	 * @return set of reserved words used (includes java.lang.* classes)
	 */
	public Set<String> getKeywords() {
		return this.keywords.keySet();
	}

	/**
	 * @return multiset of reserved words used
	 */
	public Map<String, Integer> getCardinalKeywords() {
		return new MultiSet(this.keywords);
	}

	/**
	 * @return words that aren't reserved or a literal or a comment
	 */
	public Set<String> getNonkeywords() {
		return this.nonkeywords.keySet();
	}

	/**
	 * @return method names, variable names, etc as a multiset
	 */
	public Map<String, Integer> getCardinalNonkeywords() {
		return new MultiSet(this.nonkeywords);
	}

	/**
	 * @return set of the annotations used
	 */
	public Set<String> getAnnotations() {
		return annotations.keySet();
	}

	/**
	 * @return multiset of the used annotations
	 */
	public Map<String, Integer> getCardinalAnnotations() {
		return new HashMap<String, Integer>(annotations);
	}

	/**
	 * @return set of all words in the file (including literals and comments)
	 */
	public Set<String> getAllWords() {
		return allWords.keySet();
	}

	/**
	 * @return multiset of all words in the file (including literals and comments)
	 */
	public Map<String, Integer> getCardinalAllWords() {
		return new MultiSet(allWords);
	}
	
	/**
	 * @return # chars in the doc
	 */
	public int getNumChars() {
		return numChars;
	}

	public int getNumComments() {
		return numComments;
	}

	public int getNumLiterals() {
		return numLiterals;
	}

	public int getNumTokens() {
		return numTokens;
	}

	public int getNumReservedWords() {
		return numReservedWords;
	}

	public int getNumUniqueReservedWords() {
		return numUniqueReservedWords;
	}

	public int getNumNonReservedWords() {
		return numNonReservedWords;
	}

	public int getNumUniqueNonReservedWords() {
		return numUniqueNonReservedWords;
	}

	public int getNumAnnotations() {
		return numAnnotations;
	}

	public int getNumUniqueAnnotations() {
		return numUniqueAnnotations;
	}
	
	public int getNumUniqueWords() {
		return numReservedWords + numNonReservedWords + numAnnotations;
	}
	
	/**
	 * @return ratio of unique words to total words (reserved words don't count)
	 */
	public double getComplexity() {
		return numUniqueNonReservedWords / (double) numNonReservedWords;
	}
	
	/**
	 * @return avg # chars per word (not reserved words)
	 */
	public double getAvgCharsPerWord() {
		int total = 0;
		int numElements = 0;
		for (String s : nonkeywords.keySet()) {
			numElements++;
			total += nonkeywords.get(s);
		}
		if (numElements == 0) {
			return 0;
		}
		return total / (double) numElements;
	}
	
	public String mostCommonNonReservedWord() {
		return mostCommonWord(nonkeywords);
	}
	
	public String mostCommonReservedWord() {
		return mostCommonWord(keywords);
	}
	
	private String mostCommonWord(MultiSet m) {
		String s = "";
		boolean first = true;
		for (String s2 : m.keySet()) {
			if (first) {
				first = false;
				s = s2;
			}
			if (m.get(s2).compareTo(m.get(s)) > 0) {
				s = s2;
			}
		}
		return s;
	}
}