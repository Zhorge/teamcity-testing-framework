package com.example.teamcity.ui.pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.BuildTypeElement;
import io.qameta.allure.Step;
import java.util.List;

public class ProjectPage extends BasePage {
  private static final String PROJECT_URL = "/project.html?projectId=%s";

  private final ElementsCollection buildTypeElements = $$x("//div[contains(@class, 'BuildTypes__item')]");

  public final SelenideElement title = $x("//span[contains(@class, 'ProjectPageHeader__title')]");

  public ProjectPage() {
    title.shouldBe(visible, DEFAULT_TIMEOUT);
  }

  @Step("Open project page")
  public static ProjectPage open(String projectId) {
    Selenide.open(PROJECT_URL.formatted(projectId));
    return new ProjectPage();
  }

  private List<BuildTypeElement> getBuildTypes() {
    return generatePageElements(buildTypeElements, BuildTypeElement::new);
  }

  @Step("Check that build type '{buildTypeName}' is exist")
  public boolean isBuildTypeElementExists(String buildTypeName) {
    return getBuildTypes()
        .stream()
        .anyMatch(b -> b.getName().has(text(buildTypeName)));
  }
}
