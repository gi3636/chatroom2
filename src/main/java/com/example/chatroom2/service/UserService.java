package com.example.chatroom2.service;

import com.example.chatroom2.entity.User;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/23/2021 3:15 PM
 */
@Service
public interface UserService {

    void addUser(User user);
    User findUser(Integer id);
    User findUser(String username);
    void updateUser(User user);
    void deleteUser(User user);
    User test();




}
