package installer.logic.extractor;

import java.io.IOException;
import java.nio.file.*;

public class OfficialLauncherFileExtractor {

    public boolean extractFiles() {
        try {
            // Get the ".minecraft" folder path
            String userHome = System.getProperty("user.home");
            Path minecraftFolderPath = Paths.get(userHome, "AppData", "Roaming", ".minecraft");

            // Get the resources folder path within the JAR
            Path resourcesFolderPath = Paths.get(OfficialLauncherFileExtractor.class.getResource("/resources").toURI());

            // Walk through the resources folder and copy files to ".minecraft"
            Files.walk(resourcesFolderPath)
                    .forEach(source -> {
                        try {
                            // Get the relative path from resources folder
                            Path relativePath = resourcesFolderPath.relativize(source);
                            Path destination = minecraftFolderPath.resolve(relativePath);

                            // If it's a file, copy it to the destination
                            if (Files.isRegularFile(source)) {
                                Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                            } else if (Files.isDirectory(source)) {
                                // If it's a directory, create the corresponding directory in ".minecraft"
                                Files.createDirectories(destination);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

            return true; // Extraction successful

        } catch (Exception e) {
            e.printStackTrace();
            return false; // Extraction failed
        }
    }
}
