package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import com.codeborne.selenide.ElementsCollection;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.element.TextInput;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;

public class CalendarPage {

    /******************************************************/
    /********************HTML-ELEMENTS*********************/
    /******************************************************/

    private TextBlock htmlDownloadCalendar = new TextBlock($(By.xpath("//div[@id='schedule-overlay']")));
    private TextBlock htmlDateMonth = new TextBlock($(By.xpath("//h3//span[@id = 'schedule-month-title']")));
    private TextBlock htmlLocatorDayInTable = new TextBlock($(By.xpath("//a[contains(@class, 'fc-day-grid-event')]")));
    private Button htmlCalendarButton = new Button($(By.xpath("//input[@name='filter-date']")));
    private TextBlock htmlMonthInTable = new TextBlock($(By.xpath("//div[@class='datepicker-months']//span")));
    private Button htmlNextYearButton = new Button($(By.xpath("//div[@class='datepicker-months']//th[@class='next']")));
    private Button htmlAllowButton = new Button($(By.xpath("//button[contains (text(),  'Применить')]")));
    private Button htmlSelectEmployee = new Button($(By.xpath("//div[@id='schedule-filters']//span[@class='select2-selection__rendered']")));
    private TextInput htmlSearchEmployeeInput = new TextInput($(By.xpath("//span[contains (@class,  'select2-dropdown')]//input")));
    private TextBlock htmlResultOfSearch = new TextBlock($(By.xpath("//span[@class='select2-results']//li")));

    /******************************************************/
    /************************METHODS***********************/
    /******************************************************/

    @Step("Ожидание загрузки календаря")
    public void waitDownloadCalendar() throws InterruptedException {
        $(htmlDownloadCalendar.getWrappedElement()).shouldHave(attribute("style", "display: none;"));
    }

    @Step("Получение месяца со страницы")
    public String getMonthOnPage() {
        String month = htmlDateMonth.getText();
        return month.split(" ")[0];
    }

    @Step("Получение года со страницы")
    public int getYearOnPage() {
        String year = htmlDateMonth.getText();
        return Integer.parseInt(year.split(" ")[1]);
    }

    @Step("Проверка наличия наличия дней в календаре")
    private Boolean checkingDayInCalendar(String className){
        ElementsCollection days = $$((By) htmlLocatorDayInTable.getWrappedElement());
        long mount = days.stream().filter(day -> day.getAttribute("class").contains(className)).count();
        return mount > 0;
    }

    public enum days {
        weekend, workDay
    }

    @Step("Метод выбора рабочего или выходного дня")
    public Boolean checkingAvailabilityDaysInCalendar(Enum day) {
        if(day == days.weekend){
           return checkingDayInCalendar("schedule-badge--no-event");
        } else {
            return checkingDayInCalendar("schedule-badge--default");
        }
    }

    @Step("Выбрать следующий месяц")
    public void chooseNextMonth() throws InterruptedException {
        htmlCalendarButton.click();
        ElementsCollection months = $$((By) htmlLocatorDayInTable.getWrappedElement());

        for (int i = 0; i < 12; i++) {
            if (months.get(i).getAttribute("class").contains("focused active")) {
                if (i != 11) {
                    months.get(i + 1).click();
                } else {
                    htmlNextYearButton.click();
                    months = $$((By) htmlMonthInTable.getWrappedElement());
                    months.get(0).click();
                }
                htmlAllowButton.click();
                break;
            }
        }
        waitDownloadCalendar();
    }

    @Step("Метод смены сотрудника")
    public void changeEmployee(String employee) throws InterruptedException {
        htmlSelectEmployee.click();
        htmlSearchEmployeeInput.sendKeys(employee);
        htmlResultOfSearch.click();
        htmlAllowButton.click();
        waitDownloadCalendar();
    }
}