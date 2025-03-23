package com.example.teamcity.ui;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;


import com.codeborne.selenide.SelenideElement;


public class ValidateElement {

  private ValidateElement() {
    throw new IllegalStateException("Utility class");
  }

  public static void byText(SelenideElement element, String expectedText) {
    element.shouldBe(visible);
    element.shouldHave(exactText(expectedText));
  }
}
