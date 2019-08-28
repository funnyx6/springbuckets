package com.funnyx.oauth.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicResponse<T> {

  private int code;

  private String message;

  private T content;

  {
    code = 0;
    message = "成功";
  }

  public BasicResponse() {}

  public BasicResponse(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public BasicResponse(T content) {
    this.content = content;
  }

  public BasicResponse(int code, String message, T content) {
    this.code = code;
    this.message = message;
    this.content = content;
  }
}
