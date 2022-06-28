package com.hojoon.mysteam.category.adapter.rest.dto.request;

import com.hojoon.mysteam.category.application.usecase.command.SaveCategoryCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCategoryRequest {

  private String categoryName;

  public AddCategoryRequest(String categoryName) {
    this.categoryName = categoryName;
  }

  public SaveCategoryCommand toCommand() {
    return new SaveCategoryCommand(categoryName);
  }
}
