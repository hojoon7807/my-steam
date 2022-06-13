package com.hojoon.mysteam.member.application.usecase.exception;

import com.hojoon.mysteam.common.exception.BusinessException;

public class NotFoundMemberException extends BusinessException {

  public NotFoundMemberException(String message) {
    super(message);
  }
}
