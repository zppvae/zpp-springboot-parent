package org.zpp.springboot.es.reposiory;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.zpp.springboot.es.model.CloudDiskEntity;

public interface CloudDiskReposiory extends ElasticsearchRepository<CloudDiskEntity, String> {

}