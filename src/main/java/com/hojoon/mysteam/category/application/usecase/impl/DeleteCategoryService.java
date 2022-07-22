package com.hojoon.mysteam.category.application.usecase.impl;

import com.hojoon.mysteam.category.application.usecase.DeleteCategoryUsecase;
import com.hojoon.mysteam.category.domain.model.repository.DeleteCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteCategoryService implements DeleteCategoryUsecase {

  private final DeleteCategoryPort deleteCategoryPort;

  @Override
  public void accept(Long id) {
    deleteCategoryPort.deleteCategoryById(id);
  }
}
