package com.example.chatroom2.service;

import com.example.chatroom2.entity.Message;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 5:28 PM
 */
@Service
public interface MessageService {

    void addMessage(Message message);

    List<Message> findAllMessageByGroupId(Integer groupId);

    List<Message> findAllMessageByUserId(Integer userId);
}
