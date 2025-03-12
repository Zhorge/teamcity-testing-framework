package com.example.teamcity.ui.pages.admin;

import static com.codeborne.selenide.Selectors.byId;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.ProjectsPage;

public class CreateProjectPage extends CreateBasePage {
  private static final String PROJECT_SHOW_MODE = "createProjectMenu";

  private final SelenideElement projectNameInput = $(byId("projectName"));

  public static CreateProjectPage open(String projectId) {
    return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateProjectPage.class);
  }

  public CreateProjectPage createForm(String url) {
    baseCreateForm(url);
    return page(CreateProjectPage.class);
  }

  public ProjectsPage setupProject(String projectName, String buildTypeName) {
    projectNameInput.val(projectName);
    buildTypeNameInput.val(buildTypeName);
    submitButton.click();
    return page(ProjectsPage.class);
  }
}
