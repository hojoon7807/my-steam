package com.hojoon.mysteam.category.adapter.rest.controller;

import com.hojoon.mysteam.category.application.usecase.DeleteCategoryUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DeleteCatgoryController {

  private final DeleteCategoryUsecase deleteCategoryUsecase;

  @DeleteMapping("categories/{categoryId}")
  public ResponseEntity deleteCategory(@PathVariable Long categoryId) {
    deleteCategoryUsecase.accept(categoryId);

    return ResponseEntity.noContent().build();
  }
}
