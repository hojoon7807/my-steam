package com.hojoon.mysteam.category.domain.model.repository;

import com.hojoon.mysteam.category.domain.model.Category;

public interface UpdateCategoryPort {
  Category updateCategory(Category updateCategory);
}
