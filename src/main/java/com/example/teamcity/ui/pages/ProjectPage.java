package com.example.teamcity.ui.pages;

import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

public class ProjectPage extends BasePage {
  private static final String PROJECT_URL = "/project.html?projectId=%s";

  public final SelenideElement title = $x("//span[contains(@class, 'ProjectPageHeader__title')]");

  public static ProjectPage open(String projectId) {
    return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
  }
}
