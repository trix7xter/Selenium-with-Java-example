package pages.report.group;

import io.qameta.allure.Step;
import ru.yandex.qatools.htmlelements.element.TextBlock;

import static com.codeborne.selenide.Selectors.byCssSelector;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.$;

public class EditPage {

    public EditPage(){
    }

    /******************************************************/
    /********************HTML-ELEMENTS*********************/
    /******************************************************/

    private TextBlock htmlForAvatar = new TextBlock($(byCssSelector(".m-topbar__userpic .avatarCover")));
    private TextBlock htmlForFirstnameAndLastname = new TextBlock($(byXpath("//span[contains (@class, 'm-card-user__name')]")));
    private TextBlock htmlForEmail = new TextBlock($(byCssSelector("[class^='m-card-user__email']")));

    /******************************************************/
    /***********************METHODS***********************/
    /******************************************************/

    @Step("Нажатие на иконку аватарки")
    public EditPage clickOnAvatar(){
        htmlForAvatar.click();
        return this;
    }

    @Step("Получить фамилию пользователя")
    public String getUserLastname(){
        String initials = htmlForFirstnameAndLastname.getText();
        return initials.split(" ")[1];
    }

    @Step("Получить email пользователя")
    public String getUserEmail(){
        return htmlForEmail.getText();
    }
}