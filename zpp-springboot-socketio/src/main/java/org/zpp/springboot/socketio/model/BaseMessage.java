package org.zpp.springboot.socketio.model;

/**
 * 聊天消息传输对象
 */
public class BaseMessage {

    /** 消息类型  {@link org.zpp.springboot.socketio.common.enums.MessageTypeEnum}*/
    private Integer msgType;

    private Object data;

    public BaseMessage(){}

    public BaseMessage(Integer msgType){
        this.msgType = msgType;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
