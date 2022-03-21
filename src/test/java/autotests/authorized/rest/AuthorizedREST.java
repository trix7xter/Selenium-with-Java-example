package autotests.authorized.rest;

import configurations.ConfProperties;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import okhttp3.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;

public class AuthorizedREST {

    @Step("Проверка логина через REST API")
    @BeforeMethod
    public void authorizedTest() throws IOException {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        JavaNetCookieJar cookieJar = new JavaNetCookieJar(cookieManager);

        ConfProperties confProperties = ConfProperties.getConfProperties();
        String loginPage = confProperties.getProperty("loginPage");
        String loginCheck = confProperties.getProperty("loginCheckPage");

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .followRedirects(false)
                .build();

        Request request = new Request.Builder()
                .url(loginPage)
                .build();

        Call call = client.newCall(request);
        Response responseGet = call.execute();

        RequestBody formBody = new FormBody.Builder()
                .add("_csrf", "")
                .add("_username", "Авто Пользователь")
                .add("_password", "Пароль")
                .add("_submit", "Войти")
                .build();

        Request requestPost = new Request.Builder()
                .url(loginCheck)
                .post(formBody)
                .build();

        Call callPost = client.newCall(requestPost);
        Response responsePost = callPost.execute();

        WebDriverManager.chromedriver().setup();
        WebDriver webDriver = new ChromeDriver();

        webDriver.get(loginPage);
        webDriver.manage().deleteAllCookies();

        cookieManager.getCookieStore().getCookies().forEach(httpCookie -> {
            Cookie cookie = new Cookie(
                    httpCookie.getName(),
                    httpCookie.getValue(),
                    httpCookie.getDomain(),
                    httpCookie.getPath(),
                    null
            );

            webDriver.manage()
                    .addCookie(cookie);
        });
        webDriver.navigate().refresh();
    }
}
