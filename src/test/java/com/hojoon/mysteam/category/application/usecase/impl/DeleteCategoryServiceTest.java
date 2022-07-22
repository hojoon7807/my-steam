package com.hojoon.mysteam.category.application.usecase.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.hojoon.mysteam.category.adapter.persistence.exception.NotFoundCategoryException;
import com.hojoon.mysteam.category.domain.model.repository.DeleteCategoryPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteCategoryServiceTest {

  @Mock
  private DeleteCategoryPort deleteCategoryPort;

  @InjectMocks
  private DeleteCategoryService deleteCategoryService;

  @Test
  @DisplayName("해당 ID가 유효하면 성공적으로 카테고리 정보를 삭제한다")
  void deleteCategoryByValidId() {
    Long ID = 1L;
    ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
    doNothing().when(deleteCategoryPort).deleteCategoryById(idCapture.capture());

    deleteCategoryService.accept(ID);

    verify(deleteCategoryPort, times(1)).deleteCategoryById(ID);
    assertThat(idCapture.getValue()).isEqualTo(ID);
  }

  @Test
  @DisplayName("해당 ID가 유효하지않으면 NotFoundCategoryException이 발생한다")
  void deleteCategoryByInvalidId() {
    doThrow(new NotFoundCategoryException()).when(deleteCategoryPort).deleteCategoryById(anyLong());

    assertThatThrownBy(() -> deleteCategoryService.accept(1L)).isInstanceOf(
        NotFoundCategoryException.class).hasMessage("해당 카테고리가 존재하지 않습니다");
  }

}