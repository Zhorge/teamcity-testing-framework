package com.example.teamcity.ui.pages.admin;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class CreateBuildTypePage extends CreateBasePage {
  private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

  public final SelenideElement buildTypeErrorMessage = $(byId("error_buildTypeName"));

  @Step("Open 'Create Build Type Page' ")
  public static CreateBuildTypePage open(String projectId) {
    return Selenide.open(
        CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildTypePage.class);
  }

  public CreateBuildTypePage createForm(String url) {
    baseCreateForm(url);
    return page(CreateBuildTypePage.class);
  }

  @Step("Send 'Build Type Name' and Click 'Proceed'")
  public CreateBuildTypePage setupBuildType(String buildTypeName) {
    buildTypeNameInput.val(buildTypeName);
    submitButton.click();
    return page(CreateBuildTypePage.class);
  }
}
