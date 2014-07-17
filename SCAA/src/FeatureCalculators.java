import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.apache.commons.lang3.StringUtils;

/**
 * Calculators.
 * These features are currently being extended.
 * 
 * @author Aylin Caliskan-Islam (ac993@drexel.edu)
 */


public class FeatureCalculators {
	

    public FeatureCalculators( ) {
    }
    
    public static void preprocessDataToAPISymbols(String filePath) throws IOException, InterruptedException{
    	//should take filename to test each time
    	//just needs the name of the directory with the authors and their source files as an input
    	//and outputs .txt files in source file's corresponding directory - has only APISymbols 
   
    	 Runtime dbTime = Runtime.getRuntime();
    	 Runtime joernTime = Runtime.getRuntime();
    	 Runtime scriptTime = Runtime.getRuntime();

          Process stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
           });
           stopDB.waitFor();
           BufferedReader br = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
           while(br.ready())
               System.out.println(br.readLine());
           
           Process deleteIndex = dbTime.exec(new String[]{"/bin/sh", "-c","rm -r /Users/Aylin/git/joern/.joernIndex"});
           deleteIndex.waitFor();

           Process joernRun = joernTime.exec(new String[]{"/bin/sh", "-c", 
        		   "cd /Users/Aylin/git/joern"+"\n"+ "java -jar /Users/Aylin/git/joern/bin/joern.jar " + filePath });
           joernRun.waitFor();
           BufferedReader br1 = new BufferedReader(new InputStreamReader(joernRun.getInputStream()));
           while(br1.ready())
               System.out.println(br1.readLine());

           Process startDB = dbTime.exec(new String[]{"/bin/sh","-c",  
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j start"        		   
           });
           startDB.waitFor();
           BufferedReader br2 = new BufferedReader(new InputStreamReader(startDB.getInputStream()));
           while(br2.ready())
               System.out.println(br2.readLine());
        
           
   		Process runScript = scriptTime.exec(new String[]{"/bin/sh", "-c", 
        		   "cd /Users/Aylin/git/joern-tools"+"\n"+ "python /Users/Aylin/git/joern-tools/template.py"
           });
           runScript.waitFor();
           
           
           BufferedReader br3 = new BufferedReader(new InputStreamReader(runScript.getInputStream()));
     //  	String output_filename = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/small_jam_data/byName/cgy4ever/cgy4ever_3_0.txt";
           String output_filename = filePath.substring(0, filePath.length()-3).concat("txt");
       	while(br3.ready())
           { //   System.out.println(br3.readLine());
           Util.writeFile(br3.readLine().toString() +"\n",output_filename, true);
   		   }
       	
       	
       	
        stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
      		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
         });
         stopDB.waitFor();
         BufferedReader br4 = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
         while(br4.ready())
             System.out.println(br4.readLine());
      
    	
    }
    
    public static void preprocessDataToASTFeatures(String filePath) throws IOException, InterruptedException, ScriptException{
    	//should take filename to test each time
    	//just needs the name of the directory with the authors and their source files as an input
    	//and outputs .ast files in source file's corresponding directory - has AST information 
   
    	 Runtime dbTime = Runtime.getRuntime();
    	 Runtime joernTime = Runtime.getRuntime();
    	 Runtime scriptTime = Runtime.getRuntime();

          Process stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
           });
           stopDB.waitFor();
           BufferedReader br = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
           while(br.ready())
               System.out.println(br.readLine());
           
           Process deleteIndex = dbTime.exec(new String[]{"/bin/sh", "-c","rm -r /Users/Aylin/git/joern/.joernIndex"});
           deleteIndex.waitFor();

           Process joernRun = joernTime.exec(new String[]{"/bin/sh", "-c", 
        		   "cd /Users/Aylin/git/joern"+"\n"+ "java -jar /Users/Aylin/git/joern/bin/joern.jar " + filePath });
           joernRun.waitFor();
           BufferedReader br1 = new BufferedReader(new InputStreamReader(joernRun.getInputStream()));
           while(br1.ready())
               System.out.println(br1.readLine());

           Process startDB = dbTime.exec(new String[]{"/bin/sh","-c",  
        		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j start"        		   
           });
           startDB.waitFor();
           BufferedReader br2 = new BufferedReader(new InputStreamReader(startDB.getInputStream()));
           while(br2.ready())
               System.out.println(br2.readLine());

           String output_filename = filePath.substring(0, filePath.length()-3).concat("dep");
           String cmd1 = "echo \'queryNodeIndex(\"type:Function\").id\' | "
           		+ "python /Users/Aylin/git/joern-tools/lookup.py -g |  "
           		+ "python /Users/Aylin/git/joern-tools/getAst.py | "
           		+ "python /Users/Aylin/git/joern-tools/astLabel.py |  "
           		+ "python /Users/Aylin/git/joern-tools/ast2Features.py >" 
           		+ output_filename;
           
           Process joernscripts = dbTime.exec((new String[]{"/bin/sh","-c", cmd1}));

           joernscripts.waitFor();
              BufferedReader br5 = new BufferedReader(new InputStreamReader(joernscripts.getInputStream()));
              while(br5.ready())
                  System.out.println(br5.readLine());
             
              BufferedReader br6 = new BufferedReader(new InputStreamReader(joernscripts.getErrorStream()));
              while(br6.ready())
                  System.out.println(br6.readLine());
        
        	    
        stopDB = dbTime.exec(new String[]{"/bin/sh", "-c",
     		   "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/joern_related/neo4j-community-1.9.7/bin/neo4j stop"        		   
        });
        stopDB.waitFor();
        BufferedReader br4 = new BufferedReader(new InputStreamReader(stopDB.getInputStream()));
        while(br4.ready())
            System.out.println(br4.readLine());
      
    	
    }
    
    
    
    
	public static void main(String[] args) throws Exception, IOException, InterruptedException {

    	
//    String test_cpp_dir = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/small_jam_data/byName/";	
    String test = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/small_jam_data/byName/test/";	
    String test_cpp_dir = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/6FilesPerAuthor/";
       List test_file_paths = Util.listCPPFiles(test_cpp_dir); //use this for preprocessing
       
 //   List test_file_paths = Util.listTextFiles(test_cpp_dir); // use this to list txt files with API symbols
    for(int i=0; i< test_file_paths.size(); i++){
//		int testIDlength = test_file_paths.get(i).toString().length();    		
		String filePath = test_file_paths.get(i).toString();  
//    System.out.println(filePath);
		
//	preprocessDataToAPISymbols(filePath);
//	preprocessDataToASTFeatures(filePath);
    
    }


  
   //Get API symbols and their count in each txt file
//   String[] APIsymbols = uniqueAPISymbols(test);
	String dataDir= "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/AnalysisCode/";
    String[] ASTTypes = uniqueASTTypes(dataDir);
    String[] DepASTTypes = uniqueDepASTTypes(dataDir);

	String featureText = Util.readFile("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/AnalysisCode/for/simpleforlabels.dep");
/*	for (int i=0; i<ASTTypes.length; i++)
    { System.out.println(ASTTypes[i]);}*/
	for (int i=0; i<DepASTTypes.length; i++)
    { System.out.println(DepASTTypes[i]);}
//    int[] symCount = APISymbolCount(featureText, APIsymbols );
    float[] symCount = DepASTTypeTFIDF(featureText, dataDir, ASTTypes );
    float[] symCount1 = DepASTTypeTF(featureText, ASTTypes );

    for (int i=0; i<ASTTypes.length; i++)
    {    float idf = DepASTTypeIDF( dataDir, ASTTypes[i].toString() );
    	System.out.println("tfidf: "+symCount[i] + " tf: " +symCount1[i] + " idf: " + idf );}
    
    
    }
     

    public static String[] uniqueASTTypes (String dirPath) throws IOException{
  	  
 	   
	    List test_file_paths = Util.listASTFiles(dirPath);
		HashSet<String> uniqueWords = new HashSet<String>();

	    for(int i=0; i< test_file_paths.size(); i++){
			String filePath = test_file_paths.get(i).toString();  
	   
	   String inputText =Util.readFile(filePath);
	   Pattern pattern = Pattern.compile("type:(.*?)\n");
	   Matcher matcher = pattern.matcher(inputText);
	   while (matcher.find()) {
	       uniqueWords.add(matcher.group(1));
	   }}
	   String[] words = uniqueWords.toArray(new String[0]);

      return words;
}   
 
    
     public static String[] uniqueAPISymbols (String dirPath) throws IOException{
	  
	   
	    List test_file_paths = Util.listTextFiles(dirPath);
		HashSet<String> uniqueWords = new HashSet<String>();

	    for(int i=0; i< test_file_paths.size(); i++){
			String filePath = test_file_paths.get(i).toString();  
	   
	   String inputText =Util.readFile(filePath);
	   Pattern pattern = Pattern.compile("u'(.*?)'");
	   Matcher matcher = pattern.matcher(inputText);
	   while (matcher.find()) {
	       uniqueWords.add(matcher.group(1));
	   }}
	   String[] words = uniqueWords.toArray(new String[0]);

       return words;
}
     
     public static String[] uniqueDepASTTypes(String dirPath) throws IOException{
   	  
  	   
	    List test_file_paths = Util.listDepFiles(dirPath);
		HashSet<String> uniqueWords = new HashSet<String>();

	    for(int i=0; i< test_file_paths.size(); i++){
			String filePath = test_file_paths.get(i).toString();  
	   
	   String inputText =Util.readFile(filePath);
	   Pattern pattern = Pattern.compile("([\\w']+)");
	   Matcher matcher = pattern.matcher(inputText);
	   while (matcher.find()) {
	       uniqueWords.add(matcher.group(1));
	   }}
	   String[] words = uniqueWords.toArray(new String[0]);

       return words;
}
     
     
     
     //not normalized by the number of APISymbols in the source code
     public static float [] APISymbolTF (String featureText, String[] APISymbols )
     {    
     float symbolCount = APISymbols.length;
     float [] counter = new float[(int) symbolCount];
     for (int i =0; i<symbolCount; i++){
//if case insensitive, make lowercase
//   String str = APISymbols[i].toString().toLowerCase();
  	 String str = "u'"+APISymbols[i].toString()+"'";
//if case insensitive, make lowercase
//   strcounter = StringUtils.countMatches(featureText.toLowerCase(), str);
  	 counter[i] = StringUtils.countMatches(featureText, str);  	   

     }
     return counter;
     }  
     
     public static float APISymbolIDF (String datasetDir, String APISymbol ) throws IOException
	 {    
			
		 float counter = 0;
		 float IDFcounter = 0;

	
		 File file = new File(datasetDir);
	     String[] directories = file.list(new FilenameFilter() {
	    	 @Override
	     public boolean accept(File current, String name) 
	    	 {
	    		 return new File(current, name).isDirectory();
			 }
				   });
	     int dirLen = directories.length;
	
		 for(int j=0; j< dirLen; j++)
			{
			String authorName = directories[j];
			List test_file_paths = Util.listTextFiles(datasetDir+authorName+"/");
	 		for(int i=0; i< test_file_paths.size(); i++)
	 		{
	 			String featureText = Util.readFile(test_file_paths.get(i).toString());
	 		  	 String str = "u'"+APISymbol+"'";
	 		  	 int termFrequencyAuthor = StringUtils.countMatches(featureText, str);  	
	 		  	 if (termFrequencyAuthor>0)
	 		  		 counter++;
	 		} 
	 		if(counter>0)
	 			IDFcounter++;
	 		
	 }
		 if (IDFcounter==0)
		 {return 0;}
		return (float) ((Math.log(dirLen/IDFcounter))/ (Math.log(2)));
	
	 }

	public static float [] APISymbolTFIDF (String featureText, String datasetDir,String[] APISymbols ) throws IOException
     {    
     float symbolCount = APISymbols.length;
     float tf =0;
     float idf = 0;
     float [] counter = new float[(int) symbolCount];
     for (int i =0; i<symbolCount; i++){
//if case insensitive, make lowercase
//   String str = APISymbols[i].toString().toLowerCase();
  	 String str = "u'"+APISymbols[i].toString()+"'";

  	 tf = StringUtils.countMatches(featureText, str);  	
  	 idf = APISymbolIDF(datasetDir, APISymbols[i].toString());
  	 counter[i] = tf * idf;
     }
     return counter;
     }  
     
     
     //not normalized by the number of ASTTypes in the source code in the source code
     public static float [] ASTTypeTF (String featureText, String[] ASTTypes )
     {    
     float symbolCount = ASTTypes.length;
     float [] counter = new float[(int) symbolCount];
     for (int i =0; i<symbolCount; i++){
//if case insensitive, make lowercase
//   String str = APISymbols[i].toString().toLowerCase();
  	 String str = "type:"+ASTTypes[i].toString()+"\n";
//if case insensitive, make lowercase
//   strcounter = StringUtils.countMatches(featureText.toLowerCase(), str);
  	 counter[i] = StringUtils.countMatches(featureText, str);  	   

     }
     return counter;
     }   
     //use this for dep file with label AST
     public static float [] DepASTTypeTF (String featureText, String[] ASTTypes )
     {    
     float symbolCount = ASTTypes.length;
     float [] counter = new float[(int) symbolCount];
     for (int i =0; i<symbolCount; i++){
//if case insensitive, make lowercase
//   String str = APISymbols[i].toString().toLowerCase();
  	 String str = ASTTypes[i].toString();
//if case insensitive, make lowercase
//   strcounter = StringUtils.countMatches(featureText.toLowerCase(), str);
  	 counter[i] = StringUtils.countMatches(featureText, str);  	   

     }
     return counter;
     }   
     //use this for dep file with label AST
     public static float DepASTTypeIDF (String datasetDir, String ASTType ) throws IOException
	 {    
			
		 float counter = 0;
		 float IDFcounter = 0;

		 File file = new File(datasetDir);
	     String[] directories = file.list(new FilenameFilter() {
	    	 @Override
	     public boolean accept(File current, String name) 
	    	 {
	    		 return new File(current, name).isDirectory();
			 }
				   });
	     float dirLen = directories.length;
		 for(int j=0; j< dirLen; j++)
			{
			String authorName = directories[j];
			List test_file_paths = Util.listDepFiles(datasetDir+authorName+"/");
	 		for(int i=0; i< test_file_paths.size(); i++)
	 		{
	 			String featureText = Util.readFile(test_file_paths.get(i).toString());
	 		  	 String str = ASTType;
	 		  	 int termFrequencyAuthor = StringUtils.countMatches(featureText, str);  	
	 		  	 if (termFrequencyAuthor>0)
	 		  		 counter++;
	 		} 
	 		if(counter>0)
	 			IDFcounter++;
	 		
	 }
		 if (IDFcounter==0)
		 {return 0;}
		return (float) ((Math.log(dirLen/IDFcounter))/ (Math.log(2)));
	
	 }
     //use this for dep file with label AST
	public static float [] DepASTTypeTFIDF (String featureText, String datasetDir, String[] ASTTypes ) throws IOException
	   {    
	   float symbolCount = ASTTypes.length;
	   float idf = 0;
	   float tf = 0;
	   float [] counter = new float[(int) symbolCount];
	   for (int i =0; i<symbolCount; i++){
	//if case insensitive, make lowercase
	// String str = APISymbols[i].toString().toLowerCase();
		 String str = ASTTypes[i].toString();
		 
	//if case insensitive, make lowercase
	// strcounter = StringUtils.countMatches(featureText.toLowerCase(), str);
		 
		 tf = StringUtils.countMatches(featureText, str);  	
		 idf = DepASTTypeIDF(datasetDir, ASTTypes[i].toString());
		 counter[i] = tf * idf;
	   }
	   return counter;
	   }

	public static float ASTTypeIDF (String datasetDir, String ASTType ) throws IOException
     {    
    		
		 float counter = 0;
		 float IDFcounter=0;
    	 File file = new File(datasetDir);
   	     String[] directories = file.list(new FilenameFilter() {
   	    	 @Override
         public boolean accept(File current, String name) 
   	    	 {
   	    		 return new File(current, name).isDirectory();
    		 }
    			   });
   	     float dirLen = directories.length;
    	 for(int j=0; j< dirLen; j++)
    		{
  			String authorName = directories[j];
    		List test_file_paths = Util.listASTFiles(datasetDir+authorName+"/");
     		for(int i=0; i< test_file_paths.size(); i++)
     		{
     			String featureText = Util.readFile(test_file_paths.get(i).toString());
     		  	 String str = "type:"+ASTType+"\n";
	 		  	 int termFrequencyAuthor = StringUtils.countMatches(featureText, str);  	
	 		  	 if (termFrequencyAuthor>0)
	 		  		 counter++;
	 		} 
	 		if(counter>0)
	 			IDFcounter++;
	 		
	 }
		 if (IDFcounter==0)
		 {return 0;}
		return (float) ((Math.log(dirLen/IDFcounter))/ (Math.log(2)));
	
	 }
	
	
     public static float [] ASTTypeTFIDF (String featureText, String datasetDir, String[] ASTTypes ) throws IOException
     {    
     float symbolCount = ASTTypes.length;
     float idf = 0;
     float tf = 0;
     float [] counter = new float[(int) symbolCount];
     for (int i =0; i<symbolCount; i++){
//if case insensitive, make lowercase
//   String str = APISymbols[i].toString().toLowerCase();
  	 String str = "type:"+ASTTypes[i].toString()+"\n";
  	 
//if case insensitive, make lowercase
//   strcounter = StringUtils.countMatches(featureText.toLowerCase(), str);
  	 
  	 tf = StringUtils.countMatches(featureText, str);  	
  	 idf = ASTTypeIDF(datasetDir, ASTTypes[i].toString());
  	 counter[i] = tf * idf;
     }
     return counter;
     }   
     
   public static float wordCountIndex(String inputText){
	   //returns the word count separated by spaces
	    if (inputText == null)
	       return 0;
	    return inputText.trim().split("\\s+").length;
	}
  
   
   public static int functionIDCount (String featureText)
	  {		   int counter = 0;

			   String str = "FunctionId";
			   counter = StringUtils.countMatches(featureText, str);
		   return counter;
		   
		   }   
   
   public static int CFGNodeCount (String ASTText)
	  {		   int counter = 0;

			   String str = "isCFGNode:";
			   counter = StringUtils.countMatches(ASTText, str);
		   return counter;
		   
		   }      
   
   public static int ASTFunctionIDCount (String ASTText)
	  {		   int counter = 0;

			   String str = "functionId:";
			   counter = StringUtils.countMatches(ASTText, str);
		   return counter;
		   
		   } 
   
   public static float averageASTDepth (String ASTText)
   {    
   float ASTFunctionIDCountNo = ASTFunctionIDCount(ASTText);
   float counter = 0;   
   
   
//   String str1 = ")";
//   String str2 = "(";

   String lines[] =  ASTText.split("\\n");
   for (int i =0; i<lines.length; i++)
   {
	   if (lines[i].startsWith("functionId:"))
	   {
		   for(char c : lines[i].toCharArray()){
			      if(c == ')' ){
			    	  counter++;
			      }
			      if(c == '(' ){
			    	  counter++;
			      }
//	   counter = counter + StringUtils.countMatches(ASTText, str1);
//	   counter = counter + StringUtils.countMatches(ASTText, str2);
	   }	   
   }}

//   return counter;

   return counter/ASTFunctionIDCountNo;
   
   
   }  
   
   
   
   
   public static int DictionaryIndex (String inputText)
   {
	   
   String [] dictionaryWords = {"a choice",	"a lie",   "your option"};

   
   
   
   int counter = 0;
   int privacyPhraseCount = dictionaryWords.length;
   for (int i =0; i<privacyPhraseCount; i++){
	   int strcounter=0;
	   String str = dictionaryWords[i].toString().toLowerCase();
	   //if case insensitive, make lowercase
	   strcounter = StringUtils.countMatches(inputText.toLowerCase(), str);
	   counter=counter+strcounter;
   }   
   
   return counter;
   
   }

	  
   public static int countQuotesIndex (String inputText){
   int quote_score =0;
   for (Character c: inputText.toCharArray()) {
       if (c.equals('\"')) {

       	quote_score++;
       }
   }

   return quote_score;

}	  
	  
   

   
   
   
	  public static void wordsCount (String featureText) throws IOException 
	   {
		   
		   String[] splitted = featureText.split(" ");
		      HashMap hm = new HashMap();
		      int x;
		   
		   for (int i = 0; i < splitted.length; i++) {
	            if (!hm.containsKey(splitted[i])) {
	                hm.put(splitted[i], 1);
	            } else {
	                hm.put(splitted[i], (Integer) hm.get(splitted[i]) + 1);
	            }
	        }
		   
		   for (Object word : hm.keySet()){
	            System.out.println(word + " " + (Integer) hm.get(word));
	        }
		   
		   
			for(int i=0; i<50; i++)
			{
				
			}}
}