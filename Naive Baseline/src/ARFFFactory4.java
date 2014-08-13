import java.util.List;


public class ARFFFactory4 extends ARFFFactory3 {
	
	@Override
	protected void appendAttributes(FeatureSet f, StringBuffer x) {
		super.appendAttributes(f, x);

		x.append(f.nestingDepth() + ",");
		x.append(f.branchingFactor() + ",");
	}
	
	@Override
	protected void arffAttributes(List<String> allLines) {
		super.arffAttributes(allLines);
		
		allLines.add("@attribute nestingDepth numeric\n");
		allLines.add("@attribute branchingFactor numeric\n");
	}
}