package org.zpp.springboot.redis;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication
public class RedisApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(RedisApplication.class, args);
	}
}