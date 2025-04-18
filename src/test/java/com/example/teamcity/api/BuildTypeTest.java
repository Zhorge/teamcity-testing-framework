package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.RoleGenerator;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Role;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.Steps;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import java.util.Arrays;
import java.util.List;
import org.testng.annotations.Test;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static com.example.teamcity.api.spec.ValidationResponseSpecifications.checkBuildTypeWithIdAlreadyExist;
import static com.example.teamcity.api.spec.ValidationResponseSpecifications.checkProjectIdWrongPermission;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
  private static final String BUILD_TYPE_IS_NOT_CORRECT_MESSAGE = "Build type is not correct";

  @Test(
      description = "User should be able to create build type",
      groups = {"Positive", "CRUD"})
  public void userCreatesBuildTypeTest() {
    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());
    userCheckedRequests.getRequester(Endpoint.PROJECTS).create(testData.getProject());
    userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());

    BuildType createdBuildType =
        userCheckedRequests
            .<BuildType>getRequester(Endpoint.BUILD_TYPES)
            .readById(testData.getBuildType().getId());

    BuildType expectedBuildType = testData.getBuildType();
    expectedBuildType.setSteps(new Steps(0, null));

    softy.assertEquals(createdBuildType, expectedBuildType, BUILD_TYPE_IS_NOT_CORRECT_MESSAGE);
  }

  @Test(
      description = "User should not be able to create two build types with the same id",
      groups = {"Negative", "CRUD"})
  public void userCreatesTwoBuildTypesWithTheSameIdTest() {
    BuildType buildTypeWithSameId =
        generate(
            Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());
    userCheckedRequests.<Project>getRequester(Endpoint.PROJECTS).create(testData.getProject());
    userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());

    new UncheckedBase(Specifications.authSpec(testData.getUser()), Endpoint.BUILD_TYPES)
        .create(buildTypeWithSameId)
        .then()
        .spec(checkBuildTypeWithIdAlreadyExist(testData.getBuildType().getId()));
  }

  @Test(
      description = "Project admin should be able to create build type for their project",
      groups = {"Positive", "Roles"})
  public void projectAdminCreatesBuildTypeTest() {
    setupAnotherProject(testData);
    userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());

    BuildType createdBuildType =
        userCheckedRequests
            .<BuildType>getRequester(Endpoint.BUILD_TYPES)
            .readById(testData.getBuildType().getId());

    BuildType expectedBuildType = testData.getBuildType();
    expectedBuildType.setSteps(new Steps(0, null));

    softy.assertEquals(createdBuildType, expectedBuildType, BUILD_TYPE_IS_NOT_CORRECT_MESSAGE);
  }

  @Test(
      description = "Project admin should not be able to create build type for not their project",
      groups = {"Negative", "Roles"})
  public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
    TestData testData2 = generate();

    setupAnotherProject(testData);

    setupAnotherProject(testData2);

    new UncheckedBase(Specifications.authSpec(testData2.getUser()), Endpoint.BUILD_TYPES)
        .create(testData.getBuildType())
        .then()
        .spec(checkProjectIdWrongPermission(testData.getProject().getId()));
  }

  private void setupAnotherProject(TestData testData) {
    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());
    superUserCheckRequests.getRequester(Endpoint.PROJECTS).create(testData.getProject());
    assignProjectAdminRoleFor(testData);
  }

  private void assignProjectAdminRoleFor(TestData testData) {
    // Генерация роли через RoleGenerator
    Role projectAdminRole = RoleGenerator.generateProjectAdmin(testData.getProject().getId());
    // Обновление ролей пользователя
    testData.getUser().getRoles().setRole(List.of(projectAdminRole));

    String userLocator = "username:" + testData.getUser().getUsername();
    Roles roles = testData.getUser().getRoles();
    superUserCheckRequests.getRequester(Endpoint.USERS_ROLES).updateByLocator(userLocator, roles);
  }
}