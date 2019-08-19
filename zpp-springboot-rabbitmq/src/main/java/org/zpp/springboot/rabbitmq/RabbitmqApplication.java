package org.zpp.springboot.rabbitmq;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class RabbitmqApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitmqApplication.class, args);
	}
}