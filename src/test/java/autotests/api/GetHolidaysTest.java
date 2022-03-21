package autotests.api;

import com.google.gson.JsonObject;
import configurations.ConfProperties;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;


public class GetHolidaysTest {

    @BeforeClass
    public static void setup() {
        ConfProperties confProperties = ConfProperties.getConfProperties();
        RestAssured.baseURI = "http://test-api.d6.dev.devcaz.com";
//        RequestSpecBuilder keyParameter = new RequestSpecBuilder();
//        keyParameter.addParam("Authorization", "front_2d6b0a8391742f5d789d7d915755e09e");
//        RestAssured.requestSpecification = keyParameter.build();
//        ResponseSpecBuilder responseValidations = new ResponseSpecBuilder();
//        responseValidations.expectStatusCode(200);
//        responseValidations.expectBody("response.messages.type", not(hasItem("error")));
//        RestAssured.responseSpecification = responseValidations.build();
    }


    @Test
    public void withoutParametersTest() {
        JSONObject requestBody = new JSONObject();
        requestBody.put("grant_type", "client_credentials");
        requestBody.put("scope", "guest:default");

        Response response = given().auth().oauth2("front_2d6b0a8391742f5d789d7d915755e09e").body(requestBody.toString())
                .when()
                .post("/v2/oauth2/token");
        response.prettyPrint();
    }

    @Test
    public void year2019ParametersTest() {
        given().params("year", 2019).when().
                get("/Calendar/GetHolidays")
                .then()
                .body("response.items.date",
                        everyItem(startsWith("2019")))
                .body("response.items.type", hasItems("holy_day", "short_day"));
    }

    @Test
    public void shortDayTest() throws ParseException {
        JsonPath jsonPath = given().params("day_type", "SHORT_DAY").when().
                get("/Calendar/GetHolidays")
                .then()
                .extract().body().jsonPath();
        List<HolidayItem> items = jsonPath.getList("response.items", HolidayItem.class);

        checkTypeOfDayAndYear("short_day", items);
    }

    @Test
    public void holyDayTest() throws ParseException {
        JsonPath jsonPath = given().params("day_type", "HOLY_DAY").when().
                get("/Calendar/GetHolidays")
                .then()
                .extract().body().jsonPath();
        List<HolidayItem> items = jsonPath.getList("response.items", HolidayItem.class);

        checkTypeOfDayAndYear("holy_day", items);
    }

    @Step
    private void checkTypeOfDayAndYear(String typeOfDay, List<HolidayItem> items) throws ParseException {
        for (HolidayItem item : items) {
            Assert.assertEquals(item.getType(), typeOfDay, "Expected: " + typeOfDay + ", but result is " + item.getType());
            Assert.assertEquals(item.getDate().getYear(), LocalDate.now().getYear(), "Expected: " + LocalDate.now().getYear() + ", but result is " + item.getDate().getYear());
        }
    }
}
