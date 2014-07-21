import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class DepthASTNode {
	
    public static void main(String[] args) throws Exception, IOException, InterruptedException {

    	String fileName ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/"
    			+ "SCAA_Datasets/AnalysisCode/for/"
    			+ "simpleforlabels.dep";
    	String featureText = Util.readFile( fileName);
    	int[] lines =getASTDepLines(featureText);
    	
    	for(int i=0; i<lines.length; i++)
    		System.out.println(lines[i]);
    }
	
	public static int[] getASTDepLines(String featureText)
	{		
		HashSet<String> functionIDs = new HashSet<String>();
		HashSet<String> functionIDs2 = new HashSet<String>();

        //take the function id in the beginning of the line.    
		String[] lines = featureText.split("\n");
		for(int i=0; i< lines.length; i++)
		{

	        String firstWord = lines[i].substring(0, featureText.indexOf('\t'));
	        if(!functionIDs.contains(firstWord))
	        functionIDs.add(firstWord);
		}
		int [] ASTDepLines=new int[functionIDs.size()];
		for(int i=0; i< lines.length; i++)
		{
	        String firstWord = lines[i].substring(0, featureText.indexOf('\t'));
	        if(i==0)
	        {
		    functionIDs2.add(firstWord);
	        }
	        else
	        {
	        	if(!functionIDs2.contains(firstWord)||(i==lines.length-1))
	        	{
	        		int lineNumber = i-1;
	        		ASTDepLines[functionIDs2.size()-1] = lineNumber;
	        	}
		    functionIDs2.add(firstWord);
	        }

		}       
	
	       return ASTDepLines;  
       
       
       
       
	}
}