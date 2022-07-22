package com.hojoon.mysteam.category.adapter.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hojoon.mysteam.category.adapter.persistence.exception.NotFoundCategoryException;
import com.hojoon.mysteam.category.application.usecase.DeleteCategoryUsecase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith(MockitoExtension.class)
class DeleteCatgoryControllerTest {

  @InjectMocks
  private DeleteCatgoryController deleteCatgoryController;

  @Mock
  private DeleteCategoryUsecase deleteCategoryUsecase;

  private MockMvc mockMvc;

  @BeforeEach
  void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(deleteCatgoryController)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  @Test
  @DisplayName("해당 Path가 유효하면 정상적으로 카테고리 삭제를 처리한다")
  void deleteCategoryWithValidPath() throws Exception {
    Long PATH = 1L;
    ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
    doNothing().when(deleteCategoryUsecase).accept(idCapture.capture());

    mockMvc.perform(delete("/categories/" + PATH)).andExpect(status().isNoContent());

    assertThat(idCapture.getValue()).isEqualTo(PATH);
  }

  // Error Response 구현 후 응답 수정 필요
  @Test
  @DisplayName("해당 Path가 유효하지 않으면 NotFoundCategoryException이 발생한다")
  void deleteCategoryWithInvalidPath() throws Exception {
    Long PATH = 1L;
    ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
    doThrow(new NotFoundCategoryException()).when(deleteCategoryUsecase)
        .accept(idCapture.capture());

    mockMvc.perform(delete("/categories/" + PATH)).andExpect(result -> assertAll(
            () -> assertThat(result.getResolvedException()).isInstanceOf(
                NotFoundCategoryException.class),
            () -> assertThat(result.getResolvedException()).hasMessage("해당 카테고리가 존재하지 않습니다")))
        .andDo(print());

    assertThat(idCapture.getValue()).isEqualTo(PATH);
  }
}