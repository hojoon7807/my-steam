package com.hojoon.mysteam.category.adapter.rest.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hojoon.mysteam.category.adapter.rest.dto.request.AddCategoryRequest;
import com.hojoon.mysteam.category.application.usecase.SaveCategoryUsecase;
import com.hojoon.mysteam.category.application.usecase.command.SaveCategoryCommand;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class AddCatgoryControllerTest {

  @InjectMocks
  private AddCategoryController addCatgoryController;

  @Mock
  private SaveCategoryUsecase saveCategoryUsecase;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void prepare() {
    mockMvc = MockMvcBuilders.standaloneSetup(addCatgoryController)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  @Test
  @DisplayName("카테고리 추가 요청 정보를 성공적으로 저장하고, 응답 객체를 반환한다")
  void categorySave() throws Exception {
    final Long ID = 1L;
    final String CATEGORY_NAME = "action";

    AddCategoryRequest addCategoryRequest = new AddCategoryRequest(CATEGORY_NAME);

    Category savedCategory = new Category(ID, CATEGORY_NAME);

    when(saveCategoryUsecase.apply(any(SaveCategoryCommand.class))).thenReturn(savedCategory);

    mockMvc.perform(post("/categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(addCategoryRequest)))
        .andExpect(status().isCreated())
        .andExpect(header().string("location", "http://localhost/categories/" + ID));

    verify(saveCategoryUsecase, times(1)).apply(any());
  }
}