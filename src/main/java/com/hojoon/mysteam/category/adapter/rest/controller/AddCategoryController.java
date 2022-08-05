package com.hojoon.mysteam.category.adapter.rest.controller;

import com.hojoon.mysteam.category.adapter.rest.dto.request.AddCategoryRequest;
import com.hojoon.mysteam.category.application.usecase.SaveCategoryUsecase;
import com.hojoon.mysteam.category.domain.model.Category;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class AddCategoryController {

  private final SaveCategoryUsecase saveCategoryUsecase;

  @PostMapping("admin/categories")
  public ResponseEntity addCategory(@RequestBody AddCategoryRequest addCategoryRequest) {
    Category category = saveCategoryUsecase.apply(addCategoryRequest.toCommand());
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(category.getId()).toUri();
    return ResponseEntity.created(location).build();
  }
}
