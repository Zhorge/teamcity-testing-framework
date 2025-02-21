package com.example.teamcity;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static org.apache.http.HttpStatus.SC_OK;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.settings.AuthSettings;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Step;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

public class BaseTest {
  protected SoftAssert softy;
  protected CheckedRequests superUserCheckRequests = new CheckedRequests(Specifications.superUserSpec());
  protected TestData testData;

  @BeforeSuite(alwaysRun = true)
  public void beforeAll() {
    updateAuthSettings();
  }

  @BeforeMethod(alwaysRun = true)
  public void beforeTest() {
    softy = new SoftAssert();
    testData = generate();
  }

  @AfterMethod(alwaysRun = true)
  public void afterTest() {
    softy.assertAll();
    TestDataStorage.getStorage().deleteCreatedEntities();
  }

  @Step("Enable per-project permissions via API")
  private void updateAuthSettings() {
    // 1. Получаем текущие настройки
    AuthSettings currentSettings =
        new UncheckedBase(Specifications.superUserSpec(), Endpoint.SERVER_AUTH_SETTINGS)
            .read()
            .as(AuthSettings.class);

// 2. Обновляем нужные параметры
    AuthSettings updatedSettings = AuthSettings.builder()
        .perProjectPermissions(true)
        .modules(currentSettings.getModules())
        .allowGuest(currentSettings.getAllowGuest())
        .emailVerification(currentSettings.getEmailVerification())
        .guestUsername(currentSettings.getGuestUsername())
        .build();

    // 3. Отправляем обновленные настройки
    new UncheckedBase(Specifications.superUserSpec(), Endpoint.SERVER_AUTH_SETTINGS)
        .update(updatedSettings)
        .then()
        .statusCode(SC_OK);
  }
}
