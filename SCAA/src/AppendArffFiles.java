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
    	String file1 = "/Users/Aylin/Desktop/new_may/test_5.7_same25000_CONCENTRATE_APPEND.arff";
    	String file2 = "/Users/Aylin/Desktop/new_may/samenumber_append.arff";
		InputStream    fis;
		BufferedReader br;
		String         line;
    	int atDataLineNumberFile1 = AppendArffFiles.grepLineNumber(file1, word);
    	int atDataLineNumberFile2 = AppendArffFiles.grepLineNumber(file2, word);


//    	System.out.println(AppendArffFiles.readSpecificLineNumber(file1, atDataLineNumberFile1));
//    	System.out.println(AppendArffFiles.readSpecificLineNumber(file2, atDataLineNumberFile2));
    	
    	int numberOfInstances = 84;
    	
    	for(int i = atDataLineNumberFile1+1; i <= atDataLineNumberFile1 + numberOfInstances; i++)
    	{
    		int file2LineNumber=1;

    	//	System.out.println(AppendArffFiles.getInstanceID(file1, i));
    		fis = new FileInputStream(file2);
    		br = new BufferedReader(new InputStreamReader(fis, Charset.forName("UTF-8")));
    		while ((line = br.readLine()) != null) {
        		if (AppendArffFiles.getInstanceID(file1, i).equals(AppendArffFiles.getInstanceID(file2, file2LineNumber)))
        				{
        		String firstPart = getInstance(file1, i);
        		String secondPart = getInstanceVector(file2, file2LineNumber);
        		System.out.println(firstPart);
        			Util.writeFile( firstPart+ "," +secondPart + "\n", "/Users/Aylin/Desktop/new_may/samenumber_complete.txt", true);

        				}
        				
        				
    		file2LineNumber++;
    		}

    		// Done with the file
    		br.close();
    		br = null;
    		fis = null;    			
    		
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
    	String firstWord = arr[0];
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
