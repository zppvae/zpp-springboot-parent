package org.zpp.springboot.socketio.service;

import org.zpp.springboot.socketio.model.ChatMessage;

import java.util.List;

/**
 * @author zpp
 * @date 2018/8/15 11:05
 */
public interface ChatMessageService {

    public int save(ChatMessage cm);

    public int update(ChatMessage cm);

    public List<ChatMessage> getList(ChatMessage cm);
}
