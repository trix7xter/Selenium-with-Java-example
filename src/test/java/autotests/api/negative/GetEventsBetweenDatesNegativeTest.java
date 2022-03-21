package autotests.api.negative;

import configurations.ConfProperties;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class GetEventsBetweenDatesNegativeTest extends BaseSettingsForNegativeTests{

    @BeforeClass
    public void setupEventsBetweenDatesNegative(){
        ResponseSpecBuilder responseValidations = new ResponseSpecBuilder();
        responseValidations.expectStatusCode(200);
        responseValidations.expectBody("response.messages.type", hasItem("error"));
        responseValidations.expectBody("response.count", hasItem(0));
        RestAssured.responseSpecification = responseValidations.build();
    }

    @Test
    public void incorrectYearsForBetweenDatesTest() {
        given()
                .param("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8")
                .param( "user_id", "123")
                .param( "dateFrom", "2019-02-20")
                .param( "dateTo", "2019-02-01")
                .when().
                get("/Calendar/GetEventsBetweenDates")
                .then()
                .body("response.messages.text", hasItem("Не удалось получить календарь пользователя"));
    }

    @Test
    public void requestWithoutDateTest() {
        given()
                .param("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8")
                .param( "user_id", "3")
                .when().
                get("/Calendar/GetEventsBetweenDates")
                .then()
                .body("response.messages.text", hasItem("Необходимо указать параметры date_from и date_to в формате Y-m-d"));
    }

    @Test
    public void requestWithoutIdTest() {
        given()
                .param("key", "wvS9fmlcgT6jOIO6tyhESV55F6dbNpk3PeWkobf8")
                .param( "dateFrom", "2019-02-20")
                .param( "dateTo", "2019-02-21")
                .when().
                get("/Calendar/GetEventsBetweenDates")
                .then()
                .body("response.messages.text", hasItem("Пользователь не найден"));
    }
}
