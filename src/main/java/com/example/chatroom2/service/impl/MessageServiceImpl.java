package com.example.chatroom2.service.impl;

import com.example.chatroom2.dao.MessageDao;
import com.example.chatroom2.entity.Message;
import com.example.chatroom2.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 5:29 PM
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;

    @Override
    public void addMessage(Message message) {
        messageDao.save(message);
    }

    @Override
    public List<Message> findAllMessageByGroupId(Integer groupId) {
        return messageDao.findByGroupIdOrderByCreateTimeAsc(groupId);
    }

    @Override
    public List<Message> findAllMessageByUserId(Integer userId) {
        return messageDao.findByFromUserOrderByCreateTimeAsc(userId);
    }
}
