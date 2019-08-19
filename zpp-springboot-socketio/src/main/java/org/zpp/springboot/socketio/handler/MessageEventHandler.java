package org.zpp.springboot.socketio.handler;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

import com.corundumstudio.socketio.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zpp.springboot.socketio.common.Constants;
import org.zpp.springboot.socketio.common.enums.ChatMessageStatusEnum;
import org.zpp.springboot.socketio.common.enums.MessageTypeEnum;
import org.zpp.springboot.socketio.model.*;

import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import io.socket.client.Socket;
import org.zpp.springboot.socketio.request.chatroom.ChatroomInviteUserRequest;
import org.zpp.springboot.socketio.response.chatroom.ChatroomDTO;
import org.zpp.springboot.socketio.response.chatroom.ChatroomMemberDTO;
import org.zpp.springboot.socketio.service.ChatMessageService;
import org.zpp.springboot.socketio.service.ChatroomService;
import org.zpp.springboot.socketio.service.UserService;
import org.zpp.springboot.socketio.util.ChatUtils;


/**
 *
 * Description:
 * @author zpp
 * @date   2018年5月29日
 */
@Component
public class MessageEventHandler {
	private static final Logger log = LoggerFactory.getLogger(MessageEventHandler.class);

	private static final ConcurrentSkipListMap<String, ClientInfo> userSessions = new ConcurrentSkipListMap<>();

	private static final Set<ClientInfo> users = new HashSet<>(20);

	/**在线连接数 **/
	private static AtomicInteger onlineCount = new AtomicInteger(0);

	private final SocketIOServer server;

	@Autowired
    private UserService userService;

	@Autowired
	private ChatMessageService chatMessageService;

	@Autowired
	private ChatroomService chatroomService;

	@Autowired
	public MessageEventHandler(SocketIOServer server) {
		this.server = server;
	}

	/**
	 * connect事件处理，当客户端发起连接时将调用
	 *
	 * @param client
	 */
	@OnConnect
	public void onConnect(SocketIOClient client) {
		String roomId = client.getHandshakeData().getSingleUrlParam(Constants.ROOM_KEY);
		String clientName = client.getHandshakeData().getSingleUrlParam(Constants.CLIENT_KEY);
		String sessionId = client.getSessionId().toString();

		UUID session = client.getSessionId();

		User user = userService.selectByName(clientName);
		ClientInfo si = new ClientInfo();

		// 更新设置客户端连接信息
        si.setClientId(user.getUserId());
		si.setLeastSignificantBits(session.getLeastSignificantBits());
		si.setMostSignificantBits(session.getMostSignificantBits());
		si.setLastConnectedTime(new Date());
		si.setOnline(true);
		si.setClientName(clientName);
		si.setSessionId(sessionId);

		if (StringUtils.isNotBlank(roomId)) {
			client.joinRoom(roomId);
		}
		userSessions.put(sessionId, si);

		addUser(si);
		client.set(sessionId,si);


		pushOnlineUserList();

//		pushOffLineMessage(user.getUserId());

		// 在线数加1
		log.info("新用户加入，sessionId:" + session+ "、当前连接数："+ onlineCount.incrementAndGet());
	}

	private void addUser(ClientInfo ci){
		Iterator<ClientInfo> it = users.iterator();
		while (it.hasNext()){
			ClientInfo client = it.next();
			if(ci.getClientId().intValue() == client.getClientId()){
				client.setSessionId(ci.getSessionId());
				client.setLastConnectedTime(ci.getLastConnectedTime());
				client.setLeastSignificantBits(ci.getLeastSignificantBits());
				client.setMostSignificantBits(ci.getMostSignificantBits());
				client.setOnline(true);
				break;
			}
		}

		users.add(ci);
	}
	/**
	 * 推送在线的用户列表
	 */
	private void pushOnlineUserList(){
		BaseMessage base = new BaseMessage();
		base.setMsgType(MessageTypeEnum.LOAD_USER_LIST.getMsgType());
		base.setData(users);
		server.getBroadcastOperations().sendEvent(Constants.LOAD_USER_LIST, base);
	}

