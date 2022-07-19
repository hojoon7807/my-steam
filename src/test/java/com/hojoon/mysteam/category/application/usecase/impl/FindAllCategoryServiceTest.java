package com.hojoon.mysteam.category.application.usecase.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.FindCategoryPort;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindAllCategoryServiceTest {

  @Mock
  private FindCategoryPort findCategoryPort;

  @InjectMocks
  private FindAllCategoryService findAllCategoryService;

  @Test
  @DisplayName("카테고리가 존재하면 모든 카테고리를 담고 있는 리스트를 반환한다")
  void findAllCategories() {
    Category testCategory = new Category(1L, "test");
    Category testCategory2 = new Category(2L, "test2");
    when(findCategoryPort.findAllCategories()).thenReturn(List.of(testCategory, testCategory2));

    List<Category> categories = findAllCategoryService.get();

    assertThat(categories).hasSize(2);
  }

  @Test
  @DisplayName("카테고리가 존재하지 않으면 빈 리스트를 반환한다")
  void findAllCategoriesWithoutCategory() {
    when(findCategoryPort.findAllCategories()).thenReturn(List.of());

    List<Category> categories = findAllCategoryService.get();

    assertThat(categories).isEmpty();
  }
}