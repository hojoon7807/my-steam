package com.hojoon.mysteam.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 테스트 controller.
 */
@RestController
public class HelloController {

  @GetMapping("/")
  public String home() {
    return "hellooo";
  }
}
