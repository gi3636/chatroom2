package com.example.chatroom2.controller;

import com.example.chatroom2.common.GlobalException.GlobalException;
import com.example.chatroom2.common.ResultCode.ResultCode;
import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.entity.GroupChat;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.model.form.RegisterForm;
import com.example.chatroom2.service.GroupChatService;
import com.example.chatroom2.service.UserService;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 注册管理
 * @author: fenggi123
 * @create: 8/23/2021 3:29 PM
 */
@Api(tags = "注册管理")
@Controller
public class RegisterController {

    @Autowired
    UserService userService;

    @Autowired
    GroupChatService groupChatService;

    //跳转页面
    @GetMapping("/register")
    public String register() {
        return "register";
    }


    @PostMapping(value = "/register")
    @ResponseBody
    public ResultVo registerCheck(@RequestBody RegisterForm registerForm) {
        //获取数据
        String username = registerForm.getUsername();
        String password = registerForm.getPassword();
        String secondPassword = registerForm.getSecondPassword();
        String code = registerForm.getCode();
        System.out.println(registerForm);

        //非空判断
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(secondPassword) ||
                StringUtils.isBlank(code)
        ) {
            throw GlobalException.from(ResultCode.PARAMS_ERROR);
        }

        //确认密码判断
        if (!password.equals(secondPassword)) {
            throw GlobalException.from(ResultCode.PASSWORD_NOT_SAME);
        }
        //已被注册
        if (userService.findUser(username) != null) {
            throw GlobalException.from(ResultCode.ACCOUNT_EXIST);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        GroupChat groupChat = groupChatService.findGroupChat(1);
        if (groupChat != null){
            user.getGroupChatList().add(groupChat);
        }else{
            groupChat = new GroupChat();
            groupChat.setGroupName("第一个群");
            groupChat.setDescription("这是公共群好好珍惜");
            groupChat.setManager(user);
            user.getGroupChatList().add(groupChat);
        }
        userService.addUser(user);
        return new ResultVo(ResultCode.REGISTER_SUCCESS);
    }

}
