import java.io.File;
import java.text.NumberFormat;
import java.util.Locale;

public class LargestFileFinder {

    private static File largestFile = null;
    private static long largestFileSize = 0;

    public static void main(String[] args) {

        String directoryPath = "C:\\Program Files\\";
        File directory = new File(directoryPath);

        if (directory.exists() && directory.isDirectory()) {
            findLargestFile(directory);
            if (largestFile != null) {
                NumberFormat numberFormat = NumberFormat.getInstance(Locale.ENGLISH);
                String formattedSize = numberFormat.format(largestFileSize);
                System.out.println("The biggest file: " + largestFile.getAbsolutePath());
                System.out.println("Size: " + formattedSize + " byte");
            } else {
                System.out.println("There are no files in the specified directory.");
            }
        } else {
            System.out.println("The specified directory does not exist or is not a directory.");
        }
    }

    private static void findLargestFile(File directory) {
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    long fileSize = file.length();
                    if (fileSize > largestFileSize) {
                        largestFileSize = fileSize;
                        largestFile = file;
                    }
                } else if (file.isDirectory()) {
                    findLargestFile(file);
                }
            }
        }
    }
}
