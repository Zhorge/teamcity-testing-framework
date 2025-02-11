package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
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
  @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
  public void userCreatesBuildTypeTest() {
    var userCheckedRequests = new CheckedRequests(Specifications.authSpec(testData.getUser()));
    superUserCheckRequests.getRequester(Endpoint.USERS).create(testData.getUser());

    userCheckedRequests.<Project>getRequester(Endpoint.PROJECTS).create(testData.getProject());

    userCheckedRequests.getRequester(Endpoint.BUILD_TYPES).create(testData.getBuildType());

    var createdBuildType = userCheckedRequests.<BuildType>getRequester(Endpoint.BUILD_TYPES).read(testData.getBuildType().getId());

    softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
  }

  @Test(
      description = "User should not be able to create two build types with the same id",
      groups = {"Negative", "CRUD"})
  public void userCreatesTwoBuildTypesWithTheSameIdTest() {
    var buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

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
    step("Create user");
    step("Create project");
    step("Grant user PROJECT_ADMIN role in project");

    step("Create buildType for project by user (PROJECT_ADMIN)");
    step("Check buildType was created successfully");
  }

  @Test(
      description = "Project admin should not be able to create build type for not their project",
      groups = {"Negative", "Roles"})
  public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {
    step("Create user1");
    step("Create project1");
    step("Grant user1 PROJECT_ADMIN role in project1");

    step("Create user2");
    step("Create project2");
    step("Grant user2 PROJECT_ADMIN role in project2");

    step("Create buildType for project1 by user2");
    step("Check buildType was not created with forbidden code");
  }
}
