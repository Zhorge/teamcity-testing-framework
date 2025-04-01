package com.example.teamcity.ui.pages.admin;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Selectors.byClassName;
import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selectors.byValue;
import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.BasePage;
import io.qameta.allure.Step;

public abstract class CreateBasePage extends BasePage {
  protected static final String CREATE_URL =
      "/admin/createObjectMenu.html?projectId=%s&showMode=%s";

  protected SelenideElement urlInput = $(byId("url"));
  protected SelenideElement submitButton = $(byValue("Proceed"));
  protected SelenideElement buildTypeNameInput = $(byId("buildTypeName"));
  protected SelenideElement connectionSuccessfulMessage = $(byClassName("connectionSuccessful"));

  @Step("Send all project parameters (repository URL)")
  protected void baseCreateForm(String url) {
    urlInput.val(url);
    submitButton.click();
    connectionSuccessfulMessage.should(appear, DEFAULT_TIMEOUT);
  }
}
