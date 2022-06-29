package com.hojoon.mysteam.category.application.usecase.command;

import lombok.Getter;

@Getter
public class SaveCategoryCommand {
  private String categoryName;

  public SaveCategoryCommand(String categoryName) {
    this.categoryName = categoryName;
  }
}
