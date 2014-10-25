public class Driver {

	/**
	 * Dependencies: Apache Commons IO, Util.java, ARFFFactory*.java, everything
	 * else in the Naive-Baseline package
	 */
	public static void main(String args[]) {
		String s1 = "C:\\Users\\Andrew\\Downloads\\debug";
		String s2 = "C:\\Users\\Andrew\\Downloads\\out.arff";
		(new ARFFFactory4()).makeARFF(s1, s2);
	}
}