package org.zpp.springboot.socketio.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zpp.springboot.socketio.common.Constants;
import org.zpp.springboot.socketio.model.ClientInfo;
import org.zpp.springboot.socketio.model.RoomMessageInfo;
import org.zpp.springboot.socketio.util.ChatUtils;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 群聊命名空间处理器
 */
public class GroupChatEventListener {
	private static final Logger log = LoggerFactory.getLogger(GroupChatEventListener.class);
	// 会话集合
	private static final ConcurrentSkipListMap<String, ClientInfo> webSocketMap = new ConcurrentSkipListMap<>();
	// 静态变量，用来记录当前在线连接数。（原子类、线程安全）
	private static AtomicInteger onlineCount = new AtomicInteger(0);
	private SocketIOServer server;


	@Autowired
	public GroupChatEventListener(SocketIOServer server) {
		this.server = server;
	}


//    /**
//	 *  房间消息接收入口
//	 * @param client
//	 * @param ackRequest
//	 * @param data
//	 */
//    @OnEvent(value = Constants.GROUP_CHAT_MESSAGE)
//    public void onRoom(SocketIOClient client, AckRequest ackRequest, RoomMessageInfo data) {
//        // 房间广播消息
////        client.joinRoom(data.getRoomId());
//        server.getRoomOperations(data.getRoomId()).sendEvent(Constants.GROUP_CHAT_MESSAGE, data);
//
//        //发送消息给除client的其他用户消息
////        server.getRoomOperations(data.toString()).sendEvent(Constants.ROOM_CHAT_KEY, client, "消息");
//    }

	/**
	 * 加入房间
	 * @param client
	 * @param ackRequest
	 * @param data
	 */
	@OnEvent(value = Constants.JOIN_KEY)
	public void join(SocketIOClient client, AckRequest ackRequest, ClientInfo data) {
		ChatUtils.joinRoom(client,data.getRoomId());
	}



}