import java.io.File;

public class HashCheck {
	
	private static String option;
	private static String filePath;
	private static File file;

	public static void main(String[] args) {
		if (args.length == 2) {
			option = args[0];
			filePath = args[1];
			file = new File(filePath);
			//Scan directory
		} else {
			System.err.println("Usage of HashCheck: java HashCheck [OPTIONS] [FILES/DIRECTORIES] \n"
					+ "Options: \n"
					+ "-c check. Checks given files and/or files in directories against hashes in hash file \n"
					+ "-h hash. Scans given files and/or files in directories and saves their hashes.");
		}
	}

}
