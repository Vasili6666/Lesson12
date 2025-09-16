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
        // -----------------------------
        // Основные настройки браузера
        // -----------------------------
        Configuration.baseUrl = System.getProperty("url", "https://demoqa.com");
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserVersion = System.getProperty("version", "114.0");
        Configuration.browserSize = System.getProperty("windowSize", "1920x1080");
        Configuration.timeout = Integer.parseInt(System.getProperty("timeout", "5000"));
        Configuration.pageLoadStrategy = "eager";

        // -----------------------------
        // Remote WebDriver (Selenoid)
        // -----------------------------
        // Берем URL удаленного драйвера из Jenkins через -DremoteDriverUrl
        Configuration.remote = System.getProperty("remoteDriverUrl");

        // -----------------------------
        // Capabilities для видео и VNC
        // -----------------------------
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        Configuration.browserCapabilities = capabilities;

        // -----------------------------
        // Allure Listener
        // -----------------------------
        SelenideLogger.addListener("allure", new AllureSelenide());

        // Для отладки: показываем какой remote используется
        System.out.println("Remote WebDriver URL: " + Configuration.remote);
    }

    // -----------------------------
    // Добавление скриншотов, логов и видео после каждого теста
    // -----------------------------
    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();

        // Создаём videoUrl для прикрепления видео в Allure
        String sessionId = Selenide.sessionId().toString();
        String remoteUrl = System.getProperty("REMOTE_DRIVER_URL", "selenoid.autotests.cloud");
        String videoUrl = String.format("https://%s/video/%s.mp4", remoteUrl, sessionId);

        Attach.addVideo(videoUrl);
    }

    // -----------------------------
    // Закрытие браузера после всех тестов
    // -----------------------------
    @AfterAll
    static void afterAll() {
        closeWebDriver();
    }
}
