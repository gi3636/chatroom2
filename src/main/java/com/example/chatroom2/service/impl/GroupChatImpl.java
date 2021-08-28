package com.example.chatroom2.service.impl;

import com.example.chatroom2.dao.GroupChatDao;
import com.example.chatroom2.entity.GroupChat;
import com.example.chatroom2.service.GroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 8:29 AM
 */
@Service
public class GroupChatImpl implements GroupChatService {
    @Autowired
    GroupChatDao groupChatDao;

    @Override
    public void addGroupChat(GroupChat groupChat) {
        groupChatDao.save(groupChat);
    }

    @Override
    public GroupChat findGroupChat(Integer id) {
       return null;
    }

    @Override
    public void findGroupChat(String groupName) {

    }
}
