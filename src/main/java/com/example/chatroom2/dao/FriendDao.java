package com.example.chatroom2.dao;

import com.example.chatroom2.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 12:20 PM
 */
public interface FriendDao extends JpaRepository<Friend,Integer> {
}
