package com.funnyx.oauth.exception;

import lombok.Data;

@Data
public class AuthorizationException extends RuntimeException {

  private int code;

  public AuthorizationException(int code, String message) {
    super(message);
    this.code = code;
  }
}
