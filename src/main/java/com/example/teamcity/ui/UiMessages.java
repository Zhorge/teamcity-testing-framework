package com.example.teamcity.ui;

public class UiMessages {

  private UiMessages() {
    throw new IllegalStateException("Utility class");
  }

  private static final String NEW_PROJECT_SUCCESS_MESSAGE =
      "New project \"%s\", build configuration \"%s\" and VCS root "
          + "\"%s#refs/heads/master\" have been successfully created.";

  public static String newProjectSuccessMessage(
      String projectName, String buildConfigName, String repoUrl) {
    return String.format(NEW_PROJECT_SUCCESS_MESSAGE, projectName, buildConfigName, repoUrl);
  }
}
