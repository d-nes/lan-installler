package installer.logic;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class OfficialLauncherInjector {
    public boolean injectProfile() {
        try {
            // Get the current user's home directory
            String userHome = System.getProperty("user.home");

            // Path to your JSON file in Minecraft directory
            Path jsonFilePath = Paths.get(userHome, "AppData", "Roaming", ".minecraft", "launcher_profiles.json");

            // New profile details
            String newProfile = "\n\t\"lan2024\" : {\n" +
                    "      \"created\" : \"2023-12-19T22:47:00.622Z\",\n" +
                    "      \"gameDir\" : \"" + userHome.replace("\\", "\\\\") + "\\\\AppData\\\\Roaming\\\\.minecraft\\\\lan2024\",\n" +
                    "      \"icon\" : \"Lectern_Book\",\n" +
                    "      \"javaArgs\" : \"-Xmx4G -XX:+UnlockExperimentalVMOptions -XX:+UseG1GC -XX:G1NewSizePercent=20 -XX:G1ReservePercent=20 -XX:MaxGCPauseMillis=50 -XX:G1HeapRegionSize=32M\",\n" +
                    "      \"lastUsed\" : \"2024-01-06T18:25:25.942Z\",\n" +
                    "      \"lastVersionId\" : \"lan2024\",\n" +
                    "      \"name\" : \"LAN 2024\",\n" +
                    "      \"type\" : \"custom\"\n" +
                    "    },";

            // Read the content of the file
            String fileContent = new String(Files.readAllBytes(jsonFilePath), StandardCharsets.UTF_8);

            // Find the position of "profiles" key
            int profilesIndex = fileContent.indexOf("\"profiles\" : {") + "\"profiles\" : {".length();

            // Insert the new profile details after the "profiles" key
            String modifiedContent = new StringBuilder(fileContent).insert(profilesIndex, newProfile).toString();

            // Write the modified content back to the file
            Files.write(jsonFilePath, modifiedContent.getBytes(StandardCharsets.UTF_8));

            return true; // Injection successful

        } catch (IOException e) {
            e.printStackTrace();
            return false; // Injection failed
        }
    }
}
