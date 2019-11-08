package org.zpp.springboot.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zpp
 * @date 2019/11/8 13:33
 */
@MapperScan("org.zpp.springboot.sharding.mapper")
@SpringBootApplication
public class ShardingApplication {

    public static void main(String[] args){
        SpringApplication.run(ShardingApplication.class, args);
    }
}
