package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface {
  private final UncheckedBase uncheckedBase;

  public CheckedBase(RequestSpecification spec, Endpoint endpoint) {
    super(spec, endpoint);
    this.uncheckedBase = new UncheckedBase(spec, endpoint);
  }

  @Override
  @Step("POST method for '{body}'")
  public T create(BaseModel body) {
    var createdModel = (T) uncheckedBase
        .create(body)
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());

    TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
    return createdModel;
  }

  @Override
  @Step("GET method")
  public T read() {
    return (T) uncheckedBase
        .read()
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());
  }

  @Override
  @Step("GET method By id = '{id}'")
  public T readById(String id) {
    return (T) uncheckedBase
        .readById(id)
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());
  }

  @Override
  @Step("GET method By locator = '{id}'")
  public T readByLocator(String locator) {
    return (T) uncheckedBase
        .readByLocator(locator)
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());
  }

  @Override
  @Step("PUT method for '{body}'")
  public T update(BaseModel body) {
    return (T) uncheckedBase
        .update(body)
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());
  }

  @Override
  @Step("PUT method By id = '{id}'")
  public T updateById(String id, BaseModel body) {
    return (T) uncheckedBase
        .updateById(id, body)
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());
  }

  @Override
  @Step("GET method By locator = '{locator}' for '{body}'")
  public T updateByLocator(String locator, BaseModel body) {
    return (T) uncheckedBase
        .updateByLocator(locator, body)
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());
  }

  @Override
  @Step("DELETE method By id = '{id}'")
  public Object deleteById(String id) {
    return uncheckedBase
        .deleteById(id)
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().asString();
  }
}
