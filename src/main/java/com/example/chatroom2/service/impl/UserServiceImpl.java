package com.example.chatroom2.service.impl;

import com.example.chatroom2.dao.UserDao;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/23/2021 3:23 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    public void addUser(User user) {
        userDao.save(user);
    }

    public User findUser(Integer id) {
        return userDao.findUserById(id);
    }

    public User findUser(String username) {
        return userDao.findUserByUsername(username);
    }
}
