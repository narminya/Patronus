package com.demo.patronus.config;

import org.apache.http.HttpHost;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestClientBuilder;
import org.opensearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.config.*;
@Configuration
public class ElasticsearchClientConfig {

    @Bean
    public RestHighLevelClient elasticsearchClient() {

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost("localhost", 9200))
                .setRequestConfigCallback(
                        requestConfigBuilder -> requestConfigBuilder
                                .setConnectionRequestTimeout(0));

        return new RestHighLevelClient(builder);
    }

}