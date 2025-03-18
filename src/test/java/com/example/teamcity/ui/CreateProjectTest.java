package com.example.teamcity.ui;

import static com.codeborne.selenide.Condition.exactText;
import static io.qameta.allure.Allure.step;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.ui.pages.ProjectPage;
import com.example.teamcity.ui.pages.ProjectsPage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import org.testng.annotations.Test;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
  private static final String REPO_URL = "https://github.com/Zhorge/teamcity-testing-framework";

  @Test(
      description = "User should be able to create project",
      groups = {"Positive"})
  public void userCreatesProject() {
    // подготовка окружения
    loginAs(testData.getUser());

    // взаимодействие с UI
    CreateProjectPage.open("_Root")
        .createForm(REPO_URL)
        .setupProject(testData.getProject().getName(), testData.getBuildType().getName());

    // проверка состояния API
    // (корректность отправки данных с UI на API)
    var createdProject =
        superUserCheckRequests
            .<Project>getRequester(Endpoint.PROJECTS)
            .readByLocator("name:" + testData.getProject().getName());
    softy.assertNotNull(createdProject);

    // Добавление созданного через UI проекта в дата сторедж для дальнейшего удаления после теста
    TestDataStorage.getStorage().addCreatedEntity(Endpoint.PROJECTS, createdProject);

    // проверка состояния UI
    // (корректность считывания данных и отображение данных на UI)
    ProjectPage.open(createdProject.getId())
        .title.shouldHave(exactText(testData.getProject().getName()));

    var projectExists =
        ProjectsPage.open().getProjects().stream()
            .anyMatch(project -> project.getName().text().equals(testData.getProject().getName()));
    softy.assertTrue(projectExists);
  }

  @Test(
      description = "User should not be able to craete project without name",
      groups = {"Negative"})
  public void userCreatesProjectWithoutName() {
    // подготовка окружения
    step("Login as user");
    step("Check number of projects");

    // взаимодействие с UI
    step("Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)");
    step("Send all project parameters (repository URL)");
    step("Click `Proceed`");
    step("Set Project Name");
    step("Click `Proceed`");

    // проверка состояния API
    // (корректность отправки данных с UI на API)
    step("Check that number of projects did not change");

    // проверка состояния UI
    // (корректность считывания данных и отображение данных на UI)
    step("Check that error appears `Project name must not be empty`");
  }
}
