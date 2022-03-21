package autotests.login;

import autotests.TestBase;
import configurations.ConfProperties;
import io.qameta.allure.Description;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import static com.codeborne.selenide.WebDriverRunner.url;

public class LoginNegativeTest extends TestBase {

    ConfProperties confProperties = ConfProperties.getConfProperties();

    @Test
    @Description("Проверка авторизации с некорректными данными")
    public void incorrectUserNameAndPassword() {
        LoginPage loginPage = new LoginPage();
        String userLogin = "TestUser";
        String userPassword = "Password";

        loginPage.writeLogin(userLogin)
                .writePassword(userPassword);

        Assert.assertFalse(
                loginPage.checkVisibleLocatorForInvalidCredentials(),
                "Проверка на отсутствие текста Invalid Credentials");

        loginPage.clickOnLoginButton();

        Assert.assertTrue(
                loginPage.checkVisibleLocatorForInvalidCredentials(),
                "Проверка на наличие текста Invalid Credentials");

        Assert.assertEquals(
                loginPage.getTextInLogin(),
                userLogin,
                "Проверка на совпадение логина после нажатия на кнопку");

        String clearPassword = loginPage.getTextInPassword();

        Assert.assertEquals(
                "",
                clearPassword,
                "Проверка на пустое поля ввода Password"
        );
    }

    @Test
    @Description("Проверка авторизации с пустым логином и паролем")
    public void clearUserNameAndPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.clickOnLoginButton();

        Assert.assertFalse(
                loginPage.checkVisibleLocatorForInvalidCredentials(),
                "Проверка на отсутствие текста Invalid Credentials");

        Assert.assertEquals(
                confProperties.getProperty("loginPage"),
                url(),
                "Проверка отсутствия перехода на страницу после попытки авторизации с неправильными данными"
        );
    }
}