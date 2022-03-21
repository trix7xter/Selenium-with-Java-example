package autotests.login;

import autotests.TestBase;
import configurations.ConfProperties;
import configurations.DataProviderClass;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.report.group.EditPage;

import static com.codeborne.selenide.WebDriverRunner.url;

@Listeners(autotests.system.Monitoring.class)
public class LoginPositiveTest extends TestBase {

    @Test(dataProviderClass = DataProviderClass.class, dataProvider = "test-data")
    public void correctUserNameAndPassword(String login, String password) {
        LoginPage loginPage = new LoginPage();
        ConfProperties confProperties = ConfProperties.getConfProperties();
        String userEmail = confProperties.getProperty("email");

        loginPage.writeLogin(login)
                .writePassword(password);
        EditPage editPage = (EditPage) loginPage.clickOnLoginButton();

        Assert.assertEquals(
                confProperties.getProperty("editPage"),
                url(),
                "Проверка перехода на страницу после авторизации не удалась");

        editPage.clickOnAvatar();

        Assert.assertEquals(
                login.split(" ")[1],
                editPage.getUserLastname(),
                "Фамилия пользователя на странице не совпадает с фамилией, указанной при авторизации");

        Assert.assertEquals(
                userEmail,
                editPage.getUserEmail(),
                "Email пользователя на странице не совпадает с email из properties");
    }
}