package com.example.teamcity.ui.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildTypeElement;
import java.util.List;

public class ProjectPage extends BasePage {
  private static final String PROJECT_URL = "/project.html?projectId=%s";

  private final ElementsCollection buildTypeElements = $$x("//div[contains(@class, 'BuildTypes__item')]");

  public final SelenideElement title = $x("//span[contains(@class, 'ProjectPageHeader__title')]");

  private ProjectPage() {
    title.shouldBe(visible, DEFAULT_TIMEOUT);
  }

  public static ProjectPage open(String projectId) {
    return Selenide.open(PROJECT_URL.formatted(projectId), ProjectPage.class);
  }

  public List<BuildTypeElement> getBuildTypes() {
    return generatePageElements(buildTypeElements, BuildTypeElement::new);
  }
}
