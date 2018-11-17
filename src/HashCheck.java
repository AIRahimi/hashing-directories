import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
			boolean checkSumExists = checkSum.exists();
			
			if (option.equals("-c")) {
				if (checkSumExists) {
					scanDirectory(file);
				} else {
					System.err.println("Hash file does not exist. \n"
							+ "Try running option -h first, in order to create checksum for files");
				}
			} else if (option.equals("-h")) {
				if (checkSumExists) {
					System.out.print("Hash file sum.check file exists from before. "
							+ "Are you sure you want to overwrite (Y/N)? ");
					Scanner input = new Scanner(System.in);
					String overwriteInput = input.nextLine();
					input.close();
					if (overwriteInput.toLowerCase().equals("y")) {
						scanDirectory(file);
					} else {
						System.out.println("Quitting application");
						return;
					}
				} else {
					scanDirectory(file);
				}
			} else {
				printError();
			}
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
