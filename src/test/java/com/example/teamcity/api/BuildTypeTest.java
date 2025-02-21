package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.TestData;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import java.util.Arrays;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {
  private static String PROJECT_ADMIN_ROLE_ID = "PROJECT_ADMIN";

  @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
  public void userCreatesBuildTypeTest() {
    var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());

    userCheckedRequests.getRequester(Endpoint.PROJECTS).create(testData.getProject());

    userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());

    BuildType createdBuildType = userCheckedRequests.<BuildType>getRequester(Endpoint.BUILD_TYPES).readById(testData.getBuildType().getId());

    softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
  }

  @Test(
      description = "User should not be able to create two build types with the same id",
      groups = {"Negative", "CRUD"})
  public void userCreatesTwoBuildTypesWithTheSameIdTest() {
    BuildType buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

    var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());

    userCheckedRequests.<Project>getRequester(Endpoint.PROJECTS).create(testData.getProject());

    userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());
    new UncheckedBase(Specifications.authSpec(testData.getUser()), Endpoint.BUILD_TYPES)
        .create(buildTypeWithSameId)
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_BAD_REQUEST)
        .body(
            Matchers.containsString(
                "The build configuration / template ID \"%s\" is already used by another configuration or template"
                    .formatted(testData.getBuildType().getId())));
  }

  @Test(
      description = "Project admin should be able to create build type for their project",
      groups = {"Positive", "Roles"})
  public void projectAdminCreatesBuildTypeTest() {
    var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
    String userLocator = "username:" + testData.getUser().getUsername();
    String projectLocator = "p:" + testData.getProject().getId();
    Roles roles = generate(Arrays.asList(testData.getUser()), Roles.class, PROJECT_ADMIN_ROLE_ID, projectLocator);

    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());

    superUserCheckRequests.getRequester(Endpoint.PROJECTS).create(testData.getProject());

    superUserCheckRequests.getRequester(Endpoint.USERS_ROLES).updateByLocator(userLocator, roles);

    userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());

    BuildType createdBuildType = userCheckedRequests.<BuildType>getRequester(Endpoint.BUILD_TYPES).readById(testData.getBuildType().getId());
    softy.assertEquals(createdBuildType.getId(), testData.getBuildType().getId(), "Build type id is not correct");
    softy.assertEquals(createdBuildType.getProject().getId(), testData.getProject().getId(), "Project id is not correct");
  }

  @Test(
      description = "Project admin should not be able to create build type for not their project",
      groups = {"Negative", "Roles"})
  public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
    TestData testData2 = generate();

    step("Create user1");
    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());

    step("Create project1");
    superUserCheckRequests.getRequester(Endpoint.PROJECTS).create(testData.getProject());

    step("Grant user1 PROJECT_ADMIN role in project1");
    String userLocator = "username:" + testData.getUser().getUsername();
    String projectLocator = "p:" + testData.getProject().getId();
    Roles roles = generate(Arrays.asList(testData.getUser()), Roles.class, PROJECT_ADMIN_ROLE_ID, projectLocator);

    superUserCheckRequests.getRequester(Endpoint.USERS_ROLES).updateByLocator(userLocator, roles);

    step("Create user2");
    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData2.getUser());

    step("Create project2");
    superUserCheckRequests.getRequester(Endpoint.PROJECTS).create(testData2.getProject());

    step("Grant user2 PROJECT_ADMIN role in project2");
    String userLocator2 = "username:" + testData2.getUser().getUsername();
    String userLocator1 = "username:" + testData.getUser().getUsername();
    String projectLocator2 = "p:" + testData2.getProject().getId();
    Roles roles2 = generate(Arrays.asList(testData2.getUser()), Roles.class, PROJECT_ADMIN_ROLE_ID, projectLocator2);
    superUserCheckRequests.getRequester(Endpoint.USERS_ROLES).updateByLocator(userLocator2, roles2);

    superUserCheckRequests.getRequester(Endpoint.USERS).readByLocator(userLocator1);
    superUserCheckRequests.getRequester(Endpoint.USERS).readByLocator(userLocator2);

    new UncheckedBase(Specifications.authSpec(testData2.getUser()), Endpoint.BUILD_TYPES)
        .create(testData.getBuildType())
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_FORBIDDEN)
        .body(
            Matchers.containsString(
                "You do not have enough permissions to edit project with id: %s"
                    .formatted(testData.getProject().getId())))
        .body(
            Matchers.containsString(
                "Access denied. Check the user has enough permissions to perform the operation."));
  }
}
