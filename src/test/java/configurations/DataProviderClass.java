package configurations;

import org.testng.annotations.DataProvider;

public class DataProviderClass {

    @DataProvider(name = "test-data")
    public static Object[][] dataProviderMethodWithTestData(){
        return new Object[][] {{ "\u0410\u0432\u0442\u043e \u041c\u044f\u0443", "Password" }, { "\u0410\u0432\u0442\u043e \u041f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044c", "Password"} };
    }
}