package com.example.chatroom2.controller;

import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.entity.Friend;
import com.example.chatroom2.entity.GroupChat;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.model.vo.UserVo;
import com.example.chatroom2.service.FriendService;
import com.example.chatroom2.service.GroupChatService;
import com.example.chatroom2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 8:17 AM
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    UserService userService;
    @Autowired
    GroupChatService groupChatService;

    @Autowired
    FriendService friendService;

    @GetMapping("/1")
    public ResultVo test1() {
        User user = new User();
        user.setUsername("123");
        user.setPassword("123");
        userService.addUser(user);
        User user1 = userService.findUser("123");

        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName("我是第一个组");
        groupChatService.addGroupChat(groupChat);
        if (user.getGroupChatList() == null) {
            Set<GroupChat> groupChats = new HashSet<>();
            groupChats.add(groupChat);
            user.setGroupChatList(groupChats);
        } else {
            user.getGroupChatList().add(groupChat);
        }
        userService.updateUser(user);

        return ResultVo.ok();
    }

    @GetMapping("/2")
    public ResultVo test2() {
        User user = userService.findUser("123");
        userService.deleteUser(user);
        return ResultVo.ok();
    }

    @GetMapping("/3")
    public ResultVo test3() {
        User user1 = userService.findUser("fenggi");
        if (user1 == null) {
            user1 = new User();
            user1.setUsername("fenggi");
            user1.setPassword("fenggi");
            userService.addUser(user1);
        }

        User user2 = userService.findUser("suxin");
        if (user2 == null) {
            user2 = new User();
            user2.setUsername("suxin");
            user2.setPassword("suxin");
            userService.addUser(user2);
        }
        User user3 = userService.findUser("方法");
        if (user3 == null) {
            user3 = new User();
            user3.setUsername("方法");
            user3.setPassword("方法");
            userService.addUser(user3);
        }
        User user4 = userService.findUser("测试");
        if (user4 == null) {
            user4 = new User();
            user4.setUsername("测试");
            user4.setPassword("测试");
            userService.addUser(user4);
        }
        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);
        Friend friend1 = new Friend();
        friend1.setUser(user1);
        friend1.setFriend(user3);
        Friend friend2 = new Friend();
        friend2.setUser(user1);
        friend2.setFriend(user4);

        if (user1.getFriendList() == null) {
            //没有朋友
            Set<Friend> friends = new HashSet<>();
            friends.add(friend);
            friends.add(friend1);
            friends.add(friend2);
            user1.setFriendList(friends);
        } else {
            user1.getFriendList().add(friend);
        }

        userService.updateUser(user1);


        return ResultVo.ok();
    }

    @GetMapping("/4")
    public ResultVo getFriend() {
        User user = userService.findUser("fenggi");
        Set<Friend> list = user.getFriendList();
        for (Friend friend : list) {
            System.out.println(friend);
        }
        return null;
    }

    @GetMapping("/5")
    public ResultVo test5() {
        User user = userService.findUserInfo(1);
        return ResultVo.ok().data("userVo", user);
    }

    @GetMapping("/6")
    public ResultVo test6() {
        Integer temp = 3;
        temp = temp / 2;
        System.out.println(temp);
        return ResultVo.ok();
    }

    
}
