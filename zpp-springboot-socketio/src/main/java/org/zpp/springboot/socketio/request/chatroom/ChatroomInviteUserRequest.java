package org.zpp.springboot.socketio.request.chatroom;

import java.util.List;

/**
 * 邀请用户进入会议室
 * @author zpp
 * @date 2018/8/27 18:23
 */
public class ChatroomInviteUserRequest {

    /** 聊天室id*/
    private Integer roomId;

    /** 邀请的用户id*/
    private List<Integer> userIds;

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public List<Integer> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Integer> userIds) {
        this.userIds = userIds;
    }
}
