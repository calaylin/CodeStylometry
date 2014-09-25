
import weka.classifiers.*;
import weka.classifiers.functions.*;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.filters.Filter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.Charset;
import java.util.*;

public class AuthorClassification {


	public static void main(String[] args) throws Exception 
	{

		int numberFiles=13;
		int seedNumber=7;
		int foldNumber=10;
		String arffFile = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/mergedArffs/incremental/"+numberFiles+"filesDifficult2014FS9Andrew.arff";		 
		Classifier cls = new RandomForest();
		Instances data = new Instances(new FileReader(arffFile));
		data.setClassIndex(data.numAttributes() - 1);
		System.out.println("number of authors: " + data.numClasses());
		 
		cls.buildClassifier(data);
		System.out.println(cls);
		String[] options = weka.core.Utils.splitOptions("-I 300 -K 0 -S "+seedNumber);

	    Evaluation eval = new Evaluation(data);
	    eval.crossValidateModel(cls, data,foldNumber , new Random(seedNumber));
        System.out.println(eval.toSummaryString("\nResults\n", true));
        System.out.println(eval.fMeasure(1) + " " + eval.precision(1) + " " + eval.recall(1));

	        
	      
		 


	}
	
}