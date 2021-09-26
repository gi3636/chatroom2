package com.example.chatroom2.controller;

import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.entity.GroupChat;
import com.example.chatroom2.entity.Message;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.service.GroupChatService;
import com.example.chatroom2.service.MessageService;
import com.example.chatroom2.service.UserService;
import com.example.chatroom2.util.JwtInfo;
import com.example.chatroom2.util.JwtUtils;
import com.example.chatroom2.util.SessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;
import java.util.Set;

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

    @Autowired
    private GroupChatService groupChatService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/chatroom")
    public String chatroom() {
        return "chatroom";
    }

    @GetMapping("/checkToken")
    @ResponseBody
    public ResultVo getUser(){
        JwtInfo jwtInfo = sessionContext.getJwtInfo();
        return ResultVo.ok().data("user",jwtInfo);
    }

    /**
     * 获取群聊天信息
     * @param groupId
     * @return
     */
    @GetMapping("/history/{groupId}")
    @ResponseBody
    public ResultVo getGroupChatHistory(@PathVariable Integer groupId){
        List<Message> messageList = messageService.findAllMessageByGroupId(groupId);
        return ResultVo.ok().data("messageList",messageList);
    }

    /**
     * 获取当天用户所有信息
     * @param request
     * @return
     */
    @GetMapping("/history/user")
    @ResponseBody
    public ResultVo getAllChatHistory( HttpServletRequest request){
        Integer userId = JwtUtils.getIdByJwtToken(request);
        List<Message> messageList = messageService.findAllMessageByUserId(userId);
        return ResultVo.ok().data("messageList",messageList);
    }

    @GetMapping("/getGroupUserList/{groupId}")
    @ResponseBody
    public ResultVo getGroupUserList(@PathVariable Integer groupId){
        GroupChat groupChat = groupChatService.findGroupChat(groupId);
        Set<User> userList = groupChat.getUsers();
        return ResultVo.ok().data("userList",userList);
    }











}
