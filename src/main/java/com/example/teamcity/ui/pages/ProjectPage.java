package com.example.teamcity.ui.pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class ProjectPage extends BasePage {
  private static final String PROJECT_URL = "/project/%s?mode=builds";

  public final SelenideElement title = $(By.xpath("//span[@class='ProjectPageHeader__title']"));

  public static ProjectPage open(String projectId) {
    System.out.println(PROJECT_URL.formatted(projectId));
    return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
  }
}
