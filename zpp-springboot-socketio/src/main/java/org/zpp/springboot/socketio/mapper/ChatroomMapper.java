package org.zpp.springboot.socketio.mapper;

import org.zpp.springboot.socketio.model.ChatMessage;
import org.zpp.springboot.socketio.model.Chatroom;
import org.zpp.springboot.socketio.model.ChatroomMember;

import java.util.List;

/**
 * @author zpp
 * @date 2018/8/15 11:11
 */
public interface ChatroomMapper {
    public int save(Chatroom room);

    public int update(Chatroom room);

    public List<Chatroom> getList(Chatroom room);

    /**
     * 查询用户所在的聊天室
     * @param userId
     * @return
     */
    public List<Chatroom> getListByUserId(Integer userId);

    /**
     * 添加成员到聊天室
     * @param members
     * @return
     */
    public int addMembers(List<ChatroomMember> members);

    /**
     * 移除成员
     * @param member
     * @return
     */
    public int removeMember(ChatroomMember member);

    public List<ChatroomMember> getListByRoomId(Integer roomId);
}
