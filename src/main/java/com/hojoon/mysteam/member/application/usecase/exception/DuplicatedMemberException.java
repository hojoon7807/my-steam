package com.hojoon.mysteam.member.application.usecase.exception;

import com.hojoon.mysteam.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DuplicatedMemberException extends BusinessException {

  public DuplicatedMemberException(String message) {
    super(message);
  }
}
