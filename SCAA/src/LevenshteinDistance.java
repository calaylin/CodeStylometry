import java.io.*;
import java.util.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.csvreader.CsvWriter;


public class LevenshteinDistance {
 
  public static int computeDistance(String s1, String s2) {
 //   s1 = s1.toLowerCase();
 //   s2 = s2.toLowerCase();
 
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
	  

	// Construct a BufferedReader object from the input file
		String file1 = Util.readFile("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/"
				+ "SCAA_Datasets/bigExperiments/250authors/9FilesExactlyPer250Author_2014/"
				+ "PlutoShe/2974486_5644738749267968_PlutoShe.cpp");
		String file2 = Util.readFile("/Users/Aylin/Desktop/Drexel/2014/ARLInternship/"
				+ "SCAA_Datasets/bigExperiments/250authors/9FilesExactlyPer250Author_2014/"
				+ "PlutoShe/2974486_5690574640250880_PlutoShe.cpp");
	  
	  printDistance(file1, file2);

  }
}