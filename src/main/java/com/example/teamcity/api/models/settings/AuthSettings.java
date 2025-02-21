package com.example.teamcity.api.models.settings;

import com.example.teamcity.api.models.BaseModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthSettings extends BaseModel {
  private Boolean allowGuest;
  private String guestUsername;
  private Boolean perProjectPermissions;
  private Boolean emailVerification;
  private AuthModules modules;
  private String welcomeText;
  private Boolean collapsedInfoFrame;
}