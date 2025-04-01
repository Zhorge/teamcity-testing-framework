package com.example.teamcity.api.requests;

import com.example.teamcity.api.models.BaseModel;

public interface CrudInterface {
  Object create(BaseModel body);
  Object read();
  Object readById(String id);
  Object readByLocator(String locator);
  Object update(BaseModel body);
  Object updateById(String id, BaseModel body);
  Object updateByLocator(String locator, BaseModel body);
  Object deleteById(String id);
}
