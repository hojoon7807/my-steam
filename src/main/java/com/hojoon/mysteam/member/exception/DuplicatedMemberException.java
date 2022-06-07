package com.hojoon.mysteam.member.exception;

import com.hojoon.mysteam.common.exception.BusinessException;

public class DuplicatedMemberException extends BusinessException {

  public DuplicatedMemberException(String message) {
    super(message);
  }
}
