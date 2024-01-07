package installer.logic.extractor;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class OfficialLauncherFileExtractor {

    public boolean extractFiles() {
        try {
            // Get the ".minecraft" folder path
            String userHome = System.getProperty("user.home");
            Path minecraftFolderPath = Paths.get(userHome, "AppData", "Roaming", ".minecraft");

            // Specify the path to the zip file
            PackageDownloader pkg = new PackageDownloader();
            Path zipFilePath = pkg.downloadPackage();

            // Create a temporary directory for extraction
            Path tempDir = Files.createTempDirectory("extracted-files");

            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFilePath))) {
                ZipEntry zipEntry;

                while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                    // Calculate the destination path
                    Path destination = tempDir.resolve(zipEntry.getName());

                    // If it's a directory, create the corresponding directory in the temporary directory
                    if (zipEntry.isDirectory()) {
                        Files.createDirectories(destination);
                    } else {
                        // If it's a file, copy it to the temporary directory
                        Files.copy(zipInputStream, destination, StandardCopyOption.REPLACE_EXISTING);
                    }
                }
            }

            // Copy files from the temporary directory to ".minecraft"
            Files.walk(tempDir)
                    .forEach(source -> {
                        try {
                            Path relativePath = tempDir.relativize(source);
                            Path destination = minecraftFolderPath.resolve(relativePath);

                            if (Files.isRegularFile(source)) {
                                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                            } else if (Files.isDirectory(source)) {
                                Files.createDirectories(destination);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            // Clean up: Delete the temporary directory
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .forEach(path -> {
                        try {
                            Files.delete(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            pkg.deletePackage(zipFilePath);

            return true; // Extraction successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Extraction failed
        }
    }
}
