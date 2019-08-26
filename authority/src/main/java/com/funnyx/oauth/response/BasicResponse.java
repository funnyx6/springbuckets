package com.funnyx.oauth.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicResponse {

  private int code;

  private String message;

  public BasicResponse() {}

  public BasicResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
