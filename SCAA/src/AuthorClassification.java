import weka.classifiers.*;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.Utils;

import java.io.FileReader;
import java.util.*;

public class AuthorClassification {


	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception 
	{
		double accuracy=0;
		int endRelax = 1;
		int numberFiles;
		int numFeatures=0; //0 is the default logM+1
		int seedNumber;
		double total =0;
		double average =0;
		String fileName = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/Results/AutomatedResults/main250/"
				+ "InfoGain250Authors2014NoDummies.txt";
		for(numberFiles=9; numberFiles<10; numberFiles++){
			  Util.writeFile(numberFiles+"FilesPerAuthor: \n",fileName, true);	
			  for(int relaxPar = 1; relaxPar<=endRelax; relaxPar++){
				  total=0;
				  average=0;

				  for(seedNumber=1; seedNumber<6; seedNumber++){
			int foldNumber=numberFiles;

		String arffFile = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/bigExperiments/InfoGain/"+numberFiles+"BigExperiment250_2014FS9Andrew_infoGain0total200features.arff";		 
		RandomForest cls = new RandomForest();
		Instances data = new Instances(new FileReader(arffFile));
		data.setClassIndex(data.numAttributes() - 1);
		data.stratify(foldNumber);
//		System.out.println("Number of instances: " + data.numInstances()+" and number of authors: " + data.numClasses());
//		System.out.println("Number of authors: " + data.numClasses());

		String[] options = weka.core.Utils.splitOptions("-I 300 -K "+numFeatures+" -S "+seedNumber);

		cls.setOptions(options);
		cls.buildClassifier(data);
		Evaluation eval=null;
		
		
		if(endRelax==1)
		 eval = new Evaluation(data);
		else
		 eval= new RelaxedEvaluation(data, relaxPar);
		
		
		
		eval.crossValidateModel(cls, data,foldNumber , new Random(seedNumber));
			
/*		System.out.println("Relaxed by, "+relaxPar+", seedNo,"+seedNumber+", files,"+numberFiles+", authors,"+data.numClasses());
		Util.writeFile("Relaxed by, "+relaxPar+", seedNo,"+seedNumber+", files,"+numberFiles+", authors,"+data.numClasses(),
				fileName, true);*/
	     
			
		if(numFeatures==0){
			int defaultNumFeatures=(int)Utils.log2(data.numAttributes()) + 1;
			Util.writeFile("Number of features used, "+defaultNumFeatures+ ", Correctly classified instances, "+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
		    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"\n"  ,
					fileName, true);	
			System.out.println("Number of features used, "+defaultNumFeatures+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());

		}
		
		else{	
		System.out.println("Number of features used, "+cls.getNumFeatures()+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());

			     Util.writeFile("Number of features used, default is 0 (logM+1) "+cls.getNumFeatures()+ ", Correctly classified instances, "+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
			    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"\n"  ,
	    		 fileName, true);	
		}
	     accuracy=eval.pctCorrect();
	     total =total+accuracy;
	     average = total/seedNumber;
			}	

/*				  System.out.println("total is "+total);
				  System.out.println("avg is "+average);
				  System.out.println("accuracy is "+accuracy);*/

		System.out.println("\nThe average accuracy with "+numberFiles+"files is "+average+"\n");	
	     Util.writeFile("\nThe average accuracy with "+numberFiles+"files is "+average+", relaxed by, "+relaxPar+", \n",
	    		 fileName, true);		
	     }}

	}
	
}