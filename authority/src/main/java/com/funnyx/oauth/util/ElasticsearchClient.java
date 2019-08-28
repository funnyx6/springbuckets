package com.funnyx.oauth.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchClient {

  @Bean
  public RestHighLevelClient client() {
    RestHighLevelClient client =
        new RestHighLevelClient(
            RestClient.builder(
                new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")));
    return client;
  }
}
