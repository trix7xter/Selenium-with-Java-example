package autotests.api.negative;

import configurations.ConfProperties;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;


public class GetHolidaysNegativeTest extends BaseSettingsForNegativeTests{

    @Test
    public void withoutKeyNegativeTest() {
        when().
                get("/Calendar/GetHolidays")
                .then()
                .body("response.messages.text", hasItem("Доступ запрещен"))
                .body("response.messages.type", hasItem("error"))
                .body("response.count", hasItem(0))
                .statusCode(200);
    }

    @Test
    public void incorrectYearInFutureTest() {
        given()
                .param("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8")
                .param( "year", 2100)
                .when().
                get("/Calendar/GetHolidays")
                .then()
                .body("response.messages.text", hasItem("Запрос успешно выполнен"))
                .body("response.messages.type", hasItem("success"))
                .body("response.count", hasItem(0))
                .statusCode(200);
    }

    @Test
    public void incorrectYearInPastTest() {
        given()
                .param("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8")
                .param( "year", 2000)
                .when().
                get("/Calendar/GetHolidays")
                .then()
                .body("response.messages.text", hasItem("Запрос успешно выполнен"))
                .body("response.messages.type", hasItem("success"))
                .body("response.count", hasItem(0))
                .statusCode(200);
    }
}
