package com.example.teamcity.api.requests.checked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.generators.TestDataStorage;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import io.qameta.allure.Step;
import io.restassured.response.Response;
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
  @Step("Create object: {body}")
  public T create(BaseModel body) {
    Response response = sendCreateRequest(body);
    T createdModel = extractModel(response);
    TestDataStorage.getStorage().addCreatedEntity(endpoint, createdModel);
    return createdModel;
  }

  @Override
  @Step("Read all objects")
  public T read() {
    Response response = sendReadRequest();
    return extractModel(response);
  }

  @Override
  @Step("Read object by ID: {id}")
  public T readById(String id) {
    Response response = sendReadByIdRequest(id);
    return extractModel(response);
  }

  @Override
  @Step("Read object by locator: {locator}")
  public T readByLocator(String locator) {
    Response response = sendReadByLocatorRequest(locator);
    return extractModel(response);
  }

  @Override
  @Step("Update object: {body}")
  public T update(BaseModel body) {
    Response response = sendUpdateRequest(body);
    return extractModel(response);
  }

  @Override
  @Step("Update object by ID: {id}")
  public T updateById(String id, BaseModel body) {
    Response response = sendUpdateByIdRequest(id, body);
    return extractModel(response);
  }

  @Override
  @Step("Update object by locator: {locator}")
  public T updateByLocator(String locator, BaseModel body) {
    Response response = sendUpdateByLocatorRequest(locator, body);
    return extractModel(response);
  }

  @Override
  @Step("Delete object by ID: {id}")
  public Object deleteById(String id) {
    Response response = sendDeleteByIdRequest(id);
    return response.then().assertThat().statusCode(HttpStatus.SC_OK).extract().asString();
  }

  // ——— Helper methods with @Step annotations ———

  @Step("Send CREATE request via UncheckedBase")
  private Response sendCreateRequest(BaseModel body) {
    return uncheckedBase.create(body);
  }

  @Step("Send READ request via UncheckedBase")
  private Response sendReadRequest() {
    return uncheckedBase.read();
  }

  @Step("Send READ BY ID request via UncheckedBase [ID: {id}]")
  private Response sendReadByIdRequest(String id) {
    return uncheckedBase.readById(id);
  }

  @Step("Send READ BY LOCATOR request via UncheckedBase [Locator: {locator}]")
  private Response sendReadByLocatorRequest(String locator) {
    return uncheckedBase.readByLocator(locator);
  }

  @Step("Send UPDATE request via UncheckedBase")
  private Response sendUpdateRequest(BaseModel body) {
    return uncheckedBase.update(body);
  }

  @Step("Send UPDATE BY ID request via UncheckedBase [ID: {id}]")
  private Response sendUpdateByIdRequest(String id, BaseModel body) {
    return uncheckedBase.updateById(id, body);
  }

  @Step("Send UPDATE BY LOCATOR request via UncheckedBase [Locator: {locator}]")
  private Response sendUpdateByLocatorRequest(String locator, BaseModel body) {
    return uncheckedBase.updateByLocator(locator, body);
  }

  @Step("Send DELETE BY ID request via UncheckedBase [ID: {id}]")
  private Response sendDeleteByIdRequest(String id) {
    return uncheckedBase.deleteById(id);
  }

  @Step("Extract model from response")
  private T extractModel(Response response) {
    return (T) response
        .then().assertThat().statusCode(HttpStatus.SC_OK)
        .extract().as(endpoint.getModelClass());
  }
}

