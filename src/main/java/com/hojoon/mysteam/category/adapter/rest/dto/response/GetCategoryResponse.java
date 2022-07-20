package com.hojoon.mysteam.category.adapter.rest.dto.response;

import lombok.Getter;

@Getter
public class GetCategoryResponse {
  private String categoryName;

  public GetCategoryResponse(String categoryName) {
    this.categoryName = categoryName;
  }
}
