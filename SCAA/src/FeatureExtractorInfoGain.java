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

/**
 * FeatureExtractor writes extracted features to arff file to be used with WEKA
 * @author Aylin Caliskan-Islam (ac993@drexel.edu)
 */

public class FeatureExtractorInfoGain {
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
		
		//list the cppKeywords that appear in infogain
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
		  

    	String output_filename = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAAarffs/bigExperiments/InfoGain/" +"InfoGain_FilesPerAuthor_bigExperiments.arff" ;
		String test_dir = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/bigExperiments/9FilesExactlyPerAuthor_2014/";
       	List test_file_paths = Util.listTextFiles(test_dir);

	String text = "";
  	//Writing the test arff
  	//first specify relation
	Util.writeFile("@relation InfoGain "+"\n"+"\n", output_filename, true);
	Util.writeFile("@attribute instanceID {", output_filename, true);
	
   	List test_cpp_paths = Util.listCPPFiles(test_dir);
   	for(int j=0; j < test_cpp_paths.size();j++ )
	{
		File fileCPP = new File(test_cpp_paths.get(j).toString());
		String fileName = fileCPP.getName();
		Util.writeFile(fileName+",", output_filename, true);
		if ((j+1)==test_cpp_paths.size())
			Util.writeFile("}"+"\n", output_filename, true);
	}

//	Util.writeFile("@attribute 'functionIDCount' numeric"+"\n", output_filename, true);
//	Util.writeFile("@attribute 'CFGNodeCount' numeric"+"\n", output_filename, true);
//	Util.writeFile("@attribute 'ASTFunctionIDCount' numeric"+"\n", output_filename, true);
	Util.writeFile("@attribute 'getMaxDepthASTLeaf' numeric"+"\n", output_filename, true);

    
//List the info gain nodes
    	String[] ASTtypesTF = {""};
        String[] ASTtypesTFIDF = {""};
        String[] ASTtypesAvgDep = {""};

    for (int i=0; i<ASTtypesTF.length; i++)	
    	
  {  	ASTtypesTF[i] = ASTtypesTF[i].replace("'", "apostrophesymbol");
    	Util.writeFile("@attribute 'ASTNodeTypesTF "+i+"=["+ASTtypesTF[i]+"]' numeric"+"\n", output_filename, true);}
    
    for (int i=0; i<ASTtypesTFIDF.length; i++)	
  {	    ASTtypesTFIDF[i] = ASTtypesTFIDF[i].replace("'", "apostrophesymbol");
    	Util.writeFile("@attribute 'ASTNodeTypesTFIDF "+i+"=["+ASTtypesTFIDF[i]+"]' numeric"+"\n", output_filename, true);}
    
    for (int i=0; i<ASTtypesAvgDep.length; i++)	
  {	    ASTtypesAvgDep[i] = ASTtypesAvgDep[i].replace("'", "apostrophesymbol");
    	Util.writeFile("@attribute 'ASTNodeTypeAvgDep "+i+"=["+ASTtypesAvgDep[i]+"]' numeric"+"\n", output_filename, true);}
    for (int i=0; i<cppKeywords.length; i++)	
  {	Util.writeFile("@attribute 'cppKeyword "+i+"=["+cppKeywords[i]+"]' numeric"+"\n", output_filename, true);}

    File authorFileName = null;
	//Writing the classes (authorname)
	Util.writeFile("@attribute 'authorName' {",output_filename, true);
	for(int i=0; i< test_file_paths.size(); i++){
		int testIDlength = test_file_paths.get(i).toString().length();   
		authorFileName= new File(test_file_paths.get(i).toString());
		String authorName= authorFileName.getParentFile().getName();

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
   	for(int i=0; i< test_file_paths.size(); i++){
		String featureText = Util.readFile(test_file_paths.get(i).toString());
		int testIDlength = test_file_paths.get(i).toString().length(); 
		authorFileName= new File(test_file_paths.get(i).toString());
		String authorName= authorFileName.getParentFile().getName();

		System.out.println(test_file_paths.get(i));
		System.out.println(authorName);
		File fileCPPID = new File(test_cpp_paths.get(i).toString());
		String fileNameID = fileCPPID.getName();
		Util.writeFile(fileNameID+",", output_filename, true);
//		Util.writeFile(FeatureCalculators.functionIDCount(featureText)+",", output_filename, true);
		String ASTText = Util.readFile(test_file_paths.get(i).toString().substring(0,testIDlength-3)+"ast");
		String DepASTText = Util.readFile(test_file_paths.get(i).toString().substring(0,testIDlength-3)+"dep");
		String sourceCode = Util.readFile(test_file_paths.get(i).toString().substring(0,testIDlength-3)+"cpp");

//		Util.writeFile(FeatureCalculators.CFGNodeCount(ASTText)+",", output_filename, true);
//		Util.writeFile(FeatureCalculators.ASTFunctionIDCount(ASTText)+",", output_filename, true);
		Util.writeFile(DepthASTNode.getMaxDepthASTLeaf(DepASTText, ASTtypesTF)+",", output_filename, true);
		


	    //get count of each ASTtype not-DepAST type present	 
	    float[] typeCount = FeatureCalculators.DepASTTypeTF(DepASTText, ASTtypesTF );
	    for (int j=0; j<ASTtypesTF.length; j++)
		{Util.writeFile(typeCount[j] +",", output_filename, true);}	
	    
		//get tfidf of each AST Type present	 
	    float[] DepastTypeTFIDF = FeatureCalculators.DepASTTypeTFIDF(DepASTText, test_dir, ASTtypesTFIDF);
	    for (int j=0; j<ASTtypesTFIDF.length; j++)
		{Util.writeFile(ASTtypesTFIDF[j]+",", output_filename, true);}	
		
    	float [] depFeature =DepthASTNode.getAvgDepthASTNode(DepASTText,ASTtypesAvgDep);
    	for(int k=0;k<depFeature.length;k++)
		{Util.writeFile(depFeature[k] +",", output_filename, true);}	
	    
    	float [] cppKeywordsTF =FeatureCalculators.getCPPKeywordsTF(sourceCode);
    	for(int k=0;k<cppKeywordsTF.length;k++)
		{Util.writeFile(cppKeywordsTF[k] +",", output_filename, true);}	
    	
    	
		Util.writeFile(authorName+"\n", output_filename, true);

   	
   			
       	}
   	}
   
	  
   	
	  public static String[]  uniqueDirectoryWords (String directoryFilePath){

		    String text = "FunctionName: operator"+ "";
		            
		            

		    Matcher m = Pattern.compile("(?m)^.*$").matcher(text);

		   
		    while (m.find()) {
		        System.out.println("line = " + m.group());
		        if(m.group().startsWith("Features (list):"));
		        
		    }
		  


	        while (m.find()) {
	            System.out.println("line = " + m.group());}
		  
		  String[] words = text.split( "\\s+");
		  Set<String> uniqueWords = new HashSet<String>();

		   for (String word : words) {
		       uniqueWords.add(word);
		   }
		   words = uniqueWords.toArray(new String[0]);
		   return words;
		 }
		   
	  
}











