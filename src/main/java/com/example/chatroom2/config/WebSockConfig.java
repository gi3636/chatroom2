package com.example.chatroom2.config;

import com.example.chatroom2.entity.Message;
import com.example.chatroom2.service.GroupChatService;
import com.example.chatroom2.service.MessageService;
import com.example.chatroom2.service.UserService;
import com.example.chatroom2.util.RedisUtils;
import com.example.chatroom2.util.SessionContext;
import com.example.chatroom2.websocket.ChatEndPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/29/2021 8:06 AM
 */
@Configuration
public class WebSockConfig {

    @Bean
    //注入ServerEndPointExporter bean对象,自动注册使用了@ServerEndPoint注解的bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Autowired
    public void redisUtils(RedisUtils redisUtils){
        ChatEndPoint.redisUtils = redisUtils;
    }

    //注入service,如果没有配置，ChatEndPoint 没有办法使用这些Service
    @Autowired
    public void userService(UserService userService){
        ChatEndPoint.userService = userService;
    }

    @Autowired
    public void groupChatService(GroupChatService groupChatService){
        ChatEndPoint.groupChatService = groupChatService;
    }

    @Autowired
    public void messageService(MessageService messageService){
        ChatEndPoint.messageService = messageService;
    }
}
