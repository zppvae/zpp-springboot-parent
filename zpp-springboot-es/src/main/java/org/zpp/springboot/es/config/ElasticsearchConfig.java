package org.zpp.springboot.es.config;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;


@Configuration
@EnableElasticsearchRepositories(basePackages = "org.zpp.springboot.es.reposiory")
public class ElasticsearchConfig {

    @Value("${elasticsearch.ip}")
    private String hostName;

    /**
     * 端口
     */
    @Value("${elasticsearch.port}")
    private String port;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.pool}")
    private String poolSize;

    @Bean
    public TransportClient transportClient() {
        TransportClient transportClient = null;
        try {
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("cluster.name", clusterName)
                    //增加嗅探机制，找到ES集群
                    .put("client.transport.sniff", false)
                    //增加线程池个数
                    .put("thread_pool.search.size", Integer.parseInt(poolSize))
                    .build();
            //配置信息Settings自定义
            transportClient = new PreBuiltTransportClient(esSetting);
            // 通讯IP
            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(hostName), Integer.parseInt(port));
            transportClient.addTransportAddresses(transportAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return transportClient;
    }


}
