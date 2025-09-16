package properties.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SystemPropertiesTest {

    @Test
    @Tag("property")
    void systemPropertiesTest(){


        String browser = System.getProperty("browser", "chrome");
        String version = System.getProperty("version", "101");
        String windowSize = System.getProperty("windowSize", "1920x1080");
        System.out.println(browser);
        System.out.println(version);
        System.out.println(windowSize);
    }
}
