package com.example.teamcity.ui.pages.admin;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public class CreateBuildTypePage extends CreateBasePage {
  private static final String BUILD_TYPE_SHOW_MODE = "createBuildTypeMenu";

  private final SelenideElement projectNameInput = $(byId("buildTypeName"));

  public static CreateBuildTypePage open(String projectId) {
    return Selenide.open(
        CREATE_URL.formatted(projectId, BUILD_TYPE_SHOW_MODE), CreateBuildTypePage.class);
  }

  public CreateBuildTypePage createForm(String url) {
    baseCreateForm(url);
    return page(CreateBuildTypePage.class);
  }

  public void setupBuildType(String buildTypeName) {
    buildTypeNameInput.val(buildTypeName);
    submitButton.click();
  }
}
