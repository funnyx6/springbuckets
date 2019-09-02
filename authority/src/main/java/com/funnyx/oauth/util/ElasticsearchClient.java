package com.funnyx.oauth.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchClient {

  @Value("${elasticsearch.host}")
  private String host;

  @Value("${elasticsearch.master-port}")
  private int port1;

  @Value("${elasticsearch.slave-port}")
  private int port2;

  @Bean
  public RestHighLevelClient client() {
    RestHighLevelClient client =
        new RestHighLevelClient(
            RestClient.builder(
                new HttpHost(host, port1, "http"), new HttpHost(host, port2, "http")));
    return client;
  }
}
