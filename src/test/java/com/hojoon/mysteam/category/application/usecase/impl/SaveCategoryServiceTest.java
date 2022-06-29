package com.hojoon.mysteam.category.application.usecase.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.hojoon.mysteam.category.application.usecase.command.SaveCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.SaveCategoryPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Slf4j
@ExtendWith(MockitoExtension.class)
class SaveCategoryServiceTest {

  @InjectMocks
  private SaveCategoryService saveCategoryService;

  @Mock
  private SaveCategoryPort saveCategoryPort;

  @Test
  @DisplayName("요청 받은 카테고리 생성 정보를 성공적으로 저장하고 Category 객체를 반환한다")
  void saveCategory() {
    final String CATEGORY_NAME = "action";
    SaveCategoryCommand saveCategoryCommand = new SaveCategoryCommand(CATEGORY_NAME);
    Category category = new Category(1L, CATEGORY_NAME);

    when(saveCategoryPort.saveCategory(any(Category.class))).thenReturn(category);

    Category savedCategory = saveCategoryService.apply(saveCategoryCommand);

    assertThat(savedCategory.getCategoryName()).isEqualTo(CATEGORY_NAME);
  }
}