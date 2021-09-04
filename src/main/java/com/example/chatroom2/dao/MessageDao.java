package com.example.chatroom2.dao;

import com.example.chatroom2.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 5:11 PM
 */
@Repository
public interface MessageDao extends JpaRepository<Message,Long> {

    List<Message> findByGroupIdOrderByCreateTimeAsc(Integer groupId);


    List<Message> findByFromUserOrderByCreateTimeAsc(Integer userId);
}
