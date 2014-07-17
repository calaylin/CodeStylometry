import java.util.List;
import java.util.Map;
import java.util.Set;


public interface FeatureSet {
	
	public boolean newLineBrace();
	public int numFunctions();
	public int nestingDepth();
	public int length();
	public int numTokens();
	public int numComments();
	public List<String> getComments();
	public Map<String, Integer> getLiterals();
	public Map<String, Integer> getReservedWords();
	public Map<Loops, Integer> getLoops();
	public List<Integer> lineLengths();
	public double avgLineLength();
	public Map<ControlStatement, Integer> getControlStructures();
	public int numEmptyLines();
	public double whiteSpaceRatio();
	public Set<Integer> numFunctionParams();
	public double avgParamsPerFunction();
	public Map<Integer, Integer> getVariableLocality();
	
	// ++ vs += 1
	// spaces vs tabs
	// x=1 vs x = 1
	// variable names
}