package org.zpp.springboot.socketio.common;

public class Constants {
	
	public static final String TOKEN = "token";
	
	public static final String ROOM_KEY = "roomId";
	
	public static final String CLIENT_KEY = "clientName";
	
	public static final String BROADCAST_KEY = "broadcast";
	
	public static final String ROOM_CHAT_KEY = "roomChat";

	public static final String JOIN_KEY = "join";

	///*****************聊天室事件*******************//
	/** 创建聊天室*/
	public static final String CHATROOM_CREATE_EVENT = "chatroom_create";

	/** 用户所在的聊天室*/
	public static final String CHATROOM_LIST_EVENT = "chatroom_list";

	/** 聊天室添加用户*/
	public static final String CHATROOM_ADD_USER_EVENT = "chatroom_add_user";

	/** 聊天室信息*/
	public static final String CHATROOM_INFO_EVENT = "chatroom_info";

	/** 聊天室成员*/
	public static final String CHATROOM_MEMBER_EVENT = "chatroom_member";


	/** 单聊消息*/
	public static final String CHAT_MESSAGE = "chat_message";

	/** 离线单聊消息*/
	public static final String OFFLINE_CHAT_EVENT = "offline_chat";

	/** 用户消息*/
	public static final String USER_MESSAGE = "user_message";

	/** 系统消息*/
	public static final String SYSTEM_MESSAGE = "system_message";



	//****************用户事件*************//

	/** 所有用户*/
	public static final String ALL_USER_LIST_EVENT = "user_list";

	/** 加载用户*/
	public static final String LOAD_USER_LIST = "load_user_list";

	/** 加载当前用户*/
	public static final String LOAD_USER= "load_current_user";


}
