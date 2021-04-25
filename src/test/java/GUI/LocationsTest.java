package GUI;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Path;

public class LocationsTest {

    private Path workingDir=Path.of("", "src/main/resources");

    /**
     * testaa onko locations.json olemassa eli luettavissa
     */
    @Test
    void checkLocationsTest() throws IOException {
        Path jsonFile = this.workingDir.resolve("locations.json");
        Assertions.assertTrue(jsonFile.toFile().exists());
    }
}