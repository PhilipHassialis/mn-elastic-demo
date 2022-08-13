package com.hassialis.philip;

import java.util.HashMap;
import java.util.UUID;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javafaker.Faker;

import io.micronaut.scheduling.annotation.Scheduled;
import jakarta.inject.Singleton;

@Singleton
public class TestDataProvider {

  private static final Logger LOG = LoggerFactory.getLogger(TestDataProvider.class);

  private final RestHighLevelClient client;
  private static final Faker FAKER = new Faker();

  public TestDataProvider(RestHighLevelClient client) {
    this.client = client;
  }

  @Scheduled(fixedDelay = "10s")
  void insertDocument() {
    var document = new HashMap<>();
    document.put("fist_name", FAKER.name().firstName());
    document.put("last_name", FAKER.name().lastName());

    final IndexRequest indexRequest = new IndexRequest()
        .index("mn-es-idx")
        .id(UUID.randomUUID().toString())
        .source(document, XContentType.JSON);

    // client.in

    try {
      client.index(indexRequest, RequestOptions.DEFAULT);
    } catch (Exception e) {
      LOG.error("Error while inserting document", e);
    }

    // , new ActionListener<>() {
    // @Override
    // public void onFailure(Exception arg0) {
    // // TODO Auto-generated method stub
    // LOG.debug("Error while inserting document {}", arg0);
    // }

    // @Override
    // public void onResponse(IndexResponse indexResponse) {
    // // TODO Auto-generated method stub
    // LOG.debug("Insrted document {} with id {}", document, indexResponse.getId());
    // }
    // });

  }

}