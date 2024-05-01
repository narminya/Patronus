package com.demo.patronus.repository.es;

import com.demo.patronus.models.elasticSearch.StreamSearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends ElasticsearchRepository<StreamSearch, String> {

    List<StreamSearch> findByCaptionOrDescriptionOrCategory(String caption, String description, String category);
}