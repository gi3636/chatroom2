package com.example.chatroom2.controller;

import com.example.chatroom2.common.ResultVo;
import com.example.chatroom2.entity.User;
import com.example.chatroom2.model.vo.UserVo;
import com.example.chatroom2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: fenggi123
 * @create: 9/1/2021 10:20 PM
 */
@Controller
//@RequestMapping("/user")
public class UserController {


    @Autowired
    UserService userService;

    @GetMapping("/getAvatar/{userId}")
    @ResponseBody
    public String getAvatar(@PathVariable Integer userId){
        User user = userService.findUser(userId);
        System.out.println("查询头像："+user);
        return user.getAvatar();
    }

    @GetMapping("/getUserInfo/{userId}")
    @ResponseBody
    public ResultVo getUserInfo (@PathVariable Integer userId){
        User user = userService.findUserInfo(userId);
        UserVo userVo = new UserVo(user.getId(),user.getUsername(),user.getAvatar());
        return ResultVo.ok().data("userVo",userVo);
    }

}
