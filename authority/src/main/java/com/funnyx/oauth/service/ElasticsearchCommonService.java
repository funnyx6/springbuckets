package com.funnyx.oauth.service;

import com.alibaba.fastjson.JSON;
import com.funnyx.oauth.exception.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ElasticsearchCommonService {

  @Autowired private RestHighLevelClient client;

  /**
   * 根据索引名称和文档ID来获取数据
   *
   * @param indexName 索引名称
   * @param docId 文档ID
   * @return String
   * @throws Exception
   */
  public String get(String indexName, String docId) throws Exception {
    try {
      GetRequest getRequest = new GetRequest(indexName, docId);
      GetResponse response = client.get(getRequest, RequestOptions.DEFAULT);
      if (response.isExists()) {
        return response.getSourceAsString();
      } else {
        return "";
      }
    } catch (RuntimeException e) {
      throw new ElasticsearchException(5000, "获取文档数据失败");
    }
  }

  public <T> T getObject(String indexName, String docId, Class<T> cls) throws Exception {
    try {
      String s = get(indexName, docId);
      return JSON.parseObject(s, cls);
    } catch (RuntimeException ex) {
      throw new ElasticsearchException(5000, "获取文档数据失败");
    }
  }

  /**
   * 索引文档
   *
   * @param indexName 索引名称
   * @param docContent json格式字符串
   * @return
   * @throws Exception
   */
  public boolean index(String indexName, String docContent) throws Exception {
    try {
      IndexRequest request = new IndexRequest(indexName);
      request.setRefreshPolicy(WriteRequest.RefreshPolicy.WAIT_UNTIL);
      request.source(docContent, XContentType.JSON);
      IndexResponse response = client.index(request, RequestOptions.DEFAULT);
      if (response.getResult() == DocWriteResponse.Result.CREATED) {
        return true;
      } else {
        return false;
      }
    } catch (RuntimeException ex) {
      throw new ElasticsearchException(5000, "文档索引失败");
    }
  }

  /**
   * 查询指定索引名称的文档数量
   *
   * @param indexName 索引名称
   * @return
   * @throws Exception
   */
  public Long count(String indexName) throws Exception {
    try {
      CountRequest countRequest = new CountRequest(indexName);
      SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
      searchSourceBuilder.query(QueryBuilders.matchAllQuery());
      countRequest.source(searchSourceBuilder);
      CountResponse response = client.count(countRequest, RequestOptions.DEFAULT);
      return response.getCount();
    } catch (RuntimeException ex) {
      throw new ElasticsearchException(5000, "获取文档数量失败");
    }
  }

  /**
   * 查询索引中全部文档数据
   *
   * @param indexName 索引名称
   * @param clazz
   * @param <T>
   * @return
   * @throws Exception
   */
  public <T> List<T> searchAll(String indexName, Class<T> clazz) throws Exception {
    List<T> list = new ArrayList<>();
    try {
      SearchRequest searchRequest = new SearchRequest(indexName);
      SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
      sourceBuilder.query(
          QueryBuilders.matchAllQuery()); // Set the query. Can be any type of QueryBuilder
      sourceBuilder.from(0);
      sourceBuilder.size(count(indexName).intValue());
      sourceBuilder.timeout(new TimeValue(5, TimeUnit.SECONDS));
      SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
      SearchHits hits = response.getHits();
      SearchHit[] searchHits = hits.getHits();
      for (SearchHit hit : searchHits) {
        String sourceAsString = hit.getSourceAsString();
        T t = JSON.parseObject(sourceAsString, clazz);
        list.add(t);
      }
      return list;
    } catch (RuntimeException ex) {
      throw new ElasticsearchException(5000, "获取文档数据失败");
    }
  }

  public <T> List<T> search(String indexName, Class<T> clazz, QueryBuilder queryBuilder) {
    return null;
  }
}