	/**
	 * 用户登录时推送用户未读取的消息
	 * @param userId
	 */
	private void pushOffLineMessage(Integer userId){
		ChatMessage cm = new ChatMessage();
		cm.setReceiveUserId(userId);
		cm.setStatus(ChatMessageStatusEnum.SEND_NO.getKey());
		List<ChatMessage> messages = chatMessageService.getList(cm);
		if (messages != null && messages.size() > 0) {
			List<MessageInfo> list = new ArrayList<>();
			MessageInfo temp = null;

			for (int i = 0; i < messages.size(); i++) {
				ChatMessage message = messages.get(i);
				temp = new MessageInfo();
				temp.setMsgId(message.getMsgId());
				temp.setClientId(message.getSendUserId());
				temp.setMsg(message.getContent());
				temp.setSourceClientId(message.getSendUserId());
				temp.setTargetClientId(message.getReceiveUserId());
				list.add(temp);
			}
			BaseMessage base = new BaseMessage();
			base.setMsgType(MessageTypeEnum.CHAT_MESSAGE.getMsgType());
			base.setData(list);

			ClientInfo clientInfo = getClient(userId);
			UUID target = new UUID(clientInfo.getMostSignificantBits(), clientInfo.getLeastSignificantBits());
			// 向目标会话发送信息
			server.getClient(target).sendEvent(Constants.OFFLINE_CHAT_EVENT, new AckCallback<String>(String.class,5) {
				@Override
				public void onSuccess(String msgId) {
					log.info("离线消息接收到客户端反馈 " + clientInfo.getSessionId() + " msgId: " + msgId);
					ChatMessage cm = new ChatMessage();
					cm.setMsgId(Integer.valueOf(msgId));
					cm.setStatus(ChatMessageStatusEnum.SEND_YES.getKey());
					chatMessageService.update(cm);
				}

			},base);

		}
	}
	/**
	 * 获取当前用户信息
	 * @param client
	 */
	@OnEvent(value = Constants.LOAD_USER)
	public void onLoadUser(SocketIOClient client) {
		ClientInfo clientInfo = userSessions.get(client.getSessionId().toString());
		client.sendEvent(Constants.LOAD_USER,clientInfo);
	}

	/**
	 * disconnect事件处理，当客户端断开连接时将调用
	 *
	 * @param client
	 */
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		String sessionId = client.getSessionId().toString();
		client.del(sessionId);
		userSessions.remove(sessionId);

		removeOffLine(sessionId);
		ChatUtils.leaveRoom(client);


