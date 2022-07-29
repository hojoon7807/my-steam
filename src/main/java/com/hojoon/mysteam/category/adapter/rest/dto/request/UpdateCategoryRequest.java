package com.hojoon.mysteam.category.adapter.rest.dto.request;

import lombok.Getter;

@Getter
public class UpdateCategoryRequest {
  private String categoryName;

  public UpdateCategoryRequest() {
  }

  public UpdateCategoryRequest(String categoryName) {
    this.categoryName = categoryName;
  }
}
