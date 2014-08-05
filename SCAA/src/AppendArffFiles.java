


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

/**
 * Appends two arff files where each feature vector contains the same ID.
 * This can be used to combine extracted features with features extracted from JSylo 
 * (eg Writeprints limited, save to arff files in the Analysis tab) 
 *
 *
 * @author Aylin Caliskan-Islam (ac993@drexel.edu)
 */
public class AppendArffFiles {

	//after @data, if the first csv element is the same as file2's first csv element, 
	//append file2's that line to file1 and move
    public static void main(String[] args) throws Exception{
    	
    	String word = "@data";
    	String file1 = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/173InstanceIDfs8CodeJam8.4_12_09_33.arff";
    	String file2 = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/AndrewInstanceIDoutput4.arff";
    	String outputArffName ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/merged4fs8.arff";

    	int atDataLineNumberFile1 = AppendArffFiles.grepLineNumber(file1, word);
    	int atDataLineNumberFile2 = AppendArffFiles.grepLineNumber(file2, word);
    	
    	int numberOfInstances = 1038; //173 authors, each with 6 files
		int file2LineNumberStart=atDataLineNumberFile2+numberOfInstances;
		
    	for(int i = atDataLineNumberFile1+1; i <= atDataLineNumberFile1 + numberOfInstances; i++)
    	{
    		//make this ascending in general cases
        	for(int j=file2LineNumberStart; j >= atDataLineNumberFile2 + 1; j--)
        	{
        		if (AppendArffFiles.getInstanceID(file1, i).equals(AppendArffFiles.getInstanceID(file2, j)))
        				{
        					String firstPart = getInstance(file1, i);
        					String secondPart = getInstanceVector(file2, j);
        					System.out.println(firstPart);
        					Util.writeFile( firstPart+ "," +secondPart + "\n", outputArffName, true);
        					//just for andrew's file to save time, if random or, this needs to be fixed
        					file2LineNumberStart=j; //remove this line in general cases
        					j= atDataLineNumberFile2 + 1;

        				}      				
    		}

    	}     	

    }
    public static String getInstanceID(String file, int lineNumber) throws IOException
    {
    	//will give an error if there is onl
    	String line = AppendArffFiles.readSpecificLineNumber(file, lineNumber);  	
    	String arr[] = line.split(",", 2);
    	String firstWord = arr[0];
    	return firstWord;
    }
    
    public static String getInstance(String file, int lineNumber) throws IOException
    {
    	//will give an error if there is onl
    	String line = AppendArffFiles.readSpecificLineNumber(file, lineNumber);  	
    	String arr[] = line.split(" ", 1);
    	String firstWord = arr[0];
    	return firstWord;
    }
      
    
    public static String getInstanceVector(String file, int lineNumber) throws IOException
    {
    	
    	String line = AppendArffFiles.readSpecificLineNumber(file, lineNumber);
    	String arr[] = line.split(",", 2);
    //	String firstWord = arr[0];
    	String theRest = arr[1];
       	return theRest;
    }
    
    
    public static String readSpecificLineNumber (String file, int lineNumber) throws IOException
    {
    	String lineString = (String)FileUtils.readLines(new File(file)).get(lineNumber-1);
    	
    	return lineString;
    }
    
    
	public static int grepLineNumber(String file, String word) throws Exception {
	    BufferedReader buf = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file))));

	    String line;
	    int lineNumber = 0;
	    while ((line = buf.readLine()) != null)   {
	        lineNumber++;
	        if (word.equals(line)) {
	            return lineNumber;
	        }
	    }
	    return -1;
	}	
	
}
