package com.example.teamcity.ui.pages;

import static com.codeborne.selenide.Selenide.$$;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.example.teamcity.ui.elements.ProjectElement;
import java.util.List;

public class ProjectsPage extends BasePage {
  private static final String PROJECTS_URL = "/favorite/projects";

  private ElementsCollection projectElements = $$("div[class*='Subproject__container']");


  // ElementCollection -> List<ProjectElement>
  // UI elements -> List<Object>
  // ElementCollection -> List<BasePageElement>

  public static ProjectsPage open() {
    return Selenide.open(PROJECTS_URL, ProjectsPage.class);
  }

  public List<ProjectElement> getProjects() {
    return generatePageElements(projectElements, ProjectElement::new);
  }
}
