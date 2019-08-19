package org.zpp.springboot.socketio.conroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.corundumstudio.socketio.SocketIOServer;

@Controller
public class TestController {
	
	@Autowired
	private SocketIOServer server;
	
	
	/**
	 * 广播消息测试
	 * @param msg
	 * @return
	 */
	@GetMapping(value="/broadcast/{roomId}/{msg}")
	@ResponseBody
	public String broadcast(@PathVariable("roomId") String roomId,@PathVariable("msg") String msg){
		server.getRoomOperations(roomId).sendEvent("broadcast", "广播啦啦啦啦");
		return "success";
	}
}
