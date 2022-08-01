package com.hojoon.mysteam.category.adapter.rest.dto.response;

import lombok.Getter;

@Getter
public class UpdateCategoryResponse {

  private String categoryName;

  public UpdateCategoryResponse(String categoryName) {
    this.categoryName = categoryName;
  }
}
