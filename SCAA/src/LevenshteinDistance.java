import java.io.*;
import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;

import com.csvreader.CsvWriter;


public class LevenshteinDistance {
 
  public static int computeDistance(String s1, String s2) {
 //   s1 = s1.toLowerCase();
 //   s2 = s2.toLowerCase();
 
	  System.out.println("Length of first string: "+s1.length());
	  System.out.println("Length of second string: "+s2.length());

    int[] costs = new int[s2.length() + 1];
    for (int i = 0; i <= s1.length(); i++) {
      int lastValue = i;
      for (int j = 0; j <= s2.length(); j++) {
        if (i == 0)
          costs[j] = j;
        else {
          if (j > 0) {
            int newValue = costs[j - 1];
            if (s1.charAt(i - 1) != s2.charAt(j - 1))
              newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
            costs[j - 1] = lastValue;
            lastValue = newValue;
          }
        }
      }
      if (i > 0)
        costs[s2.length()] = lastValue;
    }
    return costs[s2.length()];
  }
 
  public static void printDistance(String s1, String s2) {
 //   System.out.println(s1 + "-->" + s2 + ": " + computeDistance(s1, s2));
	    System.out.println(computeDistance(s1, s2));

  }
 
  @SuppressWarnings("resource")
public static void main(String[] args) throws IOException {
	  
	  	String parentDir = "/Users/Aylin/Desktop/Drexel/2014/ARLInternship/"
				+ "SCAA_Datasets/bigExperiments/250authors/9FilesExactlyPer250Author_2014/";
	   	String output_file= "/Users/Aylin/Desktop/similarityTest.txt";

	  File file = new File(parentDir);
	   String[] directories = file.list(new FilenameFilter() 
	   {
	     @Override
	     public boolean accept(File current, String name) 
	     {
	       return new File(current, name).isDirectory();
	     }
	   });
	   System.out.println(Arrays.toString(directories));	
	   for (int i =0; i< directories.length; i++)
	   {
		   	double author_ratio=0;
		   //authorname is directoryname - 1 because Andrew put an extra 0 at the end of the authorname
		   String authorName = directories[i].toString().substring(0, directories[i].toString().length()); 
		   String authorDir = parentDir + directories[i] + "/";
		   Util.writeFile(authorName +"\n", output_file, true);
		   System.out.println(authorName);
		   System.out.println(authorDir);

		   BufferedReader br = null;
			String line = "";
		   	List test_cpp_paths = Util.listCPPFiles(authorDir);

		   	for(int j=0; j < test_cpp_paths.size();j++ )
			{
				double  avg_ratio=0;
				double ratio =0;

				String file1 = Util.readFile(test_cpp_paths.get(j).toString());
				   Util.writeFile(test_cpp_paths.get(j).toString() +"\n", output_file, true);
				for(int k=0; k < test_cpp_paths.size();k++ )
				{
					if(j!=k){
						String file2 = Util.readFile(test_cpp_paths.get(k).toString());
						int distance =computeDistance(file1, file2);
						if(file1.length() <= file2.length()){
							 ratio = distance/(double)((Integer)file2.length());
						}
						if(file2.length() < file1.length()){
							 ratio = distance/(double)((Integer)file1.length());
						}	
						
						Util.writeFile("File1 length: "+file1.length() +" " +
								"File2 length:"+file2.length()+" ", output_file, true);
						Util.writeFile("distance: "+Integer.toString(distance)+
								" "+"ratio: "+ Double.toString(ratio) + "\n" ,output_file, true);
						avg_ratio =avg_ratio+ratio;
					}}
					avg_ratio = avg_ratio/(double)((Integer)(test_cpp_paths.size()-1));
					   Util.writeFile("average ratio of file: "+avg_ratio +"\n", output_file, true);
					   System.out.println("average ratio of file: "+avg_ratio );

					   author_ratio=author_ratio+avg_ratio;
				}
		   			author_ratio=author_ratio/(double)((Integer)(test_cpp_paths.size()));
					   System.out.println("average ratio of author: "+author_ratio);
   
		   			Util.writeFile("average ratio of author: "+author_ratio +"\n", output_file, true);

			}	  
	   }  
}