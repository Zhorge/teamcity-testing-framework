package com.example.teamcity.api.generators;

import com.example.teamcity.api.models.Role;
import java.util.Objects;

public class RoleGenerator {
  private static final String PROJECT_ADMIN_ROLE_ID = "PROJECT_ADMIN";
  private static final String PROJECT_SCOPE_PREFIX = "p:";

  public static Role generateProjectAdmin(String projectId) {
    Objects.requireNonNull(projectId, "Project ID cannot be null");
    String projectLocator = PROJECT_SCOPE_PREFIX + projectId;
    return Role.builder()
        .roleId(PROJECT_ADMIN_ROLE_ID)
        .scope(projectLocator)
        .build();
  }
}
