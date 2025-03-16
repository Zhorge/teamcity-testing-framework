package com.example.teamcity.ui.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.elements.ProjectElement;
import java.util.List;

public class ProjectsPage extends BasePage {
  private static final String PROJECTS_URL = "/favorite/projects";

  private final ElementsCollection projectElements = $$("div[class*='Subproject__container']");

  private final SelenideElement header = $(".MainPanel__router--gF > div");

  // ElementCollection -> List<ProjectElement>
  // UI elements -> List<Object>
  // ElementCollection -> List<BasePageElement>

  public static ProjectsPage open() {
    return Selenide.open(PROJECTS_URL, ProjectsPage.class);
  }

  public ProjectsPage() {
    header.shouldBe(visible, DEFAULT_TIMEOUT);
  }

  public List<ProjectElement> getProjects() {
    return generatePageElements(projectElements, ProjectElement::new);
  }
}
