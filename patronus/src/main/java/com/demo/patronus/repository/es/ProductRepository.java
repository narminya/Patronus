package com.demo.patronus.repository.es;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    Page<Product> findByReferenceOrNameOrDescription(String caption, String description, String category,
                                                     Pageable pageable);
}