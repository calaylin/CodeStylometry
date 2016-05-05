import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;



public class FeatureExtractorConcurrent implements Runnable  {
	
	private String test_dir; 
	private String output_filename;
	private int start;
	private int end;

		    public FeatureExtractorConcurrent(String test_dir, 
			String output_filename,int start, int end){
				

		    	this.test_dir =test_dir;
		    	this.output_filename=output_filename;
		        this.start = start;
		        this.end = end;
		        
		    }

		    public static void main(String[] args){
		    	
				String test_dir = "";
		    	String output_filename = "";
				//test_dir.replace("/", "") ;
		    	
		    	//REMOVE COMMENTS
		    	//RemoveComments.removeComments(test_dir);

		       	List test_file_paths = Util.listTextFiles(test_dir);		       			
				int totalFiles = test_file_paths.size();
				int threads = 30;
				int extra = totalFiles % threads;
				int noFiles= totalFiles/threads;
		        Thread looper31 = null;

				
				
		        Thread looper1 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"1.arff" ,(noFiles*0),(noFiles*1)-1));
		        Thread looper2 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"2.arff",(noFiles*1),(noFiles*2)-1));
		        Thread looper3 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"3.arff" ,(noFiles*2),(noFiles*3)-1));
		        Thread looper4 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"4.arff",(noFiles*3),(noFiles*4)-1));		        
		        Thread looper5 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"5.arff" ,(noFiles*4),(noFiles*5)-1));
		        Thread looper6 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"6.arff",(noFiles*5),(noFiles*6)-1));
		        Thread looper7 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"7.arff" ,(noFiles*6),(noFiles*7)-1));
		        Thread looper8 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"8.arff",(noFiles*7),(noFiles*8)-1));		    
		        Thread looper9 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"9.arff" ,(noFiles*8),(noFiles*9)-1));
		        Thread looper10 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"10.arff",(noFiles*9),(noFiles*10)-1));		        
		        Thread looper11 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"11.arff" ,(noFiles*10),(noFiles*11)-1));
		        Thread looper12 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"12.arff",(noFiles*11),(noFiles*12)-1));		        
		        Thread looper13 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"13.arff" ,(noFiles*12),(noFiles*13)-1));
		        Thread looper14 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"14.arff",(noFiles*13),(noFiles*14)-1));
		        Thread looper15 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"15.arff" ,(noFiles*14),(noFiles*15)-1));
		        Thread looper16 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"16.arff" ,(noFiles*15),(noFiles*16)-1));
		        Thread looper17 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"17.arff" ,(noFiles*16),(noFiles*17)-1));
		        Thread looper18 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"18.arff" ,(noFiles*17),(noFiles*18)-1));
		        Thread looper19 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"19.arff" ,(noFiles*18),(noFiles*19)-1));
		        Thread looper20 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"20.arff" ,(noFiles*19),(noFiles*20)-1));
		        Thread looper21 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"21.arff" ,(noFiles*20),(noFiles*21)-1));
		        Thread looper22 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"22.arff",(noFiles*21),(noFiles*22)-1));		        
		        Thread looper23 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"23.arff" ,(noFiles*22),(noFiles*23)-1));
		        Thread looper24 = new Thread (new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"24.arff",(noFiles*23),(noFiles*24)-1));
		        Thread looper25 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"25.arff" ,(noFiles*24),(noFiles*25)-1));
		        Thread looper26 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"26.arff" ,(noFiles*25),(noFiles*26)-1));
		        Thread looper27 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"27.arff" ,(noFiles*26),(noFiles*27)-1));
		        Thread looper28 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"28.arff" ,(noFiles*27),(noFiles*28)-1));
		        Thread looper29 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"29.arff" ,(noFiles*28),(noFiles*29)-1));
		        Thread looper30 = new Thread(new 
		        		FeatureExtractorConcurrent(test_dir,output_filename+"30.arff" ,(noFiles*29),(noFiles*30)-1));
    
		        
		        
				if (extra > 0){
					   looper31 = new Thread(new 
				        		FeatureExtractorConcurrent(test_dir,
				        				output_filename+"31.arff" ,(noFiles*30),(totalFiles-1)));	
						looper31.start();
				}
		        
		        
		        looper1.start();
		        looper2.start();
		        looper3.start();
		        looper4.start();
		        looper5.start();
		        looper6.start();
		        looper7.start();
		        looper8.start();
		        looper9.start();
		        looper10.start();
		        looper11.start();
		        looper12.start();
		        looper13.start();
		        looper14.start();
		        looper15.start();
		        looper16.start();
		        looper17.start();
		        looper18.start();
		        looper19.start();
		        looper20.start();
		        looper21.start();
		        looper22.start();
		        looper23.start();
		        looper24.start();
		        looper25.start();
		        looper26.start();
		        looper27.start();
		        looper28.start();
		        looper29.start();
		        looper30.start();
		    }
		    @Override
		    public void run() {
		    	
		       	List test_file_paths = Util.listTextFiles(test_dir);		       			

				String text = "";
				//Writing the test arff
				Util.writeFile("@relation "+test_dir +"\n"+"\n",
				    			output_filename, true);
				Util.writeFile("@attribute instanceID_original {", output_filename, true);

				   	for(int j=0; j < test_file_paths.size();j++)
					{
						File sourceFile = new File(test_file_paths.get(j).toString());
						String fileName = sourceFile.getName() +"_"+ sourceFile.getParentFile().getParentFile().getName();
						Util.writeFile(fileName+",", output_filename, true);
						if ((j+1)==test_file_paths.size()){
						Util.writeFile("}"+"\n", output_filename, true);
						}
					} 	
		    
		
		  String [] cppKeywords = {"alignas",	"alignof",	"and",	"and_eq",	"asm",	"auto",	
				  "bitand",	"bitor",	"bool",	"break",	"case",	"catch",	"char",	"char16_t",	"char32_t",
				  "class",	"compl",	"const",	"constexpr",	"const_cast",	"continue",	"decltype",	"default",	
				  "delete",	"do",	"double",	"dynamic_cast",	"else",	"enum",	"explicit",	"export",	
				  "extern",	"FALSE",	"float",	"for",	"friend",	"goto",	"if",	"inline",	"int",	"long",	
				  "mutable",	"namespace",	"new",	"noexcept",	"not",	"not_eq",	"nullptr",	"operator",	"or",
				  "or_eq"	,"private"	,"protected"	,"public"	,"register",	"reinterpret_cast",	"return",	
				  "short",	"signed",	"sizeof",	"static",	"static_assert",	"static_cast",	"struct",	
				  "switch",	"template",	"this"	,"thread_local",	"throw",	"TRUE",	"try",	"typedef",	"typeid",
				  "typename",	"union",	"unsigned",	"using",	"virtual",	"void",	"volatile",	"wchar_t",	"while",
				  "xor",	"xor_eq", "override", "final"};
		  
		  

   	List test_cpp_paths = Util.listCPPFiles(test_dir);
   	for(int j=0; j < test_cpp_paths.size();j++ )
	{
		File fileCPP = new File(test_cpp_paths.get(j).toString());
		String fileName = fileCPP.getName();
		Util.writeFile(fileName+",", output_filename, true);
		if ((j+1)==test_cpp_paths.size())
			Util.writeFile("}"+"\n", output_filename, true);
	}

	Util.writeFile("@attribute 'functionIDCount' numeric"+"\n", output_filename, true);
	Util.writeFile("@attribute 'CFGNodeCount' numeric"+"\n", output_filename, true);
	Util.writeFile("@attribute 'ASTFunctionIDCount' numeric"+"\n", output_filename, true);
	Util.writeFile("@attribute 'getMaxDepthASTLeaf' numeric"+"\n", output_filename, true);

//	Util.writeFile("@attribute 'AverageASTDepth' numeric"+"\n", output_filename, true);

	
    try {
		String[] APIsymbols = FeatureCalculators.uniqueAPISymbols(test_dir);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
    //uniqueASTTypes does not contain user input, such as function and variable names
    //uniqueDepASTTypes contain user input, such as function and variable names
    
//Use the following for syntactic inner nodes and code leaves (remember to change astlabel.py accordingly!
       String[] ASTtypes = null;
	try {
		ASTtypes = FeatureCalculators.uniqueDepASTTypes(test_dir);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
       String[] wordUnigramsCPP = null;
	try {
		wordUnigramsCPP = FeatureCalculators.wordUnigramsCPP(test_dir);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
      	String[] ASTNodeBigrams = null;
		try {
			ASTNodeBigrams = BigramExtractor.getASTNodeBigrams(test_dir);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	

    //if only interested in syntactic features use this if the dep file contains user input    
 //   String[] ASTtypes =FeatureCalculators.uniqueASTTypes(test_dir);

    
//    for (int i=0; i<APIsymbols.length; i++)	
//    {	Util.writeFile("@attribute 'APIsymbols["+i+"]' numeric"+"\n", output_filename, true);}
    /*    for (int i=0; i<APIsymbols.length; i++)	
    {	Util.writeFile("@attribute 'APIsymbolsTFIDF["+i+"]' numeric"+"\n", output_filename, true);}
    */
//    for (int i=0; i<ASTtypes.length; i++)	
//    {	Util.writeFile("@attribute 'ASTtypes["+i+"]' numeric"+"\n", output_filename, true);}
/*    for (int i=0; i<ASTtypes.length; i++)	
    {	Util.writeFile("@attribute 'ASTtypesTFIDF["+i+"]' numeric"+"\n", output_filename, true);}
*/
      	
    	for (int i=0; i<ASTNodeBigrams.length; i++)		
  	  {  	ASTNodeBigrams[i] = ASTNodeBigrams[i].replace("'", "apostrophesymbol");
  	    	Util.writeFile("@attribute 'ASTNodeBigramsTF "+i+"=["+ASTNodeBigrams[i]+"]' numeric"+"\n", output_filename, true);}
      
    	for (int i=0; i<wordUnigramsCPP.length; i++)	   	
       {  	wordUnigramsCPP[i] = wordUnigramsCPP[i].replace("'", "apostrophesymbol");
            	Util.writeFile("@attribute 'wordUnigramsC "+i+"=["+wordUnigramsCPP[i]+"]' numeric"+"\n", output_filename, true);}

    	  
    for (int i=0; i<ASTtypes.length; i++)	
    	
  {  	ASTtypes[i] = ASTtypes[i].replace("'", "apostrophesymbol");
    	Util.writeFile("@attribute 'ASTNodeTypesTF "+i+"=["+ASTtypes[i]+"]' numeric"+"\n", output_filename, true);}
    for (int i=0; i<ASTtypes.length; i++)	
  {	    ASTtypes[i] = ASTtypes[i].replace("'", "apostrophesymbol");
    	Util.writeFile("@attribute 'ASTNodeTypesTFIDF "+i+"=["+ASTtypes[i]+"]' numeric"+"\n", output_filename, true);}
    for (int i=0; i<ASTtypes.length; i++)	
  {	    ASTtypes[i] = ASTtypes[i].replace("'", "apostrophesymbol");
    	Util.writeFile("@attribute 'ASTNodeTypeAvgDep "+i+"=["+ASTtypes[i]+"]' numeric"+"\n", output_filename, true);}
    for (int i=0; i<cppKeywords.length; i++)	
  {	Util.writeFile("@attribute 'cppKeyword "+i+"=["+cppKeywords[i]+"]' numeric"+"\n", output_filename, true);}

    File authorFileName = null;
	//Writing the classes (authorname)
	Util.writeFile("@attribute 'authorName' {",output_filename, true);
	for(int i=0; i< test_file_paths.size(); i++){
		int testIDlength = test_file_paths.get(i).toString().length();   
		authorFileName= new File(test_file_paths.get(i).toString());
//		String authorName= authorFileName.getParentFile().getName();
		String authorName= authorFileName.getParentFile().getName();
		   System.out.println(authorName);

		text = text.concat(authorName + ",");  
		String[] words = text.split( ",");
		  Set<String> uniqueWords = new HashSet<String>();

		   for (String word : words) {
		       uniqueWords.add(word);
		   }
		   words = uniqueWords.toArray(new String[0]);
		   int authorCount = words.length;
		   if (i+1==test_file_paths.size()){
		   for (int j=0; j< authorCount; j++){
			   {System.out.println(words[j]);
				if(j+1 == authorCount)
				{
			   Util.writeFile(words[j]+"}"+"\n\n",output_filename, true);
				}
				else
				{
				Util.writeFile(words[j]+","+"",output_filename, true);

					}
				}
			   }

		   }
		   
		 }
	
   	
	Util.writeFile("@data"+"\n", output_filename, true);	
	//Finished defining the attributes
	
	
	//EXTRACT LABELED FEATURES
   //	for(int i=0; i< test_file_paths.size(); i++){
		for(int i=start; i<=end; i++){

		String featureText = null;
		try {
			featureText = Util.readFile(test_file_paths.get(i).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int testIDlength = test_file_paths.get(i).toString().length(); 
		authorFileName= new File(test_file_paths.get(i).toString());
		String authorName= authorFileName.getParentFile().getName();

		System.out.println(test_file_paths.get(i));
		System.out.println("authorname"+authorName);
		File fileCPPID = new File(test_cpp_paths.get(i).toString());
		String fileNameID = fileCPPID.getName();
		Util.writeFile(fileNameID+",", output_filename, true);
		Util.writeFile(FeatureCalculators.functionIDCount(featureText)+",", output_filename, true);
		String ASTText = null;
		try {
			ASTText = Util.readFile(test_file_paths.get(i).toString().substring(0,testIDlength-3)+"ast");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String DepASTText = null;
		try {
			DepASTText = Util.readFile(test_file_paths.get(i).toString().substring(0,testIDlength-3)+"dep");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sourceCode = null;
		try {
			sourceCode = Util.readFile(test_file_paths.get(i).toString().substring(0,testIDlength-3)+"cpp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Util.writeFile(FeatureCalculators.CFGNodeCount(ASTText)+",", output_filename, true);
		Util.writeFile(FeatureCalculators.ASTFunctionIDCount(ASTText)+",", output_filename, true);
		try {
			Util.writeFile(DepthASTNode.getMaxDepthASTLeaf(DepASTText, ASTtypes)+",", output_filename, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		
		
		
//		Util.writeFile(FeatureCalculators.averageASTDepth(ASTText)+",", output_filename, true);


		//get count of each API symbol present	 
/*	    float[] symCount = FeatureCalculators.APISymbolTF(featureText, APIsymbols );
	    for (int j=0; j<APIsymbols.length; j++)
		{Util.writeFile(symCount[j]+",", output_filename, true);}	
*/
/*		//get tfidf of each API symbol present	 
	    float[] symTFIDF = FeatureCalculators.APISymbolTFIDF(featureText,test_dir, APIsymbols );
	    for (int j=0; j<APIsymbols.length; j++)
		{Util.writeFile(symTFIDF[j]+",", output_filename, true);}	 */

	    
/*	    //get count of each AST type present	 
	    float[] typeCount = FeatureCalculators.ASTTypeTF(ASTText, ASTtypes );
	    for (int j=0; j<ASTtypes.length; j++)
		{Util.writeFile(typeCount[j] +",", output_filename, true);}	*/
	    
/*		//get tfidf of each AST Type present	 
	    float[] astTypeTFIDF = FeatureCalculators.ASTTypeTFIDF(featureText, test_dir, ASTtypes);
	    for (int j=0; j<ASTtypes.length; j++)
		{Util.writeFile(astTypeTFIDF[j]+",", output_filename, true);}	*/

	    //get frequency of each ASTnodebigram in CPP source file's AST	 
		float[] bigramCount = null;
		try {
			bigramCount = BigramExtractor.getASTNodeBigramsTF(DepASTText, ASTNodeBigrams );
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int j=0; j<ASTNodeBigrams.length; j++)
		{Util.writeFile(bigramCount[j] +",", output_filename, true);}	    
		    
	    //get count of each wordUnigram in CPP source file	 
	    float[] wordUniCount = FeatureCalculators.WordUnigramTF(sourceCode, wordUnigramsCPP);
	    for (int j=0; j<wordUniCount.length; j++)
		{Util.writeFile(wordUniCount[j] +",", output_filename, true);}	
	    
	    //get count of each ASTtype not-DepAST type present	 
	    float[] typeCount = FeatureCalculators.DepASTTypeTF(DepASTText, ASTtypes );
	    for (int j=0; j<ASTtypes.length; j++)
		{Util.writeFile(typeCount[j] +",", output_filename, true);}	
	    
		//get tfidf of each AST Type present	 
	    float[] DepastTypeTFIDF = null;
		try {
			DepastTypeTFIDF = FeatureCalculators.DepASTTypeTFIDF(DepASTText, test_dir, ASTtypes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    for (int j=0; j<ASTtypes.length; j++)
		{Util.writeFile(DepastTypeTFIDF[j]+",", output_filename, true);}	
		
    	float[] depFeature = null;
		try {
			depFeature = DepthASTNode.getAvgDepthASTNode(DepASTText,ASTtypes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	for(int k=0;k<depFeature.length;k++)
		{Util.writeFile(depFeature[k] +",", output_filename, true);}	
	    
    	float [] cppKeywordsTF =FeatureCalculators.getCPPKeywordsTF(sourceCode);
    	for(int k=0;k<cppKeywordsTF.length;k++)
		{Util.writeFile(cppKeywordsTF[k] +",", output_filename, true);}	
    	
    	
		Util.writeFile(authorName+"\n", output_filename, true);

   	
   			}
       	}
   	}
   
	  
   	
	

	











