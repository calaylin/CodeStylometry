import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;


/*
 * Takes a folder with author files and converts it to a problem set composed
 * of only training documents in xml format.
*/
public class ProblemSetWriter {
	public static void main(String[] args) {
	
	//problemSetFilename: the xml file that contains the problem set, all training set for cross validation
       	String problemSetFilename = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/"
       			+ "balbalbal.xml";
    //test_dir: the folder that has the author files
        String test_dir = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/SCAA_Datasets/13FilesPerAuthor_merged_withAST/";	
		File file = new File(test_dir);
        String[] authorName = file.list(new FilenameFilter() 
		   {
		     @Override
		     public boolean accept(File current, String name) 
		     {
		       return new File(current, name).isDirectory();
		     }
		   });
        
		//System.out.println(Arrays.toString(authorName));
    	Util.writeFile("<problem-set>"+"\n", problemSetFilename, true);
    	Util.writeFile("\t" + "<training name=\"Authors\">"+"\n", problemSetFilename, true);
		for(int i=0; i< authorName.length; i++)
		{
 
			Util.writeFile("\t"+"\t"+ "<author name=\""+ authorName[i] + "\">"+"\n", problemSetFilename, true);
			List test_cpp_paths = Util.listCPPFiles(test_dir + authorName[i] + "/");
			//     	System.out.println(test_cpp_paths);
			for(int j=0; j < test_cpp_paths.size();j++ )
			{
				File fileCPP = new File(test_cpp_paths.get(j).toString());
				String fileName = fileCPP.getName();
				Util.writeFile("\t"+"\t"+"\t"+"<document title=\""+ fileName + "\">"
						+ test_cpp_paths.get(j).toString() + "</document>"
						+ "\n", problemSetFilename, true);
			}
			Util.writeFile("\t"+"\t"+ "</author>"+ "\n", problemSetFilename, true);

		}
		Util.writeFile("\t"+ "</training>"+ "\n", problemSetFilename, true);
		Util.writeFile("\t"+ "<test>"+ "\n", problemSetFilename, true);
		Util.writeFile("\t"+ "</test>"+ "\n", problemSetFilename, true);
		Util.writeFile("</problem-set>"+ "\n", problemSetFilename, true);
	
	
	}
}
