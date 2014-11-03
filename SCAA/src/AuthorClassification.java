import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.*;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

import java.io.FileReader;
import java.util.*;

public class AuthorClassification {


	public static void main(String[] args) throws Exception 
	{
		double accuracy=0;
		int endRelax = 1;
		int numberFiles;
		int numFeatures=0; //0 is the default logM+1
		int seedNumber;
		double total =0;
		double average =0;

		String fileName  ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/" 
				+	"2classes20pairs/"+"CSFS_auc.txt";
		for(int authorNo=1; authorNo<21; authorNo+=1){
			String arffFile = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/2classes20pairs/CSFS/"
			//		+ "CodeJam_9FilesPerAuthor_syntactic_ready_pair"+authorNo+".arff";
					+ "9files2014FS9Andrew_ready_pair"+authorNo+".arff";
		for(numberFiles=9; numberFiles<10; numberFiles++){
			  Util.writeFile(numberFiles+"FilesPerAuthor: \n",fileName, true);	
			  for(int relaxPar = 1; relaxPar<=endRelax; relaxPar++){
				  total=0;
				  
				  average=0;

				  for(seedNumber=1; seedNumber<5; seedNumber++){
			int foldNumber=numberFiles;


 
		RandomForest cls = new RandomForest();
		Instances data = new Instances(new FileReader(arffFile));
		
		//Start information gain that selects up to 200 features that have nonzero infogain
		int n = 200; // number of features to select 
	    AttributeSelection attributeSelection = new  AttributeSelection(); 
	     Ranker ranker = new Ranker(); 
	     ranker.setNumToSelect(n); 
	     ranker.setThreshold(0);
	     InfoGainAttributeEval infoGainAttributeEval = new InfoGainAttributeEval(); 
	     attributeSelection.setEvaluator(infoGainAttributeEval); 
	     attributeSelection.setSearch(ranker); 
	     attributeSelection.setInputFormat(data); 
	     data = Filter.useFilter(data, attributeSelection); 
	     //end of infogain
	     
	     
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
		 ThresholdCurve tc = new ThresholdCurve();
	     int classIndex = 0;
	     Instances result1 = tc.getCurve(eval.predictions(), classIndex);	
	     tc.getROCArea(result1);
	    
	     Instances result2 = tc.getCurve(eval.predictions(), 1);	
	     tc.getROCArea(result2);
		System.out.println("AUC class1: "+   tc.getROCArea(result1) + " AUC class2: "+ tc.getROCArea(result2));
				//"\n"+"Number of features used, "+cls.getNumFeatures()+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());

	     Util.writeFile("AUC class1: "+   tc.getROCArea(result1) + " AUC class2: "+ tc.getROCArea(result2)   +"\n"+"Number of features used, default is 0 (logM+1) "+cls.getNumFeatures()+ ", Correctly classified instances, "+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
	    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"\n"  ,
		 fileName, true);	

			
		if(numFeatures==0){
			int defaultNumFeatures=(int)Utils.log2(data.numAttributes()) + 1;
			Util.writeFile("Number of features used, "+defaultNumFeatures+ ", Correctly classified instances, "+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
		    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"max depth of trees"+cls.getMaxDepth()+"\n"  ,
					fileName, true);	
		//	System.out.println("Number of features used, "+defaultNumFeatures+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());

		}
		
		else{	
	//	System.out.println("Number of features used, "+cls.getNumFeatures()+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());

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

	//	System.out.println("\nThe average accuracy with "+numberFiles+"files is "+average+"\n");	
	     Util.writeFile("\nThe average accuracy with "+numberFiles+"files is "+average+", relaxed by, "+relaxPar+", \n",
	    		 fileName, true);	

	     }}
		}
	}
	
}