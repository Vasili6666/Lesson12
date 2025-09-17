package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class TestBase {

    @BeforeAll
    static void beforeAll() {

        Configuration.baseUrl = System.getProperty("url", "https://demoqa.com");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("version", "114.0");
        Configuration.browserSize = System.getProperty("windowSize", "1920x1080");
        Configuration.timeout = Integer.parseInt(System.getProperty("timeout", "5000"));
        Configuration.pageLoadStrategy = "eager";

        Configuration.remote = System.getProperty("remoteDriverUrl");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        SelenideLogger.addListener("allure", new AllureSelenide());

    }

    @AfterEach
    void addAttachments() {
        String sessionId = Selenide.sessionId().toString();
        String remoteUrl = System.getProperty("videoHost", "selenoid.autotests.cloud");
        String videoUrl = String.format("https://%s/video/%s.mp4", remoteUrl, sessionId);

        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo(videoUrl);  // передаем ссылку сюда
    }

    @AfterAll
    static void afterAll() {
        closeWebDriver();
    }
}
