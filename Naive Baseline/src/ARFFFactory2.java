import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class ARFFFactory2 extends ARFFFactory {
	
	protected Set<String> instanceIDs = new HashSet<>();
	
	@Override
	protected void appendAttributes(FeatureSet f, StringBuffer x) {
		x.append(((AbstractExtractor) f).getFile().getName() + ",");
		instanceIDs.add(((AbstractExtractor) f).getFile().getName());
		
		x.append(f.numFunctions() + ",");
		x.append(f.length() + ",");
		x.append(f.numTokens() + ",");
		x.append(f.numComments() + ",");
		x.append(f.getLiterals().size() + ",");
		x.append(f.getReservedWords().size() + ",");
		x.append(f.avgLineLength() + ",");
		x.append(f.numEmptyLines() + ",");
		x.append(f.whiteSpaceRatio() + ",");
		x.append(f.avgParamsPerFunction() + ",");
		
		x.append(stdDev(f.lineLengths()) + ",");
		x.append(f.numMacros() + ",");
		x.append(("" + f.tabsLeadLines()).toUpperCase() + ","); // double check
		x.append(f.getWhiteSpace().get(WhiteSpace.tab) + ",");
		x.append(f.getWhiteSpace().get(WhiteSpace.space) + ",");
		x.append(stdDev(f.numFunctionParams()) + ",");
		x.append(f.getControlStructures().get(ControlStatement.ifStatement) + ",");
		x.append(f.getControlStructures().get(ControlStatement.elifStatement) + ",");
		x.append(f.getControlStructures().get(ControlStatement.elseStatement) + ",");
		x.append(f.getControlStructures().get(ControlStatement.switchStatement) + ",");
		x.append(f.getControlStructures().get(ControlStatement.ternaryOperator) + ",");
		x.append(f.getLoops().get(Loops.forLoop) + ",");
		x.append(f.getLoops().get(Loops.whileLoop) + ",");
		x.append(f.getLoops().get(Loops.doWhileLoop) + ",");
		x.append(("" + f.newLineBrace()).toUpperCase() + ",");
	}
	
	@Override
	protected void arffAttributes(List<String> allLines) {
		allLines.add("@attribute instanceID {");
		Iterator<String> id = instanceIDs.iterator();
		while (id.hasNext()) {
			allLines.add(id.next());
			if (id.hasNext()) {
				allLines.add(",");
			}
		}
		allLines.add("}\n");
		
		allLines.add("@attribute numFunctions numeric\n");
		allLines.add("@attribute length numeric\n");
		allLines.add("@attribute numTokens numeric\n");
		allLines.add("@attribute numComments numeric\n");
		allLines.add("@attribute numLiterals numeric\n");
		allLines.add("@attribute numReservedWords numeric\n");
		allLines.add("@attribute avgLineLength numeric\n");
		allLines.add("@attribute numEmptyLines numeric\n");
		allLines.add("@attribute whiteSpaceRatio numeric\n");
		allLines.add("@attribute avgParams numeric\n");
		
		allLines.add("@attribute stdDevLineLength numeric\n");
		allLines.add("@attribute numMacros numeric\n");
		allLines.add("@attribute tabsLeadLines {TRUE, FALSE}\n");
		allLines.add("@attribute numTabs numeric\n");
		allLines.add("@attribute numSpaces numeric\n");
		allLines.add("@attribute stdDevNumParams numeric\n");
		allLines.add("@attribute numIf numeric\n");
		allLines.add("@attribute numElif numeric\n");
		allLines.add("@attribute numElse numeric\n");
		allLines.add("@attribute numSwitch numeric\n");
		allLines.add("@attribute numTernary numeric\n");
		allLines.add("@attribute numFor numeric\n");
		allLines.add("@attribute numWhile numeric\n");
		allLines.add("@attribute numDo numeric\n");
		allLines.add("@attribute newLineBeforeOpeningBrace {TRUE, FALSE}\n");		
	}
}

//number of functions
//program length
//number of tokens
//number of comments
//number of String/character/numeric literals
//number of unique reserved words used
//average length of lines
//number of empty lines
//the ratio of whitespace to text
//average number of parameters per function

//standard deviation of length of lines
//number of macros
//whether tabs precede lines (versus spaces)
//number of tabs
//number of spaces
//standard deviation of number of parameters
//number of "if" statements
//number of "else if" statements
//number of "else" statements
//number of "switch" statements
//number of ternary operators
//number of "for" loops
//number of "while" loops
//number of "do-while" loops