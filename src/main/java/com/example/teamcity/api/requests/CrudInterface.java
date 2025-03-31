package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;
import io.qameta.allure.Step;

public interface CrudInterface {
  @Step("POST method for '{body}'")
  Object create(BaseModel body);
  @Step("GET method")
  Object read();
  @Step("GET method By id = '{id}'")
  Object readById(String id);
  @Step("GET method By locator = '{id}'")
  Object readByLocator(String locator);
  @Step("PUT method for '{body}'")
  Object update(BaseModel body);
  @Step("PUT method By id = '{id}'")
  Object updateById(String id, BaseModel body);
  @Step("GET method By locator = '{locator}' for '{body}'")
  Object updateByLocator(String locator, BaseModel body);
  @Step("DELETE method By id = '{id}'")
  Object deleteById(String id);
}
