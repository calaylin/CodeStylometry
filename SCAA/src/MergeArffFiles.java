


import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

/**
 * Appends two arff files where each feature vector contains the same ID.
 * This can be used to combine extracted features with features extracted from JSylo 
 * (eg Writeprints limited, save to arff files in the Analysis tab) 
 *
 *
 * @author Aylin Caliskan-Islam (ac993@drexel.edu)
 */
public class MergeArffFiles {

	//after @data, if the first csv element is the same as file2's first csv element, 
	//append file2's that line to file1 and move
    public static void main(String[] args) throws Exception{
    	
    	
    	for(int numberFiles = 8; numberFiles <9; numberFiles++){
 
    	String word = "@data";																		
    	String file1 = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/mallory_CSFS_new.arff" ;
    	String file2 = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/mallory_CSFS_andrew.arff";
    	String outputArffName ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/mallory_CSFSANdrew_all.arff";

    	

    	int atDataLineNumberFile1 = MergeArffFiles.grepLineNumber(file1, word);
    	int atDataLineNumberFile2 = MergeArffFiles.grepLineNumber(file2, word);
    	
    	for(int firstFileAttributes=1; firstFileAttributes <atDataLineNumberFile1; firstFileAttributes++ )
    	{Util.writeFile(readSpecificLineNumber(file1, firstFileAttributes) + "\n", outputArffName, true);}
    	
    	for(int secondFileAttributes=4; secondFileAttributes <=atDataLineNumberFile2; secondFileAttributes++ )
    	{Util.writeFile(readSpecificLineNumber(file2, secondFileAttributes) + "\n", outputArffName, true);}

    	int numberOfInstances = (65 * numberFiles)-1; 
    	int numberOfInstances1 = 519; 


		int file2LineNumberStart=atDataLineNumberFile2+1;

//    	for(int i = atDataLineNumberFile1+1; i <= atDataLineNumberFile1 + numberOfInstances; i++)
        for(int i = atDataLineNumberFile1+1; i <= atDataLineNumberFile1 + numberOfInstances1; i++)
    	{
			String instID = getInstanceID(file1, i);

    		//Use this if the second file is in descending order
        	//for(int j=atDataLineNumberFile2+numberOfInstances;j>=file2LineNumberStart; j--)

        		//for normal case in ascending order
        	for(int j=file2LineNumberStart; j <= atDataLineNumberFile2+numberOfInstances; j++)
        	{
				System.out.println(j);
        		if (instID.equals(MergeArffFiles.getInstanceID(file2, j)))
        				{
        			
        					String firstPart = getInstance(file1, i);
        					String secondPart = getInstanceVector(file2, j);
        					System.out.println(firstPart);
        					
        					final Scanner scanner = new Scanner(outputArffName);
        					while (scanner.hasNextLine()) {
        					   final String lineFromFile = scanner.nextLine();
        					   if(lineFromFile.equals(firstPart)==false) { 
        						   Util.writeFile( firstPart+ "," +secondPart + "\n", outputArffName, true);
               		    			System.out.println(j);  
        					   }
        					}
        					
        					
        					
        		    		
        		    		
/*        		    		//Use this if the second file is in descending order
        		   		if(j<atDataLineNumberFile2+numberOfInstances-numberFiles){
        		    			atDataLineNumberFile2=j - numberOfInstances +numberFiles;
        		    		}
        		    		j=file2LineNumberStart-1;
        		    		//end of descending order  
*/        		    		
        		    	//if file2 is in ascending order	
        		    	//	if(j<atDataLineNumberFile2+numberOfInstances-numberFiles){
        		    	//	file2LineNumberStart= j-9;
        		    	//	j = atDataLineNumberFile2+numberOfInstances;        				      			
        		    //}
        		}
    		}
        	}
    	}    	
    	
    }
    public static String getInstanceID(String file, int lineNumber) throws IOException
    {
    	//will give an error if there is onl
    	String line = MergeArffFiles.readSpecificLineNumber(file, lineNumber);  	
    	String arr[] = line.split(",", 2);
    	String firstWord = arr[0];
    	return firstWord;
    }
    
    public static String getInstance(String file, int lineNumber) throws IOException
    {
    	//will give an error if there is onl
    	String line = MergeArffFiles.readSpecificLineNumber(file, lineNumber);  	
    	String arr[] = line.split(" ", 1);
    	String firstWord = arr[0];
    	return firstWord;
    }
      
    
    public static String getInstanceVector(String file, int lineNumber) throws IOException
    {
    	
    	String line = MergeArffFiles.readSpecificLineNumber(file, lineNumber);
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
