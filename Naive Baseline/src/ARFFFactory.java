import java.io.File;
import java.io.IOException;
import java.util.List;


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
	
	public static void printInstanceData(FeatureSet f, File target) {
		// TODO
		// one formatted print statement
	}
	
	public static void makeARFF(String rootDirectory, String targetPath) {
		// TODO
		// recursively spider thru all c/cpp files and make into a  list of files
		// call method below
	}
	
	public static void makeARFF(List<File> files, String targetPath) {
		// TODO
		// call make arffheader
		makeARFFHeader(targetPath);
		// for each file in the list, call print instance data
		for (File f : files) {
			try {
				printInstanceData((FeatureSet) getExtractor(f), new File(targetPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// done
	}
	
	public static void makeARFFHeader(String targetPath) {
		// TODO
		// put @relation at top
		// put all the @attribute lines
	}
	
}