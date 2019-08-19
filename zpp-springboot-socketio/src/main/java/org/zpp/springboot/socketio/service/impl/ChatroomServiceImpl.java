package org.zpp.springboot.socketio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zpp.springboot.socketio.mapper.ChatroomMapper;
import org.zpp.springboot.socketio.model.Chatroom;
import org.zpp.springboot.socketio.model.ChatroomMember;
import org.zpp.springboot.socketio.response.chatroom.ChatroomDTO;
import org.zpp.springboot.socketio.response.chatroom.ChatroomMemberDTO;
import org.zpp.springboot.socketio.service.ChatroomService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zpp
 * @date 2018/8/24 14:38
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatroomServiceImpl implements ChatroomService {

    @Autowired
    private ChatroomMapper chatroomMapper;

    @Override
    public int save(Chatroom room) {
        chatroomMapper.save(room);

        List<ChatroomMember> members = new ArrayList<>();
        ChatroomMember member = new ChatroomMember(room.getRoomId(),room.getCreateUserId());
        members.add(member);
        chatroomMapper.addMembers(members);
        return room.getRoomId();
    }

    @Override
    public int update(Chatroom room) {
        return chatroomMapper.update(room);
    }

    @Override
    public int delete(Chatroom room) {
        return 0;
    }

    @Override
    public List<Chatroom> getList(Chatroom room) {
        return chatroomMapper.getList(room);
    }

    @Override
    public int addMembers(Integer roomId,List<Integer> userIds) {
        int size = userIds.size();
        List<ChatroomMember> members = new ArrayList<>(size);
        ChatroomMember member = null;

        for (int i = 0; i < size; i++) {
            member = new ChatroomMember();
            member.setRoomId(roomId);
            member.setUserId(userIds.get(i));
            members.add(member);
        }

        return chatroomMapper.addMembers(members);
    }

    @Override
    public int removeMember(ChatroomMember member) {
        return chatroomMapper.removeMember(member);
    }

    @Override
    public List<Chatroom> getListByUserId(Integer userId) {
        List<Chatroom> chatrooms = chatroomMapper.getListByUserId(userId);
        if (chatrooms != null) {
            int size = chatrooms.size();
            for (int i = 0; i < size; i++) {
                Chatroom chatroom = chatrooms.get(i);
                int roomId = chatroom.getRoomId();
                chatroom.setMemberCount(chatroomMapper.getListByRoomId(roomId).size());
            }
        }
        return chatrooms;
    }

    @Override
    public ChatroomMemberDTO getChatroomMembersByRoomId(Integer roomId) {
        List<ChatroomMember> members = chatroomMapper.getListByRoomId(roomId);
        return new ChatroomMemberDTO(members);
    }

    @Override
    public ChatroomDTO getChatroomByRoomId(Integer roomId) {
        Chatroom room = new Chatroom();
        room.setRoomId(roomId);
        List<Chatroom> chatrooms = chatroomMapper.getList(room);
        List<ChatroomMember> members = chatroomMapper.getListByRoomId(roomId);
        ChatroomDTO dto = new ChatroomDTO();
        Chatroom chatroom = chatrooms.get(0);
        chatroom.setMemberCount(members.size());
        dto.setChatroom(chatroom);
        return dto;
    }
}
