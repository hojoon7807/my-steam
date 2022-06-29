package com.hojoon.mysteam.category.adapter.persistence.jpa;

import com.hojoon.mysteam.category.adapter.persistence.jpa.model.CategoryJpaEntity;
import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.SaveCategoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class CategoryJpaAdapter implements SaveCategoryPort {

  private final CategoryJpaRepository categoryJpaRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public Category saveCategory(Category category) {
    CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.save(
        categoryMapper.toEntity(category));

    return categoryMapper.toDomain(categoryJpaEntity);
  }
}
