package com.example.teamcity.ui.setup;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class FirstStartPage extends BasePage {
  private final SelenideElement restoreButton = $("#restoreButton");
  private final SelenideElement proceedButton = $("#proceedButton");
  private final SelenideElement dbTypeSelect = $("#dbType");
  private final SelenideElement acceptLicenseCheckbox = $("#accept");
  private final SelenideElement submitButton = $("input[type='submit']");

  public FirstStartPage() {
    restoreButton.shouldBe(Condition.visible, LONG_TIMEOUT);
  }

  @Step("Open first start page")
  public static FirstStartPage open() {
    return Selenide.open("/", FirstStartPage.class);
  }

  @Step("Setup first start page")
  public FirstStartPage setupFirstStart() {
    proceedButton.click();
    dbTypeSelect.shouldBe(Condition.visible, LONG_TIMEOUT);
    proceedButton.click();
    acceptLicenseCheckbox.should(Condition.exist, LONG_TIMEOUT).scrollTo().click();
    submitButton.click();
    return this;
  }
}
