package org.zpp.springboot.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;




@SpringBootApplication
@EnableAutoConfiguration
@EnableWebSocket
public class WebsocketApplication{

//	@Bean
//	public ServerEndpointExporter serverEndpointExporter() {
//		return new ServerEndpointExporter();
//	}

//	@Bean
//	public WebSocketEndpoint webSocketEndpoint() {
//		return new WebSocketEndpoint();
//	}
	
	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class,args);
	}
}