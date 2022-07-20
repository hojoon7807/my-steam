package com.hojoon.mysteam.category.application.usecase.impl;

import com.hojoon.mysteam.category.application.usecase.FindAllCategoryUsecase;
import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.FindCategoryPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllCategoryService implements FindAllCategoryUsecase {

  private final FindCategoryPort findCategoryPort;

  @Transactional(readOnly = true)
  @Override
  public List<Category> get() {
    return findCategoryPort.findAllCategories();
  }
}
