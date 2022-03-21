package autotests.calendar;

import autotests.authorized.AuthorizedBase;
import configurations.ConfProperties;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CalendarPage;

import static com.codeborne.selenide.Selenide.open;
import static pages.StaticHelper.getCurrentMonth;
import static pages.StaticHelper.getCurrentYear;

public class CalendarTest extends AuthorizedBase {

    CalendarPage calendarPage;
    ConfProperties confProperties = ConfProperties.getConfProperties();

    @BeforeMethod
    public void setupCalendar() throws InterruptedException {
        open(confProperties.getProperty("calendarPage"));
        calendarPage = new CalendarPage();
        calendarPage.waitDownloadCalendar();
    }

    @Test
    @Description ("Проверка отображения текущего месяца в календаре для авторизованного пользователя")
    public void checkCalendarCurrentMonth() throws InterruptedException {
        Assert.assertEquals(
                getCurrentMonth(),
                calendarPage.getMonthOnPage(),
                "Проверка на совпадение месяца с текущим"
        );

        Assert.assertEquals(
                getCurrentYear(),
                calendarPage.getYearOnPage(),
                "Проверка на совпадение года с текущим"
        );

        checkWorkingAndWeekendDaysAfterChange();
    }

    @Test
    @Description ("Проверка возможности переключения календаря на следующий месяц для авторизованного пользователя")
    public void checkSwitchingOfTheMonth() throws InterruptedException {
        calendarPage.chooseNextMonth();
        checkWorkingAndWeekendDaysAfterChange();
    }

    @Test
    @Description ("Проверка возможности смены пользователя в календаре")
    public void checkChangeEmployeeInCalendar() throws InterruptedException {
        calendarPage.changeEmployee("Иванов Эдуард");
        checkWorkingAndWeekendDaysAfterChange();
    }

    @Step("Проверка наличия расписания рабочих и выходных дней в календаре")
    private void checkWorkingAndWeekendDaysAfterChange() {
        Assert.assertTrue(
                calendarPage.checkingAvailabilityDaysInCalendar(CalendarPage.days.workDay),
                "Проверка наличия хотя бы одного рабочего дня");

        Assert.assertTrue(
                calendarPage.checkingAvailabilityDaysInCalendar(CalendarPage.days.weekend),
                "Проверка наличия хотя бы одного выходного дня");
    }
}