package properties.tests;

import org.junit.jupiter.api.Test;

public class SystemPropertiesTest {

    @Test
    void systemPropertiesTest(){
        System.setProperty("browser","chrome");
        String browser = System.getProperty("browser");
        System.out.println(browser);
    }
}
