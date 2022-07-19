package com.hojoon.mysteam.category.adapter.rest.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hojoon.mysteam.category.application.usecase.FindAllCategoryUsecase;
import com.hojoon.mysteam.category.domain.model.Category;
import java.util.List;
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
class GetAllCategoryControllerTest {

  @InjectMocks
  private GetAllCategoryController getAllCategoryController;

  @Mock
  private FindAllCategoryUsecase findAllCategoryUsecase;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void init() {
    mockMvc = MockMvcBuilders.standaloneSetup(getAllCategoryController)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  @Test
  @DisplayName("모든 카테고리 조회 요청을 보내면 응답 객체를 반환한다")
  void getAllCategoryRequest() throws Exception {
    List<Category> categories = List.of(new Category("test1"), new Category("test2"));
    when(findAllCategoryUsecase.get()).thenReturn(categories);

    mockMvc.perform(get("/categories").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", is(2)));
  }
}