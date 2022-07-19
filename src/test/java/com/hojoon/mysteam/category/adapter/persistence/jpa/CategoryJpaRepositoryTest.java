package com.hojoon.mysteam.category.adapter.persistence.jpa;

import static org.assertj.core.api.Assertions.assertThat;

import com.hojoon.mysteam.category.adapter.persistence.jpa.model.CategoryJpaEntity;
import com.hojoon.mysteam.common.AbstractContainerBaseTest;
import com.hojoon.mysteam.common.TCDataJpaTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@TCDataJpaTest
class CategoryJpaRepositoryTest extends AbstractContainerBaseTest {

  @Autowired
  private CategoryJpaRepository categoryJpaRepository;

  @Test
  @DisplayName("카테고리 정보가 존재하지 않으면 비어있는 List를 반환한다")
  void findAllToDBWithoutCategory(){
    List<CategoryJpaEntity> categoryJpaEntities = categoryJpaRepository.findAll();

    assertThat(categoryJpaEntities).isEmpty();
  }
}