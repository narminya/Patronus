//package com.demo.patronus.repository;
//
//import com.demo.patronus.models.Category;
//import com.demo.patronus.models.LiveStream;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.UUID;
//
//@Repository
//public interface StreamSearchRepository extends ElasticsearchRepository<LiveStream, UUID> {
//
//    Page<LiveStream> findByCaptionOrDescriptionOrCategoryNameAllIgnoreCase(String caption, String description, String categoryName, Pageable pageable);
//}
//
