package com.example.teamcity.api.enums;

import com.example.teamcity.api.models.settings.AuthSettings;
import com.example.teamcity.api.models.BaseModel;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.Roles;
import com.example.teamcity.api.models.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Endpoint {
  BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
  PROJECTS("/app/rest/projects", Project.class),
  USERS("/app/rest/users", User.class),
  USERS_ROLES("/app/rest/users/%s/roles", Roles.class),
  SERVER_AUTH_SETTINGS("/app/rest/server/authSettings", AuthSettings.class);

  private final String url;
  private final Class<? extends BaseModel> modelClass;
}