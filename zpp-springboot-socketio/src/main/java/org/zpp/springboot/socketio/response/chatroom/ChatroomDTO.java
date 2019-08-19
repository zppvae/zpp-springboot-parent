package org.zpp.springboot.socketio.response.chatroom;

import org.zpp.springboot.socketio.model.Chatroom;

/**
 * @author zpp
 * @date 2018/8/28 10:30
 */
public class ChatroomDTO {

    private Chatroom chatroom;

    public Chatroom getChatroom() {
        return chatroom;
    }

    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }
}
