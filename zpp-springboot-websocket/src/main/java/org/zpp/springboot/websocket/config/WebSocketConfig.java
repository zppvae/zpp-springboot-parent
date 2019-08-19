package org.zpp.springboot.websocket.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.zpp.springboot.websocket.handler.CountWebSocketHandler;
import org.zpp.springboot.websocket.interceptor.WebsocketHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
	
	@Autowired
	private WebsocketHandshakeInterceptor websocketHandshakeInterceptor;
	
	@Bean
    public ServerEndpointExporter serverEndpointExporter(ApplicationContext context) {
        return new ServerEndpointExporter();
    }
	
    @Override  
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(countWebSocketHandler(), "/svoc-ws")
        		.addInterceptors(websocketHandshakeInterceptor)
        		.setAllowedOrigins("*");
    }  
  
    @Bean
    public WebSocketHandler countWebSocketHandler() {
        return new CountWebSocketHandler();
    }  
}  