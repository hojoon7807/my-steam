package com.hojoon.mysteam.category.adapter.rest.controller;

import com.hojoon.mysteam.category.application.usecase.DeleteCategoryUsecase;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class DeleteCatgoryController {

  private final DeleteCategoryUsecase deleteCategoryUsecase;

  @DeleteMapping("admin/categories/{categoryId}")
  public ResponseEntity deleteCategory(@Min(1) @PathVariable("categoryId") Long categoryId) {
    deleteCategoryUsecase.accept(categoryId);

    return ResponseEntity.noContent().build();
  }
}
