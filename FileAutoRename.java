import java.util.Scanner;
import java.io.File;

public class FileAutoRename {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String foldername, prefix;
        int start, maxDigit;
        System.out.println("NOTE: Make sure that the folder is already sorted before running this program!");
        System.out.print("Enter folder name to change: ");
        foldername = input.nextLine();
        System.out.print("Enter file prefix (ex. 'Episode', press enter for no prefix): ");
        prefix = input.nextLine();
        System.out.print("Enter file counter start (ex. '1'): ");
        start = input.nextInt();
        System.out.print("Enter max digit size (ex. '3' if you want 001, 002, etc...): ");
        maxDigit = input.nextInt();
        startReadFolder(foldername, prefix, start, maxDigit);
        input.close();
    }

    /**
     * Start of the program. Iterates through the contents of the folder.
     * 
     * @param folderName
     * @param prefix
     * @param start
     * @param maxDigit
     */
    private static void startReadFolder(String folderName, String prefix, int start, int maxDigit) {
        int counter = start;
        File dir = new File(folderName);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File file : directoryListing) {
                if (!file.isDirectory()) {
                    counter = renameFile(file, prefix, counter, maxDigit);
                    if (counter == -1) {
                        System.out.println("Failed to rename file: " + file.getName());
                        // Ask if you would like to continue
                        return;
                    }
                }
            }
            System.out.println("Successfully renamed folder contents.");
        } else {
            System.out.println("Not a valid directory. Please enter a valid directory.");
        }
    }

    /**
     * Renames the file
     * 
     * @param file
     * @param prefix
     * @param counter
     * @param maxDigit
     * @return
     */
    private static int renameFile(File file, String prefix, int counter, int maxDigit) {
        String fileExt = getFileExtension(file);
        String newFileName = file.getParent() + "\\" + getNewFileName(prefix, counter, maxDigit, fileExt);
        try {
            File newFile = new File(newFileName);
            if (!file.renameTo(newFile)) {
                System.out.println("Failed to rename file.");
                return -1;
            }
            counter++;
            return counter;
        } catch (Exception e) {
            System.out.println("Failed to rename file.");
            return -1;
        }
    }

    /**
     * Gets the file name extension
     * 
     * @param file
     * @return
     */
    private static String getFileExtension(File file) {
        int i = file.getName().lastIndexOf('.');
        if (i > 0) {
            return file.getName().substring(i + 1);
        }
        return "";
    }

    /**
     * Gets the new file to update the old file name
     * 
     * @param prefix
     * @param counter
     * @param maxDigit
     * @param fileExt
     * @return
     */
    private static String getNewFileName(String prefix, int counter, int maxDigit, String fileExt) {
        String newFileExt = "." + fileExt;
        String strCounter = Integer.toString(counter);
        if (strCounter.length() == maxDigit || maxDigit == 1) {
            return prefix + Integer.toString(counter) + newFileExt;
        }
        while (strCounter.length() != maxDigit) {
            strCounter = "0" + strCounter;
        }
        return prefix + strCounter + newFileExt;
    }
}