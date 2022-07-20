package com.hojoon.mysteam.category.application.usecase;

import com.hojoon.mysteam.category.domain.model.Category;
import java.util.List;
import java.util.function.Supplier;

public interface FindAllCategoryUsecase extends Supplier<List<Category>> {

}
