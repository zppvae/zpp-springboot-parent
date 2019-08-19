package org.zpp.springboot.socketio.model;

import java.io.Serializable;

/**
 * 聊天室成员
 * @author zpp
 * @date 2018/8/24 14:32
 */
public class  ChatroomMember implements Serializable {
    private Integer roomId;

    private Integer userId;

    private String userName;

    private Long joinTime;

    public ChatroomMember(){}

    public ChatroomMember(Integer roomId,Integer userId){
        this.roomId = roomId;
        this.userId = userId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Long joinTime) {
        this.joinTime = joinTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
