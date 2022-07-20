package com.hojoon.mysteam.category.adapter.persistence.jpa;

import com.hojoon.mysteam.category.adapter.persistence.jpa.model.CategoryJpaEntity;
import com.hojoon.mysteam.category.domain.model.Category;
import com.hojoon.mysteam.category.domain.model.repository.FindCategoryPort;
import com.hojoon.mysteam.category.domain.model.repository.SaveCategoryPort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class CategoryJpaAdapter implements SaveCategoryPort, FindCategoryPort {

  private final CategoryJpaRepository categoryJpaRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public List<Category> findAllCategories() {
    List<CategoryJpaEntity> categoryJpaEntities = categoryJpaRepository.findAll();

    return categoryJpaEntities.stream()
        .map(categoryMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public Category saveCategory(Category category) {
    CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.save(
        categoryMapper.toEntity(category));

    return categoryMapper.toDomain(categoryJpaEntity);
  }
}
