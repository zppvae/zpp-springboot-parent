package org.zpp.springboot.websocket;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@SpringBootApplication
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