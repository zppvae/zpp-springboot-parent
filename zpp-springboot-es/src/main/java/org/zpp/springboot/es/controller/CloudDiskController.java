package org.zpp.springboot.es.controller;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zpp.springboot.es.model.CloudDiskEntity;
import org.zpp.springboot.es.reposiory.CloudDiskReposiory;


import java.util.List;
import java.util.Optional;

@RestController
public class CloudDiskController {
	@Autowired
	private CloudDiskReposiory cloudDiskDao;

	// springboot 整合 es 查询
	// 根据id查询文档信息
	@RequestMapping("/findById/{id}")
	public Optional<CloudDiskEntity> findById(@PathVariable String id) {
		return cloudDiskDao.findById(id);

	}

	/**
	 *
	 * @param name
	 * @param describe
	 * @param pageable
	 * 		page：页码
	 * 		value：条数
	 * @return
	 */
	@RequestMapping("/searchDisk")
	public List<CloudDiskEntity> search(String name, String describe,
										@PageableDefault(page = 0, value = 2) Pageable pageable) {
		// 1.创建查询对象
		BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
		if (!StringUtils.isEmpty(name)) {
			MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", name);
			boolQuery.must(matchQuery);
		}
		if (!StringUtils.isEmpty(describe)) {
			MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("describe", describe);
			boolQuery.must(matchQuery);
		}
		// 2.调用查询接口
		Iterable<CloudDiskEntity> search = cloudDiskDao.search(boolQuery, pageable);
		// 3.将迭代器转换为集合
		return Lists.newArrayList(search);
	}
}
