package com.hojoon.mysteam.category.adapter.persistence.exception;

import com.hojoon.mysteam.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundCategoryException extends BusinessException {

  public NotFoundCategoryException() {
    super("해당 카테고리가 존재하지 않습니다");
  }
}
