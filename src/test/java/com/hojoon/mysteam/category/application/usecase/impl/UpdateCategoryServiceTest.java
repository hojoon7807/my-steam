package com.hojoon.mysteam.category.application.usecase.impl;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.hojoon.mysteam.category.adapter.persistence.exception.NotFoundCategoryException;
import com.hojoon.mysteam.category.application.usecase.command.UpdateCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.FindCategoryPort;
import com.hojoon.mysteam.category.domain.model.repository.UpdateCategoryPort;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryServiceTest {

  @Mock
  private UpdateCategoryPort updateCategoryPort;

  @Mock
  private FindCategoryPort findCategoryPort;

  @InjectMocks
  private UpdateCategoryService updateCategoryService;

  @Test
  @DisplayName("요청받은 카테고리 정보를 성공적으로 수정하고 수정한 객체를 반환한다")
  void updateCategoryWithValidCategory() {
    Long ID = 1L;
    String UPDATE_CATEGORY_NAME = "horror";
    UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand(ID,
        UPDATE_CATEGORY_NAME);

    Category findedCategory = new Category(ID, "action");

    when(findCategoryPort.findCategoryById(updateCategoryCommand.getCategoryId())).thenReturn(
        Optional.of(findedCategory));

    findedCategory.changeCategoryName(UPDATE_CATEGORY_NAME);
    when(updateCategoryPort.updateCategory(findedCategory)).thenReturn(findedCategory);

    Category updatedCategory = updateCategoryService.apply(updateCategoryCommand);

    assertThat(updatedCategory.getCategoryName()).isEqualTo(UPDATE_CATEGORY_NAME);
  }

  @Test
  @DisplayName("요청받은 카테고리 정보가 유효하지 않으면 NotFoundCategory 예외가 발생한다")
  void updateCategoryWithInvalidCategory() {
    UpdateCategoryCommand updateCategoryCommand = new UpdateCategoryCommand(1L,
        "action");

    when(findCategoryPort.findCategoryById(updateCategoryCommand.getCategoryId())).thenReturn(
        Optional.empty());

    assertThatThrownBy(() -> updateCategoryService.apply(updateCategoryCommand))
        .isInstanceOf(NotFoundCategoryException.class)
        .hasMessage("해당 카테고리가 존재하지 않습니다");

    verify(updateCategoryPort, never()).updateCategory(any(Category.class));
  }
}