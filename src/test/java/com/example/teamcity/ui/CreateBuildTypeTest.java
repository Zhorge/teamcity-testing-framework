package com.example.teamcity.ui;

import static com.codeborne.selenide.Condition.text;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.admin.CreateBuildTypePage;
import org.testng.annotations.Test;

@Test(groups = {"Regression"})
public class CreateBuildTypeTest extends BaseUiTest {

  @Test(
      description = "User should be able to create 'build type' in created project",
      groups = {"Positive"})
  public void userCreatesBuildType() {
    // подготовка окружения
    loginAs(testData.getUser());
    userCheckedRequests.getRequester(Endpoint.PROJECTS).create(testData.getProject());

    // взаимодействие с UI
    CreateBuildTypePage.open(testData.getProject().getId())
        .createForm(REPO_URL)
        .setupBuildType(testData.getBuildType().getName());

    // проверка состояния API
    // (корректность отправки данных с UI на API)
    var createdBuildType =
        superUserCheckRequests
            .<BuildType>getRequester(Endpoint.BUILD_TYPES)
            .readByLocator("name:" + testData.getBuildType().getName());
    softy.assertNotNull(createdBuildType);

    // проверка состояния UI
    // (корректность считывания данных и отображение данных на UI)
    boolean buildTypeExists =
        ProjectPage.open(testData.getProject().getId())
            .getBuildTypes()
            .stream()
            .anyMatch(b -> b.getName().has(text(testData.getBuildType().getName())));
    softy.assertTrue(buildTypeExists, "Cannot find this 'build type' on the 'Project page'");
  }

    @Test(
        description = "User should not be able to create 'build type' with the same name in one project",
        groups = {"Negative"})
    public void userCreatesBuildTypeWithTheSameName() {
      loginAs(testData.getUser());
      userCheckedRequests.getRequester(Endpoint.PROJECTS).create(testData.getProject());
      userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());

    String errorMessage =
        "Build configuration with name \"%s\" already exists in project: \"%s\""
            .formatted(testData.getBuildType().getName(), testData.getProject().getName());

    CreateBuildTypePage.open(testData.getProject().getId())
        .createForm(REPO_URL)
        .setupBuildType(testData.getBuildType().getName())
        .buildTypeErrorMessage.shouldHave(text(errorMessage));
    }
}
