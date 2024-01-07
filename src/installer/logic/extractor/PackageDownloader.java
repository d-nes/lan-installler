package installer.logic.extractor;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.*;

public class PackageDownloader {

    private static final String DOWNLOAD_URL = "https://www.dropbox.com/scl/fi/8jkseerw12i37xu1w4t4q/lan2024files.zip?rlkey=ceuif4pzc2zubkw4qkefzmau3&dl=1";

    public Path downloadPackage() {
        try {
            // Create the output directory in the system's temporary directory
            Path outputPath = Files.createTempDirectory("downloaded-packages");

            // Extract the file name from the URL
            String fileName = "lan2024files.zip";

            // Calculate the destination path
            Path destination = outputPath.resolve(fileName);

            // Open a connection to the URL and create an InputStream
            try (InputStream inputStream = new URL(DOWNLOAD_URL).openStream()) {
                // Copy the InputStream to the destination file
                Files.copy(inputStream, destination, StandardCopyOption.REPLACE_EXISTING);
            }

            return destination; // Return the path to the downloaded file

        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if download fails
        }
    }

    public boolean deletePackage(Path filePath) {
        try {
            // Delete the file at the specified path
            Files.deleteIfExists(filePath);
            return true; // Deletion successful
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Deletion failed
        }
    }
}
