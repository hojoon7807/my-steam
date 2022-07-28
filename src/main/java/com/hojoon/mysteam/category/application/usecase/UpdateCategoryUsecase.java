package com.hojoon.mysteam.category.application.usecase;

import com.hojoon.mysteam.category.application.usecase.command.UpdateCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import java.util.function.Function;

public interface UpdateCategoryUsecase extends Function<UpdateCategoryCommand, Category> {

}
