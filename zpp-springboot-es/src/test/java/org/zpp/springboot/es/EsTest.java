package org.zpp.springboot.es;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zpp.springboot.es.ESApplication;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;


/**
 * @author zpp
 * @date 2019/12/12 16:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ESApplication.class)
@Slf4j
public class EsTest {

    @Autowired
    private TransportClient transportClient;

    /**
     * upsert语法，如果不存在，那么就insert，如果存在，那么就update
     * @throws Exception
     */
    @Test
    public void test() throws Exception{
        IndexRequest indexRequest = new IndexRequest("car_shop", "cars", "1")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("brand", "宝马")
                        .field("name", "宝马320")
                        .field("price", 520000)
                        .field("produce_date", "2019-11-01")
                        .endObject());

        UpdateRequest updateRequest = new UpdateRequest("car_shop", "cars", "1")
                .doc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("price", 550000)
                        .endObject())
                .upsert(indexRequest);

        UpdateResponse updateResponse = transportClient.update(updateRequest).get();

       log.info("version: {}",updateResponse.getVersion());
    }

    /**
     * 查询多个doc
     */
    @Test
    public void multiGet(){
        MultiGetResponse multiGetResponse = transportClient.prepareMultiGet()
                .add("car_shop", "cars", "1")
                .add("car_shop", "cars", "2")
                .get();

        for(MultiGetItemResponse multiGetItemResponse : multiGetResponse) {
            GetResponse getResponse = multiGetItemResponse.getResponse();
            if(getResponse.isExists()) {
                log.info("{}",getResponse.getSourceAsString());
            }
        }
    }

    /**
     * 批量上传
     * @throws Exception
     */
    @Test
    public void bulkUpload() throws Exception{
        BulkRequestBuilder bulkRequestBuilder = transportClient.prepareBulk();

        IndexRequestBuilder indexRequestBuilder = transportClient.prepareIndex("sale_shop", "sales", "3")
                .setSource(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("brand", "奔驰")
                        .field("name", "奔驰C200")
                        .field("price", 350000)
                        .field("produce_date", "2017-01-20")
                        .field("sale_price", 320000)
                        .field("sale_date", "2017-01-25")
                        .endObject());
        bulkRequestBuilder.add(indexRequestBuilder);

        UpdateRequestBuilder updateRequestBuilder = transportClient.prepareUpdate("sale_shop", "sales", "1")
                .setDoc(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("sale_price", 290000)
                        .endObject());
        bulkRequestBuilder.add(updateRequestBuilder);

        DeleteRequestBuilder deleteReqeustBuilder = transportClient.prepareDelete("sale_shop", "sales", "2");
        bulkRequestBuilder.add(deleteReqeustBuilder);

        BulkResponse bulkResponse = bulkRequestBuilder.get();

        for(BulkItemResponse bulkItemResponse : bulkResponse.getItems()) {
            log.info("version: {}",bulkItemResponse.getVersion());
        }

    }

    /**
     * 通过scroll获取批量数据
     */
    @Test
    public void scrollGetData(){
        SearchResponse searchResponse = transportClient.prepareSearch("sale_shop")
                .setTypes("sales")
                .setQuery(QueryBuilders.termQuery("brand.keyword", "宝马"))
                .setScroll(new TimeValue(60000))
                .setSize(1)
                .get();

        int batchCount = 0;

        do {
            for(SearchHit searchHit : searchResponse.getHits().getHits()) {
                System.out.println("batch: " + ++batchCount);
                System.out.println(searchHit.getSourceAsString());
            }
            searchResponse = transportClient.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(60000))
                    .execute()
                    .actionGet();
        } while(searchResponse.getHits().getHits().length != 0);

    }

    /**
     * 基于模板搜索
     *
     * 创建模板：
     *
     * POST /_scripts/page_query_by_brand
     * {
     * 	"script": {
     * 		"lang": "mustache",
     * 		"source": {
     * 			"from": "{{from}}",
     * 			"size": "{{size}}",
     * 			"query": {
     * 				"match": {
     * 					"brand.keyword": "{{brand}}"
     *               }
     *          }
     *      }
     *   }
     * }
     */
    @Test
    public void searchTemplateQuery(){
        Map<String, Object> scriptParams = new HashMap<String, Object>();
        scriptParams.put("from", 0);
        scriptParams.put("size", 1);
        scriptParams.put("brand", "宝马");

        SearchResponse searchResponse = new SearchTemplateRequestBuilder(transportClient)
                .setScript("page_query_by_brand")
                .setScriptType(ScriptType.STORED)
                .setScriptParams(scriptParams)
                .setRequest(new SearchRequest("sale_shop").types("sales"))
                .get()
                .getResponse();

        for(SearchHit searchHit : searchResponse.getHits().getHits()) {
            System.out.println(searchHit.getSourceAsString());
        }
    }
}
