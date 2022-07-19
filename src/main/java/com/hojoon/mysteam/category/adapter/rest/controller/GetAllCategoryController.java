package com.hojoon.mysteam.category.adapter.rest.controller;

import com.hojoon.mysteam.category.adapter.rest.dto.response.GetCategoryResponse;
import com.hojoon.mysteam.category.application.usecase.FindAllCategoryUsecase;
import com.hojoon.mysteam.category.domain.model.Category;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GetAllCategoryController {

  private final FindAllCategoryUsecase findAllCategoryUsecase;

  @GetMapping("categories")
  public ResponseEntity<List<GetCategoryResponse>> getAllCategory() {
    List<Category> categories = findAllCategoryUsecase.get();
    List<GetCategoryResponse> response = categories.stream()
        .map(category -> new GetCategoryResponse(category.getCategoryName()))
        .collect(Collectors.toList());

    return ResponseEntity.ok().body(response);
  }
}
