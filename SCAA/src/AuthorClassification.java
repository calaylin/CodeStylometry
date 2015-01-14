import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.classifiers.*;
import weka.classifiers.evaluation.ThresholdCurve;
import weka.classifiers.meta.FilteredClassifier;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.Range;
import weka.core.Utils;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.instance.RemoveRange;
import weka.filters.unsupervised.instance.RemoveWithValues;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
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

		String fileName  ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/Results/AutomatedResults/"
				+	"mallory/"+"mallory_CSFS_new.txt";
		for(int authorNo=6; authorNo<=54; authorNo+=1){
			for(numberFiles=9; numberFiles<10; numberFiles++){
				String arffFile = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/"
		    			+ "mallory_150/CSFS/" +"mallory_CSFS_"+authorNo+".arff" ;
			
			  Util.writeFile(numberFiles+"FilesPerAuthor: \n",fileName, true);	
			  for(int relaxPar = 1; relaxPar<=endRelax; relaxPar++){
				  total=0;				  
				  average=0;

				  for(seedNumber=1; seedNumber<2; seedNumber++){
					  int foldNumber=numberFiles;


 
		RandomForest cls = new RandomForest();
		Instances data = new Instances(new FileReader(arffFile));
		data.setClassIndex(data.numAttributes() - 1);
		//do not stratify if you are going to remove instances for training and testing
//		data.stratify(foldNumber);
		
		
/*		//Start information gain that selects up to 200 features that have nonzero infogain
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
*/	     
		

		 
	     
		RemoveRange filter1 = new RemoveRange();
		filter1.setInputFormat(data);
		filter1.setInstancesIndices("13-last");
		filter1.setInvertSelection(true);

		Instances trainData = Filter.useFilter(data, filter1);
		System.out.println("trainData size " + trainData.numInstances());
		 BufferedWriter writer = new BufferedWriter(new FileWriter("/Users/Aylin/Desktop/Drexel/"
		 		+ "2014/ARLInternship/SCAAarffs/mallory_150/traintest/trainData_"+authorNo+".arff"));
		 writer.write(trainData.toString());
		 writer.flush();
		 writer.close();
/*		for(int inst=0; inst<16; inst++)
		System.out.println("trainData " + trainData.classAttribute().value((int) trainData.instance(inst).classValue()));
*/
		
		RemoveRange filter2 = new RemoveRange();
		filter2.setInputFormat(data);
		filter2.setInstancesIndices("13-last");
		Instances testData = Filter.useFilter(data, filter2);
		System.out.println("testData size " + testData.numInstances());
		writer = new BufferedWriter(new FileWriter("/Users/Aylin/Desktop/Drexel/"
		 		+ "2014/ARLInternship/SCAAarffs/mallory_150/traintest/testData_"+authorNo+".arff"));
	//	 writer.write(testData.toString());
		 writer.flush();
		 writer.close();
/*		for(int inst=0; inst<29; inst++)
		System.out.println("testData " + testData.classAttribute().value((int) testData.instance(inst).classValue()));
*/
	
		
		
		 Remove rm = new Remove();
		 int authorName = (data.numAttributes() - 28);
//		 rm.setAttributeIndices("1," +authorName);  // remove 1st and the autor attribute
		 rm.setAttributeIndices("1");  // remove 1st attribute*/

		 FilteredClassifier fc = new FilteredClassifier();
		fc.setClassifier(new RandomForest());
		 fc.setFilter(rm);
		 
		 String[] options = weka.core.Utils.splitOptions("-I 300 -K "+numFeatures+" -S "+seedNumber);
			fc.setOptions(options);
		fc.buildClassifier(trainData);
//		Evaluation evalMallory=null;
//		evalMallory = new Evaluation(testData);

		
	    Evaluation eval_mal = new Evaluation(testData);
	    eval_mal.evaluateModel(fc, testData);
		
		
/*		for (int i = 0; i < testData.numInstances(); i++) {
			double classVal = fc.classifyInstance(testData
					.instance(i));
			System.out
					.println("===== Classified instance =====" + classVal);
			double[] pred = fc.distributionForInstance(testData
					.instance(i));
			 System.out.println("===== Classified instance =====" + pred);*/
		//     System.out.println("Class predicted: " + testData.instance(i).classAttribute().value((int) pred));

				 // train on trainData and make predictions on testData
				 fc.buildClassifier(trainData);
				 for (int i = 0; i < testData.numInstances(); i++) {
				   double pred = fc.classifyInstance(testData.instance(i));
				   System.out.print(fc.getOptions());
				   System.out.print("ID: " + testData.instance(i).value(0));
				   System.out.print(", actual: " + testData.classAttribute().value((int) testData.instance(i).classValue()));
				   System.out.println(", predicted: " + testData.classAttribute().value((int) pred)+"\n");
				
				   
				   Util.writeFile("ID: " + testData.instance(i).value(0),
				    		 fileName, true);
				   Util.writeFile(", actual: " + testData.classAttribute().value((int) testData.instance(i).classValue()),
				    		 fileName, true);	
				   Util.writeFile(", predicted: " + testData.classAttribute().value((int) pred)+"\n",
				    		 fileName, true);	
				  
		}
		
				   ThresholdCurve tc_mal = new ThresholdCurve();
				     int classIndex = 0;
				     Instances result1 = tc_mal.getCurve(eval_mal.predictions(), classIndex);	
				     tc_mal.getROCArea(result1);
				    
				     Instances result2 = tc_mal.getCurve(eval_mal.predictions(), 1);	
				     tc_mal.getROCArea(result2);
					System.out.println("AUC class1: "+   tc_mal.getROCArea(result1) + " AUC class2: "+ tc_mal.getROCArea(result2));
						//	+"\n"+"Number of trees used, "+fc.getNumTrees()+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+fc.measureOutOfBagError());

				     Util.writeFile("AUC class1: "+   tc_mal.getROCArea(result1) + " AUC class2: "+ tc_mal.getROCArea(result2)   +"\n"+"Number of features used, default is 0 (logM+1) "+cls.getNumFeatures()+ ", Correctly classified instances, "+eval_mal.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
				    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"\n"  ,
					 fileName, true);	
	   
				     
				     
				     
		
		
		System.out.println("Number of instances: " + data.numInstances()+" and number of authors: " + data.numClasses());
		
		
		String[] options1 = weka.core.Utils.splitOptions("-I 300 -K "+numFeatures+" -S "+seedNumber);
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
	      classIndex = 0;
	      result1 = tc.getCurve(eval.predictions(), classIndex);	
	     tc.getROCArea(result1);
	    
	      result2 = tc.getCurve(eval.predictions(), 1);	
	     tc.getROCArea(result2);
/*		System.out.println("AUC class1: "+   tc.getROCArea(result1) + " AUC class2: "+ tc.getROCArea(result2));
				//"\n"+"Number of features used, "+cls.getNumFeatures()+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());

	     Util.writeFile("AUC class1: "+   tc.getROCArea(result1) + " AUC class2: "+ tc.getROCArea(result2)   +"\n"+"Number of features used, default is 0 (logM+1) "+cls.getNumFeatures()+ ", Correctly classified instances, "+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
	    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"\n"  ,
		 fileName, true);*/	

			
		if(numFeatures==0){
			int defaultNumFeatures=(int)Utils.log2(data.numAttributes()) + 1;
/*			Util.writeFile("Number of features used, "+defaultNumFeatures+ ", Correctly classified instances, "+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
		    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"max depth of trees"+cls.getMaxDepth()+"\n"  ,
					fileName, true);	
			System.out.println("Number of features used, "+defaultNumFeatures+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());
*/
		}
		
		else{	
/*		System.out.println("Number of features used, "+cls.getNumFeatures()+ ", Relaxed by, "+relaxPar+", Correctly classified instances,"+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError());

			     Util.writeFile("Number of features used, default is 0 (logM+1) "+cls.getNumFeatures()+ ", Correctly classified instances, "+eval.pctCorrect()+", OOB error,"+cls.measureOutOfBagError()+"\n"
			    		 +"Filename is, "+arffFile.toString()+" Number of features used, "+cls.getNumFeatures()+"\n"  ,
	    		 fileName, true);	*/
		}
	     accuracy=eval.pctCorrect();
	     total =total+accuracy;
	     average = total/seedNumber;
			}	

				  System.out.println("total is "+total);
				  System.out.println("avg is "+average);
				  System.out.println("accuracy is "+accuracy);

		System.out.println("\nThe average accuracy with "+numberFiles+"files is "+average+"\n");	
	     Util.writeFile("\nThe average accuracy with "+numberFiles+"files is "+average+", relaxed by, "+relaxPar+", \n",
	    		 fileName, true);	

	     }}
		}
	}
	
}