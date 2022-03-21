package autotests.api.negative;

import configurations.ConfProperties;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeClass;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class BaseSettingsForNegativeTests {
    @BeforeClass
    public static void setup() {
        ConfProperties confProperties = ConfProperties.getConfProperties();
        RestAssured.baseURI = confProperties.getProperty("restTestPage");
        RestAssured.port = 443;
        RestAssured.basePath = "/api/v2/public";
    }
}
