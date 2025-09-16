package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class TestBase {

    protected static String REMOTE_DRIVER_URL;
    protected static String REMOTE_USER;
    protected static String REMOTE_PASSWORD;

    @BeforeAll
    static void beforeAll() throws IOException {
        loadCredentials();

        Configuration.baseUrl = System.getProperty("url", "https://demoqa.com");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("version", "114.0");
        Configuration.browserSize = System.getProperty("windowSize", "1920x1080");
        Configuration.timeout = Integer.parseInt(System.getProperty("timeout", "5000"));
        Configuration.pageLoadStrategy = "eager";
        Configuration.remote= System.getProperty("remoteDriverUrl");

       // REMOTE_DRIVER_URL = System.getProperty("remoteDriverUrl", "selenoid.autotests.cloud");

        if (!REMOTE_DRIVER_URL.isEmpty()) {
            Configuration.remote = String.format(
                    "https://%s:%s@%s/wd/hub",
                    REMOTE_USER, REMOTE_PASSWORD, REMOTE_DRIVER_URL
            );

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }

        SelenideLogger.addListener("allure", new AllureSelenide());
        System.out.println("Remote WebDriver URL: " + Configuration.remote);
    }

    private static void loadCredentials() throws IOException {
        Properties props = new Properties();
        try (InputStream is = TestBase.class.getClassLoader().getResourceAsStream("test.properties")) {
            props.load(is);
        }
        REMOTE_USER = props.getProperty("remoteUser");
        REMOTE_PASSWORD = props.getProperty("remotePassword");
    }
}
