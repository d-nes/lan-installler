package installer.logic.extractor;

import java.io.IOException;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnofficialLauncherFileExtractor {

    public boolean extractFiles() {
        try {
            // Get the ".minecraft" folder path
            String userHome = System.getProperty("user.home");
            Path minecraftFolderPath = Paths.get(userHome, "AppData", "Roaming", ".minecraft");

            // Define the source folder in the JAR for mods
            Path modsSourceFolderPath = Paths.get(UnofficialLauncherFileExtractor.class.getResource("/resources/lan2024/mods").toURI());

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

            // Define the source folder in the JAR for versions
            Path versionsSourceFolderPath = Paths.get(UnofficialLauncherFileExtractor.class.getResource("/resources/versions").toURI());

            // Define the destination folder within ".minecraft" for versions
            Path versionsDestinationFolderPath = minecraftFolderPath.resolve("versions");

            // Create the versions destination folder if it doesn't exist
            Files.createDirectories(versionsDestinationFolderPath);

            // Extract files from the versions source folder to the versions destination folder
            extractFolder(versionsSourceFolderPath, versionsDestinationFolderPath);

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
