public class Extractor {

	public static String getDownloadURL(String roundNum, String problemNum,
			String name) {
		return "http://code.google.com/codejam/contest/scoreboard/do?cmd=GetSourceCode&contest="
				+ roundNum
				+ "&problem="
				+ problemNum
				+ "&io_set_id=0&username=" + name;
	}
}