import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

public class HashCheck {
	
	// https://stackoverflow.com/questions/13682983/new-line-when-using-dataoutputstream-android
	private static final String newLine = System.getProperty("line.separator");
		
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
						clearCheckSum();
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
		
		System.out.println("Starting search of directory " + directory.getAbsolutePath());
			
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
			doOption(directory);
		}
	}
	
	public static void doOption(File entry) {
		if (option.equals("-c")) {
			checkHashFile(entry);
		} else {
			writeToHashFile(entry);
		}
	}
	
	public static void checkHashFile(File file) {
		String fileHash = createHash(file);
		String fileHashPath = file.getAbsolutePath();
		try {
			Scanner checkSumScanner = new Scanner(checkSum);
			String tempLine = "";
			while (checkSumScanner.hasNextLine()) {
				tempLine = checkSumScanner.nextLine();
				if (tempLine.contains(fileHashPath + ",")) {
					if (tempLine.contains(fileHash)) {
						System.out.println("Checked: " + fileHashPath + " hash OK");
					} else {
						System.out.println("Checked: " + fileHashPath + " hash NOT OK");
					}
				} else {
					System.out.println("No hash registered for file: " + filePath);
				}
			}
			checkSumScanner.close();
		} catch (FileNotFoundException e) {
			System.err.println("Checking hash - FileNotFound Exception: " + e.getMessage());
		}
	}
	
	public static String createHash(File file) {
		String hash = "";
		try {
			FileInputStream fis = new FileInputStream(file);
			hash = DigestUtils.sha256Hex(fis);
		} catch (FileNotFoundException e) {
			System.err.println("Creating hash - FileNotFound Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Creating hash - IO Exception: " + e.getMessage());
		}
		
		return hash;
	}
	
	public static void writeToHashFile(File fileToHash) {
		String hash_sha256 = createHash(fileToHash);
		try {
			FileOutputStream fos = new FileOutputStream(checkSum, true);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			DataOutputStream dos = new DataOutputStream(bos);

			dos.writeBytes(fileToHash.getAbsolutePath() + "," + hash_sha256 + newLine);
			dos.close();
		} catch (FileNotFoundException e) {
			System.err.println("Writing to sum.check - FileNotFound Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Writing to sum.check - IO Exception: " + e.getMessage());
		}
	}
	
	public static void clearCheckSum() {
		try {
			FileOutputStream fos = new FileOutputStream(checkSum);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			DataOutputStream dos = new DataOutputStream(bos);
			
			dos.writeBytes("########## HASH FILE ##########" + newLine);
			dos.close();
		} catch (FileNotFoundException e) {
			System.err.println("Clearing sum.check - FileNotFound Exception: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Clearing sum.check - IO Exception: " + e.getMessage());
		}
	}
}
