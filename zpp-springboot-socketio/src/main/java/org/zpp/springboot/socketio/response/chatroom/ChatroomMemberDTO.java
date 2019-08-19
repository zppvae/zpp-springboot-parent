package org.zpp.springboot.socketio.response.chatroom;

import org.zpp.springboot.socketio.model.ChatroomMember;

import java.util.List;

/**
 * 会议室成员
 * @author zpp
 * @date 2018/8/28 10:18
 */
public class ChatroomMemberDTO {
    /** 成员**/
    private List<ChatroomMember> members;

    public ChatroomMemberDTO(List<ChatroomMember> members){
        this.members = members;
    }

    public List<ChatroomMember> getMembers() {
        return members;
    }

    public void setMembers(List<ChatroomMember> members) {
        this.members = members;
    }
}
