package Config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ConfigurationTest {
    Configuration configtest = new Configuration(EditConfig.readFromConfigurationFile());


    /**
     * Testataan palauttaako laillisen prefixin testaava metodi true, jos sille syöttää parametriksi
     * cfg.txt filun pohjalta luodun olion getPrefix() metodi.
     */
    @Test
    void checkConfigLegalPrefixTest() {
        Assertions.assertEquals(true,EditConfig.checkLegalPrefix(configtest.getPrefix()));
    }

}
