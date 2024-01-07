package installer.logic.extractor;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnofficialLauncherFileExtractor {

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

            // Define the source folder in the temporary directory for mods
            Path modsSourceFolderPath = tempDir.resolve("lan2024/mods");

            // Define the destination folder within ".minecraft" for mods
            Path modsDestinationFolderPath = minecraftFolderPath.resolve("mods");

            // Define the backup folder within ".minecraft" for mods
            Path modsBackupFolderPath = minecraftFolderPath.resolve("mods_backup_" + getCurrentTimestamp());

            // Create the backup folder for mods if it doesn't exist
            Files.createDirectories(modsBackupFolderPath);

            // Move existing files in the mods destination folder to the mods backup folder
            if (Files.exists(modsDestinationFolderPath)) {
                Files.move(modsDestinationFolderPath, modsBackupFolderPath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Create the mods destination folder if it doesn't exist
            Files.createDirectories(modsDestinationFolderPath);

            // Extract files from the mods source folder to the mods destination folder
            extractFolder(modsSourceFolderPath, modsDestinationFolderPath);

            // Define the source folder in the temporary directory for versions
            Path versionsSourceFolderPath = tempDir.resolve("versions");

            // Define the destination folder within ".minecraft" for versions
            Path versionsDestinationFolderPath = minecraftFolderPath.resolve("versions");

            // Create the versions destination folder if it doesn't exist
            Files.createDirectories(versionsDestinationFolderPath);

            // Extract files from the versions source folder to the versions destination folder
            extractFolder(versionsSourceFolderPath, versionsDestinationFolderPath);

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

    private void extractFolder(Path source, Path destination) throws IOException {
        Files.walk(source)
                .forEach(src -> {
                    try {
                        // Get the relative path from the source folder
                        Path relativePath = source.relativize(src);
                        Path dest = destination.resolve(relativePath);

                        // If it's a file, copy it to the destination
                        if (Files.isRegularFile(src)) {
                            Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
                        } else if (Files.isDirectory(src)) {
                            // If it's a directory, create the corresponding directory in ".minecraft"
                            Files.createDirectories(dest);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(new Date());
    }
}
