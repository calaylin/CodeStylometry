import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;


public class DepthASTNode {
	
    public static void main(String[] args) throws Exception, IOException, InterruptedException {
    	String dataDir ="/Users/Aylin/Desktop/Drexel/2014/ARLInternship/"
    			+ "SCAA_Datasets/AnalysisCode/for/";
    	String fileName =dataDir+"simpleforlabels.dep";
        String[] DepASTTypes = FeatureCalculators.uniqueDepASTTypes(dataDir);
    	String featureText = Util.readFile( fileName);
    	float [] depFeature =getAvgDepthASTNode(featureText,DepASTTypes);
    	for(int i=0;i<depFeature.length;i++)
    		System.out.println(depFeature[i]);
    	
    }
	
    
	public static float[] getAvgDepthASTNode(String featureText, String[] ASTTypes) throws IOException
	{
		int [] lines = getASTDepLines(featureText);
		float [] occurrences=new float[ASTTypes.length];
		float [] totalDepth=new float[ASTTypes.length];;
		float [] avgDepth=new float[ASTTypes.length];;

		String textAST=null;
		for (int i=0; i<lines.length; i++)
		{
			textAST = readLineNumber(featureText, lines[i]);
			for (int j=0; j< ASTTypes.length; j++)
			{
			  	 String str = ASTTypes[j].toString();
			  	 String str1 = "(" + str + ")";
			  	 String str2 = "(" + str + "(";
			  	 String str3 = ")" + str + ")";
			  	 String str4 = ")" + str + "(";

			  	 float occurrencesHere = StringUtils.countMatches(textAST, str1) 
			  			 +StringUtils.countMatches(textAST, str2)
			  			 +StringUtils.countMatches(textAST, str3)
			  			 +StringUtils.countMatches(textAST, str4);	
			  	 occurrences[j] = occurrences[j] + occurrencesHere;
			  	 
		         WholeWordIndexFinder finder = new WholeWordIndexFinder(textAST);
		         List<IndexWrapper> indexes = finder.findIndexesForKeyword(str);
			  	 for(int k=0; k<(int)occurrencesHere; k++)
			  	 {
			  	   int rightParanthesis =0;//(
			  	   int leftParanthesis =0;//)

			  	   for (Character c: textAST.substring(0,indexes.get(k).getStart()).toCharArray()) {
			  	       if (c.equals('(')) {
			  	    	 rightParanthesis++;
			  	       }
			  	     if (c.equals(')')) {
			  	    	 leftParanthesis++;
			  	       }
			  	   }
			  	 totalDepth[j]= totalDepth[j]+rightParanthesis-leftParanthesis;		  	   
			  	 }
			  	 
			  	 if(occurrences[j]==0)
			  		avgDepth[j]=-1;
			  	 else if((occurrences[j]!=0)&&(totalDepth[j]==0))
				  	avgDepth[j]=0;
			  	 else
			  	 avgDepth[j]= totalDepth[j]/occurrences[j];		  	 
			}		
		}
		return avgDepth;
	}

    
    
    //line number starts from 0
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
    
	
	
	//starts from 0
    public static String readLineNumber (String featureText, int lineNumber) throws IOException
    {
    	List<String> lines = IOUtils.readLines(new StringReader(featureText));  	
    	return lines.get(lineNumber);
    }
	
}