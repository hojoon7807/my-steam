package com.hojoon.mysteam.category.application.usecase;

import com.hojoon.mysteam.category.application.usecase.command.SaveCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import java.util.function.Function;

public interface SaveCategoryUsecase extends Function<SaveCategoryCommand, Category> {

  @Override
  Category apply(SaveCategoryCommand saveCategoryCommand);
}
