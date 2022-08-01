package com.hojoon.mysteam.category.adapter.rest.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCategoryRequest {
  private String categoryName;

  public UpdateCategoryRequest(String categoryName) {
    this.categoryName = categoryName;
  }
}
