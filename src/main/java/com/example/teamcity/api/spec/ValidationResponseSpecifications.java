package com.example.teamcity.api.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

public class ValidationResponseSpecifications {
  private ValidationResponseSpecifications() {
    throw new IllegalStateException("Utility class");
  }

  public static ResponseSpecification checkBuildTypeWithIdAlreadyExist(String buildTypeId) {
    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
    responseSpecBuilder.expectStatusCode(HttpStatus.SC_BAD_REQUEST);
    responseSpecBuilder.expectBody(
        Matchers.containsString(
            ("The build configuration / template ID \"%s\" is already used by another configuration"
                    + " or template")
                .formatted(buildTypeId)));
    return responseSpecBuilder.build();
  }

  public static ResponseSpecification checkProjectIdWrongPermission(String projectId) {
    ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
    responseSpecBuilder.expectStatusCode(HttpStatus.SC_FORBIDDEN);
    responseSpecBuilder.expectBody(
        Matchers.containsString(
            ("You do not have enough permissions to edit project with id: %s")
                .formatted(projectId)));
    responseSpecBuilder.expectBody(
        Matchers.containsString(
            ("Access denied. Check the user has enough permissions to perform the operation.")));
    return responseSpecBuilder.build();
  }
}
