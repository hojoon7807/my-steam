package com.hojoon.mysteam.category.application.usecase.command;

import lombok.Getter;

@Getter
public class UpdateCategoryCommand {

  private Long categoryId;
  private String categoryName;

  public UpdateCategoryCommand(Long categoryId, String categoryName) {
    this.categoryId = categoryId;
    this.categoryName = categoryName;
  }
}
