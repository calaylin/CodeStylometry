public class Driver {

	/**
	 * Dependencies: Apache Commons IO, Util.java, ARFFFactory*.java, everything
	 * else in the Naive-Baseline package
	 */
	public static void main(String args[]) {
		if (args.length != 2) {
			System.err
					.println("Usage: <root directory of all test files> <target ARFF file>");
			System.exit(1);
		}
		(new ARFFFactory4()).makeARFF(args[0], args[1]);
	}
}