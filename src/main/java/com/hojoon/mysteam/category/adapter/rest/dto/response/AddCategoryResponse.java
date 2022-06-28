package com.hojoon.mysteam.category.adapter.rest.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCategoryResponse {

  private String categoryName;

  public AddCategoryResponse(String categoryName) {
    this.categoryName = categoryName;
  }
}
