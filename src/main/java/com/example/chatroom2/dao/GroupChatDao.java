package com.example.chatroom2.dao;

import com.example.chatroom2.entity.GroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 8:26 AM
 */
@Repository
public interface GroupChatDao extends JpaRepository<GroupChat,Integer> {


}
