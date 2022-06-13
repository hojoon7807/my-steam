package com.hojoon.mysteam.member.adapter.rest.controller;

import com.hojoon.mysteam.member.adapter.rest.dto.request.SignUpMemberRequest;
import com.hojoon.mysteam.member.adapter.rest.dto.response.SignUpMemberResponse;
import com.hojoon.mysteam.member.application.usecase.CreateMemberUsecase;
import com.hojoon.mysteam.member.domain.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class SignUpMemberController {

  private final CreateMemberUsecase createMemberUseCase;

  @PostMapping("/signup")
  public ResponseEntity<SignUpMemberResponse> signUp(
      @RequestBody SignUpMemberRequest signUpMemberRequest) {
    Member member = createMemberUseCase.apply(signUpMemberRequest.toCommand());
    return new ResponseEntity<>(new SignUpMemberResponse(member.getEmail()), HttpStatus.CREATED);
  }
}
