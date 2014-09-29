import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ARFFFactory {

	public static AbstractExtractor getExtractor(File f) throws IOException {
		AbstractExtractor x = null;
		if (f.getName().matches(".*\\.cpp")) {
			x = new ExtractorCPP(f);
		} else {
			x = new ExtractorC(f);
		}
		return x;
	}
	
	protected void appendAttributes(FeatureSet f, StringBuffer x) {
		x.append(f.numFunctions() + ",");
		x.append(f.length() + ",");
		x.append(f.numTokens() + ",");
		x.append(f.numComments() + ",");
		x.append(f.getLiterals().size() + ",");
		x.append(f.getReservedWords().size() + ",");
		x.append(f.avgLineLength() + ",");
		x.append(f.numEmptyLines() + ",");
		x.append(f.whiteSpaceRatio() + ",");
		x.append(f.avgParamsPerFunction() + ",");
	}

	public String getInstanceData(FeatureSet f, Set<String> authors) {

		StringBuffer x = new StringBuffer();
		//
		appendAttributes(f, x);
		//
		x.append(getAuthorName((AbstractExtractor) f) + "\n");
		authors.add(getAuthorName((AbstractExtractor) f));
		return x.toString();
		// Util.writeFile(allLines, targetPath, true);
	}

	public static String getAuthorName(AbstractExtractor e) {
		File f = e.getFile();
//		String s = f.getName();
//		s = s.replaceFirst("p[\\d]+\\.", "");
//		int i = s.lastIndexOf('.');
//		s = s.replaceAll(",", "");
//		return s.substring(0, i - 1);
		String s = f.getParentFile().getName();
		return s.substring(0, s.length());
	}

	public void makeARFF(String rootDirectory, String targetPath) {
		// recursively spider thru all c/cpp files and make into a list of files
		// call method below
		// throw new UnsupportedOperationException();
		Stack<File> files = new Stack<File>();
		List<File> programs = new LinkedList<File>();
		File f = new File(rootDirectory);
		files.add(f);
		while (files.size() > 0) {
			File temp = files.pop();
			for (File myFile : temp.listFiles()) {
				if (myFile.isDirectory()) {
					files.add(myFile);
				} else if (myFile.isFile()) {
					if (myFile.getName().matches(".*\\.c")
							|| myFile.getName().matches(".*\\.cpp")) {
						programs.add(myFile);
					}
				}
			}
		}
		makeARFF(programs, targetPath);
	}

	public void makeARFF(List<File> files, String targetPath) {
		Set<String> authors = new HashSet<>();
		List<String> allLines = new LinkedList<String>();
		// for each file in the list, get instance data
		for (File f : files) {
			System.out.println(f.getAbsolutePath());
			try {
				allLines.add(getInstanceData((FeatureSet) getExtractor(f),
						authors));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// call make arffheader
		makeARFFHeader(targetPath, authors);
		Util.writeFile(allLines, targetPath, true);
		System.out.println(authors.size() + " authors");
		System.out.println(files.size() + " files");
	}
	
	protected void arffAttributes(List<String> allLines) {
		allLines.add("@attribute numFunctions numeric\n");
		allLines.add("@attribute length numeric\n");
		allLines.add("@attribute numTokens numeric\n");
		allLines.add("@attribute numComments numeric\n");
		allLines.add("@attribute numLiterals numeric\n");
		allLines.add("@attribute numReservedWords numeric\n");
		allLines.add("@attribute avgLineLength numeric\n");
		allLines.add("@attribute numEmptyLines numeric\n");
		allLines.add("@attribute whiteSpaceRatio numeric\n");
		allLines.add("@attribute avgParams numeric\n");
	}

	public void makeARFFHeader(String targetPath, Set<String> authors) {
		// put @relation at top
		// put all the @attribute lines
		List<String> allLines = new LinkedList<String>();
		allLines.add("@relation code_style\n\n");
		// add all the @attributes
		arffAttributes(allLines);
		//
		allLines.add("@attribute author {");
		Iterator<String> author = authors.iterator();
		while (author.hasNext()) {
			allLines.add(author.next());
			if (author.hasNext()) {
				allLines.add(",");
			}
		}
		allLines.add("}\n\n@data\n");
		Util.writeFile(allLines, targetPath, false);
	}
	
	public static double stdDev(Map<Integer, Integer> mappy) {
		List<Integer> list = new LinkedList<Integer>();
		for (Integer i : mappy.keySet()) {
			for (int j = 0; j < mappy.get(i); j++) {
				list.add(i);
			}
		}
		return stdDev(list);
	}
	
	public static double variance(List<Integer> list)  {
		int sum1 = 0; // E(x^2)
		int sum2 = 0; // E(x)
		double size = list.size();
		for (Integer i : list) {
			sum1 += i * i;
			sum2 += i;
		}
		return (sum1 / size) - (sum2 / size) * (sum2 / size);
	}
	
	public static double stdDev(List<Integer> list)  {
		return Math.sqrt(variance(list));
	}

}