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
  public Response create(BaseModel body) {
    return sendRequest("POST", endpoint.getUrl(), body);
  }

  @Override
  public Response read() {
    return sendRequest("GET", endpoint.getUrl(), null);
  }

  @Override
  public Response readById(String id) {
    String url = endpoint.getUrl() + "/id:" + id;
    return sendRequest("GET", url, null);
  }

  @Override
  public Response readByLocator(String locator) {
    String url = endpoint.getUrl() + "/" + locator;
    return sendRequest("GET", url, null);
  }

  @Override
  public Response update(BaseModel body) {
    return sendRequest("PUT", endpoint.getUrl(), body);
  }

  @Override
  public Response updateById(String id, BaseModel body) {
    String url = endpoint.getUrl() + "/id:" + id;
    return sendRequest("PUT", url, body);
  }

  @Override
  public Response updateByLocator(String locator, BaseModel body) {
    String url = endpoint.getUrl().formatted(locator); // если URL содержит %s
    return sendRequest("PUT", url, body);
  }

  @Override
  public Response deleteById(String id) {
    String url = endpoint.getUrl() + "/id:" + id;
    return sendRequest("DELETE", url, null);
  }

  // --- Universal request method with Allure step ---
  @Step("Send '{method} {url}' request")
  private Response sendRequest(String method, String url, BaseModel body) {
    var request = RestAssured
        .given()
        .spec(spec);

    if (body != null) {
      request.body(body);
    }

    return switch (method.toUpperCase()) {
      case "POST" -> request.post(url);
      case "GET" -> request.get(url);
      case "PUT" -> request.put(url);
      case "DELETE" -> request.delete(url);
      default -> throw new IllegalArgumentException("Unsupported method: " + method);
    };
  }
}