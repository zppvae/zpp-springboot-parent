package org.zpp.springboot.es.reposiory;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.zpp.springboot.es.model.CloudDisk;

public interface CloudDiskReposiory extends ElasticsearchRepository<CloudDisk, String> {

}