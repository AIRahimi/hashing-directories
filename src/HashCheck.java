import java.io.File;
import java.io.FileNotFoundException;

public class HashCheck {
	
	private static String option;
	private static String filePath;
	private static File file;
	private static File checkSum; 

	public static void main(String[] args) {
		if (args.length == 2) {
			option = args[0];
			filePath = args[1];
			file = new File(filePath);
			checkSum = new File("sum.check");
			if (option.equals("-c") && !(checkSum.exists())) { // dersom option c og sum.check ikke eksisterer
				
			} else if (option.equals("-h")) {
				//Check if existing sum.check
				//Confirm overwrite or exit?
			} else {
				printError();
			}
			scanDirectory(file);
		} else {
			printError();
		}
	}
	
	public static void printError() {
		System.err.println("Usage of HashCheck: java -jar HashCheck.jar [OPTION] [FILES/DIRECTORIES] \n"
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
					doOption(entry);
				}
			}
		} else {
			filePath = directory.getParent();
			doOption(directory);
		}
	}
	
	public static void doOption(File entry) {
		if (option.equals("-c")) {
			checkHash(entry);
		} else {
			createHash(entry);
		}
	}
	
	public static void checkHash(File file) {
		String fileHash = createHash(file);
	}
	
	public static String createHash(File file) {
		return null;
	}
}
