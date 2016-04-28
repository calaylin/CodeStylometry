import java.io.IOException;
import java.util.List;


public class RemoveComments {

    public static void main(String[] args) throws IOException
	{
    	String test = "githubManySmallSnippets/";	
    	List test_file_paths = Util.listCPPFiles(test); //use this for preprocessing       
    	for(int i=0; i< test_file_paths.size(); i++)
    	{
		String fileName = test_file_paths.get(i).toString();  
		System.out.println(fileName);
		String sourceCode = Util.readFile(fileName);    
  //    System.out.println(sourceCode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)",""));
		//does not catch Gleb.kalachev's comments, removed them manually.  Has a lot of commented code.
		Util.writeFile(sourceCode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)",""), fileName, false);
    	}}
        public static void removeComments(String test) throws IOException
    	{
        	List test_file_paths = Util.listCPPFiles(test); //use this for preprocessing       
        	for(int i=0; i< test_file_paths.size(); i++)
        	{
    		String fileName = test_file_paths.get(i).toString();  
    		System.out.println(fileName);
    		String sourceCode = Util.readFile(fileName);    
      //    System.out.println(sourceCode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)",""));
    		//does not catch Gleb.kalachev's comments, removed them manually.  Has a lot of commented code.
    		Util.writeFile(sourceCode.replaceAll("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?://.*)",""), fileName, false);
        	}	
    	
	}
    	
}
