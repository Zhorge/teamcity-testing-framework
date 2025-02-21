package com.example.teamcity.api.requests.unchecked;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.CrudInterface;
import com.example.teamcity.api.requests.Request;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class UncheckedBase extends Request implements CrudInterface {

  public UncheckedBase(RequestSpecification spec, Endpoint endpoint) {
    super(spec, endpoint);
  }

  @Override
  public Response create(BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .post(endpoint.getUrl());
  }

  @Override
  public Response read() {
    return RestAssured
        .given()
        .spec(spec)
        .get(endpoint.getUrl());
  }

  @Override
  public Response readById(String id) {
    return RestAssured
        .given()
        .spec(spec)
        .get(endpoint.getUrl() + "/id:" + id);
  }

  @Override
  public Response readByLocator(String locator) {
    return RestAssured
        .given()
        .spec(spec)
        .get(endpoint.getUrl() + "/" + locator);
  }

  @Override
  public Response update(BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .put(endpoint.getUrl());
  }

  @Override
  public Response updateById(String id, BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .put(endpoint.getUrl() + "/id:" + id);
  }

  public Response updateByLocator(String locator, BaseModel body) {
    return RestAssured
        .given()
        .spec(spec)
        .body(body)
        .put(endpoint.getUrl().formatted(locator));
  }

  @Override
  public Response deleteById(String id) {
    return RestAssured
        .given()
        .spec(spec)
        .delete(endpoint.getUrl() + "/id:" + id);
  }
}
