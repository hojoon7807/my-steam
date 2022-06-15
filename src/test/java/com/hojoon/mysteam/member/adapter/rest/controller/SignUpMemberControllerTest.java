package com.hojoon.mysteam.member.adapter.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hojoon.mysteam.member.adapter.rest.dto.request.SignUpMemberRequest;
import com.hojoon.mysteam.member.application.usecase.CreateMemberUsecase;
import com.hojoon.mysteam.member.application.usecase.exception.DuplicatedMemberException;
import com.hojoon.mysteam.member.domain.model.Member;
import com.hojoon.mysteam.member.domain.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class SignUpMemberControllerTest {

  @InjectMocks
  private SignUpMemberController signUpMemberController;

  @Mock
  private CreateMemberUsecase createMemberUsecase;

  private MockMvc mockMvc;

  private ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void prepare() {
    mockMvc = MockMvcBuilders.standaloneSetup(signUpMemberController)
        .addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
  }

  @Test
  @DisplayName("회원가입 요청 정보를 성공적으로 저장하고, 응답 객체를 반환한다")
  void memberSave() throws Exception {
    final Long ID = 1L;
    final String EMAIL = "hojoon@gmail.com";
    final String PASSWORD = "1234";
    final String NAME = "hojoon";
    final String PHONE_NUM = "01012341234";

    SignUpMemberRequest signUpMemberRequest = new SignUpMemberRequest(EMAIL, NAME, PASSWORD,
        PHONE_NUM);

    Member savedMember = Member.builder()
        .id(ID)
        .email(EMAIL)
        .password(PASSWORD)
        .phoneNum(PHONE_NUM)
        .name(NAME)
        .role(Role.USER)
        .build();

    when(createMemberUsecase.apply(any())).thenReturn(savedMember);

    ResultActions resultActions = mockMvc.perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpMemberRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.email").value(EMAIL));

    verify(createMemberUsecase, times(1)).apply(any());
  }

  @Test
  @DisplayName("이미 존재하는 유저가 회원가입 요청을 하면, DuplicatedMember 예외가 발생한다")
  void memberSave_duplicatedMember() throws Exception {
    SignUpMemberRequest signUpMemberRequest = new SignUpMemberRequest("email", "name", "password",
        "phonenum");

    when(createMemberUsecase.apply(any())).thenThrow(
        new DuplicatedMemberException("이미 존재하는 회원입니다."));

    ResultActions resultActions = mockMvc.perform(
            post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpMemberRequest)))
        .andExpect(result -> assertAll(
                () -> assertThat(result.getResolvedException()).isInstanceOf(
                    DuplicatedMemberException.class),
                () -> assertThat(result.getResolvedException()).hasMessage("이미 존재하는 회원입니다.")
            )
        )
        .andExpect(status().isBadRequest());
  }
}