package com.example.chatroom2.controller;

import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.entity.GroupChat;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.service.UserService;
import com.example.chatroom2.util.JwtInfo;
import com.example.chatroom2.util.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/23/2021 8:58 AM
 */
@Controller
public class ChatroomController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionContext sessionContext;

    @GetMapping("/chatroom")
    public String chatroom() {
        return "chatroom";
    }


    @GetMapping("/test")
    @ResponseBody
    public ResultVo test() {
        SecureRandom secureRandom = new SecureRandom();
        int i = secureRandom.nextInt(10000);
        System.out.println(i);

        User user = new User();
        user.setPassword("1234");
        user.setUsername("rrr");
        redisTemplate.opsForValue().set("test", user);

        return ResultVo.ok();
    }

    @GetMapping("/test1")
    @ResponseBody
    public ResultVo test1() {
        User user = (User) redisTemplate.opsForValue().get("test");

        return ResultVo.ok().data("user", user);
    }

    @GetMapping("/test2")
    @ResponseBody
    public ResultVo test2() {
        User user = userService.test();
        redisTemplate.opsForValue().set("index::user", user);

        return ResultVo.ok().data("user", user);
    }

    @GetMapping("/test3")
    @ResponseBody
    public ResultVo test3() {
        User user = userService.test();
        return ResultVo.ok().data("user", user);
    }

    @GetMapping("/test4")
    @ResponseBody
    public ResultVo test4() {
        JwtInfo jwtInfo = sessionContext.getInfo();
        return ResultVo.ok().data("jwtInfo", jwtInfo);
    }

    @GetMapping("/test/5")
    @ResponseBody
    public ResultVo test5() {
        User user = userService.findUser(1);
        GroupChat groupChat = new GroupChat();
        groupChat.setGroupName("我是第一个组");
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


}
