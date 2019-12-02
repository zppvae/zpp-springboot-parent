package org.zpp.springboot.es.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "index_user", type = "user")
@Data
public class User {
	@Id
	private String id;
	private String name;
	private int sex;
	private int age;
}