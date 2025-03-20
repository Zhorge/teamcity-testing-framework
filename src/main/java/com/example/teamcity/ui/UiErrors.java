package com.example.teamcity.ui;

public class UiErrors {

  private UiErrors() {
    throw new IllegalStateException("Utility class");
  }

  private static final String BUILD_CONFIG_NAME_MUST_BE_NOT_NULL =
      "Build configuration with name \"%s\" already exists in project: \"%s\"";

  public static final String CANT_FIND_BUILD_TYPE_ON_PROJECT_PAGE =
      "Cannot find this 'build type' on the 'Project page'";
  public static final String CANT_FIND_PROJECT_ON_PROJECT_PAGE =
      "Cannot find this 'project' on the 'Projects page'";

  public static String buildConfigNameMustBeNotNull(String buildConfigName, String projectName) {
    return String.format(BUILD_CONFIG_NAME_MUST_BE_NOT_NULL, buildConfigName, projectName);
  }
}
