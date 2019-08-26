package com.funnyx.oauth.global;

import com.funnyx.oauth.exception.AuthorizationException;
import com.funnyx.oauth.response.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthorizationException.class)
  public BasicResponse error(AuthorizationException e) {
    log.error(e.getMessage());
    return BasicResponse.builder().code(e.getCode()).message(e.getMessage()).build();
  }
}
