package org.zpp.springboot.socketio.mapper;

import org.zpp.springboot.socketio.model.ChatMessage;

import java.util.List;

/**
 * @author zpp
 * @date 2018/8/15 11:11
 */
public interface ChatMessageMapper {
    public int save(ChatMessage cm);

    public int update(ChatMessage cm);

    public List<ChatMessage> getList(ChatMessage cm);
}
