package org.zpp.springboot.websocket.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

	@Value("${server.port}")
	private Integer port;

	@Autowired
	private HttpServletRequest request;
	
//	/**
//	 * 群发消息
//	 * @return
//	 * @throws IOException
//	 */
//	@GetMapping(value="/group")
//	@ResponseBody
//	public String sendGroup() throws IOException{
//		WebSocketEndpoint.sendGroup(request.getParameter("msg"));
//		return "success";
//	}
    @GetMapping(value="/")
	@ResponseBody
	public String index() throws IOException{
		return port+"";
	}

}
