package com.example.chatroom2.service.impl;

import com.example.chatroom2.dao.UserDao;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    public void addUser(User user) {
        userDao.save(user);
    }

    @Override
    public User findUser(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public User findUser(String username) {
        return userDao.findUserByUsername(username);
    }

    @Override
    public void updateUser(User user) {
        userDao.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }


    /**
     * @param
     * @return
     * @Cacheable(value = "xxx", key = "'xxx'")：标注在方法上，对方法返回结果进行缓存。
     * 下次请求时，如果缓存存在，则直接读取缓存数据返回；
     * 如果缓存不存在，则执行方法，并把返回的结果存入缓存中。一般用在查询方法上。
     */
    @Cacheable(value = "index", key = "'test'")
    public User test() {
        return userDao.findUserById(1);
    }
}
