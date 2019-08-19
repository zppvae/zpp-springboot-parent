package org.zpp.springboot.socketio.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 单聊消息
 * @author zpp
 * @date 2018/8/15 10:33
 */
public class ChatMessage {

    private Integer msgId;

    private Integer sendUserId;

    private Integer receiveUserId;

    private Long sendTime;

    /** {@link org.zpp.springboot.socketio.common.enums.ChatMessageStatusEnum}*/
    private int status;

    /** 消息内容*/
    private String content;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public Long getSendTime() {
        return sendTime;
    }

    public void setSendTime(Long sendTime) {
        this.sendTime = sendTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

