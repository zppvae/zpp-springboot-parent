package org.zpp.springboot.socketio.common.enums;

/**
 * 消息类型枚举
 */
public enum MessageTypeEnum {

    LOAD_USER_LIST(100,"加载用户列表"),

    CHAT_MESSAGE(200,"单聊消息"),

    OFFLINE_CHAT_MESSAGE(300,"离线单聊消息"),

    CHATROOM_LIST(400,"加载聊天室列表"),

    ;

    private Integer msgType;

    private String msgDesc;

    MessageTypeEnum(Integer msgType,String msgDesc){
        this.msgType = msgType;
        this.msgDesc = msgDesc;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public String getMsgDesc() {
        return msgDesc;
    }

}
