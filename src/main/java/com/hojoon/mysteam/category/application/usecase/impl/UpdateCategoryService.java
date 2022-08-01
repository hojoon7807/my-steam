package com.hojoon.mysteam.category.application.usecase.impl;

import com.hojoon.mysteam.category.adapter.persistence.exception.NotFoundCategoryException;
import com.hojoon.mysteam.category.application.usecase.UpdateCategoryUsecase;
import com.hojoon.mysteam.category.application.usecase.command.UpdateCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.FindCategoryPort;
import com.hojoon.mysteam.category.domain.model.repository.UpdateCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCategoryService implements UpdateCategoryUsecase {

  private final UpdateCategoryPort updateCategoryPort;
  private final FindCategoryPort findCategoryPort;

  @Override
  @Transactional
  public Category apply(UpdateCategoryCommand updateCategoryCommand) {
    Category findedCategory = findCategory(updateCategoryCommand.getCategoryId());
    findedCategory.changeCategoryName(updateCategoryCommand.getCategoryName());
    return updateCategoryPort.updateCategory(findedCategory);
  }

  private Category findCategory(Long categoryId) {
    return findCategoryPort.findCategoryById(categoryId).orElseThrow(
        NotFoundCategoryException::new);
  }

}
