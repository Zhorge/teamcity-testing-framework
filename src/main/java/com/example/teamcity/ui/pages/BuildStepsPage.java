package com.example.teamcity.ui.pages;

import static com.codeborne.selenide.Selenide.$;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

public class BuildStepsPage extends BasePage {
  public final SelenideElement createdProjectSuccessMessage = $(By.id("unprocessed_objectsCreated"));
}
