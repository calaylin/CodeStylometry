import weka.classifiers.*;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import edu.drexel.psal.jstylo.analyzers.WekaAnalyzer;
import edu.drexel.psal.jstylo.generics.*;

import java.io.FileReader;
import java.util.*;

public class AuthorClassification {

	public static MultiplePrintStream out;

	public static void main(String[] args) throws Exception 
	{

		int numberFiles=2;
		int seedNumber=7;
		int foldNumber=2;
		int relaxParam=2;
		String arffFile = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/mergedArffs/incremental/"+numberFiles+"files2014FS9Andrew.arff";		 
		RandomForest cls = new RandomForest();
		Instances data = new Instances(new FileReader(arffFile));
		data.setClassIndex(data.numAttributes() - 1);
		System.out.println("number of authors: " + data.numClasses());
		String[] options = weka.core.Utils.splitOptions("-I 300 -K 0 -S "+seedNumber);

		cls.setOptions(options);
		cls.buildClassifier(data);
		
/*		
	    Evaluation eval = new Evaluation(data);
	    eval.crossValidateModel(cls, data,foldNumber , new Random(seedNumber));
        System.out.println(eval.toSummaryString("\nResults\n", true));
        System.out.println(eval.fMeasure(1) + " " + eval.precision(1) + " " + eval.recall(1));
*/
	        
		WekaDetailedAnalyzer wa = new WekaDetailedAnalyzer(cls,relaxParam);
		Evaluation eval = wa.runCrossValidation(data, foldNumber, seedNumber);
		System.out.println("done! results:");
		System.out.println(eval.toSummaryString());
		System.out.println(eval.toClassDetailsString());
		//out.println(eval.toMatrixString());
 //       System.out.println(eval.fMeasure(1) + " " + eval.precision(1) + " " + eval.recall(1));
		System.out.println();
	}
		 


	public static class WekaDetailedAnalyzer extends WekaAnalyzer
	{
		protected Classifier cls;
		protected int relaxParam;
		protected boolean relaxed;
		
		public static MultiplePrintStream out;
		
		public WekaDetailedAnalyzer(Classifier classifier) {
			super(classifier);
			cls = classifier;
			if (out == null)
				out = AuthorClassification.out;
		}
		
		public WekaDetailedAnalyzer(Classifier classifier, int relaxParam) {
			super(classifier);
			cls = classifier;
			if (out == null)
				out = AuthorClassification.out;
			if (relaxParam < 1)
				relaxed = false;
			else
			{
				relaxed = true;
				this.relaxParam = relaxParam;
			}
		}
	}
}