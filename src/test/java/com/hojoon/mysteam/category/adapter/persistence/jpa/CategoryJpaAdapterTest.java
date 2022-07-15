package com.hojoon.mysteam.category.adapter.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.common.AbstractContainerBaseTest;
import com.hojoon.mysteam.common.TCDataJpaTest;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;

@TCDataJpaTest
@Slf4j
@Import({CategoryJpaAdapter.class, CategoryMapper.class})
class CategoryJpaAdapterTest extends AbstractContainerBaseTest {

  @Autowired
  private CategoryJpaAdapter categoryJpaAdapter;

  @Test
  @DisplayName("카테고리 정보가 유효하면 해당 객체를 성공적으로 저장하고 반환한다")
  void saveNewMember() {
    final String CATEGORY_NAME = "action";
    Category category = new Category(CATEGORY_NAME);

    Category savedCategory = categoryJpaAdapter.saveCategory(category);

    assertAll(() -> assertThat(savedCategory).isNotNull(),
        () -> assertThat(savedCategory.getCategoryName()).isEqualTo(CATEGORY_NAME));
  }

  @Test
  @DisplayName("DB에 카테고리 정보가 존재하지 않으면 Empty List를 반환한다")
  void findAllCategoriesReturnEmptyList() {
    List<Category> categories = categoryJpaAdapter.findAllCategories();

    assertThat(categories).isEmpty();
  }

  @Test
  @DisplayName("모든 카테고리 정보를 조회하고 List로 반환한다")
  @Sql("CategoryJpaAdapterTest.sql")
  void findAllCategories() {
    List<Category> categories = categoryJpaAdapter.findAllCategories();

    assertThat(categories).hasSize(2);
  }
}