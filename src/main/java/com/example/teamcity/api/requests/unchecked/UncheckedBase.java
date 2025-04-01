package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedBase extends Request implements CrudInterface {

  public UncheckedBase(RequestSpecification spec, Endpoint endpoint) {
    super(spec, endpoint);
  }

  @Override
  @Step("Send POST request with body: {body}")
  public Response create(BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .post(endpoint.getUrl());
  }

  @Override
  @Step("Send GET request (all entities)")
  public Response read() {
    return RestAssured
        .given()
        .spec(spec)
        .get(endpoint.getUrl());
  }

  @Override
  @Step("Send GET request by ID: {id}")
  public Response readById(String id) {
    return RestAssured
        .given()
        .spec(spec)
        .get(endpoint.getUrl() + "/id:" + id);
  }

  @Override
  @Step("Send GET request by locator: {locator}")
  public Response readByLocator(String locator) {
    return RestAssured
        .given()
        .spec(spec)
        .get(endpoint.getUrl() + "/" + locator);
  }

  @Override
  @Step("Send PUT request with body: {body}")
  public Response update(BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .put(endpoint.getUrl());
  }

  @Override
  @Step("Send PUT request by ID: {id} with body: {body}")
  public Response updateById(String id, BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .put(endpoint.getUrl() + "/id:" + id);
  }

  @Override
  @Step("Send PUT request by locator: {locator} with body: {body}")
  public Response updateByLocator(String locator, BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .put(endpoint.getUrl().formatted(locator));
  }

  @Override
  @Step("Send DELETE request by ID: {id}")
  public Response deleteById(String id) {
    return RestAssured
        .given()
        .spec(spec)
        .delete(endpoint.getUrl() + "/id:" + id);
  }
}