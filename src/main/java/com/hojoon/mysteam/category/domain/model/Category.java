package com.hojoon.mysteam.category.domain.model;

import lombok.Getter;

@Getter
public class Category {
  private Long id;
  private String categoryName;

  public Category(Long id, String categoryName) {
    this.id = id;
    this.categoryName = categoryName;
  }
}
