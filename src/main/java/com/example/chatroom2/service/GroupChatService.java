package com.example.chatroom2.service;

import com.example.chatroom2.entity.GroupChat;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 8:27 AM
 */
@Service
public interface GroupChatService {

    void addGroupChat(GroupChat groupChat);
    GroupChat findGroupChat(Integer id);
    void findGroupChat(String groupName);
}
