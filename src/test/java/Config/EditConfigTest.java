package Config;

import net.jqwik.api.*;
import net.jqwik.api.constraints.Chars;
import org.junit.jupiter.api.*;

public class EditConfigTest {

    /**
     * Testaa palauttaako checkLegalPrefix() metodi true oikeilla arvoilla.
     */
    @Test
    void checkLegalPrefixTest() {
        String symbols = "*./!?,%&#";
        for (int i = 0; i < symbols.length();i++){
            Assertions.assertEquals(true,EditConfig.checkLegalPrefix(""+symbols.charAt(i)));
        }
    }

    /**
     * Testaa palauttaako checkLegalPrefix() metodi false väärillä arvoilla.
     */
    @Test
    void checkIllegalPrefixTest() {
        String symbols = "abs1236";
        for (int i = 0; i < symbols.length();i++){
            Assertions.assertEquals(false,EditConfig.checkLegalPrefix(""+symbols.charAt(i)));
        }
    }

    /**
     * Testaa toimiiko cfg.txt filuun kirjoitus oikein. Kirjoittaa cfg-filuun prefix arvoksi "!".
     * Luodaan uusi Configuration olio lukemalla uudelleenkirjoitettu cfg.txt tiedosto ja
     * pyydetään siltä prefix.
     */
    @Test
    void writeToConfigurationFileTest() {
        EditConfig.writeToConfigurationFile("prefix==" + "!");
        Configuration configObject = new Configuration(EditConfig.readFromConfigurationFile());
        Assertions.assertEquals("!",configObject.getPrefix());
    }
}
