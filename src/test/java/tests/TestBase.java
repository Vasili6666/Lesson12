/*package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    public static final String BASE_URL = System.getProperty("baseUrl", "https://demoqa.com");
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final String BROWSER_VERSION = System.getProperty("version", "");
    public static final String BROWSER_SIZE = System.getProperty("windowSize", "1920x1080");
    public static final String REMOTE_DRIVER_URL = System.getProperty("remoteDriverUrl", "selenoid.autotests.cloud");
    public static final int TIMEOUT = Integer.parseInt(System.getProperty("timeout", "5000"));

   *//* @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = "https://demoqa.com";
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 10000;

    }*//*

}*/
package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    public static final String BASE_URL = System.getProperty("baseUrl", "https://demoqa.com");
    public static final String BROWSER = System.getProperty("browser", "chrome");
    public static final String BROWSER_VERSION = System.getProperty("version", "");
    public static final String BROWSER_SIZE = System.getProperty("windowSize", "1920x1080");
    public static final String REMOTE_DRIVER_URL = System.getProperty("remoteDriverUrl", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
    public static final int TIMEOUT = Integer.parseInt(System.getProperty("timeout", "5000"));

    @BeforeAll
    static void beforeAll() {
        Configuration.baseUrl = BASE_URL;
        Configuration.browser = BROWSER;
        Configuration.browserVersion = BROWSER_VERSION;
        Configuration.browserSize = BROWSER_SIZE;
        Configuration.timeout = TIMEOUT;
        Configuration.pageLoadStrategy = "eager";

        if (!REMOTE_DRIVER_URL.isEmpty()) {
            Configuration.remote = REMOTE_DRIVER_URL;

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                    "enableVNC", true,
                    "enableVideo", true
            ));
            Configuration.browserCapabilities = capabilities;
        }

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}

