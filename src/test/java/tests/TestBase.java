package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    public static final String BASE_URL = System.getProperty("baseUrl", "https://demoqa.com");
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final String BROWSER_VERSION = System.getProperty("version", "");
    public static final String BROWSER_SIZE = System.getProperty("windowSize", "1920x1080");
    public static final String REMOTE_DRIVER_URL = System.getProperty("remoteDriverUrl", "selenoid.autotests.cloud");
    public static final int TIMEOUT = Integer.parseInt(System.getProperty("timeout", "5000"));

   /* @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;

    }*/

}