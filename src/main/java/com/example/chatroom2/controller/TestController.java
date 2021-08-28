package com.example.chatroom2.controller;

import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.entity.GroupChat;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.service.GroupChatService;
import com.example.chatroom2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/1")
    public ResultVo test1(){
        User user = new User();
        user.setUsername("123");
        user.setPassword("123");
        userService.addUser(user);
        User user1 = userService.findUser("123");

        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName("我是第一个组");
        groupChatService.addGroupChat(groupChat);
        if(user.getGroupChatList() == null){
            List<GroupChat> groupChats = new ArrayList<>();
            groupChats.add(groupChat);
            user.setGroupChatList(groupChats);
        }else {
            user.getGroupChatList().add(groupChat);
        }
        userService.updateUser(user);

        return  ResultVo.ok();
    }

    @GetMapping("/2")
    public ResultVo test2(){
        User user = userService.findUser("123");
        userService.deleteUser(user);
        return  ResultVo.ok();
    }
}
