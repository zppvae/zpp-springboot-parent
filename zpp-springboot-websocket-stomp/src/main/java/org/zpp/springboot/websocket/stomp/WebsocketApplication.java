package org.zpp.springboot.websocket.stomp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@SpringBootApplication
@EnableAutoConfiguration
@EnableWebSocket
public class WebsocketApplication{
	
	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class,args);
	}
}