package com.demo.patronus.models.elasticSearch;

import jakarta.persistence.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "streams")
public class StreamSearch {
    @Id
    private String id;
    @Field(type = FieldType.Text, name = "caption")
    private String caption;
    @Field(type = FieldType.Text, name = "description")
    private String description;
    @Field(type = FieldType.Text, name = "category")
    private String category;
}
