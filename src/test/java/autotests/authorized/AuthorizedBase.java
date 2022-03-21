package autotests.authorized;

import autotests.TestBase;
import configurations.ConfProperties;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import pages.LoginPage;
import pages.report.group.EditPage;

import static com.codeborne.selenide.WebDriverRunner.url;

public class AuthorizedBase extends TestBase {

    @Step ("Метод авторизации с корректными данными")
    @BeforeMethod
    public void beforeMethod() {
        LoginPage loginPage = new LoginPage();
        ConfProperties confProperties = ConfProperties.getConfProperties();

        String userLogin = confProperties.getProperty("login");
        String userPassword = confProperties.getProperty("password");

        loginPage.writeLogin(userLogin)
                .writePassword(userPassword);
        EditPage editPage = (EditPage) loginPage.clickOnLoginButton();

        Assert.assertEquals(
                confProperties.getProperty("editPage"),
                url(),
                "Проверка перехода на страницу после авторизации");
    }
}