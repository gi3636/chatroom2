package com.example.chatroom2.service;

import com.example.chatroom2.entity.Friend;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 12:21 PM
 */
@Service
public interface FriendService {

    void addFriend(Friend friend);

}
