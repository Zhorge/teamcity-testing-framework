package com.example.teamcity.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.example.teamcity.BaseTest;
import com.example.teamcity.api.config.Config;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.User;
import com.example.teamcity.ui.pages.LoginPage;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import java.util.Map;

public class BaseUiTest extends BaseTest {
  protected static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";

  @BeforeSuite(alwaysRun = true)
  public void setupUiTest() {
    Configuration.browser = Config.getProperty("browser");
    Configuration.baseUrl = "http://" + Config.getProperty("host");
    // НЕТ ПИШИТЕ UI ТЕСТЫ С ЛОКАЛЬНЫМ БРАУЗЕРОМ - СРАЗУ ПРОВЕРЯЕМ НА REMOTE БРАУЗЕРЕ
    Configuration.remote = Config.getProperty("remote");
    Configuration.browserSize = Config.getProperty("browserSize");

    Configuration.browserCapabilities.setCapability(
        "selenoid:options", Map.of("enableVNC", true, "enableLog", true));

    SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
        .screenshots(true)
        .savePageSource(true)
        .includeSelenideSteps(true));
  }

  @AfterMethod(alwaysRun = true)
  public void closeWebDriver() {
    Selenide.closeWebDriver();
  }

  @Step("Login as user: 'user.name'")
  protected void loginAs(User user) {
    superUserCheckRequests.getRequester(Endpoint.USERS).create(user);
    LoginPage.open().login(user);
  }
}
