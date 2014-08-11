import java.util.Iterator;
import java.util.List;


public class ARFFFactory3 extends ARFFFactory2 {
	
	@Override
	protected void appendAttributes(FeatureSet f, StringBuffer x) {
		double len = f.length();
		
		x.append(((AbstractExtractor) f).getFile().getName() + ",");
		instanceIDs.add(((AbstractExtractor) f).getFile().getName());
		
		x.append(Math.log(f.numFunctions() / len) + ",");
//		x.append(f.length() + ",");
		x.append(Math.log(f.numTokens() / len) + ",");
		x.append(Math.log(f.numComments() / len) + ",");
		x.append(Math.log(f.getLiterals().size() / len) + ",");
		x.append(Math.log(f.getReservedWords().size() / len) + ",");
		x.append(f.avgLineLength() + ",");
		x.append(Math.log(f.numEmptyLines() / len) + ",");
		x.append(f.whiteSpaceRatio() + ",");
		x.append(f.avgParamsPerFunction() + ",");
		
		x.append(stdDev(f.lineLengths()) + ",");
		x.append(Math.log(f.numMacros() / len) + ",");
		x.append(("" + f.tabsLeadLines()).toUpperCase() + ","); // double check
		x.append(Math.log(f.getWhiteSpace().get(WhiteSpace.tab) / len) + ",");
		x.append(Math.log(f.getWhiteSpace().get(WhiteSpace.space) / len) + ",");
		x.append(stdDev(f.numFunctionParams()) + ",");
		x.append(Math.log(f.getControlStructures().get(ControlStatement.ifStatement) / len) + ",");
		x.append(Math.log(f.getControlStructures().get(ControlStatement.elifStatement) / len) + ",");
		x.append(Math.log(f.getControlStructures().get(ControlStatement.elseStatement) / len) + ",");
		x.append(Math.log(f.getControlStructures().get(ControlStatement.switchStatement) / len) + ",");
		x.append(Math.log(f.getControlStructures().get(ControlStatement.ternaryOperator) / len) + ",");
		x.append(Math.log(f.getLoops().get(Loops.forLoop) / len) + ",");
		x.append(Math.log(f.getLoops().get(Loops.whileLoop) / len) + ",");
		x.append(Math.log(f.getLoops().get(Loops.doWhileLoop) / len) + ",");
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
		
		allLines.add("@attribute log(numFunctions/length) numeric\n");
//		allLines.add("@attribute length numeric\n");
		allLines.add("@attribute log(numTokens/length) numeric\n");
		allLines.add("@attribute log(numComments/length) numeric\n");
		allLines.add("@attribute log(numLiterals/length) numeric\n");
		allLines.add("@attribute log(numReservedWords/length) numeric\n");
		allLines.add("@attribute avgLineLength numeric\n");
		allLines.add("@attribute log(numEmptyLines/length) numeric\n");
		allLines.add("@attribute whiteSpaceRatio numeric\n");
		allLines.add("@attribute avgParams numeric\n");
		
		allLines.add("@attribute stdDevLineLength numeric\n");
		allLines.add("@attribute log(numMacros/length) numeric\n");
		allLines.add("@attribute tabsLeadLines {TRUE, FALSE}\n");
		allLines.add("@attribute log(numTabs/length) numeric\n");
		allLines.add("@attribute log(numSpaces/length) numeric\n");
		allLines.add("@attribute stdDevNumParams numeric\n");
		allLines.add("@attribute log(numIf/length) numeric\n");
		allLines.add("@attribute log(numElif/length) numeric\n");
		allLines.add("@attribute log(numElse/length) numeric\n");
		allLines.add("@attribute log(numSwitch/length) numeric\n");
		allLines.add("@attribute log(numTernary/length) numeric\n");
		allLines.add("@attribute log(numFor/length) numeric\n");
		allLines.add("@attribute log(numWhile/length) numeric\n");
		allLines.add("@attribute log(numDo/length) numeric\n");
		allLines.add("@attribute newLineBeforeOpeningBrace {TRUE, FALSE}\n");
	}
}