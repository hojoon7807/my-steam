package com.hojoon.mysteam.category.domain.model.repository;

import com.hojoon.mysteam.category.domain.model.Category;

public interface SaveCategoryPort {

  Category saveCategory(Category category);
}
