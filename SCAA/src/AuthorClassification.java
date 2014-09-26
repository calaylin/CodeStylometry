import weka.classifiers.*;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import java.io.FileReader;
import java.util.*;

public class AuthorClassification {


	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception 
	{

		int endRelax = 3;
		int numberFiles;
		int seedNumber;
		for(numberFiles=9; numberFiles<10; numberFiles++){

		for(seedNumber=8; seedNumber<9; seedNumber++){
			int foldNumber=numberFiles;

		String arffFile = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/mergedArffs/incremental/"+numberFiles+"files2014FS9Andrew_ready.arff";		 
		RandomForest cls = new RandomForest();
		Instances data = new Instances(new FileReader(arffFile));
		data.setClassIndex(data.numAttributes() - 1);
		data.stratify(foldNumber);
		System.out.println("Number of authors: " + data.numClasses());
		String[] options = weka.core.Utils.splitOptions("-I 300 -K 0 -S "+seedNumber);

		cls.setOptions(options);
		cls.buildClassifier(data);
		Evaluation eval=null;
		
		
		if(endRelax==1)
		 eval = new Evaluation(data);
		else
		 eval= new RelaxedEvaluation(data, endRelax);
		
		
		
		eval.crossValidateModel(cls, data,foldNumber , new Random(seedNumber));
			
		System.out.println("Relaxed by: "+endRelax+", seedNo:"+seedNumber+", "+numberFiles+" files per each of the "+data.numClasses()+" authors");
		Util.writeFile("Relaxed by: "+endRelax+", seedNo:"+seedNumber+", "+numberFiles+" files per each of the "+data.numClasses()+" authors"+"\n",
				"/Users/Aylin/Desktop/relaxedresultsSep25.txt", true);
	     
			
		System.out.println("Relaxed by: "+endRelax+", Correctly classified instances: "+eval.pctCorrect()+" OOB error: "+cls.measureOutOfBagError());
	     Util.writeFile("Relaxed by: "+endRelax+", Correctly classified instances: "+eval.pctCorrect()+" OOB error: "+cls.measureOutOfBagError()+"\n",
	    		 "/Users/Aylin/Desktop/relaxedresultsSep25.txt", true);	
	     
				
	      
			}	 
		}

	}
	
}