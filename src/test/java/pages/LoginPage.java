package pages;

import io.qameta.allure.Step;
import pages.report.group.EditPage;
import ru.yandex.qatools.htmlelements.element.*;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.url;

public class LoginPage {

    public LoginPage() {
    }

    /******************************************************/
    /********************HTML-ELEMENTS*********************/
    /******************************************************/

    private TextInput htmlForUsername = new TextInput($(byCssSelector("#username")));
    private TextBlock htmlForInvalidCredentials = new TextBlock($(byXpath("//div[contains (text(), 'Invalid credentials.')]")));
    private TextInput htmlForPassword = new TextInput($(byCssSelector("[name='_password']")));
    private Button htmlForLoginButton = new Button($(byXpath("//input[@value='Войти']")));

    /******************************************************/
    /***********************METHODS***********************/
    /******************************************************/

    @Step("Ввод логина пользователя {login}")
    public LoginPage writeLogin(String login) {
        htmlForUsername.sendKeys(login);
        return this;
    }

    @Step("Ввод пароля пользователя {password}")
    public LoginPage writePassword(String password) {
        htmlForPassword.sendKeys(password);
        return this;
    }

    @Step("Нажать на кнопку 'Войти'")
    public Object clickOnLoginButton() {
        htmlForLoginButton.click();
        if(url().equals("https://tt.quality-lab.ru/login")){
            return this;
        }
        return new EditPage();
    }

    @Step("Проверка на видимость текста 'Invalid Credentials'")
    public boolean checkVisibleLocatorForInvalidCredentials() {
        return htmlForInvalidCredentials.isDisplayed();
    }

    @Step("Получить текст из поля Login")
    public String getTextInLogin() {
        return htmlForUsername.getAttribute("value");
    }

    @Step("Получить текст из поля Password")
    public String getTextInPassword() {
        return htmlForPassword.getAttribute("value");
    }
}
