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
      description = "User should be able to create build type in created project",
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
    softy.assertTrue(buildTypeExists, "Cannot find this build type on the 'Project page'");
  }

  //  @Test(
  //      description = "User should not be able to craete project without name",
  //      groups = {"Negative"})
  //  public void userCreatesProjectWithoutName() {
  //    // подготовка окружения
  //    step("Login as user");
  //    step("Check number of projects");
  //
  //    // взаимодействие с UI
  //    step("Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)");
  //    step("Send all project parameters (repository URL)");
  //    step("Click `Proceed`");
  //    step("Set Project Name");
  //    step("Click `Proceed`");
  //
  //    // проверка состояния API
  //    // (корректность отправки данных с UI на API)
  //    step("Check that number of projects did not change");
  //
  //    // проверка состояния UI
  //    // (корректность считывания данных и отображение данных на UI)
  //    step("Check that error appears `Project name must not be empty`");
  //  }

}
