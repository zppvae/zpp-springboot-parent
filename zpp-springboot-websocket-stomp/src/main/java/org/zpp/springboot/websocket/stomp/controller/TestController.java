package org.zpp.springboot.websocket.stomp.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zpp.springboot.websocket.stomp.model.Greeting;

@Controller
public class TestController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

    @Autowired  
    private SimpMessagingTemplate simpMessage; 
    
    @Autowired
    private SimpUserRegistry userRegistry;
    
	@SubscribeMapping("/subscribe/room")
    public void sub() {
        logger.info("房间消息订阅");
    }
	
	@MessageMapping("/ws") //registerStompEndpoints方法配置的  
    @SendTo("/topic/greetings")  
    public void greeting(@Header("atytopic") String topic, @Headers Map<String, Object> headers) {  
        System.out.println("connected successfully....");  
        System.out.println(topic);  
        System.out.println(headers);  
    }  
	
	@MessageMapping("/message")  
    @SendToUser("/message")  
    public Greeting handleSubscribe() {  
        System.out.println("订阅消息");  
        return new Greeting("订阅消息");  
    }
	
	/** 
     * 测试对指定用户发送消息方法 
     * 点对点
     * 最终发送地址  /user/{userId}/message
     * @return 
     */  
    @RequestMapping(path = "/send/{userId}", method = RequestMethod.GET)  
    @ResponseBody
    public Greeting sendToUser(@PathVariable("userId") Integer userId) {  
//    	logger.info("当前在线人数:" + userRegistry.getUserCount());
    	simpMessage.convertAndSendToUser(userId+"", "/message", new Greeting("test message"));  
        return new Greeting("success");  
    } 
}
