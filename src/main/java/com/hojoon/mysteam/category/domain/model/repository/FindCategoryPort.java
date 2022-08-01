package com.hojoon.mysteam.category.domain.model.repository;

import com.hojoon.mysteam.category.domain.model.Category;
import java.util.List;
import java.util.Optional;

public interface FindCategoryPort {

  List<Category> findAllCategories();

  Optional<Category> findCategoryById(Long categoryId);
}
