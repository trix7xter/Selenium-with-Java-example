package autotests;

import autotests.system.Monitoring;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverProvider;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import configurations.ConfProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import javax.annotation.Nonnull;
import java.net.MalformedURLException;
import java.net.URL;

import static com.codeborne.selenide.Selenide.*;

@Listeners(autotests.system.Monitoring.class)
public class TestBase {

    /**
     * осуществление первоначальной настройки
     */
    @Step ("Предварительная настройка браузера")
    @BeforeMethod
    public void setup() throws InterruptedException {
        ConfProperties confProperties = ConfProperties.getConfProperties();
        Configuration.browser = "chrome";
        open(confProperties.getProperty("loginPage"));
        WebDriverRunner.getWebDriver().manage().window().setSize(new Dimension(1200, 800));
    }

    /**
     * закрытие браузера
     */
    @Step ("Закрытие браузера")
    @AfterMethod
    public void exit() {
        if (WebDriverRunner.getWebDriver() != null) {
            closeWebDriver();
        }
    }
}