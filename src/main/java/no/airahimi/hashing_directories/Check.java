package no.airahimi.hashing_directories;

import no.airahimi.hashing_directories.gui.Main;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;


public class Check {

    private File file;
    private File saveTo;

    private StringBuilder output = new StringBuilder();

    public Check(File file, File saveTo) {
        this.file = file;
        this.saveTo = saveTo;
    }

    public void scanDirectory() {
        scanDirectory(file);
    }

    private void scanDirectory(File directory) {
        File entry;

        //System.out.println("Starting search of directory " + directory.getAbsolutePath());

        if (directory.isDirectory()) {
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
                    checkHashFile(entry);
                }
            }
        } else {
            checkHashFile(directory);
        }
    }

    private void checkHashFile(File file) {
        String fileHash = createHash(file);
        String fileHashPath = file.getAbsolutePath();
        String tempLine;
        boolean containsFile = false;
        boolean containsFileHash = false;


        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(saveTo));
            while ((tempLine = bufferedReader.readLine()) != null) {
                if (tempLine.contains(fileHashPath)) {
                    containsFile = true;
                    if (tempLine.contains(fileHash)) {
                        containsFileHash = true;
                    }
                }
            }

            if (containsFile && containsFileHash) {
                output.append("Checked: " + fileHashPath + " hash OK" + Main.NEW_LINE);
            } else if (containsFile) {
                output.append("Checked: " + fileHashPath + " hash NOT OK" + Main.NEW_LINE);
            } else {
                output.append("No hash registered for file: " + fileHashPath + Main.NEW_LINE);
            }

            bufferedReader.close();
        } catch (IOException e) {
            output.append("Checksum file does not exist" + Main.NEW_LINE);
        }
    }

    private String createHash(File file) {
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

    public String getOutputString() {
        return output.toString();
    }
}
