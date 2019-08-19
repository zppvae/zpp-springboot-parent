package org.zpp.springboot.socketio.model;

import java.time.LocalDate;

/**
 * 聊天室
 * @author zpp
 * @date 2018/8/24 14:26
 */
public class Chatroom {

    private Integer roomId;

    private String roomName;

    private Long createTime;

    /** 创建者*/
    private Integer createUserId;

    /** 聊天室人数*/
    private Integer memberCount;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Integer memberCount) {
        this.memberCount = memberCount;
    }
}
