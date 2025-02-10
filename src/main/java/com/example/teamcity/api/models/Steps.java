package com.example.teamcity.api.models;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Steps extends BaseModel {
  private Integer count;
  private List<Step> step;
}
