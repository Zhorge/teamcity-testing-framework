package com.example.teamcity.ui;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;


import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import java.time.Duration;


public class ValidateElement {

  private ValidateElement() {
    throw new IllegalStateException("Utility class");
  }

  @Step("Check that element has exact text: '{expectedText}'")
  public static void byText(SelenideElement element, String expectedText) {
    element.shouldBe(visible, Duration.ofSeconds(30));
    element.shouldHave(exactText(expectedText));
  }
}
