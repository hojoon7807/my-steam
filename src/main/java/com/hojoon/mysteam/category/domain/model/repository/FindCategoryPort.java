package com.hojoon.mysteam.category.domain.model.repository;

import com.hojoon.mysteam.category.domain.model.Category;
import java.util.List;

public interface FindCategoryPort {

  List<Category> findAllCategories();
}
