package com.hojoon.mysteam.category.application.usecase.impl;

import com.hojoon.mysteam.category.application.usecase.SaveCategoryUsecase;
import com.hojoon.mysteam.category.application.usecase.command.SaveCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.SaveCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SaveCategoryService implements SaveCategoryUsecase {

  private final SaveCategoryPort saveCategoryPort;

  @Transactional
  @CacheEvict(value = "categories", allEntries = true)
  @Override
  public Category apply(SaveCategoryCommand saveCategoryCommand) {
    Category category = new Category(saveCategoryCommand.getCategoryName());
    return saveCategoryPort.saveCategory(category);
  }
}
