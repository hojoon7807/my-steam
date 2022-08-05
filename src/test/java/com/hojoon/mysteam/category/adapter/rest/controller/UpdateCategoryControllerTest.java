package com.hojoon.mysteam.category.adapter.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hojoon.mysteam.category.adapter.persistence.exception.NotFoundCategoryException;
import com.hojoon.mysteam.category.adapter.rest.dto.request.UpdateCategoryRequest;
import com.hojoon.mysteam.category.application.usecase.UpdateCategoryUsecase;
import com.hojoon.mysteam.category.application.usecase.command.UpdateCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @Mock
  private UpdateCategoryUsecase updateCategoryUsecase;

  @InjectMocks
  private UpdateCategoryController updateCategoryController;

  @BeforeEach
  void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(updateCategoryController)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  @Test
  @DisplayName("카테고리 수정 요청 정보를 성공적으로 처리하고, 응답 객체를 반환한다")
  void updateCategoryWithValidRequest() throws Exception {
    Long ID = 1L;
    String CATEGORY_NAME = "updateName";
    UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest(CATEGORY_NAME);

    when(updateCategoryUsecase.apply(any(UpdateCategoryCommand.class)))
        .thenReturn(new Category(ID, CATEGORY_NAME));

    mockMvc.perform(patch("/admin/categories/" + ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateCategoryRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.categoryName").value(CATEGORY_NAME));
  }

  @Test
  @DisplayName("카테고리 수정 요청 정보가 유효하지 않으면, NotFoundCategory 예외가 발생한다")
  void updateCategoryWithInvalidRequest() throws Exception {
    Long ID = 1L;
    String CATEGORY_NAME = "updateName";
    UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest(CATEGORY_NAME);

    when(updateCategoryUsecase.apply(any(UpdateCategoryCommand.class)))
        .thenThrow(new NotFoundCategoryException());

    mockMvc.perform(patch("/admin/categories/" + ID)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateCategoryRequest)))
        .andExpect(
            result ->
                assertAll(
                    () -> assertThat(result.getResolvedException()).isInstanceOf(
                        NotFoundCategoryException.class),
                    () -> assertThat(result.getResolvedException()).hasMessage(
                        "해당 카테고리가 존재하지 않습니다")));
  }
}