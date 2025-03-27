package com.example.teamcity.ui.pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.User;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class LoginPage extends BasePage {
  private static final String LOGIN_URL = "/login.html";

  private final SelenideElement inputUsername = $("#username");
  private final SelenideElement inputPassword = $("#password");
  private final SelenideElement inputSubmitLogin = $(".loginButton");

  @Step("Open login page")
  public static LoginPage open() {
    return Selenide.open(LOGIN_URL, LoginPage.class);
  }

  @Step("Login with {user.username}")
  public ProjectsPage login(User user) {
    // Метод val вместо clear, sendKeys
    inputUsername.val(user.getUsername());
    inputPassword.val(user.getPassword());
    inputSubmitLogin.click();
    return page(ProjectsPage.class);
  }
}
