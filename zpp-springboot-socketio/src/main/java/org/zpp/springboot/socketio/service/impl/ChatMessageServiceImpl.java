package org.zpp.springboot.socketio.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zpp.springboot.socketio.mapper.ChatMessageMapper;
import org.zpp.springboot.socketio.model.ChatMessage;
import org.zpp.springboot.socketio.service.ChatMessageService;

import java.util.List;

/**
 * @author zpp
 * @date 2018/8/15 11:10
 */
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatMessageMapper chatMessageMapper;

    @Transactional
    @Override
    public int save(ChatMessage cm) {
        return chatMessageMapper.save(cm);
    }

    @Transactional
    @Override
    public int update(ChatMessage cm) {
        return chatMessageMapper.update(cm);
    }

    @Override
    public List<ChatMessage> getList(ChatMessage cm) {
        return chatMessageMapper.getList(cm);
    }
}
