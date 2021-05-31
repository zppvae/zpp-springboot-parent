package org.zpp.springboot.sharding;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zpp
 * @date 2019/11/8 13:33
 */
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class ShardingApplication {

    public static void main(String[] args){
        SpringApplication.run(ShardingApplication.class, args);
    }
}
