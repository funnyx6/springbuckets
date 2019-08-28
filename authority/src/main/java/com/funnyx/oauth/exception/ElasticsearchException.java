package com.funnyx.oauth.exception;

import lombok.Data;

@Data
public class ElasticsearchException extends RuntimeException {

  private int code;

  public ElasticsearchException(int code, String message) {
    super(message);
    this.code = code;
  }
}