		pushOnlineUserList();
		// 在线数减1
		log.info("用户离开，sessionId:" + client.getSessionId() + "、当前连接数："
				+ onlineCount.decrementAndGet());
	}

	/**
	 * 移除离线用户
	 * @param sessionId
	 */
	private void removeOffLine(String sessionId){
		Iterator<ClientInfo> it = users.iterator();
		while (it.hasNext()){
			ClientInfo client = it.next();
			if(sessionId.equals(client.getSessionId())){
				client.setOnline(false);
				break;
			}
		}
	}

	/**
	 * 客户端连接错误
	 * @param client
	 */
	@OnEvent(value = Socket.EVENT_CONNECT_ERROR)
	public void onConnetError(SocketIOClient client) {
		String token = client.getHandshakeData().getSingleUrlParam(Constants.TOKEN);
		log.error("client连接错误，token:" + token);
	}


	/**
	 * 客户端连接超时
	 * @param client
	 */
	@OnEvent(value = Socket.EVENT_CONNECT_TIMEOUT)
	public void onConnetTimeout(SocketIOClient client) {
		String token = client.getHandshakeData().getSingleUrlParam(Constants.TOKEN);
		log.error("client连接超时，token:" + token);

	}

	/**
	 * 单聊消息
	 * @param client
	 * @param ackRequest
	 * @param data
	 */
	@OnEvent(value = Socket.EVENT_MESSAGE)
	public void onEvent(SocketIOClient client, AckRequest ackRequest, MessageInfo data) {
		if (ackRequest.isAckRequested()) {
			log.info("已收到客户端发送消息请求");
			ackRequest.sendAckData("服务端已收到发送请求");
		}
		BaseMessage message = new BaseMessage();
		message.setMsgType(MessageTypeEnum.CHAT_MESSAGE.getMsgType());

		User sourceUser = userService.selectById(data.getSourceClientId());
		User targetUser = userService.selectById(data.getTargetClientId());
		data.setClientId(sourceUser.getUserId());
		data.setClientName(sourceUser.getUserName());

		ChatMessage cm = new ChatMessage();
		cm.setSendUserId(sourceUser.getUserId());
		cm.setReceiveUserId(targetUser.getUserId());
		cm.setContent(data.getMsg());
		chatMessageService.save(cm);

		Integer msgId = cm.getMsgId();
		data.setMsgId(msgId);
		message.setData(data);

		ClientInfo clientInfo = getClient(data.getTargetClientId());
		if (clientInfo != null && clientInfo.isOnline()) {
			UUID target = new UUID(clientInfo.getMostSignificantBits(), clientInfo.getLeastSignificantBits());
			log.info("目标会话UUID:" + target);
			// 向目标会话发送信息
			server.getClient(target).sendEvent(Constants.CHAT_MESSAGE, new AckCallback<String>(String.class,1) {
				@Override
				public void onSuccess(String msgId) {
					log.info("聊天消息接收到客户端反馈 " + clientInfo.getSessionId() + " msgId: " + msgId);
					ChatMessage cm = new ChatMessage();
					cm.setMsgId(Integer.valueOf(msgId));
					cm.setStatus(ChatMessageStatusEnum.SEND_YES.getKey());
					chatMessageService.update(cm);
				}

			},message);
		} else {
			log.info("目标客户端不在线，消息："+data.getMsg());
		}

	}

	private ClientInfo getClient(Integer clientId){
		for (ClientInfo user : users) {
			if (clientId.intValue() == user.getClientId()) {
				return user;
			}
		}

		return null;
	}

    /**
	 *  房间消息接收入口
	 * @param client
	 * @param ackRequest
	 * @param data
	 */
    @OnEvent(value = Constants.ROOM_CHAT_KEY)
    public void onRoom(SocketIOClient client, AckRequest ackRequest, RoomMessageInfo data) {
        server.getRoomOperations(data.getRoomId()).sendEvent(Constants.ROOM_CHAT_KEY, data);
    }
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

	/**
	 * 加载用户
	 * @param client
	 * @param ackRequest
	 */
	@OnEvent(value = Constants.LOAD_USER_LIST)
	public void loadUser(SocketIOClient client, AckRequest ackRequest) {
		BaseMessage base = new BaseMessage();
		base.setMsgType(MessageTypeEnum.LOAD_USER_LIST.getMsgType());
		base.setData(users);
		client.sendEvent(Constants.LOAD_USER_LIST,base);
	}

	/**
	 * 创建聊天室
	 * @param client
	 * @param ackRequest
	 * @param data
	 */
	@OnEvent(value = Constants.CHATROOM_CREATE_EVENT)
	public void createChatroom(SocketIOClient client, AckRequest ackRequest, Chatroom data) {
		ClientInfo clientInfo = userSessions.get(client.getSessionId().toString());
		data.setCreateUserId(clientInfo.getClientId());
		int roomId = chatroomService.save(data);
		data.setRoomId(roomId);
		client.sendEvent(Constants.CHATROOM_CREATE_EVENT,data);
	}

	/**
	 * 聊天室添加用户
	 * @param client
	 * @param ackRequest
	 * @param data
	 */
	@OnEvent(value = Constants.CHATROOM_ADD_USER_EVENT)
	public void inviceUserJoinChatroom(SocketIOClient client, AckRequest ackRequest, ChatroomInviteUserRequest data) {
		chatroomService.addMembers(data.getRoomId(),data.getUserIds());
	}

	/**
	 * 聊天室信息
	 * @param client
	 * @param ackRequest
	 * @param data
	 */
	@OnEvent(value = Constants.CHATROOM_INFO_EVENT)
	public void chatRoomInfo(SocketIOClient client, AckRequest ackRequest, Chatroom data) {
		ChatroomDTO dto = chatroomService.getChatroomByRoomId(data.getRoomId());
		client.sendEvent(Constants.CHATROOM_INFO_EVENT,dto);
	}


	/**
	 * 用户所在的聊天室列表
	 * @param client
	 * @param ackRequest
	 */
	@OnEvent(value = Constants.CHATROOM_LIST_EVENT)
	public void chatroomList(SocketIOClient client, AckRequest ackRequest) {
		ClientInfo clientInfo = userSessions.get(client.getSessionId().toString());
		handleChatroomList(client,clientInfo);
	}

	/**
	 * 向用户推送聊天室列表
	 * @param client
	 * @param clientInfo
	 */
	private void handleChatroomList(SocketIOClient client,ClientInfo clientInfo){
		List<Chatroom> rooms = chatroomService.getListByUserId(clientInfo.getClientId());
		BaseMessage bm = new BaseMessage(MessageTypeEnum.CHATROOM_LIST.getMsgType());
		bm.setData(rooms);
		client.sendEvent(Constants.CHATROOM_LIST_EVENT,bm);
	}


	/**
	 * 聊天室成员
	 * @param client
	 * @param ackRequest
	 */
	@OnEvent(value = Constants.CHATROOM_MEMBER_EVENT)
	public void chatroomMember(SocketIOClient client, AckRequest ackRequest,Chatroom data) {
		ChatroomMemberDTO dto = chatroomService.getChatroomMembersByRoomId(data.getRoomId());
		client.sendEvent(Constants.CHATROOM_MEMBER_EVENT,dto);
	}

	/**
	 * 加载所有用户
	 * @param client
	 * @param ackRequest
	 */
	@OnEvent(value = Constants.ALL_USER_LIST_EVENT)
	public void allUserList(SocketIOClient client, AckRequest ackRequest) {
		client.sendEvent(Constants.ALL_USER_LIST_EVENT,userService.getAllUser());
	}
}