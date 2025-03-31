package com.example.teamcity.api.requests;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.requests.checked.CheckedBase;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import java.util.EnumMap;

public class CheckedRequests {
  private final EnumMap<Endpoint, CheckedBase> requests = new EnumMap<>(Endpoint.class);

  public CheckedRequests(RequestSpecification spec) {
    for (Endpoint endpoint : Endpoint.values()) {
      requests.put(endpoint, new CheckedBase(spec, endpoint));
    }
  }

  @Step("Выполнен запрос '{endpoint}'")
  public <T extends BaseModel> CheckedBase<T> getRequester(Endpoint endpoint) {
    return requests.get(endpoint);
  }
}