import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.io.FileUtils;

import weka.core.Instances;

/**
 * Appends two arff files where each feature vector contains the same ID.
 * This can be used to combine features at different times.
 * The two files can contain the same features.
 * It does a right join based on the first file. The resulting file would only have features 
 * in the first file that also existed in the second file.
 *
 *
 * @author Aylin Caliskan-Islam (aylinc@princeton.edu)
 */
public class MergeArffFilesNew {

	//Find the intersecting userIDs and merge all the features and instances to a new file
	//file2's instances and features are appended to file1
    public static void main(String[] args) throws Exception{
    	 

            String file1 ="/Users/Aylin/Desktop/Princeton/BAA/arffs/"

            + "C_62Authors14files_decompiledNEW.arff";

            String file2 ="/Users/Aylin/Desktop/Princeton/BAA/arffs/"

         //   + "merged/C_62Authors14files_original_C++.arff";
                    + "62authors14FilesUsenixAndrewFeatures.arff";


            String outputArffName ="/Users/Aylin/Desktop/Princeton/BAA/arffs/merged/"

            + "C_62Authors14files_decompiledPlusOriginal.arff";

           

		
		
        Util.writeFile("@relation " + file1+file2+"\n" +"\n" , outputArffName, true);

        // Read all the instances in the files 
		Instances instances = new Instances(new FileReader(file1));
		Instances instances2 = new Instances(new FileReader(file2));
		
		for (int att=0; att < instances.numAttributes(); att++)

		{		System.out.println(instances.attribute(att).name());
				String type=null;
				if(instances.attribute(att).isNumeric()){
					type="numeric";
				}
				else if(instances.attribute(att).isNominal()){
					type="nominal";
				}
				else if(instances.attribute(att).isString()){
					type="string";
				}

				System.out.println(type);
				Util.writeFile("@attribue " +"`"+instances.attribute(att).name()+"'" + "_decompiled "
				+ type +"\n", outputArffName, true);
		}
		
		for (int att2=0; att2 < instances2.numAttributes(); att2++)
		{	//	System.out.println(instances2.attribute(att2).name());
				String type=null;
				if(instances2.attribute(att2).isNumeric()){
					type="numeric";
				}
				else if(instances2.attribute(att2).isNominal()){
					type="nominal";
				}
				else if(instances2.attribute(att2).isString()){
					type="string";
				}
			//	System.out.println(type);

				Util.writeFile("@attribue "+"`" +instances.attribute(att2).name() +"'"+ "_original "
				+ type +"\n", outputArffName, true);
		}

		Util.writeFile( "\n" +"@data" +"\n", outputArffName, true);

		
		
		for (int i=0;i< instances.numInstances();i++){
			for(int j=0; j< instances2.numInstances();j++){

			if(instances2.instance(j).stringValue(0).contains(instances.instance(i).stringValue(0)))
					{
		        System.out.println(i+" \n"); 


				Util.writeFile(instances.instance(i)
						+ ","+ instances2.instance(j)+ "\n", outputArffName, true );
					}
			
		}}
    }
	
}
