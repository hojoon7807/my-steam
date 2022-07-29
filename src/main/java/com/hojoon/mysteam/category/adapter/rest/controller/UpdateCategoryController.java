package com.hojoon.mysteam.category.adapter.rest.controller;

import com.hojoon.mysteam.category.adapter.rest.dto.request.UpdateCategoryRequest;
import com.hojoon.mysteam.category.adapter.rest.dto.response.UpdateCategoryResponse;
import com.hojoon.mysteam.category.application.usecase.UpdateCategoryUsecase;
import com.hojoon.mysteam.category.application.usecase.command.UpdateCategoryCommand;
import com.hojoon.mysteam.category.domain.model.Category;
import javax.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class UpdateCategoryController {

  private final UpdateCategoryUsecase updateCategoryUsecase;

  @PatchMapping("categories/{categoryId}")
  public ResponseEntity<UpdateCategoryResponse> updateCategory(
      @PathVariable @Min(1) Long categoryId,
      @RequestBody UpdateCategoryRequest updateCategoryRequest) {

    Category category = updateCategoryUsecase.apply(new UpdateCategoryCommand(categoryId,
        updateCategoryRequest.getCategoryName()));

    return ResponseEntity.ok(new UpdateCategoryResponse(category.getCategoryName()));
  }
}
