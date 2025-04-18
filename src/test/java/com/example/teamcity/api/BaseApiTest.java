package com.example.teamcity.api;

import static org.apache.http.HttpStatus.SC_OK;

import com.example.teamcity.BaseTest;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.settings.AuthSettings;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import io.qameta.allure.Step;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseApiTest extends BaseTest {
  private final UncheckedBase authSettingsRequest =
      new UncheckedBase(Specifications.superUserSpec(), Endpoint.SERVER_AUTH_SETTINGS);
  private AuthSettings currentSettings;

  @BeforeSuite(alwaysRun = true)
  public void setUp() {
    setUpServerAuthSettings();
  }

  @AfterSuite(alwaysRun = true)
  public void afterAll() {
    cleanUpServerAuthSettings();
  }

  @Step("Enable per-project permissions via API")
  private void setUpServerAuthSettings() {
    currentSettings = authSettingsRequest.read().as(AuthSettings.class);
    authSettingsRequest.update(withPerProjectPermissions(true)).then().statusCode(SC_OK);
  }

  @Step("Return per-project permissions")
  private void cleanUpServerAuthSettings() {
    boolean value = currentSettings.getPerProjectPermissions();
    authSettingsRequest.update(withPerProjectPermissions(value));
  }

  private AuthSettings withPerProjectPermissions(boolean value) {
    return AuthSettings.builder()
        .perProjectPermissions(value)
        .modules(currentSettings.getModules())
        .allowGuest(currentSettings.getAllowGuest())
        .emailVerification(currentSettings.getEmailVerification())
        .guestUsername(currentSettings.getGuestUsername())
        .build();
  }
}
