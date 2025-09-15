package properties.tests;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

public class SystemPropertiesTest {

    @Test
    @Tag("property")
    void systemPropertiesTest(){

        String browser = System.getProperty("browser");
        System.out.println(browser);
    }
}
