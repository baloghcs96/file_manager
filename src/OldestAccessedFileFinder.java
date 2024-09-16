import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Comparator;
import java.util.Optional;

public class OldestAccessedFileFinder {

    public static void main(String[] args) throws IOException {
        String directoryPath = "D:\\Games\\";
        Path directory = Paths.get(directoryPath);

        if (Files.exists(directory) && Files.isDirectory(directory)) {
            Optional<Path> oldestFile = findOldestAccessedFile(directory);

            if (oldestFile.isPresent()) {
                System.out.println("The most recently opened file: " + oldestFile.get());
                BasicFileAttributes attr = Files.readAttributes(oldestFile.get(), BasicFileAttributes.class);
                System.out.println("Last access time: " + attr.lastAccessTime());
            } else {
                System.out.println("There are no files in the specified directory.");
            }
        } else {
            System.out.println("The specified directory does not exist or is not a directory.");
        }
    }

    private static Optional<Path> findOldestAccessedFile(Path directory) throws IOException {
        return Files.walk(directory)
                .filter(Files::isRegularFile)
                .filter(path -> {
                    try {
                        Files.readAttributes(path, BasicFileAttributes.class);
                        return true;
                    } catch (IOException e) {
                        System.err.println("Unable to access: " + path + " - " + e.getMessage());
                        return false;
                    }
                })
                .min(Comparator.comparing(OldestAccessedFileFinder::getLastAccessTime));
    }

    private static FileTime getLastAccessTime(Path file) {
        try {
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            return attr.lastAccessTime();
        } catch (IOException e) {
            e.printStackTrace();
            return FileTime.fromMillis(Long.MAX_VALUE);
        }
    }
}

