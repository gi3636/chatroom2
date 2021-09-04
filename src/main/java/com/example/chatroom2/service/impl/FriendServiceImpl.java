package com.example.chatroom2.service.impl;

import com.example.chatroom2.dao.FriendDao;
import com.example.chatroom2.entity.Friend;
import com.example.chatroom2.service.FriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 12:22 PM
 */
@Service
public class FriendServiceImpl implements FriendService {

    @Autowired
    FriendDao friendDao;


    @Override
    public void addFriend(Friend friend) {
        friendDao.save(friend);
    }
}
