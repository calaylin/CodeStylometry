import weka.classifiers.*;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import java.io.FileReader;
import java.util.*;

public class AuthorClassification {


	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception 
	{

		int endRelax = 10;
		int numberFiles;
		int seedNumber;
		String fileName = "/Users/Aylin/Desktop/relaxedresults_syntactic_Sep28.txt";
		for(numberFiles=2; numberFiles<15; numberFiles++){
			  Util.writeFile(numberFiles+"FilesPerAuthor: \n",fileName, true);	

		for(seedNumber=0; seedNumber<10; seedNumber++){
			int foldNumber=numberFiles;
			for(int relaxPar = 1; relaxPar<=endRelax; relaxPar++){

		String arffFile = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/mergedArffs/incremental/syntactic/"+numberFiles+"Files2014FS9Andrew_ready.arff";		 
		RandomForest cls = new RandomForest();
		Instances data = new Instances(new FileReader(arffFile));
		data.setClassIndex(data.numAttributes() - 1);
		data.stratify(foldNumber);
		System.out.println("Number of instances: " + data.numInstances()+" and number of authors: " + data.numClasses());
//		System.out.println("Number of authors: " + data.numClasses());

		String[] options = weka.core.Utils.splitOptions("-I 300 -K 0 -S "+seedNumber);

		cls.setOptions(options);
		cls.buildClassifier(data);
		Evaluation eval=null;
		
		
		if(endRelax==1)
		 eval = new Evaluation(data);
		else
		 eval= new RelaxedEvaluation(data, relaxPar);
		
		
		
		eval.crossValidateModel(cls, data,foldNumber , new Random(seedNumber));
			
		System.out.println("Relaxed by: "+relaxPar+", seedNo:"+seedNumber+", "+numberFiles+" files, "+data.numClasses()+" authors");
		Util.writeFile("Relaxed by: "+relaxPar+", seedNo:"+seedNumber+", "+numberFiles+" files, "+data.numClasses()+" authors",
				fileName, true);
	     
			
		System.out.println("Relaxed by: "+relaxPar+", Correctly classified instances: "+eval.pctCorrect()+" OOB error: "+cls.measureOutOfBagError());
	     Util.writeFile(", Correctly classified instances: "+eval.pctCorrect()+", OOB error: "+cls.measureOutOfBagError()+"\n",
	    		 fileName, true);	
	     
				
	      
			}	 
	     Util.writeFile("\n",
	    		 fileName, true);		
	     }}

	}
	
}