package com.example.chatroom2.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.chatroom2.config.GetHttpSessionConfigurator;
import com.example.chatroom2.constant.MessageType;
import com.example.chatroom2.entity.GroupChat;
import com.example.chatroom2.entity.Message;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.service.FriendService;
import com.example.chatroom2.service.GroupChatService;
import com.example.chatroom2.service.MessageService;
import com.example.chatroom2.service.UserService;
import com.example.chatroom2.util.JwtInfo;
import com.example.chatroom2.util.RedisKeyEnum;
import com.example.chatroom2.util.RedisUtils;
import com.example.chatroom2.util.SessionContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description:
 * @author: fenggi123
 * @create: 8/28/2021 5:45 PM
 */
@ServerEndpoint(value = "/websocket/{userId}",configurator = GetHttpSessionConfigurator.class)
@Component
//每个客户端都会有一个ChatEndPoint，通过session 发送数据
public class ChatEndPoint {
    //用来存储每一个客户端对象对应的ChatEndPoint对象
    //考虑到线程安全使用ConcurrentHashMap #并发编程
    private static Map<Integer,ChatEndPoint> onlineUsers = new ConcurrentHashMap<>();

    //通过session 对象，发送消息给指定的用户
    private Session session;

    //通过session 对象，发送消息给指定的用户
    private HttpSession httpSession;


    //存储token信息
    private JwtInfo jwtInfo;
    public static UserService userService;
    public static GroupChatService groupChatService;
    public static FriendService friendService;
    public static MessageService messageService;

    public static SessionContext sessionContext;
    public static RedisUtils redisUtils;



    /**
     * 建立时调用
     * @param session
     * @param config
     */
    @OnOpen
    public void onOpen(@PathParam("userId") Integer userId, Session session , EndpointConfig config){
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        this.session = session;
        Object obj =  redisUtils.get(RedisKeyEnum.OAUTH_APP_TOKEN.keyBuilder(String.valueOf(userId)));
        jwtInfo = JSONObject.parseObject(obj.toString(),JwtInfo.class);
        System.out.println(jwtInfo);


        //存放进去在线用户
        onlineUsers.put(userId,this);
        for (Map.Entry<Integer,ChatEndPoint> entry : onlineUsers.entrySet()){
            System.out.println("key ="+entry.getKey());
            System.out.println("value ="+entry.getValue());
        }

        //发送信息
        Message message = new Message();
        message.setChatType(MessageType.CHAT_GROUP);
        message.setContent(jwtInfo.getUsername()+"已上线");
        message.setFromUser(jwtInfo.getId());
        message.setMsgType(MessageType.MSG_NOTICE);
        message.setSendTo(1);
        message.setGroupId(1);
        message.setCreateTime(new Date());
        sendGroupMessage(message);
        message.setMsgType(MessageType.MSG_TEXT);
        sendGroupMessage(message);
        saveMessage(message);
//        List<Message> messageList = groupChat.getMessageList();
//        for (Message message1 : messageList){
//            System.out.println(message1);
//        }
        List<Message> messageList = messageService.findAllMessageByGroupId(1);
        for (Message message1 : messageList){
            System.out.println(message1);
        }

    }




    /**
     * 接受信息时调用
     * @param msg
     */
    @OnMessage
    public void onMessage(String msg){
        Message message = JSON.parseObject(msg,Message.class);
        message.setCreateTime(new Date());
        System.out.println(message);
        //私聊
        if(message.getChatType().equals(MessageType.CHAT_SINGLE)){
            sendSingleMessage(message);
        }
        //群聊
        if(message.getChatType().equals(MessageType.CHAT_GROUP)){
            sendGroupMessage(message);
        }

    }

    /**
     * 结束是调用
     * @param session
     */
    @OnClose
    public void onClose(Session session){

    }

    public void saveMessage(Message message){
        messageService.addMessage(message);
    }

    public void sendSingleMessage(Message message){
        sendMessage(message);
    }

    public void sendGroupMessage(Message message){
        GroupChat groupChat = groupChatService.findGroupChat(message.getGroupId());
        System.out.println("groupChat 查询："+groupChat);
        Set<User> users = groupChat.getUsers();
        for (User user : users){
//            if(user.getId() == jwtInfo.getId()){
//                continue;
//            }
            System.out.println(user.getId());
            //发送信息
            message.setSendTo(user.getId());
            sendMessage(message);
        }
    }


    public void sendMessage(Message message){
        String msg = JSON.toJSONString(message);
        System.out.println("发送消息："+msg);
        //如果接收者不在线就不发去前端
        if(onlineUsers.get(message.getSendTo()) != null){
            ChatEndPoint chatEndPoint = onlineUsers.get(message.getSendTo());
            chatEndPoint.session.getAsyncRemote().sendText(msg);
        }
    }





}

