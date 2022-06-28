package com.hojoon.mysteam.category.adapter.persistence.jpa;

import com.hojoon.mysteam.category.adapter.persistence.jpa.model.CategoryJpaEntity;
import com.hojoon.mysteam.category.domain.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

  public Category toDomain(CategoryJpaEntity categoryJpaEntity) {
    return new Category(categoryJpaEntity.getId(), categoryJpaEntity.getName());
  }

  public CategoryJpaEntity toEntity(Category category) {
    return new CategoryJpaEntity(category.getId(), category.getCategoryName());
  }
}
