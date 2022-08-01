package com.hojoon.mysteam.category.domain.model;

import lombok.Getter;

@Getter
public class Category {
  private Long id;
  private String categoryName;

  public Category(String categoryName) {
    this.categoryName = categoryName;
  }

  public Category(Long id, String categoryName) {
    this.id = id;
    this.categoryName = categoryName;
  }

  public void changeCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }
}
