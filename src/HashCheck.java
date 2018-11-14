import java.io.File;

public class HashCheck {
	
	private static String option;
	private static String filePath;
	private static File file;

	public static void main(String[] args) {
		if (args.length == 2) {
			option = args[0];
			if (option.equals("-c") || option.equals("-h")) {
				filePath = args[1];
				file = new File(filePath);
				scanDirectory(file);
			} else {
				printError();
			}
		} else {
			printError();
		}
	}
	
	public static void printError() {
		System.err.println("Usage of HashCheck: java HashCheck [OPTIONS] [FILES/DIRECTORIES] \n"
				+ "Options: \n"
				+ "-c check. Checks given files and/or files in directories against hashes in hash file \n"
				+ "-h hash. Scans given files and/or files in directories and saves their hashes.");
	}
	
	/**
	 * Løst ved hjelp av:
	 * http://kark.hin.no/opsys/tanenbaum/java/ExecutableFiles.java
	 */
	public static void scanDirectory(File directory) { 
		File entry;
		
		System.out.println("Starting search of directory " + file.getAbsolutePath());
		
		if (directory.isDirectory()) {
			filePath = directory.getAbsolutePath();
			String[] directoryContents = directory.list();
			
			if (directoryContents == null) {
				return;
			}
			
			for (String fileInDirectory : directoryContents) {
				entry = new File(directory, fileInDirectory);
				if (fileInDirectory.charAt(0) == '.') {
					continue;
				}
				if (entry.isDirectory()) {
					scanDirectory(entry);
				} else {
					//Sjekk options og utfør deretter
				}
			}
		} else {
			filePath = directory.getParent();
			//Sjekk options og utfør deretter
		}
		
		
	}

}
