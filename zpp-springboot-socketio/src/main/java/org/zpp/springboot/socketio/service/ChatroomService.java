package org.zpp.springboot.socketio.service;

import org.zpp.springboot.socketio.model.Chatroom;
import org.zpp.springboot.socketio.model.ChatroomMember;
import org.zpp.springboot.socketio.response.chatroom.ChatroomDTO;
import org.zpp.springboot.socketio.response.chatroom.ChatroomMemberDTO;

import java.util.List;

/**
 * @author zpp
 * @date 2018/8/24 14:34
 */
public interface ChatroomService {

    public int save(Chatroom room);

    public int update(Chatroom room);

    public int delete(Chatroom room);

    public List<Chatroom> getList(Chatroom room);

    /**
     * 查询用户所在的聊天室
     * @param userId
     * @return
     */
    public List<Chatroom> getListByUserId(Integer userId);

    /**
     * 添加成员到聊天室
     * @param userIds
     * @return
     */
    public int addMembers(Integer roomId,List<Integer> userIds);

    /**
     * 移除成员
     * @param member
     * @return
     */
    public int removeMember(ChatroomMember member);

    /**
     * 会议室成员
     * @param roomId
     * @return
     */
    public ChatroomMemberDTO getChatroomMembersByRoomId(Integer roomId);

    public ChatroomDTO getChatroomByRoomId(Integer roomId);
}
