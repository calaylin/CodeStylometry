import java.util.List;
import java.util.Map;

public interface FeatureSet {

	/**
	 * Shows if the code is of the style "[stmt] {\n" or "[stmt]\n{\n".
	 * 
	 * @return
	 */
	public boolean newLineBrace();

	public int numFunctions();

	public int nestingDepth();

	public int length();

	public int numTokens();

	public int numComments();

	public List<String> getComments();

	public Map<String, Integer> getLiterals();

	public Map<String, Integer> getReservedWords();
	
	public Map<String, Integer> getUserDefinedWords();

	public Map<Loops, Integer> getLoops();

	public List<Integer> lineLengths();

	public double avgLineLength();

	/**
	 * Map each control structure to the number of times it occurs.
	 * 
	 * @return
	 */
	public Map<ControlStatement, Integer> getControlStructures();

	public int numEmptyLines();

	public double whiteSpaceRatio();

	public Map<Integer, Integer> numFunctionParams();

	public double avgParamsPerFunction();

	public Map<Integer, Integer> getVariableLocality();

	// ++ vs += 1
	// spaces vs tabs
	// x=1 vs x = 1
	// variable names
}